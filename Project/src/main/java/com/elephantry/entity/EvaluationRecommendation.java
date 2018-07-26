package com.elephantry.entity;

public class EvaluationRecommendation {
	private int recommendationId;
	private String name;
	private double threshold;
	private double RMSE;
	private double MAE;
	public int getRecommendationId() {
		return recommendationId;
	}
	public void setRecommendationId(int recommendationId) {
		this.recommendationId = recommendationId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getThreshold() {
		return threshold;
	}
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}
	public double getRMSE() {
		return RMSE;
	}
	public void setRMSE(double rMSE) {
		RMSE = rMSE;
	}
	public double getMAE() {
		return MAE;
	}
	public void setMAE(double mAE) {
		MAE = mAE;
	}
	
	
}
