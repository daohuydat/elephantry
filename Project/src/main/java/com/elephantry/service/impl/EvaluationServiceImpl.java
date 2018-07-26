package com.elephantry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elephantry.dao.EvaluationDAO;
import com.elephantry.entity.Evaluation;
import com.elephantry.entity.EvaluationRecommendation;
import com.elephantry.service.EvaluationService;

@Service
public class EvaluationServiceImpl implements EvaluationService {

	@Autowired
	private EvaluationDAO evaluationDAO;
	
	@Override
	public Integer save(Evaluation entity) {
		try {
			return evaluationDAO.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Evaluation findById(Integer id) {
		try {
			return evaluationDAO.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void update(Evaluation entity) {
		try {
			evaluationDAO.saveOrUpdate(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<EvaluationRecommendation> findAllLastEvaluation(Integer customerId, int last) {
		try {
			return evaluationDAO.findAllLastEvaluation(customerId, last);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
