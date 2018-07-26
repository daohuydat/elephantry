/**
 * @author datdh
 * */
package com.elephantry.background;

import java.net.InetSocketAddress;
import java.security.PrivilegedExceptionAction;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.ClusterStatus;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.security.UserGroupInformation;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.elephantry.entity.Evaluation;
import com.elephantry.entity.Recommendation;
import com.elephantry.mahout.hadoop.HDRCThread;
import com.elephantry.mahout.hadoop.util.ArgumentBuilder;
import com.elephantry.mahout.hadoop.util.HDConfig;
import com.elephantry.mahout.hadoop.util.HDFinalQueue;
import com.elephantry.mahout.hadoop.util.HDHelper;
import com.elephantry.mahout.hadoop.util.HDPreparedQueue;
import com.elephantry.mahout.hadoop.util.HDQueueOperation;
import com.elephantry.mahout.hadoop.util.HDQueueOperation.QueueStatus;
import com.elephantry.mahout.hadoop.util.HDRunningList;
import com.elephantry.mahout.hadoop.util.HDSubmittedQueue;
import com.elephantry.model.E;
import com.elephantry.model.InitConfigParams;
import com.elephantry.service.EvaluationService;
import com.elephantry.service.RecommendationService;
import com.elephantry.service.impl.EvaluationServiceImpl;
import com.elephantry.service.impl.RecommendationServiceImpl;

/**
 * Application Lifecycle Listener implementation class BackgroundJobManager
 *
 */
public class BackgroundJobManager implements ServletContextListener {

	private JobClient jobClient;
	private ClusterStatus clusterStatus;

	private ScheduledExecutorService scheduler;
	private HDFinalQueue hdFinalQueue;
	private HDPreparedQueue hdPreparedQueue;
	private HDSubmittedQueue hdSubmittedQueue;

	private HDQueueOperation hdQueueOperation;
	private HDRunningList hdRunningList;

	private InitConfigParams configParams;
	private Configuration defaultConf;

	private void init() {
		configParams = HDHelper.getInitConfigParams();
		defaultConf = HDHelper.getDefaultConf(configParams);

		hdSubmittedQueue = HDSubmittedQueue.getInstance();
		hdPreparedQueue = HDPreparedQueue.getInstance();
		hdFinalQueue = HDFinalQueue.getInstance();

		hdRunningList = HDRunningList.getInstance();

		hdQueueOperation = HDQueueOperation.getInstance();
		// check init status
		if (configParams.isStartQueueAtStartup()) {
			hdSubmittedQueue.fetchDataFromDB(hdQueueOperation.isFetchRunning());
			if (hdQueueOperation.isFetchRunning()) {
				hdQueueOperation.invalidateFetchRunning();
			}
			hdQueueOperation.setQueueStatus(QueueStatus.STARTED);
		}

		hdPreparedQueue.setSubmittedQueue(hdSubmittedQueue);
		hdFinalQueue.setPreparedQueue(hdPreparedQueue);

	}

	public BackgroundJobManager() {

	}

	public void contextDestroyed(ServletContextEvent sce) {
		if (scheduler != null) {
			scheduler.shutdownNow();
		}
	}

	public void contextInitialized(ServletContextEvent sce) {
		init();
		scheduler = Executors.newSingleThreadScheduledExecutor();

		scheduler.scheduleAtFixedRate(new HadoopJob(), 20, 30, TimeUnit.SECONDS);
		scheduler.scheduleAtFixedRate(new TimerSubmittingJob(), 1, 5, TimeUnit.MINUTES);
		scheduler.scheduleAtFixedRate(new FailedRecommendationJob(), 1, 5, TimeUnit.MINUTES);
	}

	private boolean loadClusterStatus() {
		try {
			UserGroupInformation ugi = UserGroupInformation.createProxyUser(configParams.getHadoopUser(),
					UserGroupInformation.getLoginUser());
			ugi.doAs(new PrivilegedExceptionAction<Boolean>() {
				public Boolean run() throws Exception {
					jobClient = new JobClient(
							new InetSocketAddress(configParams.getHadoopIpAddress(), configParams.getiJobTrackerPort()),
							defaultConf);
					clusterStatus = jobClient.getClusterStatus(true);
					return true;
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	class HadoopJob implements Runnable {

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		@Override
		public void run() {
			try {

				System.out.println("HadoopJob background job: " + dateFormat.format(new Date()));
				hdRunningList.finishedScan();

				if (hdQueueOperation.getQueueStatus() == QueueStatus.STARTED && !hdRunningList.isReachMaxSize()
						&& isFreeSlot()) {
					estimateTime();
					runNext(hdFinalQueue.getNext());
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		private boolean runNext(Recommendation r) {
			if (r != null) {
				try {
					UserGroupInformation ugi = UserGroupInformation.createProxyUser(configParams.getHadoopUser(),
							UserGroupInformation.getLoginUser());
					ugi.doAs(new PrivilegedExceptionAction<Boolean>() {
						public Boolean run() throws Exception {
							FileSystem fs = FileSystem.get(defaultConf);
							String workingDir = fs.getWorkingDirectory().toString();

							Path path = new Path(workingDir + r.getFolderOutputURL());
							if (fs.exists(path)) {
								fs.delete(path, true);
							}
							path = new Path(workingDir + r.getFolderInputURL() + "temp");
							if (fs.exists(path)) {
								fs.delete(path, true);
							}
							Evaluation evaluation = getEvaluation(r.getRecommendationId());
							if (evaluation != null) { /* run evaluation */
								path = new Path(workingDir + r.getFolderOutputURL() + "rmse");
								if (fs.exists(path)) {
									fs.delete(path, true);
								}
								path = new Path(workingDir + r.getFolderInputURL() + "temprmse");
								if (fs.exists(path)) {
									fs.delete(path, true);
								}
							}

							ArgumentBuilder argumentBuilder = new ArgumentBuilder();
							argumentBuilder.set(HDConfig.MAPRED_INPUT_DIR + workingDir + r.getFolderInputURL())
									.set(HDConfig.MAPRED_OUTPUT_DIR + workingDir + r.getFolderOutputURL())
									.set(HDConfig.SIMALARITY_CLASS, "SIMILARITY_PEARSON_CORRELATION")
									.set(HDConfig.TEMP_DIR, workingDir + r.getFolderInputURL() + "temp")
									.set(HDConfig.NUM_RECOMMENDATION, r.getNumOfItem())
									.set(HDConfig.R_THRESHOLD, r.getThreshold());

							HDRCThread thread = new HDRCThread(argumentBuilder, defaultConf, r);
							thread.setName(r.getName());
							hdRunningList.add(thread);
							thread.start();
							return true;
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
			return true;
		}

		private boolean isFreeSlot() {
			if (loadClusterStatus()) {
				try {
					int freeMapSlots = clusterStatus.getMaxMapTasks() - clusterStatus.getMapTasks();
					int freeReduceSlots = clusterStatus.getMaxReduceTasks() - clusterStatus.getReduceTasks();

					return freeMapSlots >= 2 && freeReduceSlots >= 1;
				} catch (Exception e) {
					clusterStatus = null;
					e.printStackTrace();
				}
			}
			return false;
		}

		private Evaluation getEvaluation(Integer recommendationId) {
			ApplicationContext applicationContext = null;
			Evaluation evaluation = null;
			try {
				applicationContext = ContextLoader.getCurrentWebApplicationContext();
				EvaluationService evaluationService = (EvaluationServiceImpl) applicationContext
						.getBean("evaluationService");
				evaluation = evaluationService.findById(recommendationId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return evaluation;
		}

		public void estimateTime() {

			ApplicationContext applicationContext = null;
			try {
				applicationContext = ContextLoader.getCurrentWebApplicationContext();
				RecommendationService recommendationService = (RecommendationServiceImpl) applicationContext
						.getBean("recommendationService");
				double avgSecondPerRow = recommendationService.getAvgSecondPerRow();
				double accrualDuration = 0; /* in minutes */
				Recommendation r = null;
				if (hdQueueOperation.getQueueStatus() == QueueStatus.STARTED
						|| hdQueueOperation.getQueueStatus() == QueueStatus.PAUSED) {

					List<Recommendation> recommendations = hdFinalQueue.getAll();
					for (int i = 0; i < recommendations.size(); i++) {
						r = recommendations.get(i);
						accrualDuration += r.getRecordCount() * avgSecondPerRow / 60;
						r.setEstimatedDuration(accrualDuration);
						recommendationService.update(r);
					}

					recommendations = hdPreparedQueue.getAll();
					for (int i = 0; i < recommendations.size(); i++) {
						r = recommendations.get(i);
						accrualDuration += r.getRecordCount() * avgSecondPerRow / 60;
						r.setEstimatedDuration(accrualDuration);
						recommendationService.update(r);
					}

					recommendations = hdSubmittedQueue.getAllHigh();
					for (int i = 0; i < recommendations.size(); i++) {
						r = recommendations.get(i);
						accrualDuration += r.getRecordCount() * avgSecondPerRow / 60;
						r.setEstimatedDuration(accrualDuration);
						recommendationService.update(r);
					}

					recommendations = hdSubmittedQueue.getAllNormal();
					for (int i = 0; i < recommendations.size(); i++) {
						r = recommendations.get(i);
						accrualDuration += r.getRecordCount() * avgSecondPerRow / 60;
						r.setEstimatedDuration(accrualDuration);
						recommendationService.update(r);
					}

					recommendations = hdSubmittedQueue.getAllLow();
					for (int i = 0; i < recommendations.size(); i++) {
						r = recommendations.get(i);
						accrualDuration += r.getRecordCount() * avgSecondPerRow / 60;
						r.setEstimatedDuration(accrualDuration);
						recommendationService.update(r);
					}
					hdQueueOperation.setEstimatedMinute(accrualDuration);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	class TimerSubmittingJob implements Runnable {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		@Override
		public void run() {
			try {
				Date date = new Date();
				System.out.println("TimerSubmittingJob background job: " + dateFormat.format(date));
				if (hdQueueOperation.getQueueStatus() == QueueStatus.STARTED) {
					submitWaitingTimerRecommendations();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public boolean submitWaitingTimerRecommendations() {
			ApplicationContext applicationContext = null;
			try {
				applicationContext = ContextLoader.getCurrentWebApplicationContext();
				RecommendationService recommendationService = (RecommendationServiceImpl) applicationContext
						.getBean("recommendationService");
				List<Recommendation> list = recommendationService.findWaitingTimerRecommendations();
				if (list != null) {
					double avgSecondPerRow = recommendationService.getAvgSecondPerRow();
					double accrualDuration = hdQueueOperation
							.getEstimatedMinute(); /* in minutes */
					for (Recommendation r : list) {
						if (r.getPriority() == E.RPriority.LOW.getValue()) {
							hdSubmittedQueue.addLow(r);
						} else if (r.getPriority() == E.RPriority.NORMAL.getValue()) {
							hdSubmittedQueue.addNormal(r);
						} else if (r.getPriority() == E.RPriority.HIGH.getValue()) {
							hdSubmittedQueue.addHigh(r);
						}
						accrualDuration += r.getRecordCount() * avgSecondPerRow / 60;
						r.setEstimatedDuration(accrualDuration);

						r.setRecommendationStatusId(E.RStatus.Submitted.getValue());
						recommendationService.update(r);
					}
					hdQueueOperation.setEstimatedMinute(accrualDuration);
				}

			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
	}

	class FailedRecommendationJob implements Runnable {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		@Override
		public void run() {
			try {
				Date date = new Date();
				System.out.println("FailedRecommendationJob background job: " + dateFormat.format(date));
				if (hdQueueOperation.getQueueStatus() == QueueStatus.STARTED) {
					runFailedRecommendationsAgain();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public boolean runFailedRecommendationsAgain() {
			ApplicationContext applicationContext = null;
			try {
				applicationContext = ContextLoader.getCurrentWebApplicationContext();
				RecommendationService recommendationService = (RecommendationServiceImpl) applicationContext
						.getBean("recommendationService");
				List<Recommendation> list = recommendationService.findFailedRecommendations(3);
				if (list != null) {
					for (Recommendation r : list) {
						hdPreparedQueue.add(r);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
	}
}
