package com.elephantry.mahout.hadoop.util;

import java.util.LinkedList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.elephantry.mahout.hadoop.HDRCThread;
import com.elephantry.model.E;
import com.elephantry.service.ConfigurationService;
import com.elephantry.service.impl.ConfigurationServiceImpl;

public class HDRunningList {
	private static HDRunningList instance;
	
	private List<HDRCThread> threads;
	
	private HDRunningList(){
		threads = new LinkedList<>();
	}
	
	public static HDRunningList getInstance() {

		if (instance == null) {
			synchronized (HDRunningList.class) {
				if (instance == null) {
					instance = new HDRunningList();
				}
			}
		}
		return instance;
	}
	
	public boolean add(HDRCThread thread){
		return threads.add(thread);
	}
	
	public boolean isReachMaxSize(){
		ApplicationContext applicationContext = null;
		try {
			applicationContext = ContextLoader.getCurrentWebApplicationContext();
			ConfigurationService configurationService = 
					(ConfigurationServiceImpl) applicationContext.getBean("configurationService");
			return threads.size() >= configurationService.getInt(HDConfig.RUNNING_MAX_SIZE, HDConfig.RUNNING_MAX_SIZE_DEFAULT);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public List<HDRCThread> getAll(){
		return threads;
	}
	
	public void finishedScan(){
		try {
			for (int i = 0; i < threads.size(); i++) {
				HDRCThread t = threads.get(i);
				if (t.getStatus()==E.RStatus.Completed || t.getStatus() == E.RStatus.Failed) {
					threads.remove(i);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int size(){
		return threads.size();
	}
}
