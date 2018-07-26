package com.elephantry.mahout.hadoop.util;

import javax.servlet.http.HttpSession;

import com.elephantry.model.InitConfigParams;

public class HDQueueOperation {

	private static HDQueueOperation instance;
	private QueueStatus queueStatus;
	private double estimatedMinute;
	private boolean fetchRunning;

	public boolean isFetchRunning() {
		return fetchRunning;
	}

	public void invalidateFetchRunning() {
		this.fetchRunning = false;
	}

	public enum QueueStatus {
		STARTED(1), PAUSED(2), STOPPED(3);
		private final int value;

		private QueueStatus(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

	}

	private HDQueueOperation() {
		queueStatus = QueueStatus.STOPPED;
		estimatedMinute = 10;
		fetchRunning = true;
	}

	public static HDQueueOperation getInstance() {

		if (instance == null) {
			synchronized (HDQueueOperation.class) {
				if (instance == null) {
					instance = new HDQueueOperation();
				}
			}
		}
		return instance;
	}

	public boolean startQueue(HttpSession ss, InitConfigParams configParams) {
		boolean result = false;
		if (queueStatus == QueueStatus.STOPPED) {
			result = 
			HDHelper.loadLibrary2HDFS(ss.getServletContext(), configParams);
			if (result) {
				initAllQueue();
				setQueueStatus(QueueStatus.STARTED);
			}
		}
		return result && queueStatus == QueueStatus.STARTED;
	}

	public boolean stopQueue() {
		if (queueStatus != QueueStatus.STOPPED) {
			HDSubmittedQueue.getInstance().clearData();
			HDPreparedQueue.getInstance().clearData();
			HDFinalQueue.getInstance().clearData();
			setQueueStatus(QueueStatus.STOPPED);
		}
		return queueStatus == QueueStatus.STOPPED;
	}

	public boolean resetQueue(HttpSession ss, InitConfigParams configParams) {
		if (queueStatus != QueueStatus.STOPPED) {
			setQueueStatus(QueueStatus.STOPPED);
			HDSubmittedQueue.getInstance().clearData();
			HDPreparedQueue.getInstance().clearData();
			HDFinalQueue.getInstance().clearData();

			HDHelper.loadLibrary2HDFS(ss.getServletContext(), configParams);
			initAllQueue();
			setQueueStatus(QueueStatus.STARTED);
		}
		return queueStatus == QueueStatus.STARTED;
	}

	public boolean resumeQueue() {
		if (queueStatus == QueueStatus.PAUSED) {
			setQueueStatus(QueueStatus.STARTED);
		}
		return queueStatus == QueueStatus.STARTED;
	}

	public boolean pauseQueue() {
		if (queueStatus == QueueStatus.STARTED) {
			setQueueStatus(QueueStatus.PAUSED);
		}
		return queueStatus == QueueStatus.PAUSED;
	}

	public QueueStatus getQueueStatus() {
		return queueStatus;
	}

	public void setQueueStatus(QueueStatus queueStatus) {
		this.queueStatus = queueStatus;
	}

	public void setQueueStatus(int queueStatus) {
		if (QueueStatus.STARTED.getValue() == queueStatus) {
			this.queueStatus = QueueStatus.STARTED;
		} else if (QueueStatus.PAUSED.getValue() == queueStatus) {
			this.queueStatus = QueueStatus.PAUSED;
		} else if (QueueStatus.STOPPED.getValue() == queueStatus) {
			this.queueStatus = QueueStatus.STOPPED;
		}

	}

	private void initAllQueue() {

		HDSubmittedQueue hdSubmittedQueue = HDSubmittedQueue.getInstance();
		HDPreparedQueue hdPreparedQueue = HDPreparedQueue.getInstance();
		HDFinalQueue hdFinalQueue = HDFinalQueue.getInstance();
		HDQueueOperation hdQueueOperation = HDQueueOperation.getInstance();

		hdSubmittedQueue.clearData();
		hdPreparedQueue.clearData();
		hdFinalQueue.clearData();

		hdSubmittedQueue.fetchDataFromDB(hdQueueOperation.isFetchRunning());
		if (hdQueueOperation.isFetchRunning()) {
			hdQueueOperation.invalidateFetchRunning();
		}

		hdPreparedQueue.setSubmittedQueue(hdSubmittedQueue);
		hdFinalQueue.setPreparedQueue(hdPreparedQueue);
	}

	public double getEstimatedMinute() {
		return estimatedMinute;
	}

	public void setEstimatedMinute(double estimatedMinute) {
		this.estimatedMinute = estimatedMinute;
	}

	public static void setInstance(HDQueueOperation instance) {
		HDQueueOperation.instance = instance;
	}

	
}
