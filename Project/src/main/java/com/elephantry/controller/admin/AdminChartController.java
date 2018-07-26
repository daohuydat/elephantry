package com.elephantry.controller.admin;

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

import com.elephantry.service.PackageService;
import com.elephantry.service.RecommendationService;
import com.elephantry.service.UserService;
import com.elephantry.util.LanguageResolver;

@Controller
@RequestMapping(value = { "/{lang}/admin/chart", "/admin/chart" })
public class AdminChartController {
	
	private PackageService packageService;
	private RecommendationService recommendationService;
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setRecommendationService(RecommendationService recommendationService) {
		this.recommendationService = recommendationService;
	}
	
	@Autowired
	public void setPackageService(PackageService packageService) {
		this.packageService = packageService;
	}

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public String exportChart(@PathVariable("lang") Optional<String> lang){
		return "adminExportChart";
	}
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String viewChart(@PathVariable("lang") Optional<String> lang, Model model){
		int numOfMonth = 12;
		LanguageResolver.resolve(lang, model);
		packageChart(model);
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		recommendationChart(model, numOfMonth, year, month);
		customerChart(model, numOfMonth, year, month);
		return "adminViewChart";
	}

	private void customerChart(Model model, int numOfMonth, int year, int month) {
		Map<String, Integer> mapCustomer = userService.summaryCustomerByMonth(numOfMonth);
//		System.out.println(mapCustomer);
		List<Integer> customerValues = new ArrayList<>();
		int total = userService.countCustomerByCreatedTime(numOfMonth);
		if (month >= numOfMonth) {
			total += mapCustomer.get((year-1)+"-"+month)==null?0:mapCustomer.get((year-1)+"-"+month);
			for (int i = 0; i < month; i++) {
				Integer value = mapCustomer.get(year+"-"+(i+1));
				total += value==null?0:value;
				customerValues.add(total);
			}
		}else{
			total += mapCustomer.get((year-1)+"-"+month)==null?0:mapCustomer.get((year-1)+"-"+month);
			for (int i = month; i < 12; i++) {
				 Integer value = mapCustomer.get((year-1)+"-"+(i+1));
				 total += value==null?0:value;
//				 System.out.println((year-1)+"-"+(i+1)+"    abc    "+total);
				 customerValues.add(total);
			}
			for (int i = 0; i < month; i++) {
				Integer value = mapCustomer.get(year+"-"+(i+1));
				 total += value==null?0:value;
//				 System.out.println(year+"-"+(i+1)+"    xyz    "+total);
				 customerValues.add(total);
			}
		}
		
		
		model.addAttribute("customerByMonths", customerValues.toString());
	}

	private void recommendationChart(Model model, int numOfMonth, int year, int month) {
		Map<String, Integer> mapMonth = recommendationService.countByMonth(numOfMonth);
		List<Integer> monthValues = new ArrayList<>();
		
		if (month >= numOfMonth) {
			for (int i = month-numOfMonth+1; i <= month; i++) {
				Integer value = mapMonth.get(year+"-"+i);
				monthValues.add(value==null?0:value);
			}
		}else {
			for (int i = month; i < 12; i++) {
				Integer value = mapMonth.get((year-1)+"-"+(i+1));
//				System.out.println((year-1)+"-"+(i+1)+":value: "+value);
				monthValues.add(value==null?0:value);
			}
			for (int i = 0; i < month; i++) {
				Integer value = mapMonth.get(year+"-"+(i+1));
//				System.out.println(year+"-"+(i+1)+":value: "+value);
				monthValues.add(value==null?0:value);
			}
		}
		model.addAttribute("recommendationByMonths", monthValues.toString());
	}

	private void packageChart(Model model) {
		int freeTrial = packageService.countbyId(1);
		int standard = packageService.countbyId(2);
		int premium = packageService.countbyId(3);
		int totalP = freeTrial + standard + premium;
		double freePercen = Math.round(1.0*freeTrial/totalP*100);
		double standardPercen = Math.round(1.0*standard/totalP*100);
		double premiumPercen = Math.round(100 - freePercen - standardPercen);
		model.addAttribute("package1", freePercen);
		model.addAttribute("package2", standardPercen);
		model.addAttribute("package3", premiumPercen);
//		System.out.println(freePercen+" "+standardPercen+" "+premiumPercen);
	}
}
