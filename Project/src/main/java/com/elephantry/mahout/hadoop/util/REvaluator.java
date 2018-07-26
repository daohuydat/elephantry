package com.elephantry.mahout.hadoop.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.PrivilegedExceptionAction;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.mahout.cf.taste.common.NoSuchUserException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;

import com.elephantry.entity.Recommendation;
import com.elephantry.model.InitConfigParams;
import com.elephantry.util.InitConstant;

public class REvaluator {

	
	
	public static double[] rmseNmae(Recommendation r){
		double[] results = new double[2];
		File trainingFile  = new File(InitConstant.webServerRootPath + InitConstant.RESULT_DIR_PATH
				+ "/" + r.getFolderOutputURL() + "rmsetraining/part-r-00000");
		File testFile = new File(InitConstant.webServerRootPath + InitConstant.UPLOADED_DIR_PATH
				+r.getFolderInputURL() + "rmsetest/test.csv");
		if (trainingFile.exists() && testFile.exists()) {
			
			DataModel dataModel = null;
			BufferedReader brTraning = null;
			try {
				dataModel = new FileDataModel(testFile);
				
		 		brTraning = new BufferedReader(new FileReader(trainingFile));
		 		String readLine = "";
		 		String[] lineSplitted = null;
		 		int nCount = 0;
		 		double totalSquareError = 0;
		 		double totalError = 0;
		 		Double value = null;
		 		for (int i = 0; (readLine = brTraning.readLine()) != null; i++) {
		 			lineSplitted = readLine.split("\t");
		 			
		 			long userId = Long.parseLong(lineSplitted[0].trim());
		 			Map<String, Double> mmap = getPreference(lineSplitted[1]);
		 			try {
		 				PreferenceArray preferenceArray = dataModel.getPreferencesFromUser(userId);
		 				for (Preference preference : preferenceArray) {
							value = mmap.get(String.valueOf(preference.getItemID()));
							if (value != null) {
								totalSquareError += (preference.getValue() - value)*(preference.getValue() - value);
								totalError += Math.abs(preference.getValue() - value);
								nCount++;
							}
						}
					} catch (NoSuchUserException e) {
					}
		 			
		 		}
		 		results[0] = Math.sqrt(totalSquareError/nCount);
		 		results[1] = Math.sqrt(totalError/nCount);
		 	} catch (Exception e) {
		 		e.printStackTrace();
		 	}
		}
		
		return results;
	}
	
	private static Map<String, Double> getPreference(String preArr){
		preArr = preArr.trim().substring(preArr.indexOf("[") + 1, preArr.lastIndexOf("]"));
		Map<String, Double> treeMap = new TreeMap<String, Double>();
		String[] arr = preArr.split(",");
		String[] pairs = null;
		for (String itemValuePairs : arr) {
			pairs = itemValuePairs.split(":");
			treeMap.put(pairs[0], Double.parseDouble(pairs[1]));
		}
		return treeMap;
	}
	
	public static boolean copyTrainingFromHdfs(Recommendation r) {
		boolean result = true;
		try {
			InitConfigParams configParams = HDHelper.getInitConfigParams();
			UserGroupInformation ugi = UserGroupInformation.createProxyUser(configParams.getHadoopUser(),
					UserGroupInformation.getLoginUser());
			ugi.doAs(new PrivilegedExceptionAction<Boolean>() {
				public Boolean run() throws Exception {
					FileSystem fs = FileSystem.get(HDHelper.getDefaultConf(configParams));
					String workingDir = fs.getWorkingDirectory().toString();
					Path hdfsOutputPath = new Path(workingDir + r.getFolderOutputURL() + "rmsetraining");
					System.out.println(hdfsOutputPath);
					System.out.println(fs.exists(hdfsOutputPath));
					if (fs.exists(hdfsOutputPath)) {
						Path localOutputPath = new Path(InitConstant.webServerRootPath + InitConstant.RESULT_DIR_PATH
								+ "/" + r.getFolderOutputURL()  + "rmsetraining");
						File localOutputDir = new File(InitConstant.webServerRootPath + InitConstant.RESULT_DIR_PATH
								+ "/" + r.getFolderOutputURL() + "rmsetraining");
						if (!localOutputDir.exists()) {
							System.out.println(localOutputDir.mkdirs() + "mkdir output");
						}
						FileStatus[] fileStatus = fs.listStatus(hdfsOutputPath);
						for (int i = 0; i < fileStatus.length; i++) {
							if (!fileStatus[i].isDir() && fileStatus[i].getPath().getName().contains("part-r-00000")) {
								fs.copyToLocalFile(false, fileStatus[i].getPath(), localOutputPath);
							}
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
	
	public static boolean splitDataSet(Recommendation r, double trainingPercentage) {
		String webRootPath = InitConstant.webServerRootPath + InitConstant.UPLOADED_DIR_PATH;
		String trainingPath =webRootPath+ r.getFolderInputURL() + "rmsetraining";
		String testPath = webRootPath+r.getFolderInputURL() + "rmsetest";
		File path = new File(trainingPath);
		if (!path.exists()) {
			path.mkdirs();
		}
		path = new File(testPath);
		if (!path.exists()) {
			path.mkdirs();
		}
		
		SortedSet<Integer> testIndexs = generateRandomTestIndexs(trainingPercentage, r.getRecordCount());
		
		File trainingFile = new File(trainingPath + "/training.csv");
		File testFile = new File(testPath+ "/test.csv");
		
		File uploadedDir = new File(webRootPath+ r.getFolderInputURL());

		File[] files = null;
		if (uploadedDir.isDirectory()) {
			files = uploadedDir.listFiles();
		}
		if (files == null || files.length == 0) {
			return false;
		}

		BufferedReader br = null;
		BufferedWriter bwTraning = null;
		BufferedWriter bwTesting = null;
		
		try {
			br = new BufferedReader(new FileReader(files[0]));
			bwTraning = new BufferedWriter(new FileWriter(trainingFile));
			bwTesting = new BufferedWriter(new FileWriter(testFile));
			String readLine = "";
			Iterator<Integer> iterator = testIndexs.iterator();

			Integer index = 0;
			if (iterator.hasNext()) {
				index = iterator.next();
			}

			for (int i = 0; (readLine = br.readLine()) != null; i++) {
				if (i == index) {
					bwTesting.write(readLine);
					bwTesting.newLine();
					if (iterator.hasNext()) {
						index = iterator.next();
					}
				} else {
					bwTraning.write(readLine);
					bwTraning.newLine();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (bwTesting != null) {
					bwTesting.close();
				}
				if (bwTraning != null) {
					bwTraning.close();
				}
			} catch (Exception e2) {
			}
		}
		return true;
	}


	public static SortedSet<Integer> generateRandomTestIndexs(double trainingPercentage, int numOfRecords) {
		SortedSet<Integer> set = new TreeSet<>();
		long testSize = Math.round((1 - trainingPercentage) * numOfRecords);
		Random r = new Random();
		do {
			set.add(r.nextInt(numOfRecords));
		} while (set.size() < testSize);
		return set;
	}
}
