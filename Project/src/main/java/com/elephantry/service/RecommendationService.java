package com.elephantry.service;

import java.util.List;
import java.util.Map;

import com.elephantry.entity.Recommendation;

public interface RecommendationService {
	List<Recommendation> findAll();
	List<Recommendation> findRecommendationByStatus(Integer status);
	List<Recommendation> findRecommendationByCustomerId(Integer cusId);
	List<Recommendation>  getRecommendationByCustomerIdAndStatus(Integer cusId, Integer status);
	Recommendation findById(Integer id);
	void update(Recommendation recommendation);
	Integer save(Recommendation recommendation);
	List<Recommendation> findWaitingTimerRecommendations();
	double getAvgSecondPerRow();
	List<Recommendation> findFailedRecommendations(Integer maxFailedCount);
	List<Recommendation> searchRecommendation(int customerId, int statusId, String startDate, String endDate , String keyword, int pageNum, int itemPerPage) throws Exception;
	int getRecommendationCount(int customerId, int statusId, String startDate, String endDate, String keyword);
	int countRecommendationLastMonthOfCustomer(Integer customerId);
	Map<String, Integer> countByMonth(Integer numOfMonth);
	Map<String, Integer> countByMonth(Integer numOfMonth, Integer customerId);
	int countAllRecommendations();
	String compareRecommendationWeekAgo();
	int countWaiting(Integer customerId);
	int countSubmitted(Integer customerId);
	int countRunning(Integer customerId);
	int countCompleted(Integer customerId);
}
