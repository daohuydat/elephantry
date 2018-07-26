package com.elephantry.controller.admin;

import java.security.Principal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.elephantry.entity.Feedback;
import com.elephantry.entity.Province;
import com.elephantry.entity.User;
import com.elephantry.mahout.hadoop.util.HDFinalQueue;
import com.elephantry.mahout.hadoop.util.HDPreparedQueue;
import com.elephantry.mahout.hadoop.util.HDRunningList;
import com.elephantry.mahout.hadoop.util.HDSubmittedQueue;
import com.elephantry.service.CountryService;
import com.elephantry.service.CustomerService;
import com.elephantry.service.FeedbackService;
import com.elephantry.service.PackageService;
import com.elephantry.service.ProvinceService;
import com.elephantry.service.RecommendationService;
import com.elephantry.service.UserService;
import com.elephantry.util.LanguageResolver;
import com.elephantry.util.MailHelper;

@Controller
@RequestMapping(value = { "/{lang}/admin/home", "/admin/home" })
public class AdminHomeController {

	private FeedbackService feedbackService;
	private RecommendationService recommendationService;
	private UserService userService;
	private CustomerService customerService;
	private ProvinceService provinceService;
	private CountryService countryService;

	@Autowired
	public void setCountryService(CountryService countryService) {
		this.countryService = countryService;
	}

	@Autowired
	public void setProvinceService(ProvinceService provinceService) {
		this.provinceService = provinceService;
	}

	@Autowired
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setRecommendationService(RecommendationService recommendationService) {
		this.recommendationService = recommendationService;
	}

	@Autowired
	public void setFeedbackService(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
	}

	@RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
	public String index(@PathVariable("lang") Optional<String> lang, Model model) {
		int numOfMonth = 12;
		LanguageResolver.resolve(lang, model);
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		recommendationChart(model, numOfMonth, year, month);
		customerChart(model, numOfMonth, year, month);
		model.addAttribute("totalRecommendations", recommendationService.countAllRecommendations());
		model.addAttribute("compareRecommendation", recommendationService.compareRecommendationWeekAgo());
		model.addAttribute("runningJobs", HDRunningList.getInstance().size());
		model.addAttribute("submitJobs", HDSubmittedQueue.getInstance().getTotalSize()
				+ HDPreparedQueue.getInstance().size() + HDFinalQueue.getInstance().size());
		return "adminIndex";
	}

	@RequestMapping(value = "/profileCustomer/{id}", method = RequestMethod.GET)
	public String profileCustomer(@PathVariable("lang") Optional<String> lang, Model model, Principal principal,
			@PathVariable("id") int id) {
		String language = LanguageResolver.resolve(lang, model);
		Customer customer = customerService.getCustomerById(id);
		if (customer != null) {
			Province province = customer.getProvince();
			model.addAttribute("customer", customer);
			model.addAttribute("email", customer.getUser().getEmail());
			model.addAttribute("province", province.getProvinceName());
			model.addAttribute("country", customer.getProvince().getCountry().getCountryName());
			model.addAttribute("packageName", customer.getPackage1().getPackageName());
			return "profileCustomer";
		} else
			return "404";
	}

	@RequestMapping(value = "/changePasswordAdmin", method = RequestMethod.GET)
	public String changePasswordAdmin(@PathVariable("lang") Optional<String> lang, Model model, Principal principal) {
		String language = LanguageResolver.resolve(lang, model);

		return "changePasswordAdmin";
	}

	@ResponseBody
	@RequestMapping(value = "/changePasswordAdmin", method = RequestMethod.POST)
	public String changePasswordAdmin(@PathVariable("lang") Optional<String> lang, Model model, Principal principal,
			@RequestParam("reNewPassword") String reNewPassword) {
		String language = LanguageResolver.resolve(lang, model);
		JSONObject res = new JSONObject();
		User user = userService.findByEmail(principal.getName());
		try {
			String hashed = BCrypt.hashpw(reNewPassword, BCrypt.gensalt());
			user.setPassword(hashed);
			userService.update(user);
			res.put("noti", true);
			MailHelper.sendMailResetPassword(user.getEmail(), reNewPassword);
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
		User user = userService.findByEmail(principal.getName());
		if (BCrypt.checkpw(oldPassword, user.getPassword())) {
			return "true";
		}

		// if (customer.getUser().getPassword().equals(oldPassword)) {
		// return "true";
		// }

		return "false";
	}

	@RequestMapping(value = "/feedback/view", method = RequestMethod.GET)
	public String feedback(@PathVariable("lang") Optional<String> lang, Model model) {
		LanguageResolver.resolve(lang, model);
		return "adminViewFeedback";
	}

	@ResponseBody
	@RequestMapping(value = "feedback/read/filter", method = RequestMethod.GET)
	public String readFiler(@RequestParam("txtSortDesc") String sortDesc, @RequestParam("pageNum") int pageNum) {
		JSONObject json = new JSONObject();
		try {
			List<Feedback> lf = feedbackService.findReadByCreatedTime(sortDesc, pageNum, 10);
			JSONArray jArray = new JSONArray();
			DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			for (Feedback feedback : lf) {
				jArray.put(jsonParse(feedback, formatter));
			}
			json.put("feedbacks", jArray);
			json.put("countFeedback", feedbackService.countRead());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	@ResponseBody
	@RequestMapping(value = "feedback/unRead/filter", method = RequestMethod.GET)
	public String unReadFiler(@RequestParam("txtSortDesc") String sortDesc, @RequestParam("pageNum") int pageNum) {
		JSONObject json = new JSONObject();
		try {
			List<Feedback> lf = feedbackService.findUnReadByCreatedTime(sortDesc, pageNum, 10);
			JSONArray jArray = new JSONArray();
			DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			for (Feedback feedback : lf) {
				jArray.put(jsonParse(feedback, formatter));
			}
			json.put("feedbacks", jArray);
			json.put("countFeedback", feedbackService.countUnread());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/feedback/update", method = RequestMethod.POST)
	public String updateStatus(@RequestParam("feedbackId") int feedbackId, @RequestParam("action") String action,
			@RequestParam("txtSortDesc") String sortDesc, @RequestParam("pageNum") int pageNum) {
		JSONObject json = new JSONObject();
		try {
			Feedback f = feedbackService.findById(feedbackId);
			if (f != null) {
				if (action.equals("read")) {
					f.setRead(1);
				} else {
					f.setRead(0);
				}
				feedbackService.update(f);
				List<Feedback> result = null;
				if (action.equals("read")) {
					result = feedbackService.findUnReadByCreatedTime(sortDesc, pageNum, 10);
					json.put("countFeedback", feedbackService.countUnread());
				} else {
					result = feedbackService.findReadByCreatedTime(sortDesc, pageNum, 10);
					json.put("countFeedback", feedbackService.countRead());
				}
				JSONArray jArray = new JSONArray();
				DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
				for (Feedback feedback : result) {
					jArray.put(jsonParse(feedback, formatter));
				}

				json.put("feedbacks", jArray);
				json.put("success", true);
			} else {
				json.put("success", false);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json.toString();
	}

	private JSONObject jsonParse(Feedback f, DateFormat formatter) {
		JSONObject json = new JSONObject();
		try {
			json.put("customerName", f.getCustomer().getFirstName() + " " + f.getCustomer().getLastName());
			json.put("content", f.getContent());
			json.put("email", f.getCustomer().getUser().getEmail());
			json.put("phone", f.getCustomer().getPhone());
			json.put("read", f.getRead());
			json.put("feedbackId", f.getFeedbackId());
			json.put("createdTime", formatter.format(f.getCreatedTime()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return json;
	}

	private void customerChart(Model model, int numOfMonth, int year, int month) {
		Map<String, Integer> mapCustomer = userService.summaryCustomerByMonth(numOfMonth);
		// System.out.println(mapCustomer);
		List<Integer> customerValues = new ArrayList<>();
		int total = userService.countCustomerByCreatedTime(numOfMonth);
		if (month >= numOfMonth) {
			total += mapCustomer.get((year - 1) + "-" + month) == null ? 0 : mapCustomer.get((year - 1) + "-" + month);
			for (int i = 0; i < month; i++) {
				Integer value = mapCustomer.get(year + "-" + (i + 1));
				total += value == null ? 0 : value;
				customerValues.add(total);
			}
		} else {
			total += mapCustomer.get((year - 1) + "-" + month) == null ? 0 : mapCustomer.get((year - 1) + "-" + month);
			for (int i = month; i < 12; i++) {
				Integer value = mapCustomer.get((year - 1) + "-" + (i + 1));
				total += value == null ? 0 : value;
				// System.out.println((year-1)+"-"+(i+1)+" abc "+total);
				customerValues.add(total);
			}
			for (int i = 0; i < month; i++) {
				Integer value = mapCustomer.get(year + "-" + (i + 1));
				total += value == null ? 0 : value;
				// System.out.println(year+"-"+(i+1)+" xyz "+total);
				customerValues.add(total);
			}
		}
		DecimalFormat df = new DecimalFormat("#.##");
		String compareCustomer = df.format(
				((1.0 * customerValues.get(customerValues.size() - 1) / userService.countTotalCustomerLastWeek()) - 1)
						* 100.0);

		model.addAttribute("totalCustomer", customerValues.get(customerValues.size() - 1));
		model.addAttribute("compareCustomer", compareCustomer);
		model.addAttribute("customerByMonths", customerValues.toString());
	}

	private void recommendationChart(Model model, int numOfMonth, int year, int month) {
		Map<String, Integer> mapMonth = recommendationService.countByMonth(numOfMonth);
		List<Integer> monthValues = new ArrayList<>();

		if (month >= numOfMonth) {
			for (int i = month - numOfMonth + 1; i <= month; i++) {
				Integer value = mapMonth.get(year + "-" + i);
				monthValues.add(value == null ? 0 : value);
			}
		} else {
			for (int i = month; i < 12; i++) {
				Integer value = mapMonth.get((year - 1) + "-" + (i + 1));
				// System.out.println((year-1)+"-"+(i+1)+":value: "+value);
				monthValues.add(value == null ? 0 : value);
			}
			for (int i = 0; i < month; i++) {
				Integer value = mapMonth.get(year + "-" + (i + 1));
				// System.out.println(year+"-"+(i+1)+":value: "+value);
				monthValues.add(value == null ? 0 : value);
			}
		}
		model.addAttribute("recommendationByMonths", monthValues.toString());
	}
}
