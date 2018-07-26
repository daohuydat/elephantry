package com.elephantry.controller.portal;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.elephantry.entity.Customer;
import com.elephantry.entity.Feedback;
import com.elephantry.service.CustomerService;
import com.elephantry.service.FeedbackService;
import com.elephantry.service.RecommendationService;
import com.elephantry.util.LanguageResolver;

@Controller
@RequestMapping(value = { "/{lang}/portal/home", "/portal/home" })
public class PortalHomeController {
	
	private RecommendationService recommendationService;
	private CustomerService customerService;
	private FeedbackService feedbackService;
	
	@Autowired
	public void setRecommendationService(RecommendationService recommendationService) {
		this.recommendationService = recommendationService;
	}
	
	@Autowired
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@Autowired
	public void setFeedbackService(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
	}
	
	@RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
	public String index(@PathVariable("lang") Optional<String> lang, Model model, Principal principal) {
		LanguageResolver.resolve(lang, model);
		Customer c = customerService.findByEmail(principal.getName());
		if (c == null) {
			return "redirect:/account/signin";
		}
		int customerId = c.getCustomerId();
		int numOfMonth = 12;
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		Map<String, Integer> mapRecommendations = recommendationService.countByMonth(numOfMonth, customerId);
		List<Integer> valueRecommendations = new ArrayList<>();
		if (month >= numOfMonth) {
			for (int i = 0; i < month; i++) {
				Integer value = mapRecommendations.get(year+"-"+(i+1));
				valueRecommendations.add(value==null?0:value);
			}
		}else{
			for (int i = month; i < 12; i++) {
				Integer value = mapRecommendations.get((year-1)+"-"+(i+1));
//				System.out.println((year-1)+"-"+(i+1)+":value: "+value);
				valueRecommendations.add(value==null?0:value);
			}
			for (int i = 0; i < month; i++) {
				Integer value = mapRecommendations.get(year+"-"+(i+1));
//				System.out.println(year+"-"+(i+1)+":value: "+value);
				valueRecommendations.add(value==null?0:value);
			}
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		model.addAttribute("customerRecommendationByMonths", valueRecommendations.toString());
		model.addAttribute("totalRecommendations", recommendationService.findRecommendationByCustomerId(customerId).size());
		model.addAttribute("waitingRecommendations", recommendationService.countWaiting(customerId));
		model.addAttribute("submittedRecommendations", recommendationService.countSubmitted(customerId));
		model.addAttribute("runningRecommendations", recommendationService.countRunning(customerId));
		model.addAttribute("completedRecommendations", recommendationService.countCompleted(customerId));
		model.addAttribute("currentPackage", c.getPackage1().getPackageName());
		if(c.getExpiredCredit() != null){
			model.addAttribute("expiredDate", format.format(c.getExpiredCredit()));
		}
		return "portalIndex";
	}
	
	
	@RequestMapping(value = "/feedback", method = RequestMethod.GET)
	public String feedback(@PathVariable("lang") Optional<String> lang, Model model, @RequestParam(value="s", required=false) String s) {
		LanguageResolver.resolve(lang, model);
		if (s != null) {
			if (s.equals("1")) {
				model.addAttribute("successMessage", "done");
			}else{
				model.addAttribute("successMessage", "");
			}
		}
		return "feedback";
	}
	
	@RequestMapping(value = "/feedback", method = RequestMethod.POST)
	public String feedback(@PathVariable("lang") Optional<String> lang, Model model, @RequestParam("txtfeedback") String message, Principal principal) {
		String language = LanguageResolver.resolve(lang, model);
		Customer c = customerService.findByEmail(principal.getName());
		if (c == null) {
			return "redirect:/account/signin";
		}
		Feedback f = new Feedback(message,c);
		feedbackService.save(f);
		return  "redirect:/"+language+"/portal/home/feedback?s=1";
	}
}
