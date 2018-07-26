package com.elephantry.service;

import java.util.List;

import com.elephantry.entity.Evaluation;
import com.elephantry.entity.EvaluationRecommendation;

public interface EvaluationService {
	Integer save (Evaluation entity);
	Evaluation findById(Integer id);
	void update(Evaluation entity);
	List<EvaluationRecommendation> findAllLastEvaluation(Integer customerId, int last);
}
