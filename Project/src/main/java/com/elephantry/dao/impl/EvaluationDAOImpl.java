package com.elephantry.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.elephantry.dao.AbstractGenericDAO;
import com.elephantry.dao.EvaluationDAO;
import com.elephantry.entity.Evaluation;
import com.elephantry.entity.EvaluationRecommendation;

@Repository
@Transactional
public class EvaluationDAOImpl extends AbstractGenericDAO<Evaluation, Integer> implements EvaluationDAO{

	@Override
	public List<EvaluationRecommendation> findAllLastEvaluation(Integer customerId, int last) {
		Query query = getSession().createSQLQuery("select r.RecommendationId, r.Name, r.Threshold, e.RMSE, e.MAE "
				+ " from recommendation r join evaluation e on r.RecommendationId = e.RecommendationId "
				+ " where r.CustomerId = :customerId order by r.RecommendationId desc ;");
		query.setInteger("customerId", customerId);
		query.setFetchSize(last);
		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.list();
		
		List<EvaluationRecommendation> evaluations = new ArrayList<>();
		EvaluationRecommendation evaluation = null;
		for (Object[] row : rows) {
			evaluation = new EvaluationRecommendation();
			try {
				evaluation.setRecommendationId(Integer.parseInt(row[0].toString()));
				evaluation.setName(row[1].toString());
				evaluation.setThreshold(Double.parseDouble(row[2].toString()));
				evaluation.setRMSE(Double.parseDouble(row[3].toString()));
				evaluation.setMAE(Double.parseDouble(row[4].toString()));
				evaluations.add(evaluation);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return evaluations;
	}

}
