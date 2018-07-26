package com.elephantry.controller.portal;

import java.security.Principal;
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

import com.elephantry.entity.Customer;
import com.elephantry.service.CustomerService;
import com.elephantry.service.RecommendationService;
import com.elephantry.util.LanguageResolver;

@Controller
@RequestMapping(value= { "/{lang}/portal/chart", "/portal/chart" })
public class PortalChartController {
	
	private RecommendationService recommendationService;
	private CustomerService customerService;
	
	@Autowired
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@Autowired
	public void setRecommendationService(RecommendationService recommendationService) {
		this.recommendationService = recommendationService;
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String viewChart(@PathVariable("lang") Optional<String> lang, Model model, Principal principal){
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
		model.addAttribute("customerRecommendationByMonths", valueRecommendations.toString());
		return "customerViewChart";
	}
}
