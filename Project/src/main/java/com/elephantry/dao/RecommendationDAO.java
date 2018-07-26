package com.elephantry.dao;

import java.util.List;
import java.util.Map;

import com.elephantry.entity.Recommendation;

public interface RecommendationDAO  extends GenericDAO<Recommendation, Integer> {
	 public  List<Recommendation> listRecommendation() ;
	 List<Recommendation> findRecommendationByStatus(int status);
	 List<Recommendation> findRecommendationByCustomerId(int cusId);
	 List<Recommendation> findRecommendationByCustomerIdAndStatus(int cusId, int status);
	 List<Recommendation> findWaitingTimerRecommendations();
	 double getAvgSecondPerRow();
	 List<Recommendation> findFailedRecommendations(int maxFailedCount);
	 List<Recommendation> searchRecommendation(int customerId, int statusId, String startDate, String endDate , String keyword, int pageNum, int itemPerPage) throws Exception;
	 int getRecommendationCount(int customerId, int statusId, String startDate, String endDate, String keyword);
	 Map<String, Integer> countbyMonth(int numOfMonth); 
	 Map<String, Integer> countbyMonth(int numOfMonth, int customerId);
	 int countAllRecommendations();
	 double compareRecommendationWeekAgo();
	 int countRecommendationLastMonthOfCustomer(Integer customerId);
	 int countWaiting(Integer customerId);
	 int countSubmitted(Integer customerId);
	 int countRunning(Integer customerId);
	 int countCompleted(Integer customerId);
}
