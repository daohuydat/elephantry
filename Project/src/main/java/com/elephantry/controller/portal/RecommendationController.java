package com.elephantry.controller.portal;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.elephantry.entity.Customer;
import com.elephantry.entity.Evaluation;
import com.elephantry.entity.EvaluationRecommendation;
import com.elephantry.entity.Recommendation;
import com.elephantry.mahout.hadoop.util.HDHelper;
import com.elephantry.model.E;
import com.elephantry.service.CustomerService;
import com.elephantry.service.EvaluationService;
import com.elephantry.service.RecommendationService;
import com.elephantry.util.LanguageResolver;

@Controller
@RequestMapping(value = { "/{lang}/portal/recommendation", "/portal/recommendation" })
public class RecommendationController {

	private CustomerService customerService;
	private RecommendationService recommendationService;
	private EvaluationService evaluationService;

	@Autowired
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	@Autowired
	public void setRecommendationService(RecommendationService recommendationService) {
		this.recommendationService = recommendationService;
	}
	
	@Autowired
	public void setEvaluationService(EvaluationService evaluationService) {
		this.evaluationService = evaluationService;
	}

	@RequestMapping(value = { "/waiting", "/waiting/{customerId}" }, method = RequestMethod.GET)
	public String recommendationWaiting(@PathVariable("lang") Optional<String> lang, Model model, @RequestParam(value="s", required=false) String s) {
		LanguageResolver.resolve(lang, model);
//		List<Recommendation> list = recommendationService.getRecommendationByCustomerIdAndStatus(2,
//				E.RStatus.Waiting.getValue());
//		model.addAttribute("listRecommendation", list);
		if (s != null) {
			if (s.equals("1")) {
				model.addAttribute("successMessage", "done");
			}else{
				model.addAttribute("successMessage", "");
			}
		}
		return "recommendationWaiting";
	}

	// view detail recommendation
	@ResponseBody
	@RequestMapping(value = "/viewDetail", method = RequestMethod.POST)
	public String viewRecommendation(@RequestParam("id") int id) {
		JSONObject json = new JSONObject();
		try {
			Recommendation recommendation = recommendationService.findById(id);
			json = jsonParse(recommendation);
			String priorityName = "";
			if(recommendation.getPriority() == 1){
				priorityName = "Low";
			}else if(recommendation.getPriority() == 2){
				priorityName = "Normal";
			}else{
				priorityName = "High";
			}
			json.put("priorityName", priorityName);
			} catch (Exception e) {
			e.printStackTrace();
		}

		return json.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/waiting/search", method = RequestMethod.GET)
	public String searchWaiting(@RequestParam(value = "startDate") String startDate,
			@RequestParam(value = "endDate") String endDate, @RequestParam(value = "keyword") String keyword,
			@RequestParam(value = "pageNum") int pageNum, Principal principal) {
		JSONObject json = new JSONObject();
		try {
			JSONArray jArray = new JSONArray();
			Customer customer = customerService.findByEmail(principal.getName());

			List<Recommendation> listRecommendation = recommendationService.searchRecommendation(customer.getCustomerId(),
					E.RStatus.Waiting.getValue(), startDate, endDate, keyword.trim(), pageNum, 5);
			for (Recommendation recommendation : listRecommendation) {
				jArray.put(jsonParse(recommendation));
			}
			json.put("recommendations", jArray);
			json.put("recommendationCount", recommendationService.getRecommendationCount(customer.getCustomerId(),
					E.RStatus.Waiting.getValue(), startDate, endDate, keyword.trim()));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	// submit only 1 recommendation
	@ResponseBody
	@RequestMapping(value = "/waiting/submitonly", method = RequestMethod.POST)
	public String submitOnlyRecommendationWaiting(@RequestParam("id") int id) {
		JSONObject json = new JSONObject();
		try {
			Recommendation recommendation = recommendationService.findById(id);
			recommendation.setRecommendationStatusId(E.RStatus.Submitted.getValue());
			recommendationService.update(recommendation);
			if (recommendation.getRecommendationStatusId() == E.RStatus.Submitted.getValue()) {
				HDHelper.submit2Queue(recommendation);
			}
			json.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return json.toString();
	}

	// submit multi recommendation
	@ResponseBody
	@RequestMapping(value = "/waiting/submit", method = RequestMethod.POST)
	public String submitMultiRecommendationWaiting(@RequestParam("ids[]") List<Integer> ids) {
		JSONObject json = new JSONObject();
		try {
			if (ids.size() > 0) {
				for (int id : ids) {
					System.out.println(id);
					Recommendation recommendation = recommendationService.findById(id);
					recommendation.setRecommendationStatusId(E.RStatus.Submitted.getValue());
					recommendationService.update(recommendation);

				}
				json.put("result", true);
			} else {
				System.out.println("no checkbox choose");
			}
		} catch (Exception e) {

			System.out.println(e);
		}
		return json.toString();
	}

	// delete multi recommendation
	@ResponseBody
	@RequestMapping(value = "/waiting/delete", method = RequestMethod.POST)
	public String deleteMultiRecommendationWaiting(@RequestParam("ids[]") List<Integer> ids) {
		JSONObject json = new JSONObject();
		try {
			if (ids.size() > 0) {
				for (int id : ids) {
					System.out.println(id);
					Recommendation recommendation = recommendationService.findById(id);
					recommendation.setRecommendationStatusId(E.RStatus.Deleted.getValue());
					;
					recommendationService.update(recommendation);

				}
				json.put("result", true);
			} else {
				System.out.println("no checkbox choose");
			}
		} catch (Exception e) {

			System.out.println(e);
		}
		return json.toString();
	}

	// delete only 1 recommendation
	@ResponseBody
	@RequestMapping(value = "/waiting/deleteonly", method = RequestMethod.POST)
	public String deleteRecommendationWaiting(@PathVariable("lang") Optional<String> lang, @RequestParam("id") int id) {
		JSONObject json = new JSONObject();
		try {
			Recommendation recommendation = recommendationService.findById(id);
			recommendation.setRecommendationStatusId(E.RStatus.Deleted.getValue());
			;
			recommendationService.update(recommendation);
			json.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	@RequestMapping(value = "/submitted", method = RequestMethod.GET)
	public String recommendationSubmitted(@PathVariable("lang") Optional<String> lang, Model model, @RequestParam(value="s", required=false) String s) {
		LanguageResolver.resolve(lang, model);
		List<Recommendation> list = recommendationService.getRecommendationByCustomerIdAndStatus(2, 2);
		model.addAttribute("listRecommendation", list);
		if (s != null) {
			if (s.equals("1")) {
				model.addAttribute("successMessage", "done");
			}else{
				model.addAttribute("successMessage", "");
			}
		}
		return "recommendationSubmitted";
	}

	// search recommendation submitted
	@ResponseBody
	@RequestMapping(value = "/submitted/search", method = RequestMethod.GET)
	public String searchSubmitted(@RequestParam(value = "startDate") String startDate,
			@RequestParam(value = "endDate") String endDate, @RequestParam(value = "keyword") String keyword,
			@RequestParam(value = "pageNum") int pageNum, Principal principal) {
		JSONObject json = new JSONObject();
		try {
			JSONArray jArray = new JSONArray();
			Customer customer = customerService.findByEmail(principal.getName());

			List<Recommendation> listRecommendation = recommendationService.searchRecommendation(customer.getCustomerId(),
					E.RStatus.Submitted.getValue(), startDate, endDate, keyword.trim(), pageNum, 5);
			for (Recommendation recommendation : listRecommendation) {
				jArray.put(jsonParse(recommendation));
			}
			json.put("recommendations", jArray);
			json.put("recommendationCount", recommendationService.getRecommendationCount(customer.getCustomerId(),
					E.RStatus.Submitted.getValue(), startDate, endDate, keyword.trim()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	// cancel only 1 recommendation
	@ResponseBody
	@RequestMapping(value = "/submitted/cancelonly", method = RequestMethod.POST)
	public String cancelOnlyRecommendationWaiting(@RequestParam("id") int id) {
		JSONObject json = new JSONObject();
		try {
			Recommendation recommendation = recommendationService.findById(id);
			recommendation.setRecommendationStatusId(E.RStatus.Waiting.getValue());
			recommendationService.update(recommendation);

			json.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return json.toString();
	}

	// submit multi recommendation
	@ResponseBody
	@RequestMapping(value = "/submitted/cancelmultiple", method = RequestMethod.POST)
	public String cancelMultiRecommendationSubmmited(@RequestParam("ids[]") List<Integer> ids) {
		JSONObject json = new JSONObject();
		try {
			if (ids.size() > 0) {
				for (int id : ids) {
					System.out.println(id);
					Recommendation recommendation = recommendationService.findById(id);
					recommendation.setRecommendationStatusId(E.RStatus.Waiting.getValue());
					recommendationService.update(recommendation);

				}
				json.put("result", true);
			} else {
				System.out.println("no checkbox choose");
			}
		} catch (Exception e) {

			System.out.println(e);
		}
		return json.toString();
	}

	@RequestMapping(value = "/running", method = RequestMethod.GET)
	public String recommendationRunning(@PathVariable("lang") Optional<String> lang, Model model) {
		LanguageResolver.resolve(lang, model);
		List<Recommendation> list = recommendationService.getRecommendationByCustomerIdAndStatus(2, 3);
		model.addAttribute("listRecommendation", list);
		return "recommendationRunning";
	}

	// search recommendation running
	@ResponseBody
	@RequestMapping(value = "/running/search", method = RequestMethod.GET)
	public String searchRunning(@RequestParam(value = "startDate") String startDate,
			@RequestParam(value = "endDate") String endDate, @RequestParam(value = "keyword") String keyword,
			@RequestParam(value = "pageNum") int pageNum, Principal principal) {
		JSONObject json = new JSONObject();
		try {
			JSONArray jArray = new JSONArray();
			Customer customer = customerService.findByEmail(principal.getName());

			List<Recommendation> listRecommendation = recommendationService.searchRecommendation(customer.getCustomerId(),
					E.RStatus.Running.getValue(), startDate, endDate, keyword.trim(), pageNum, 5);
			for (Recommendation recommendation : listRecommendation) {
				jArray.put(jsonParse(recommendation));
			}
			json.put("recommendations", jArray);
			json.put("recommendationCount", recommendationService.getRecommendationCount(customer.getCustomerId(),
					E.RStatus.Running.getValue(), startDate, endDate, keyword.trim()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	@RequestMapping(value = "/completed", method = RequestMethod.GET)
	public String recommendationCompleted(@PathVariable("lang") Optional<String> lang, Model model) {
		LanguageResolver.resolve(lang, model);
		List<Recommendation> list = recommendationService.getRecommendationByCustomerIdAndStatus(2, 4);
		model.addAttribute("listRecommendation", list);
		return "recommendationCompleted";
	}

	// search recommendation completed
	@ResponseBody
	@RequestMapping(value = "/completed/search", method = RequestMethod.GET)
	public String searchCompleted(@RequestParam(value = "startDate") String startDate,
			@RequestParam(value = "endDate") String endDate, @RequestParam(value = "keyword") String keyword,
			@RequestParam(value = "pageNum") int pageNum, Principal principal) {
		JSONObject json = new JSONObject();
		try {
			JSONArray jArray = new JSONArray();
			Customer customer = customerService.findByEmail(principal.getName());

			List<Recommendation> listRecommendation = recommendationService.searchRecommendation(customer.getCustomerId(),
					E.RStatus.Completed.getValue(), startDate, endDate, keyword.trim(), pageNum, 5);
			for (Recommendation recommendation : listRecommendation) {
				jArray.put(jsonParse(recommendation));
			}
			json.put("recommendations", jArray);
			json.put("recommendationCount", recommendationService.getRecommendationCount(customer.getCustomerId(),
					E.RStatus.Completed.getValue(), startDate, endDate, keyword.trim()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	private JSONObject jsonParse(Recommendation r) {
		JSONObject json = new JSONObject();
		try {
			json.put("recommendationId", r.getRecommendationId());
			json.put("name", r.getName());
			json.put("folderInputURL", r.getFolderInputURL());
			json.put("folderOutputURL", r.getFolderOutputURL());
			json.put("numOfItem", r.getNumOfItem());
			json.put("estimatedDuration", r.getEstimatedDuration());
			json.put("recommendationStatusId", E.RStatus.getName(r.getRecommendationStatusId()));
			json.put("recommendTypeId", r.getRecommendTypeId());
			json.put("priority", r.getPriority());
			json.put("customer", r.getCustomer().getFirstName() + " " + r.getCustomer().getLastName());
			SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat datetime = new SimpleDateFormat("h:mm a, dd-MM-yyyy");
			if (r.getCreatedTime() != null) {
				json.put("createdTime", date.format(r.getCreatedTime()));
			} else {
				json.put("createdTime", "");
			}
			if (r.getStartedTime() != null) {
				json.put("startedTime", datetime.format(r.getStartedTime()));
			} else {
				json.put("startedTime", "");
			}
			if (r.getFinishedTime() != null) {
				json.put("finishedTime", datetime.format(r.getFinishedTime()));
			} else {
				json.put("finishedTime", "");
			}
			if (r.getTimer() != null) {
				json.put("timer", datetime.format(r.getTimer()));
			} else {
				json.put("timer", "");
			}
			json.put("threshold", r.getThreshold());
			json.put("failedCount", r.getFailedCount());
			json.put("recordCount", r.getRecordCount());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@RequestMapping(value = "/evaluation", method = RequestMethod.GET)
	public String feedback(@PathVariable("lang") Optional<String> lang, Model model, Principal principal) {
		LanguageResolver.resolve(lang, model);
		Customer c = customerService.findByEmail(principal.getName());
		if (c == null) {
			return "redirect:/account/signin";
		}
		List<EvaluationRecommendation> list = evaluationService.findAllLastEvaluation(c.getCustomerId(), 10);
		
		model.addAttribute("evaluations", list);
		
		return "viewEvaluation";
	}
}
