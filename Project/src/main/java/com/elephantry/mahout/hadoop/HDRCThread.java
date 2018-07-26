/**
 * @author datdh
 * */
package com.elephantry.mahout.hadoop;

import java.io.File;
import java.security.PrivilegedExceptionAction;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.util.ToolRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.elephantry.entity.Evaluation;
import com.elephantry.entity.Notification;
import com.elephantry.entity.Recommendation;
import com.elephantry.mahout.hadoop.util.ArgumentBuilder;
import com.elephantry.mahout.hadoop.util.HDConfig;
import com.elephantry.mahout.hadoop.util.HDHelper;
import com.elephantry.mahout.hadoop.util.JobHelper;
import com.elephantry.mahout.hadoop.util.REvaluator;
import com.elephantry.model.E;
import com.elephantry.model.InitConfigParams;
import com.elephantry.service.ConfigurationService;
import com.elephantry.service.EvaluationService;
import com.elephantry.service.NotificationService;
import com.elephantry.service.RecommendationService;
import com.elephantry.service.impl.ConfigurationServiceImpl;
import com.elephantry.service.impl.EvaluationServiceImpl;
import com.elephantry.service.impl.NotificationServiceImpl;
import com.elephantry.service.impl.RecommendationServiceImpl;
import com.elephantry.util.InitConstant;
import com.elephantry.util.MailHelper;

public class HDRCThread extends Thread {

	private ArgumentBuilder argumentBuilder;
	private Configuration configuration;
	private Recommendation recommendation;
	private E.RStatus status;
	private int result;
	private ElephantryRecommenderJob job;

	public HDRCThread(ArgumentBuilder argumentBuilder, Configuration configuration, Recommendation recommendation) {
		this.argumentBuilder = argumentBuilder;
		this.configuration = configuration;
		this.recommendation = recommendation;
		this.result = -1;
	}

	@Override
	public void run() {

		System.out.println("Start thread recommendation!");
		
		ApplicationContext applicationContext = null;
		RecommendationService recommendationService = null;
		NotificationService notificationService = null;
		Evaluation evaluation = null;
		double trainingPercentage = HDConfig.TRAINING_PERCENTAGE_DEFAULT;
		
		final InitConfigParams configParams = HDHelper.getInitConfigParams();
		Notification notificationStart = new Notification();
		Notification notificationCompleted = new Notification();
		try {
			applicationContext = ContextLoader.getCurrentWebApplicationContext();
			recommendationService = (RecommendationServiceImpl) applicationContext.getBean("recommendationService");
			notificationService = (NotificationServiceImpl) applicationContext.getBean("notificationService");
			EvaluationService evaluationService = (EvaluationServiceImpl) applicationContext.getBean("evaluationService");
			ConfigurationService configurationService = (ConfigurationServiceImpl) applicationContext.getBean("configurationService");
			evaluation = evaluationService.findById(recommendation.getRecommendationId());
			trainingPercentage = configurationService.getDouble(HDConfig.TRAINING_PERCENTAGE, HDConfig.TRAINING_PERCENTAGE_DEFAULT);

			recommendation.start();
			status = E.RStatus.Running;
			recommendation.setRecommendationStatusId(status.getValue());
			recommendationService.update(recommendation);

			notificationStart.setUserId(recommendation.getCustomer().getCustomerId());
			notificationCompleted.setUserId(recommendation.getCustomer().getCustomerId());

			if (checkForInputData(recommendation)) {
				/* Run recommendation */
				try {
					notificationStart.setNotiTypeId(E.NType.RStartRunning.getValue());
					notificationStart.setMessage("Your recommendation '" + recommendation.getName() + "' was started!");
					notificationService.save(notificationStart);

					UserGroupInformation ugi = UserGroupInformation.createProxyUser(configParams.getHadoopUser(),
							UserGroupInformation.getLoginUser());
					ugi.doAs(new PrivilegedExceptionAction<Boolean>() {

						public Boolean run() throws Exception {

							FileSystem fs = FileSystem.get(HDHelper.getDefaultConf(configParams));

							JobHelper.addHdfsJarsToDistributedCache(
									fs.getWorkingDirectory().toString() + configParams.getHdfsJarLibsDir(),
									configuration);
							job = new ElephantryRecommenderJob();
							result = ToolRunner.run(configuration, job, argumentBuilder.getArguments());

							return true;
						}
					});
					
					if(evaluation != null && REvaluator.splitDataSet(recommendation, trainingPercentage) && checkForRMSEInputData(recommendation)){
						/* Run RMSE */
						try {
							
							UserGroupInformation ugi1 = UserGroupInformation.createProxyUser(configParams.getHadoopUser(),
									UserGroupInformation.getLoginUser());
							ugi1.doAs(new PrivilegedExceptionAction<Boolean>() {

								public Boolean run() throws Exception {

									FileSystem fs = FileSystem.get(HDHelper.getDefaultConf(configParams));
									String workingDir = fs.getWorkingDirectory().toString();
									JobHelper.addHdfsJarsToDistributedCache(
											fs.getWorkingDirectory().toString() + configParams.getHdfsJarLibsDir(),
											configuration);
									ElephantryRecommenderJob job1 = new ElephantryRecommenderJob();
									ArgumentBuilder argumentBuilder1 = new ArgumentBuilder();
									argumentBuilder1.set(HDConfig.MAPRED_INPUT_DIR + workingDir + recommendation.getFolderInputURL() + "rmsetraining")
											.set(HDConfig.MAPRED_OUTPUT_DIR + workingDir + recommendation.getFolderOutputURL()+ "rmsetraining")
											.set(HDConfig.SIMALARITY_CLASS, "SIMILARITY_PEARSON_CORRELATION")
											.set(HDConfig.TEMP_DIR, workingDir + recommendation.getFolderInputURL() + "temprmse")
											.set(HDConfig.NUM_RECOMMENDATION, recommendation.getNumOfItem())
											.set(HDConfig.R_THRESHOLD, recommendation.getThreshold())
											;
									ToolRunner.run(configuration, job1, argumentBuilder1.getArguments());

									return true;
								}
							});
							
							REvaluator.copyTrainingFromHdfs(recommendation);
							double[] results = REvaluator.rmseNmae(recommendation);
							evaluation.setRMSE(results[0]);
							evaluation.setMAE(results[1]);
							evaluationService.update(evaluation);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} /* end run rmse */
				} catch (Exception e) {
					notificationCompleted.setNotiTypeId(E.NType.RFailed.getValue());
					notificationCompleted.setMessage(e.getMessage());
					job.addLog(e.getMessage());
					e.printStackTrace();
				}
				
			} else { // no input file for job
				job.addLog("No data set");
				notificationCompleted.setNotiTypeId(E.NType.RFailed.getValue());
				notificationCompleted.setMessage("No data set");
			}

				System.out.println("Succeed: " + (result == 0));

			status = result == 0 ? E.RStatus.Completed : E.RStatus.Failed;
			recommendation.setRecommendationStatusId(status.getValue());
			if (status == E.RStatus.Completed) {
				job.addLog("Started copy result from HDFS");
				HDHelper.copyResultFromHdfs(recommendation);
				job.addLog("Finished copy result from HDFS");
				recommendation.finish();
				notificationCompleted.setNotiTypeId(E.NType.RFinished.getValue());
				notificationCompleted
						.setMessage("Your recommendation '" + recommendation.getName() + "' was finished!");
				MailHelper.sendMailRecommendationFinished(recommendation.getCustomer().getUser().getEmail(), recommendation.getName());
			}else if(status == E.RStatus.Failed){
				recommendation.increaseFailedCount();
				job.addLog("Recommendation was failed!");
			}
			recommendationService.update(recommendation);
			notificationService.save(notificationCompleted);
			
			HDHelper.writeLogs2File(job.getLogs(),
					InitConstant.webServerRootPath + "/logs" + recommendation.getFolderOutputURL() + "log");
		
		} catch (Exception e) {
			e.printStackTrace();
			status = E.RStatus.Failed;
			if (recommendationService != null) {
				recommendation.increaseFailedCount();
				recommendation.setRecommendationStatusId(status.getValue());
				recommendationService.update(recommendation);
			}
		}
		System.out.println("End thread recommendation!");
	}

	public boolean checkForRMSEInputData(Recommendation r) {
		return checkForInputData(r, "rmsetraining");
	}
	
	public boolean checkForInputData(Recommendation r) {
		return checkForInputData(r, "");
	}
	
	private boolean checkForInputData(Recommendation r, String rmsePath) {
		boolean result = true;
		try {
			InitConfigParams configParams = HDHelper.getInitConfigParams();
			UserGroupInformation ugi = UserGroupInformation.createProxyUser(configParams.getHadoopUser(),
					UserGroupInformation.getLoginUser());
			ugi.doAs(new PrivilegedExceptionAction<Boolean>() {
				public Boolean run() throws Exception {
					FileSystem fs = FileSystem.get(HDHelper.getDefaultConf(configParams));
					Path hdfsPath = new Path(fs.getWorkingDirectory().toString() + r.getFolderInputURL() + rmsePath);

					if (!fs.exists(hdfsPath)) {
						fs.mkdirs(hdfsPath);
					}
					String uploadedDirPath = InitConstant.webServerRootPath + InitConstant.UPLOADED_DIR_PATH
							+ r.getFolderInputURL() + rmsePath;
					FileStatus[] fileStatus = fs.listStatus(hdfsPath);
					File uploadedDir = new File(uploadedDirPath);

					// get file count of hdfs and uploaded dir
					int hdfsFileCount = fileStatus.length;
					int uploadedFileCount = 0;
					File[] files = null;
					if (uploadedDir.isDirectory()) {
						files = uploadedDir.listFiles();
						uploadedFileCount = files.length;
					}

					if (uploadedFileCount == 0 || !fs.exists(hdfsPath)) {
						throw new Exception("No uploaded file");
					} else if (hdfsFileCount != uploadedFileCount) {
						if (hdfsFileCount > uploadedFileCount) {
							fs.delete(hdfsPath, true);
							fs.mkdirs(hdfsPath);
						}
						if (files != null) {
							Path[] localPaths = new Path[uploadedFileCount];
							for (int i = 0; i < files.length; i++) {
								localPaths[i] = new Path(files[i].getPath());
								System.out.println(files[i].getPath());
							}
							fs.copyFromLocalFile(false, true, localPaths, hdfsPath);
						}

					}
					return true;
				}
			});

		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}

		return result;
	}
	
	

	public Recommendation getRC() {
		return recommendation;
	}

	public E.RStatus getStatus() {
		return status;
	}
}
