package com.elephantry.controller.portal;

import static org.mockito.Matchers.intThat;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.elephantry.entity.Customer;
import com.elephantry.entity.Package;
import com.elephantry.entity.PaymentHistory;
import com.elephantry.entity.Province;
import com.elephantry.entity.Recommendation;
import com.elephantry.service.CountryService;
import com.elephantry.service.CustomerService;
import com.elephantry.service.PackageService;
import com.elephantry.service.PaymentHistoryService;
import com.elephantry.service.ProvinceService;
import com.elephantry.service.RecommendationService;
import com.elephantry.util.LanguageResolver;
import com.elephantry.util.MailHelper;
import com.sun.el.parser.ParseException;

@Controller
@RequestMapping(value = { "/{lang}/portal/account", "/portal/account" })
public class PortalAccountController {

	private CustomerService customerService;
	private ProvinceService provinceService;
	private PackageService packageService;
	private CountryService countryService;
	private PaymentHistoryService paymentHistoryService;

	@Autowired
	public void setPaymentHistoryService(PaymentHistoryService paymentHistoryService) {
		this.paymentHistoryService = paymentHistoryService;
	}

	@Autowired
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	@Autowired
	public void setPackageService(PackageService packageService) {
		this.packageService = packageService;
	}

	@Autowired
	public void setProvinceService(ProvinceService provinceService) {
		this.provinceService = provinceService;
	}

	@Autowired
	public void setCountryService(CountryService countryService) {
		this.countryService = countryService;
	}

	@ResponseBody
	@RequestMapping(value = "/updateSession", method = RequestMethod.GET)
	public String updateSession(@RequestParam int packageId, Principal principal, HttpSession httpSession) {
		Customer c = customerService.findByEmail(principal.getName());
		JSONObject res = new JSONObject();
		if (c != null) {
			// c.setPackage1(packageService.findById(packageId));
			// customerService.update(c);
//			if (httpSession.getAttribute("packageSelected") != null) {
				httpSession.setAttribute("packageSelected", packageId);
				try {
					res.put("packageSelected", packageId);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//			}

		}

		return res.toString();
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String profile(@PathVariable("lang") Optional<String> lang, Model model, Principal principal) {
		String language = LanguageResolver.resolve(lang, model);
		Customer customer = customerService.findByEmail(principal.getName());
		Province province = customer.getProvince();
		model.addAttribute("customer", customer);
		model.addAttribute("email", customer.getUser().getEmail());
		model.addAttribute("province", province);
		model.addAttribute("provinces", provinceService.findByCountryId(province.getCountry().getCountryId()));
		model.addAttribute("countries", countryService.findAll());
		model.addAttribute("packageName", customer.getPackage1().getPackageName());
		return "profile";
	}

	@ResponseBody
	@RequestMapping(value = "/saveProfile", method = RequestMethod.POST)
	public String saveProfile(@RequestParam int customerId, @RequestParam String firstName,
			@RequestParam String lastName, @RequestParam String dateOfBirth, @RequestParam String website,
			@RequestParam int provinceId, @RequestParam String company, @RequestParam String phone,
			@RequestParam boolean gender, @RequestParam String address) {
		JSONObject res = new JSONObject();
		Customer customer = customerService.getCustomerById(customerId);
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setGender((gender == true) ? "male" : "famale");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			if (!"".equals(dateOfBirth)) {
				Date dob = formatter.parse(dateOfBirth);
				customer.setDoB(dob);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		customer.setWebsite(website);
		customer.setCompany(company);
		customer.setPhone(phone);
		Province province = provinceService.findById(provinceId);
		customer.setProvince(province);
		customer.setAddress(address);
		customerService.update(customer);
		try {
			res.put("noty", true);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res.toString();
	}

	public long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();
		return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}

	@RequestMapping(value = "/packageDetail", method = RequestMethod.GET)
	public String packageDetail(@PathVariable("lang") Optional<String> lang, Model model, Principal principal,
			@RequestParam(value = "ss", required = false) String ss) {
		String language = LanguageResolver.resolve(lang, model);
		Customer customer = customerService.findByEmail(principal.getName());
		List<PaymentHistory> ls = paymentHistoryService.findbyCustomer(customer.getCustomerId());
		if (ss != null) {
			model.addAttribute("check", "1");
		}
		double totalAmount = 0;
		if (ls.size() > 0) {
			for (int i = 0; i < ls.size(); i++) {
				totalAmount = totalAmount + ls.get(i).getAmount();
			}
			if (totalAmount > 0) {
				model.addAttribute("amount", totalAmount + "$");
			} else
				model.addAttribute("amount", "");
			PaymentHistory paymentHistory = ls.get(0);
			Calendar cal = Calendar.getInstance();
			double dayPayment = getDateDiff(paymentHistory.getCreatedTime(), customer.getExpiredCredit(),
					TimeUnit.MILLISECONDS) / (1000 * 60 * 60 * 24.0);
			double dayExpiredCredit = getDateDiff(cal.getTime(), customer.getExpiredCredit(), TimeUnit.MILLISECONDS)
					/ (1000 * 60 * 60 * 24.0);
			dayPayment = (int) Math.round(dayPayment);
			dayExpiredCredit = Math.round(dayExpiredCredit);
			int a = (int) Math.round(dayExpiredCredit);
			double creditRemaining = (totalAmount / dayPayment) * dayExpiredCredit;
			creditRemaining = Math.round(creditRemaining * 100);
			creditRemaining = creditRemaining / 100;
			model.addAttribute("creditRemaining", creditRemaining + "$");
			model.addAttribute("dayExpiredCredit", a + " days");
			if (customer.getPackage1().getPackageId() == 2) {
				double daysRemainingTranfer;
				daysRemainingTranfer = Math.round(creditRemaining / 200 * 30);
				int daysRemainingTranferInt = (int) daysRemainingTranfer;
				model.addAttribute("daysRemainingTranfer", daysRemainingTranferInt);
			} else {
				model.addAttribute("daysRemainingTranfer", "");
			}
		} else {

			model.addAttribute("dayPayment", "");
			model.addAttribute("dayExpiredCredit", "");
		}

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		if (customer.getExpiredCredit() != null) {
			model.addAttribute("expiredCredit", formatter.format(customer.getExpiredCredit()));
		} else {
			model.addAttribute("expiredCredit", "");

		}
		model.addAttribute("customer", customer);
		model.addAttribute("customerId", customer.getCustomerId());
		model.addAttribute("packageName", customer.getPackage1().getPackageName());
		model.addAttribute("packageId", customer.getPackage1().getPackageId());

		return "packageDetail";
	}

	@ResponseBody
	@RequestMapping(value = "/findPackageByCustomer", method = RequestMethod.GET)
	public String findPackageByCustomer(Principal principal, HttpSession httpSession) {
		JSONObject res = new JSONObject();
		if (principal == null) {
			return "redirect:/account/signin";
		} else {
			Customer c = customerService.findByEmail(principal.getName());
			// if(httpSession.getAttribute("packageSelected") != null){
			try {
				res.put("packageId", c.getPackage1().getPackageId());
				// res.put("packageSelected",
				// httpSession.getAttribute("packageSelected").toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// }
			// Customer c = customerService.findByEmail(principal.getName());

		}
		return res.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/transferUpgrade", method = RequestMethod.POST)
	public String transferUpgrade(@PathVariable("lang") Optional<String> lang, Model model, Principal principal,
			HttpSession httpSession, @RequestParam("daysTransfer") String daysTransfer) {
		String language = LanguageResolver.resolve(lang, model);
		JSONObject res = new JSONObject();
		Customer customer = customerService.findByEmail(principal.getName());
		if (daysTransfer != null) {
			Calendar cal = Calendar.getInstance();
			int day = (int) Math.round(Double.parseDouble(daysTransfer));
			cal.add(Calendar.DATE, day);
			customer.setExpiredCredit(cal.getTime());
			Package package1 = packageService.findById(3);
			customer.setPackage1(package1);
			customerService.update(customer);
			if (httpSession.getAttribute("packageSelected") != null) {
				httpSession.setAttribute("packageSelected", 3);
				try {
					res.put("success", true);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return res.toString();
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public String changePassword(@PathVariable("lang") Optional<String> lang, Model model, Principal principal) {
		String language = LanguageResolver.resolve(lang, model);

		return "changePassword";
	}

	@ResponseBody
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public String changePassword(@PathVariable("lang") Optional<String> lang, Model model, Principal principal,
			@RequestParam("reNewPassword") String reNewPassword) {
		String language = LanguageResolver.resolve(lang, model);
		JSONObject res = new JSONObject();
		Customer customer = customerService.findByEmail(principal.getName());
		try {
			String hashed = BCrypt.hashpw(reNewPassword, BCrypt.gensalt());
			customer.getUser().setPassword(hashed);
			customerService.update(customer);
			MailHelper.sendMailResetPassword(customer.getUser().getEmail(), reNewPassword);
			res.put("noti", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return res.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/checkPassword", method = RequestMethod.POST)
	public String checkPassword(@PathVariable("lang") Optional<String> lang, Model model, Principal principal,
			@RequestParam String oldPassword) {
		String language = LanguageResolver.resolve(lang, model);
		Customer customer = customerService.findByEmail(principal.getName());
		if (BCrypt.checkpw(oldPassword, customer.getUser().getPassword())) {
			return "true";
		}

		// if (customer.getUser().getPassword().equals(oldPassword)) {
		// return "true";
		// }

		return "false";
	}

}
