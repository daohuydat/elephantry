/**
 * @author datdh
 * */

package com.elephantry.mahout.hadoop.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.elephantry.entity.Recommendation;
import com.elephantry.service.ConfigurationService;
import com.elephantry.service.impl.ConfigurationServiceImpl;

public class HDFinalQueue {
	private static HDFinalQueue instance;

	private Queue<Recommendation> queue;
	private HDPreparedQueue hdPreparedQueue;

	private HDFinalQueue() {
		queue = new LinkedList<>();
	}

	public static HDFinalQueue getInstance() {

		if (instance == null) {
			synchronized (HDFinalQueue.class) {
				if (instance == null) {
					instance = new HDFinalQueue();
				}
			}
		}
		return instance;
	}

	public void setPreparedQueue(HDPreparedQueue hdPreparedQueue) {
		this.hdPreparedQueue = hdPreparedQueue;
	}

	public int getMaxFinalSize() {
		ApplicationContext applicationContext = null;
		try {
			applicationContext = ContextLoader.getCurrentWebApplicationContext();
			ConfigurationService configurationService = 
					(ConfigurationServiceImpl) applicationContext.getBean("configurationService");
			return configurationService.getInt(HDConfig.FINAL_QUEUE_MAX_SIZE, HDConfig.FINAL_QUEUE_MAX_SIZE_DEFAULT);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return HDConfig.FINAL_QUEUE_MAX_SIZE_DEFAULT;
	}

	public void clearData(){
		try {			
			queue.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Recommendation getNext() {
		int maxFinalSize = getMaxFinalSize();
		if (queue.size() <= maxFinalSize / 2) {
			fillFromPreparedQueue(maxFinalSize);
		}
		return queue.poll();
	}

	private void fillFromPreparedQueue(int maxFinalSize) {
		if (hdPreparedQueue.isEmpty()) {
			hdPreparedQueue.fillFromSubmittedQueue();
		}
		int fillSize = maxFinalSize - queue.size();
		if (fillSize > 0) {
			Recommendation r = null;
			for (int i = 0; i < fillSize && !hdPreparedQueue.isEmpty(); i++) {
				r = hdPreparedQueue.poll();
				if (r != null) {
					queue.add(r);
				}
			}
		}
	}

	public List<Recommendation> getAll() {
		return (LinkedList<Recommendation>) queue;
	}

	public synchronized boolean add(Recommendation s) {
		queue.add(s);
		return true;
	}

	public synchronized Recommendation remove() {
		return queue.remove();
	}

	public boolean isEmpty() {
		return queue.isEmpty();
	}

	public int size() {
		return queue.size();
	}

}
