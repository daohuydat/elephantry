package com.elephantry.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.elephantry.entity.Configuration;
import com.elephantry.entity.Customer;
import com.elephantry.entity.Recommendation;
import com.elephantry.mahout.hadoop.HDRCThread;
import com.elephantry.mahout.hadoop.util.HDConfig;
import com.elephantry.mahout.hadoop.util.HDFinalQueue;
import com.elephantry.mahout.hadoop.util.HDHelper;
import com.elephantry.mahout.hadoop.util.HDPreparedQueue;
import com.elephantry.mahout.hadoop.util.HDQueueOperation;
import com.elephantry.mahout.hadoop.util.HDQueueOperation.QueueStatus;
import com.elephantry.mahout.hadoop.util.HDRunningList;
import com.elephantry.mahout.hadoop.util.HDSubmittedQueue;
import com.elephantry.mahout.hadoop.util.REvaluator;
import com.elephantry.model.InitConfigParams;
import com.elephantry.service.ConfigurationService;
import com.elephantry.service.RecommendationService;
import com.elephantry.util.LanguageResolver;

@Controller
@RequestMapping(value = { "{lang}/admin/queue", "/admin/queue" })
public class QueueController {

	private ConfigurationService configurationService;
	private RecommendationService recommendationService;

	@Autowired
	public void setRecommendationService(RecommendationService recommendationService) {
		this.recommendationService = recommendationService;
	}

	@Autowired
	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public String information(@PathVariable("lang") Optional<String> lang, Model model) {
		LanguageResolver.resolve(lang, model);
		return "adminQueue";
	}

	@RequestMapping(value = "/test1", method = RequestMethod.GET)
	public String test(@PathVariable("lang") Optional<String> lang, Model model) {
		LanguageResolver.resolve(lang, model);
		Recommendation r = recommendationService.findById(7);
		REvaluator.copyTrainingFromHdfs(r);
		double[] results = REvaluator.rmseNmae(r);
		System.err.println("RMSE: " + results[0]);
		System.err.println("MAE: " + results[1]);
		return "adminQueue";
	}

	@RequestMapping(value = "/setting", method = RequestMethod.GET)
	public String setting(@PathVariable("lang") Optional<String> lang, Model model) {
		LanguageResolver.resolve(lang, model);
		model.addAttribute("priorityScale", configurationService.find(HDConfig.PRIORITY_SCALE));
		model.addAttribute("finalQueueMaxSize", configurationService.find(HDConfig.FINAL_QUEUE_MAX_SIZE));
		model.addAttribute("preparedQueueMinSize", configurationService.find(HDConfig.PREPARED_QUEUE_MIN_SIZE));
		model.addAttribute("runningMaxSize", configurationService.find(HDConfig.RUNNING_MAX_SIZE));
		model.addAttribute("threshold", configurationService.find(HDConfig.THRESHOLD));
		model.addAttribute("trainingPercentage", configurationService.find(HDConfig.TRAINING_PERCENTAGE));

		return "adminQueueSetting";
	}

	@ResponseBody
	@RequestMapping(value = "/getQueueData", method = RequestMethod.GET)
	public String getQueueData() {
		HDPreparedQueue hdPreparedQueue = HDPreparedQueue.getInstance();
		HDFinalQueue hdFinalQueue = HDFinalQueue.getInstance();
		HDSubmittedQueue submittedQueue = HDSubmittedQueue.getInstance();

		JSONObject res = new JSONObject();
		List<Recommendation> recommendations = submittedQueue.getAllLow();
		try {
			JSONArray jsonArray = new JSONArray();
			for (Recommendation r : recommendations) {
				jsonArray.put(getJSON(r));
			}
			res.put("lowQueue", jsonArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		recommendations = submittedQueue.getAllNormal();
		try {
			JSONArray jsonArray = new JSONArray();
			for (Recommendation r : recommendations) {
				JSONObject obj = getJSON(r);

				jsonArray.put(obj);
			}
			res.put("normalQueue", jsonArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		recommendations = submittedQueue.getAllHigh();
		try {
			JSONArray jsonArray = new JSONArray();
			for (Recommendation r : recommendations) {
				jsonArray.put(getJSON(r));
			}
			res.put("highQueue", jsonArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		recommendations = hdPreparedQueue.getAll();
		try {
			JSONArray preparedJsonArray = new JSONArray();
			for (Recommendation r : recommendations) {
				preparedJsonArray.put(getJSON(r));
			}
			res.put("preparedQueue", preparedJsonArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		recommendations = hdFinalQueue.getAll();
		try {
			JSONArray finalJsonArray = new JSONArray();
			for (Recommendation r : recommendations) {
				finalJsonArray.put(getJSON(r));
			}
			res.put("finalQueue", finalJsonArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return res.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/getSwapQueueData", method = RequestMethod.GET)
	public String getSwapQueueData() {
		HDPreparedQueue hdPreparedQueue = HDPreparedQueue.getInstance();
		HDQueueOperation queueOperation = HDQueueOperation.getInstance();

		JSONObject res = new JSONObject();
		try {
			if (queueOperation.pauseQueue()) {
				res.put("queuePaused", true);
				List<Recommendation> recommendations = hdPreparedQueue.getAll();
				JSONArray preparedJsonArray = new JSONArray();
				for (Recommendation r : recommendations) {
					preparedJsonArray.put(getJSON(r));
				}
				res.put("preparedQueue", preparedJsonArray);
			} else {
				res.put("queuePaused", false);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return res.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/saveSwappedRecommendations", method = RequestMethod.POST)
	public String saveSwappedRecommendations(@RequestParam("ids[]") int[] ids) {
		HDPreparedQueue hdPreparedQueue = HDPreparedQueue.getInstance();
		HDQueueOperation queueOperation = HDQueueOperation.getInstance();

		JSONObject res = new JSONObject();
		try {
			if (queueOperation.getQueueStatus() == QueueStatus.PAUSED) {
				List<Recommendation> recommendations = hdPreparedQueue.getAll();
				List<Recommendation> temp = new ArrayList<>();
				for (int i = 0; i < ids.length; i++) {
					for (Recommendation recommendation : recommendations) {
						if (recommendation.getRecommendationId() == ids[i]) {
							temp.add(recommendation);
						}
					}
				}
				recommendations.clear();
				recommendations.addAll(temp);

				JSONArray jsonArray = new JSONArray();
				for (Recommendation r : recommendations) {
					jsonArray.put(getJSON(r));
				}
				res.put("preparedQueue", jsonArray);

				if (queueOperation.resumeQueue()) {
					res.put("success", true);
				} else {
					res.put("success", false);
				}
			} else {
				res.put("success", false);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return res.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/cancelSwappedRecommendations", method = RequestMethod.POST)
	public String cancelSwappedRecommendations() {
		HDQueueOperation queueOperation = HDQueueOperation.getInstance();

		JSONObject res = new JSONObject();
		try {
			if (queueOperation.getQueueStatus() == QueueStatus.PAUSED) {
				if (queueOperation.resumeQueue()) {
					res.put("success", true);
				} else {
					res.put("success", false);
				}
			} else {
				res.put("success", false);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return res.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/getRunningThreads", method = RequestMethod.GET)
	public String getRunningThreads() {
		HDRunningList hdRunningList = HDRunningList.getInstance();

		List<HDRCThread> threads = hdRunningList.getAll();
		JSONObject res = new JSONObject();
		try {
			JSONArray jsonArray = new JSONArray();
			for (HDRCThread t : threads) {
				jsonArray.put(getJSON(t.getRC()));
			}
			res.put("running", jsonArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return res.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/saveConfig", method = RequestMethod.POST)
	public String saveConfig(@RequestParam("key") String key, @RequestParam("value") String value) {

		JSONObject res = new JSONObject();
		try {

			Configuration conf = configurationService.find(key);
			if (conf == null) {
				res.put("success", false);
			} else {
				conf.setValue(value);
				configurationService.update(conf);
				res.put("success", true);
			}
		} catch (JSONException e) {
			e.printStackTrace();

		}
		return res.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/getQueueInfo", method = RequestMethod.GET)
	public String getQueueInfo() {
		JSONObject res = new JSONObject();
		HDRunningList runningList = HDRunningList.getInstance();
		HDFinalQueue finalQueue = HDFinalQueue.getInstance();
		HDPreparedQueue preparedQueue = HDPreparedQueue.getInstance();
		HDSubmittedQueue submittedQueue = HDSubmittedQueue.getInstance();
		HDQueueOperation queueOperation = HDQueueOperation.getInstance();

		try {
			res.put("queueStatus", queueOperation.getQueueStatus().getValue());
			res.put("finalCount", finalQueue.size());
			res.put("preparedCount", preparedQueue.size());
			res.put("lowSubmittedCount", submittedQueue.sizeLow());
			res.put("normalSubmittedCount", submittedQueue.sizeNormal());
			res.put("highSubmittedCount", submittedQueue.sizeHigh());
			res.put("runningCount", runningList.size());
			//
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return res.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/getQueueStatus", method = RequestMethod.GET)
	public String getQueueStatus() {
		JSONObject res = new JSONObject();
		HDQueueOperation queueOperation = HDQueueOperation.getInstance();
		try {
			res.put("queueStatus", queueOperation.getQueueStatus().getValue());
			//
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return res.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/doQueueAction", method = RequestMethod.POST)
	public String doQueueAction(@RequestParam("action") String action, HttpSession ss) {
		JSONObject res = new JSONObject();
		InitConfigParams configParams = HDHelper.getInitConfigParams();
		HDQueueOperation queueOperation = HDQueueOperation.getInstance();
		try {
			System.out.println("action: " + action);
			if ("start".equalsIgnoreCase(action)) {
				queueOperation.startQueue(ss, configParams);
			} else if ("stop".equalsIgnoreCase(action)) {
				queueOperation.stopQueue();
			} else if ("reset".equalsIgnoreCase(action)) {
				queueOperation.resetQueue(ss, configParams);
			} else if ("resume".equalsIgnoreCase(action)) {
				queueOperation.resumeQueue();
			} else if ("pause".equalsIgnoreCase(action)) {
				queueOperation.pauseQueue();
			}
			res.put("queueStatus", queueOperation.getQueueStatus().getValue());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return res.toString();
	}

	private JSONObject getJSON(Recommendation r) {
		JSONObject res = new JSONObject();
		try {
			Customer c = r.getCustomer();
			res.put("recommendationId", r.getRecommendationId());
			res.put("name", r.getName());
			res.put("customerId", c.getCustomerId());
			res.put("customerName", c.getFirstName() + " " + c.getLastName());
			res.put("statusId", r.getRecommendationStatusId());
			res.put("estimatedDuration", r.getEstimatedDuration());
			res.put("priority", r.getPriority());
			res.put("threshold", r.getThreshold());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return res;
	}
}
