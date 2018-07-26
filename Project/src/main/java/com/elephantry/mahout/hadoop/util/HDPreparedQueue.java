/**
 * @author datdh
 * */
package com.elephantry.mahout.hadoop.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.elephantry.entity.Recommendation;

public class HDPreparedQueue {
	private static HDPreparedQueue instance;

	private Queue<Recommendation> queue;

	private HDSubmittedQueue hdSubmittedQueue;

	private HDPreparedQueue() {
		queue = new LinkedList<>();
	}

	public static HDPreparedQueue getInstance() {

		if (instance == null) {
			synchronized (HDPreparedQueue.class) {
				if (instance == null) {
					instance = new HDPreparedQueue();
				}
			}
		}
		return instance;
	}

	public void setSubmittedQueue(HDSubmittedQueue hdSubmittedQueue) {
		this.hdSubmittedQueue = hdSubmittedQueue;
	}

	public void fillFromSubmittedQueue() {
		try {
			List<Recommendation> recommendations =  hdSubmittedQueue.getPreparedQueue();
			for(Recommendation r : recommendations){
				queue.add(r);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void clearData(){
		try {
			queue.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Recommendation> getAll(){
		return (LinkedList<Recommendation>) queue;
	}

	public boolean add(Recommendation s) {
		queue.add(s);
		return true;
	}

	public Recommendation remove() {
		return queue.remove();
	}

	public Recommendation poll() {
		return queue.poll();
	}

	public Recommendation peek() {
		return queue.peek();
	}

	public boolean isEmpty() {
		return queue.isEmpty();
	}

	public int size() {
		return queue.size();
	}

}
