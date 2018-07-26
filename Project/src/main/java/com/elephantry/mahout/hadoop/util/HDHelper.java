/**
 * @author datdh
 * */
package com.elephantry.mahout.hadoop.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.elephantry.entity.Recommendation;
import com.elephantry.mahout.hadoop.util.HDQueueOperation.QueueStatus;
import com.elephantry.model.E;
import com.elephantry.model.InitConfigParams;
import com.elephantry.service.RecommendationService;
import com.elephantry.service.impl.RecommendationServiceImpl;
import com.elephantry.util.InitConstant;

public class HDHelper {

	public static Configuration getDefaultConf(InitConfigParams configParams) {
		Configuration conf = new Configuration();
		conf.set(HDConfig.FS_DEFAULT_NAME,
				"hdfs://" + configParams.getHadoopIpAddress() + ":" + configParams.getHdfsPort());
		conf.set(HDConfig.MAPRED_JOB_TRACKER,
				configParams.getHadoopIpAddress() + ":" + configParams.getJobTrackerPort());
		return conf;
	}

	public static boolean moveUploadedFileToHdfs(String localFilePath, String hdfsDir) {
		boolean result = false;

		try {
			InitConfigParams configParams = getInitConfigParams();
			UserGroupInformation ugi = UserGroupInformation.createProxyUser(configParams.getHadoopUser(),
					UserGroupInformation.getLoginUser());
			ugi.doAs(new PrivilegedExceptionAction<Boolean>() {
				public Boolean run() throws Exception {
					FileSystem fs = FileSystem.get(HDHelper.getDefaultConf(configParams));
					String workingDir = fs.getWorkingDirectory().toString();
					Path path = new Path(workingDir + hdfsDir);
					fs.mkdirs(path);

					if (fs.exists(path)) {
						Path localPath = new Path(localFilePath);
						Path hdfsPath = new Path(workingDir + hdfsDir);
						fs.copyFromLocalFile(false, true, localPath, hdfsPath);
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

	public static boolean copyResultFromHdfs(Recommendation r) {
		boolean result = false;
		try {
			InitConfigParams configParams = getInitConfigParams();
			UserGroupInformation ugi = UserGroupInformation.createProxyUser(configParams.getHadoopUser(),
					UserGroupInformation.getLoginUser());
			ugi.doAs(new PrivilegedExceptionAction<Boolean>() {
				public Boolean run() throws Exception {
					FileSystem fs = FileSystem.get(HDHelper.getDefaultConf(configParams));
					String workingDir = fs.getWorkingDirectory().toString();
					Path hdfsOutputPath = new Path(workingDir + r.getFolderOutputURL());

					if (fs.exists(hdfsOutputPath)) {
						Path localOutputPath = new Path(InitConstant.webServerRootPath + InitConstant.RESULT_DIR_PATH
								+ "/" + r.getFolderOutputURL());
						File localOutputDir = new File(InitConstant.webServerRootPath + InitConstant.RESULT_DIR_PATH
								+ "/" + r.getFolderOutputURL());
						if (!localOutputDir.exists()) {
							localOutputDir.mkdirs();
						}
						FileStatus[] fileStatus = fs.listStatus(hdfsOutputPath);
						for (int i = 0; i < fileStatus.length; i++) {
							fs.copyToLocalFile(false, fileStatus[i].getPath(), localOutputPath);
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

	public static void submit2Queue(Recommendation r) {
		if (r != null && HDQueueOperation.getInstance().getQueueStatus() != QueueStatus.STOPPED) {
			ApplicationContext applicationContext = null;
			try {
				applicationContext = ContextLoader.getCurrentWebApplicationContext();
				RecommendationService recommendationService = (RecommendationServiceImpl) applicationContext
						.getBean("recommendationService");
				
				HDSubmittedQueue submittedQueue = HDSubmittedQueue.getInstance();
				if (r.getPriority() == E.RPriority.LOW.getValue()) {
					submittedQueue.addLow(r);
				} else if (r.getPriority() == E.RPriority.NORMAL.getValue()) {
					submittedQueue.addNormal(r);
				} else if (r.getPriority() == E.RPriority.HIGH.getValue()) {
					submittedQueue.addHigh(r);
				}
				double avgSecondPerRow = recommendationService.getAvgSecondPerRow();
				double accrualDuration = HDQueueOperation.getInstance().getEstimatedMinute(); /* in minutes */
				accrualDuration += r.getRecordCount() * avgSecondPerRow / 60;
				r.setEstimatedDuration(accrualDuration);
				HDQueueOperation.getInstance().setEstimatedMinute(accrualDuration);
				recommendationService.update(r);
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	

	public static boolean loadLibrary2HDFS(ServletContext sc, InitConfigParams configParams) {
		try {

			UserGroupInformation ugi = UserGroupInformation.createProxyUser(configParams.getHadoopUser(),
					UserGroupInformation.getLoginUser());
			ugi.doAs(new PrivilegedExceptionAction<Boolean>() {
				public Boolean run() throws Exception {
					System.out.println("***************Load library to HDFS started*****************");
					String localJarsDir = sc.getRealPath("WEB-INF/lib");

					Configuration conf = HDHelper.getDefaultConf(configParams);
					FileSystem fs = FileSystem.get(conf);
					String workingDir = fs.getWorkingDirectory().toString();
					fs.mkdirs(new Path(workingDir + configParams.getHdfsJarLibsDir()));
					List<String> fileNames = new ArrayList<String>();
					fileNames.add("mahout-core-0.9.jar");
					fileNames.add("mahout-math-0.9.jar");
					fileNames.add("guava-16.0.jar");
					fileNames.add("commons-cli-2.0-mahout.jar");
					fileNames.add("commons-math3-3.2.jar");
					fileNames.add("lucene-core-4.6.1.jar");

					JobHelper.copyLocalJarsToHdfs(localJarsDir, workingDir + configParams.getHdfsJarLibsDir(),
							fileNames, conf);

					System.out.println("***************Load library to HDFS done*****************");
					return true;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void writeLogs2File(List<String> logs, String fullPath) {
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			File dir = new File(fullPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			int fileCount = 0;
			if (dir.isDirectory()) {
				String[] names = dir.list();
				if (names != null) {
					fileCount = names.length;
				}
			}
			fw = new FileWriter(fullPath + "/job" + fileCount + ".log");
			bw = new BufferedWriter(fw);
			for (String log : logs) {
				bw.write(log + "\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static InitConfigParams getInitConfigParams() {
		ApplicationContext applicationContext = null;
		InitConfigParams params = null;
		try {
			applicationContext = ContextLoader.getCurrentWebApplicationContext();
			params = (InitConfigParams) applicationContext.getBean("initConfigParams");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}
}
