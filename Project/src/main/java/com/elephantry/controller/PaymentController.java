package com.elephantry.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.elephantry.entity.Customer;
import com.elephantry.entity.Package;
import com.elephantry.entity.PaymentHistory;
import com.elephantry.entity.PaymentMethod;
import com.elephantry.model.PaypalConfig;
import com.elephantry.service.CountryService;
import com.elephantry.service.CustomerService;
import com.elephantry.service.PackageService;
import com.elephantry.service.PaymentHistoryService;
import com.elephantry.service.PaymentMethodService;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;

import javassist.expr.NewArray;

@Controller
@RequestMapping(value = { "/payment", "/{lang}/payment" })
public class PaymentController {

	private PaypalConfig paypalConfig;
	private CustomerService customerService;
	private PaymentHistoryService paymentHistoryService;
	private PaymentMethodService paymentMethodService;
	private PackageService packageService;
	
	@Autowired
	public void setPackageService(PackageService packageService) {
		this.packageService = packageService;
	}
	
	@Autowired
	public void setPaymentMethodService(PaymentMethodService paymentMethodService) {
		this.paymentMethodService = paymentMethodService;
	}
	
	@Autowired
	public void setPaymentHistoryService(PaymentHistoryService paymentHistoryService) {
		this.paymentHistoryService = paymentHistoryService;
	}
	
	@Autowired
	public void setPaypalConfig(PaypalConfig paypalConfig) {
		this.paypalConfig = paypalConfig;
	}
	
	@Autowired
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	@RequestMapping(value = "/paypal", method = RequestMethod.GET)
	public String paypal() {

		return "paypal";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@RequestParam("timePackage") String timePackage, @RequestParam("totalPayment") String totalPayment) {
		System.out.println(timePackage + "time");
		System.out.println(totalPayment + "total");
		String approval_url = "";
		try {
			APIContext apiContext = new APIContext(paypalConfig.getClientID(),
					paypalConfig.getClientSecret(), paypalConfig.getMode());
			
			Amount amount = new Amount();
			amount.setCurrency(paypalConfig.getCurrency());
			amount.setTotal(totalPayment);

			Transaction transaction = new Transaction();
			transaction.setDescription("Purchase elephantry package");
			transaction.setAmount(amount);
			transaction.setCustom(timePackage);
			
			List<Transaction> transactions = new ArrayList<Transaction>();
			transactions.add(transaction);

			Payer payer = new Payer();
			payer.setPaymentMethod("paypal");

			Payment payment = new Payment();
			payment.setIntent("sale");
			payment.setPayer(payer);
			payment.setTransactions(transactions);
			RedirectUrls redirectUrls = new RedirectUrls();
			redirectUrls.setCancelUrl(paypalConfig.getCancelUrl());
			redirectUrls.setReturnUrl(paypalConfig.getReturnUrl());
			payment.setRedirectUrls(redirectUrls);

			Payment createdPayment = payment.create(apiContext);
			List<Links> links = createdPayment.getLinks();
			approval_url = "";
			for(Links link : links){
				if ("approval_url".equals(link.getRel())) {
					approval_url = link.getHref();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/?paymentfailed";
		}
		return "redirect:" + approval_url;
	}
	
	@RequestMapping(value = "/confirm", method = RequestMethod.GET)
	public String confirm(@RequestParam("paymentId") String paymentId,
						  @RequestParam("token") String token,
						  @RequestParam("PayerID") String payerID,
						  Model model, HttpSession httpSession,
						  Principal principal) {
		try {
			APIContext apiContext = new APIContext(paypalConfig.getClientID(),
					paypalConfig.getClientSecret(), paypalConfig.getMode());
			Payment payment = Payment.get(apiContext, paymentId) ;
			
			System.out.println(payment.toJSON());
			model.addAttribute("payperEmail", payment.getPayer().getPayerInfo().getEmail());
			model.addAttribute("payperFisrtName", payment.getPayer().getPayerInfo().getFirstName());
			model.addAttribute("payperLastName", payment.getPayer().getPayerInfo().getLastName());
//			Customer customer = customerService.findByEmail(principal.getName());
			if(httpSession.getAttribute("packageSelected") != null){
				Package package1 = packageService.findById( Integer.parseInt(httpSession.getAttribute("packageSelected").toString()));
				model.addAttribute("lbProduct", package1.getPackageName());
				model.addAttribute("lbPrice", package1.getPrice());
				model.addAttribute("lbTotallabel", Integer.parseInt(payment.getTransactions().get(0).getCustom()) * package1.getPrice());
			}else{
				model.addAttribute("lbProduct", "");
				model.addAttribute("lbPrice", "");
				model.addAttribute("lbTotallabel", "");
			}
			model.addAttribute("lbTime", Integer.parseInt(payment.getTransactions().get(0).getCustom()));
			model.addAttribute("paymentId", paymentId);
			model.addAttribute("token", token);
			model.addAttribute("payerID", payerID);
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/?paymentfailed";
		}
		
	
		return "paypalConfirm";
	}
	
	@RequestMapping(value = "/paypalExecute", method = RequestMethod.POST)
	public String paypalExecute(@RequestParam("paymentId") String paymentId,
						  @RequestParam("token") String token,
						  @RequestParam("payerID") String payerID, HttpSession httpSession,
						  Principal principal) {
		try {
			APIContext apiContext = new APIContext(paypalConfig.getClientID(),
					paypalConfig.getClientSecret(), paypalConfig.getMode());
			Payment getPayment = Payment.get(apiContext, paymentId) ;
			Payment payment = new Payment();
			payment.setId(paymentId);
			PaymentExecution paymentExecute = new PaymentExecution();
			paymentExecute.setPayerId(payerID);
			Payment returnedPayment = payment.execute(apiContext, paymentExecute);
			PaymentHistory paymentHistory = new PaymentHistory();
			paymentHistory.setAmount( Double.parseDouble( returnedPayment.getTransactions().get(0).getAmount().getTotal()));
			System.out.println("time" +getPayment.getTransactions().get(0).getCustom()); 
			System.out.println("total" + returnedPayment.getTransactions().get(0).getAmount().getTotal()); 
			int time = Integer.parseInt(getPayment.getTransactions().get(0).getCustom());
			Customer customer = customerService.findByEmail(principal.getName());
			Calendar cal = Calendar.getInstance(); 
			if(customer.getExpiredCredit() != null){
				cal.setTime(customer.getExpiredCredit());
				System.out.println(cal.getTime());
			}
			cal.add(Calendar.MONTH, time);
			customer.setExpiredCredit(cal.getTime());
			PaymentMethod paymentMethod = paymentMethodService.findById(1);
			paymentHistory.setPaymentMethod(paymentMethod);
			paymentHistory.setCustomerId(customer.getCustomerId());
			if(httpSession.getAttribute("packageSelected") != null){
				Package package1 = packageService.findById(Integer.parseInt(httpSession.getAttribute("packageSelected").toString()));
				customer.setPackage1(package1);
			}
			
			customerService.update(customer);
			paymentHistory.setTransactionId(paymentId);
			paymentHistoryService.save(paymentHistory);
			System.out.println(returnedPayment.toJSON());
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/?paymentfailed";
		}
		return "redirect:/payment/paymentSuccess";
	}
	
	

	@RequestMapping(value = "/paymentSuccess", method = RequestMethod.GET)
	public String paypalSuccess() {
		
		return "paymentSuccess";
	}

	@RequestMapping(value = "/paypalCancel", method = RequestMethod.GET)
	public String paypalCancel() {

		return "/";
	}

	

	
}
