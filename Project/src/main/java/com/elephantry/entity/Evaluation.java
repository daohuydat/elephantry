package com.elephantry.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "evaluation")
@Table(name = "evaluation")
public class Evaluation {
	@Id
	@Column(name = "RecommendationId")
	private int recommendationId;

	@Column(name = "RMSE")
	private double RMSE;

	@Column(name = "MAE")
	private double MAE;
	
	public Evaluation() {
		RMSE = MAE = -1.0;
	}

	public int getRecommendationId() {
		return recommendationId;
	}

	public void setRecommendationId(int recommendationId) {
		this.recommendationId = recommendationId;
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
