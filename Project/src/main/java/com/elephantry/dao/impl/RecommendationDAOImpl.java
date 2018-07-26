package com.elephantry.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.elephantry.dao.AbstractGenericDAO;
import com.elephantry.dao.RecommendationDAO;
import com.elephantry.entity.Customer;
import com.elephantry.entity.Recommendation;
import com.elephantry.model.E;

@Repository
@Transactional
public class RecommendationDAOImpl extends AbstractGenericDAO<Recommendation, Integer> implements RecommendationDAO {
	
	
	
	@Override
	public List<Recommendation> listRecommendation() {
			List<Recommendation> list = findAll();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Recommendation> findRecommendationByStatus(int status) {
		Query query = getSession().createQuery("from recommendation where RecommendationStatusId = :status  ");
		query.setInteger("status", status);
		return query.list();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Recommendation> findRecommendationByCustomerIdAndStatus(int cusId, int status) {
		Query query = getSession().createQuery("from recommendation where  CustomerId = :cusId and RecommendationStatusId = :status");
		query.setInteger("cusId", cusId);
		query.setInteger("status", status);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Recommendation> findWaitingTimerRecommendations() {
		Query query = getSession().createQuery("from recommendation where RecommendationStatusId = 1 and Timer <= now() ");
		return query.list(); 
	}



	@Override
	public double getAvgSecondPerRow() {
		Query query = getSession()
			.createSQLQuery("select " +
			" avg(timestampdiff(second, sub.StartedTime, sub.FinishedTime) / sub.RecordCount) as SecondPerRow "+
			" from (select StartedTime, FinishedTime, RecordCount from recommendation " +
				" where RecommendationStatusId = 4 " +
				" order by RecommendationId desc limit 10) as sub ;");
		Number avg = (Number) query.uniqueResult();
		return avg == null ? 10 : avg.doubleValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Recommendation> findFailedRecommendations(int maxFailedCount) {
		Query query = getSession().createQuery("from recommendation where RecommendationStatusId = 5 and FailedCount <= :failedCount order by Priority desc ");
		query.setInteger("failedCount", maxFailedCount);
		return query.list(); 
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Recommendation> findRecommendationByCustomerId(int cusId) {
		Query query = getSession().createQuery("from recommendation where  CustomerId = :cusId");
		query.setInteger("cusId", cusId);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Recommendation> searchRecommendation(int customerId, int statusId, String startDate, String endDate, String keyword, int pageNum, int itemPerPage) throws Exception {
		if (pageNum<1) {
			throw new Exception("pagenum smaller than 1");
		}
		keyword = keyword.trim();
		Query query = null;
		if (keyword.isEmpty()) {
			int start = (pageNum-1)*itemPerPage;
			String orderColumn = "CreatedTime";
			if(statusId == E.RStatus.Running.getValue()){
				orderColumn = "StartedTime";
			}
			if(statusId == E.RStatus.Completed.getValue()){
				orderColumn = "FinishedTime";
			}
			query = getSession().createQuery("from recommendation where CustomerId = :customerId AND RecommendationStatusId= :statusId "
					+ "AND CreatedTime BETWEEN '"+startDate+" 00:00:00' AND '"+endDate+" 23:59:59' order by "+orderColumn+" desc");
//			query = getSession().createSQLQuery("s1elect {r.*}  from Recommendation r "
//					+ " WHERE MATCH(Name) AGAINST(:keyword IN NATURAL LANGUAGE MODE) AND r.CustomerId = :customerId AND r.RecommendationStatusId= :statusId")
//					.addEntity("r", Recommendation.class);
			
			query.setInteger("customerId", customerId);
			query.setInteger("statusId", statusId);
			query.setFirstResult(start);
			query.setMaxResults(itemPerPage);
			
		}
		
		if (!keyword.isEmpty()) {
			int start = (pageNum-1)*itemPerPage;
			String orderColumn = "CreatedTime";
			if(statusId == E.RStatus.Running.getValue()){
				orderColumn = "StartedTime";
			}
			if(statusId == E.RStatus.Completed.getValue()){
				orderColumn = "FinishedTime";
			}
			query = getSession().createQuery("from recommendation where CustomerId = :customerId AND RecommendationStatusId= :statusId and Name like :keyword "
					+ "AND CreatedTime BETWEEN '"+startDate+" 00:00:00' AND '"+endDate+" 23:59:59' order by "+orderColumn+" desc");
//			query = getSession().createSQLQuery("select {r.*}  from Recommendation r "
//					+ " WHERE MATCH(Name) AGAINST(:keyword IN NATURAL LANGUAGE MODE) AND r.CustomerId = :customerId AND r.RecommendationStatusId= :statusId")
//					.addEntity("r", Recommendation.class);
			query.setInteger("customerId", customerId);
			query.setString("keyword", "%" + keyword + "%");
			query.setInteger("statusId", statusId);
			query.setFirstResult(start);
			query.setMaxResults(itemPerPage);
		}
		
		
		return query.list();
	}

	@Override
	public int getRecommendationCount(int customerId, int statusId , String startDate, String endDate, String keyword) {
		keyword = keyword.trim();
		Query query = null;
		if (keyword.isEmpty() ) {
			query = getSession().createQuery("select count(*) from recommendation where CustomerId = :customerId AND RecommendationStatusId= :statusId "
					+ "AND CreatedTime BETWEEN '"+startDate+" 00:00:00' AND '"+endDate+" 23:59:59'");
//			query = getSession().createSQLQuery("select count(*),  from Recommendation "
//					+ " WHERE MATCH(Name) AGAINST(:keyword IN NATURAL LANGUAGE MODE)  AND CustomerId = :customerId AND RecommendationStatusId= :statusId");
//			query.setString("startDate", startDate);
//			query.setString("endDate", endDate);
			query.setInteger("customerId", customerId);
			query.setInteger("statusId", statusId);
		}
		
		if (!keyword.isEmpty()) {
			query = getSession().createQuery("select count(*) from recommendation where CustomerId = :customerId AND RecommendationStatusId= :statusId and Name like :keyword"
					+ " AND CreatedTime BETWEEN '"+startDate+" 00:00:00' AND '"+endDate+" 23:59:59'");
//			query = getSession().createSQLQuery("select count(*),  from Recommendation "
//					+ " WHERE MATCH(Name) AGAINST(:keyword IN NATURAL LANGUAGE MODE)  AND CustomerId = :customerId AND RecommendationStatusId= :statusId");
//			query.setString("startDate", startDate);
//			query.setString("endDate", endDate);
			query.setInteger("customerId", customerId);
			query.setString("keyword", "%" + keyword + "%");
			query.setInteger("statusId", statusId);
		}
		Number count = (Number) query.uniqueResult();
		return count == null ? 0 : count.intValue();
	}
	@Override
	public Map<String, Integer> countbyMonth(int numOfMonth) {
		Query query = getSession().createSQLQuery("SELECT year(CreatedTime), month(CreatedTime), COUNT(RecommendationId)" 
				+" FROM recommendation WHERE CreatedTime > DATE_SUB(NOW(), INTERVAL :numOfMonth month)"
				+" GROUP BY month(CreatedTime), year(CreatedTime);");
		query.setInteger("numOfMonth", numOfMonth+1);
		Map<String, Integer> map = new HashMap<>();
		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.list();
		for (Object[] row : rows) {
			map.put(row[0]+"-"+row[1], Integer.parseInt(row[2].toString()));
		}
		return map;
	}

	@Override
	public Map<String, Integer> countbyMonth(int numOfMonth, int customerId) {
		Query query = getSession().createSQLQuery("SELECT year(CreatedTime), month(CreatedTime), COUNT(RecommendationId)"
				+" FROM recommendation WHERE CustomerId = :customerId AND CreatedTime > DATE_SUB(NOW(), INTERVAL :numOfMonth month) "
				+" GROUP BY month(CreatedTime), year(CreatedTime);");
		query.setInteger("customerId", customerId);
		query.setInteger("numOfMonth", numOfMonth+1);
		Map<String, Integer> map = new HashMap<>();
		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.list();
		for (Object[] row : rows) {
			map.put(row[0]+"-"+row[1], Integer.parseInt(row[2].toString()));
		}
		return map;
	}

	
	@Override
	public int countAllRecommendations() {
		Query query = getSession().createSQLQuery("select count(RecommendationId) from recommendation");
		Number count = (Number) query.uniqueResult();
		return count == null ? 0 : count.intValue();
	}

	@Override
	public double compareRecommendationWeekAgo() {
		Query query = getSession().createSQLQuery("select count(RecommendationId) from recommendation where CreatedTime <= DATE_SUB(NOW(), INTERVAL 1 week)");
		Number result = (Number) query.uniqueResult();
		if (result!=null) {
			return ((countAllRecommendations()-result.intValue())*1.0/result.intValue()) * 100;
		}
		return 0;
	}

	@Override
	public int countRecommendationLastMonthOfCustomer(Integer customerId) {
		Query query = getSession().createSQLQuery("select count(RecommendationId) from recommendation where CreatedTime >= DATE_SUB(NOW(), INTERVAL 1 month)"
				+ " AND CustomerId = :customerId");
		query.setInteger("customerId", customerId);
		Number count = (Number) query.uniqueResult();
		return count == null ? 0 : count.intValue();
	}
//cho nay lam voi qua viet tam
	@Override
	public int countWaiting(Integer customerId) {
		Query query = getSession().createSQLQuery("select count(RecommendationId) from recommendation where RecommendationStatusId = 1 and CustomerId = :customerId");
		query.setInteger("customerId", customerId);
		Number count = (Number) query.uniqueResult();
		return count == null ? 0 : count.intValue();
	}

	@Override
	public int countSubmitted(Integer customerId) {
		Query query = getSession().createSQLQuery("select count(RecommendationId) from recommendation where RecommendationStatusId = 2 and CustomerId = :customerId");
		query.setInteger("customerId", customerId);
		Number count = (Number) query.uniqueResult();
		return count == null ? 0 : count.intValue();
	}

	@Override
	public int countRunning(Integer customerId) {
		Query query = getSession().createSQLQuery("select count(RecommendationId) from recommendation where RecommendationStatusId = 3 and CustomerId = :customerId");
		query.setInteger("customerId", customerId);
		Number count = (Number) query.uniqueResult();
		return count == null ? 0 : count.intValue();
	}

	@Override
	public int countCompleted(Integer customerId) {
		Query query = getSession().createSQLQuery("select count(RecommendationId) from recommendation where RecommendationStatusId = 4 and CustomerId = :customerId");
		query.setInteger("customerId", customerId);
		Number count = (Number) query.uniqueResult();
		return count == null ? 0 : count.intValue();
	}
}
