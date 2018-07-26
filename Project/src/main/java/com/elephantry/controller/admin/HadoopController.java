package com.elephantry.controller.admin;

import java.net.InetSocketAddress;
import java.security.PrivilegedExceptionAction;
import java.util.Optional;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapred.ClusterStatus;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.security.UserGroupInformation;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.elephantry.mahout.hadoop.util.HDHelper;
import com.elephantry.mahout.hadoop.util.REvaluator;
import com.elephantry.model.InitConfigParams;
import com.elephantry.service.RecommendationService;
import com.elephantry.util.LanguageResolver;

@Controller
@RequestMapping(value = { "/{lang}/admin/hadoop", "/admin/hadoop" })
public class HadoopController {

	private InitConfigParams initConfigParams;
	

	@Autowired
	public void setInitConfigParams(InitConfigParams initConfigParams) {
		this.initConfigParams = initConfigParams;
	}
	
	
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public String information(@PathVariable("lang") Optional<String> lang, Model model) {
		LanguageResolver.resolve(lang, model);
		try {
			UserGroupInformation ugi = UserGroupInformation.createProxyUser(initConfigParams.getHadoopUser(),
					UserGroupInformation.getLoginUser());
			ugi.doAs(new PrivilegedExceptionAction<Boolean>() {
				@SuppressWarnings("deprecation")
				public Boolean run() throws Exception {
					Configuration conf = HDHelper.getDefaultConf(initConfigParams);
					conf.setInt("ipc.client.connect.max.retries.on.timeouts", 3);
					JobClient jobClient  = new JobClient(new InetSocketAddress(initConfigParams.getHadoopIpAddress(),
							initConfigParams.getiJobTrackerPort()), conf);
					
					ClusterStatus clusterStatus = jobClient.getClusterStatus(true);
					model.addAttribute("serverIsOn", true);
					model.addAttribute("maxMapTasks", clusterStatus.getMaxMapTasks());
					model.addAttribute("mapTasks", clusterStatus.getMapTasks());
					model.addAttribute("maxReduceTasks", clusterStatus.getMaxReduceTasks());
					model.addAttribute("reduceTasks", clusterStatus.getReduceTasks());
					model.addAttribute("usedMemory", clusterStatus.getUsedMemory() / 1000000);
					model.addAttribute("maxMemory", clusterStatus.getMaxMemory() / 1000000);
					return true;
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("serverIsOn", false);
		}
		
		return "adminHadoop";
	}

	@ResponseBody
	@RequestMapping(value = "/getClusterInfo", method = RequestMethod.GET)
	public String getClusterInfo() {
		JSONObject res = new JSONObject();
		try {
			UserGroupInformation ugi = UserGroupInformation.createProxyUser(initConfigParams.getHadoopUser(),
					UserGroupInformation.getLoginUser());
			ugi.doAs(new PrivilegedExceptionAction<Boolean>() {
				@SuppressWarnings("deprecation")
				public Boolean run() throws Exception {
					Configuration conf = HDHelper.getDefaultConf(initConfigParams);
					conf.setInt("ipc.client.connect.max.retries.on.timeouts", 3);
					JobClient jobClient  = new JobClient(new InetSocketAddress(initConfigParams.getHadoopIpAddress(),
							initConfigParams.getiJobTrackerPort()), conf);
					
					ClusterStatus clusterStatus = jobClient.getClusterStatus(true);
					res.put("maxMapTasks", clusterStatus.getMaxMapTasks());
					res.put("mapTasks", clusterStatus.getMapTasks());
					res.put("maxReduceTasks", clusterStatus.getMaxReduceTasks());
					res.put("reduceTasks", clusterStatus.getReduceTasks());
					res.put("usedMemory", clusterStatus.getUsedMemory() / 1000000);
					res.put("maxMemory", clusterStatus.getMaxMemory() / 1000000);
					return true;
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res.toString();
	}

	@RequestMapping(value = "/testReturn")
	public String testReturn(@RequestParam("id") int id){
		if (id % 2 == 0) {
			
			return "403";
		}else {
			return "adminQueueSetting";
		}
		
	}
	@RequestMapping(value = "/testRedirect")
	public String testRedirect(){
	
		return "redirect:/";
	}
	
	@RequestMapping(value = "/testSelfRedirect")
	public String testSelfRedirect(){
	
		return "redirect:/admin/hadoop/testSelfRedirect";
	}
	
	
	@RequestMapping(value = "/testForward")
	public String testForward(@RequestParam("id") int id){
		System.out.println("testForward: " + id);
		return "forward:/admin/hadoop/receiveReq";
	}
	
	@ResponseBody
	@RequestMapping(value = "/receiveReq")
	public String receiveReq(@RequestParam("id") int id){
		System.out.println("receiveReq: " + id);
		return "Hello from receiveReq";
	}

}