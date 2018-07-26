/**
 * @author datdh
 * */
package com.elephantry.mahout.hadoop.util;

public interface HDConfig {
	
	String FS_DEFAULT_NAME = "fs.default.name";
	String MAPRED_JOB_TRACKER = "mapred.job.tracker";
	String MAPRED_INPUT_DIR= "-Dmapred.input.dir=";
	String MAPRED_OUTPUT_DIR= "-Dmapred.output.dir=";
	String SIMALARITY_CLASS = "-s";
	String TEMP_DIR = "--tempDir";
	String NUM_RECOMMENDATION = "--numRecommendations";
	String R_THRESHOLD = "--threshold";
	
	/* Configuration table key */
	String THRESHOLD = "elephantry.threshold";
	double THRESHOLD_DEFAULT = 0.0;
	String PRIORITY_SCALE = "elephantry.priority.scale";
	int PRIORITY_SCALE_DEFAULT = 2;
	String PREPARED_QUEUE_MIN_SIZE = "elephantry.queue.prepared.size.min";
	int PREPARED_QUEUE_MIN_SIZE_DEFAULT = 20;
	String FINAL_QUEUE_MAX_SIZE = "elephantry.queue.final.size.max";
	int FINAL_QUEUE_MAX_SIZE_DEFAULT = 4;
	String RUNNING_MAX_SIZE = "elephantry.running.size.max";
	int RUNNING_MAX_SIZE_DEFAULT = 4;
	String TRAINING_PERCENTAGE = "elephantry.training.percentage";
	double TRAINING_PERCENTAGE_DEFAULT = 0.7;
	
}
