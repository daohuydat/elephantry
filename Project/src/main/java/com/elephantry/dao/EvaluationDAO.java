package com.elephantry.dao;

import java.util.List;

import com.elephantry.entity.Evaluation;
import com.elephantry.entity.EvaluationRecommendation;

public interface EvaluationDAO extends GenericDAO<Evaluation, Integer>{
	List<EvaluationRecommendation> findAllLastEvaluation(Integer customerId, int last);
}
