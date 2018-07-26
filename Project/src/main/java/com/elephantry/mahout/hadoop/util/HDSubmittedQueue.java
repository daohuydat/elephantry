/**
 * @author datdh
 * */
package com.elephantry.mahout.hadoop.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.elephantry.entity.Recommendation;
import com.elephantry.model.E;
import com.elephantry.service.ConfigurationService;
import com.elephantry.service.RecommendationService;
import com.elephantry.service.impl.ConfigurationServiceImpl;
import com.elephantry.service.impl.RecommendationServiceImpl;

public class HDSubmittedQueue {
	private static HDSubmittedQueue instance;

	private Queue<Recommendation> queueLow;
	private Queue<Recommendation> queueNormal;
	private Queue<Recommendation> queueHigh;

	private HDSubmittedQueue() {
		queueLow = new LinkedList<>();
		queueNormal = new LinkedList<>();
		queueHigh = new LinkedList<>();
	}

	public static HDSubmittedQueue getInstance() {

		if (instance == null) {
			synchronized (HDSubmittedQueue.class) {
				if (instance == null) {
					instance = new HDSubmittedQueue();
				}
			}
		}
		return instance;
	}

	public List<Recommendation> getPreparedQueue() {
		int minSize = getMinSize();
		List<Recommendation> result = new ArrayList<>();
		while (getTotalSize() > 0 && result.size() < minSize) {
			result.addAll(computePreparedQueue());
		}

		return result;
	}

	public void clearData() {
		try {
			queueHigh.clear();
			queueLow.clear();
			queueNormal.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean fetchDataFromDB(boolean fetchRunning) {
		ApplicationContext applicationContext = null;
		try {
			applicationContext = ContextLoader.getCurrentWebApplicationContext();
			RecommendationService recommendationService = 
					(RecommendationServiceImpl) applicationContext.getBean("recommendationService");
			
			List<Recommendation> list = recommendationService.findRecommendationByStatus(E.RStatus.Failed.getValue());
			if (list != null) {
				for (Recommendation r : list) {
					if (r.getFailedCount() > 1) {
						continue;
					}
					if (E.RPriority.LOW.getValue() == r.getPriority()) {
						addLow(r);
					} else if (E.RPriority.NORMAL.getValue() == r.getPriority()) {
						addNormal(r);
					} else if (E.RPriority.HIGH.getValue() == r.getPriority()) {
						addHigh(r);
					}
				}
			}
			if (fetchRunning) {
				list = recommendationService.findRecommendationByStatus(E.RStatus.Running.getValue());
				if (list != null) {
					for (Recommendation r : list) {
						if (E.RPriority.LOW.getValue() == r.getPriority()) {
							addLow(r);
						} else if (E.RPriority.NORMAL.getValue() == r.getPriority()) {
							addNormal(r);
						} else if (E.RPriority.HIGH.getValue() == r.getPriority()) {
							addHigh(r);
						}
					}
				}				
			}
			list = recommendationService.findRecommendationByStatus(E.RStatus.Submitted.getValue());
			if (list != null) {
				for (Recommendation r : list) {
					if (E.RPriority.LOW.getValue() == r.getPriority()) {
						addLow(r);
					} else if (E.RPriority.NORMAL.getValue() == r.getPriority()) {
						addNormal(r);
					} else if (E.RPriority.HIGH.getValue() == r.getPriority()) {
						addHigh(r);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	

	public List<Recommendation> getAllLow() {
		return (LinkedList<Recommendation>) queueLow;
	}

	public List<Recommendation> getAllNormal() {
		return (LinkedList<Recommendation>) queueNormal;
	}

	public List<Recommendation> getAllHigh() {
		return (LinkedList<Recommendation>) queueHigh;
	}

	private int getPriorityScale(){
		ApplicationContext applicationContext = null;
		try {
			applicationContext = ContextLoader.getCurrentWebApplicationContext();
			ConfigurationService configurationService = 
					(ConfigurationServiceImpl) applicationContext.getBean("configurationService");
			return configurationService.getInt(HDConfig.PRIORITY_SCALE, HDConfig.PRIORITY_SCALE_DEFAULT);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return HDConfig.PRIORITY_SCALE_DEFAULT;
	}
	
	private int getMinSize(){
		ApplicationContext applicationContext = null;
		try {
			applicationContext = ContextLoader.getCurrentWebApplicationContext();
			ConfigurationService configurationService = 
					(ConfigurationServiceImpl) applicationContext.getBean("configurationService");
			return configurationService.getInt(HDConfig.PREPARED_QUEUE_MIN_SIZE, HDConfig.PREPARED_QUEUE_MIN_SIZE_DEFAULT);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return HDConfig.PREPARED_QUEUE_MIN_SIZE_DEFAULT;
	}
	
	private List<Recommendation> computePreparedQueue() {
		int priorityScale = getPriorityScale();
		List<Recommendation> result = new ArrayList<>();

		int queueNormalSize = queueNormal.size();
		int queueHighSize = queueHigh.size();

		Queue<Recommendation> lowTemp = new LinkedList<>();
		Queue<Recommendation> normalTemp = new LinkedList<>();
		Queue<Recommendation> highTemp = new LinkedList<>();
		// case 1:
		// if (queueHighSize <= queueNormalSize && queueNormalSize <=
		// queueLowSize) {

		int sizeTemp = 0;
		if (!queueHigh.isEmpty()) {
			sizeTemp = queueHighSize < 2 * priorityScale ? queueHighSize : 2 * priorityScale;
			try {
				for (int i = 0; i < sizeTemp; i++) {
					highTemp.add(queueHigh.remove());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (!queueNormal.isEmpty()) {
			sizeTemp = queueNormalSize < priorityScale ? queueNormalSize : priorityScale;
			try {
				for (int i = 0; i < sizeTemp; i++) {
					normalTemp.add(queueNormal.remove());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (!queueLow.isEmpty()) {
			try {
				lowTemp.add(queueLow.remove());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// get data here
		for (int i = 0; i < priorityScale; i++) {
			for (int j = 0; j < priorityScale; j++) {
				if (!highTemp.isEmpty()) {
					result.add(highTemp.poll());
				} else
					break;
			}
			if (!normalTemp.isEmpty()) {
				result.add(normalTemp.poll());
			}
		}
		if (!lowTemp.isEmpty()) {
			result.add(lowTemp.poll());
		}
		// case 2:
		// }
		return result;
	}

	public int getTotalSize() {
		return queueLow.size() + queueNormal.size() + queueHigh.size();
	}

	public synchronized boolean addLow(Recommendation s) {
		queueLow.add(s);
		return true;
	}

	public synchronized Recommendation removeLow() {
		return queueLow.remove();
	}

	public boolean isEmptyLow() {
		return queueLow.isEmpty();
	}

	public int sizeLow() {
		return queueLow.size();
	}

	public synchronized boolean addNormal(Recommendation s) {
		queueNormal.add(s);
		return true;
	}

	public synchronized Recommendation removeNormal() {
		return queueNormal.remove();
	}

	public boolean isEmptyNormal() {
		return queueNormal.isEmpty();
	}

	public int sizeNormal() {
		return queueNormal.size();
	}

	public synchronized boolean addHigh(Recommendation s) {
		queueHigh.add(s);
		return true;
	}

	public synchronized Recommendation removeHigh() {
		return queueHigh.remove();
	}

	public boolean isEmptyHigh() {
		return queueHigh.isEmpty();
	}

	public int sizeHigh() {
		return queueHigh.size();
	}
}
