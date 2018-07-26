package com.elephantry.service.impl;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elephantry.dao.RecommendationDAO;
import com.elephantry.entity.Recommendation;
import com.elephantry.service.RecommendationService;


@Service
public class RecommendationServiceImpl implements RecommendationService {

	@Autowired
	private RecommendationDAO recommendationDAO;
	
	@Override
	public List<Recommendation> findAll() {
		// TODO Auto-generated method stub
		return recommendationDAO.findAll();
	}

	@Override
	public List<Recommendation> findRecommendationByStatus(Integer status) {
		List<Recommendation> list = null;
		try {
			list = recommendationDAO.findRecommendationByStatus(status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void update(Recommendation recommendation) {
		try {
			recommendationDAO.saveOrUpdate(recommendation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Integer save(Recommendation recommendation) {
		// TODO Auto-generated method stub
		Integer result = 0;
		try {
			result = recommendationDAO.save(recommendation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Recommendation findById(Integer id) {
		Recommendation recommendation = null;
		try {
			recommendation =  recommendationDAO.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recommendation;
	}

	@Override
	public List<Recommendation> getRecommendationByCustomerIdAndStatus(Integer cusId, Integer status) {
		List<Recommendation> list = null;
		try {
			list =  recommendationDAO.findRecommendationByCustomerIdAndStatus(cusId, status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Recommendation> findWaitingTimerRecommendations() {
		List<Recommendation> list = null;
		try {
			list =  recommendationDAO.findWaitingTimerRecommendations();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public double getAvgSecondPerRow() {
		double time = 10;
		try {
			time = recommendationDAO.getAvgSecondPerRow();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}

	@Override
	public List<Recommendation> findFailedRecommendations(Integer maxFailedCount) {
		List<Recommendation> list = null;
		try {
			list =  recommendationDAO.findFailedRecommendations(maxFailedCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Recommendation> findRecommendationByCustomerId(Integer cusId) {
		List<Recommendation> list = null;
		try {
			list =  recommendationDAO.findRecommendationByCustomerId(cusId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Recommendation> searchRecommendation(int customerId, int statusId, String startDate, String endDate, String keyword, int pageNum,
			int itemPerPage) throws Exception {
		List<Recommendation> listRecommendation = null;
		try {
			listRecommendation = recommendationDAO.searchRecommendation(customerId, statusId ,startDate , endDate, keyword, pageNum, itemPerPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listRecommendation;
	}

	@Override
	public int getRecommendationCount(int customerId, int statusId, String startDate, String endDate, String keyword) {
		int recommendationCount = 0;
		try {
			recommendationCount = recommendationDAO.getRecommendationCount(customerId, statusId, startDate , endDate, keyword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recommendationCount;
	}
	
	@Override
	public Map<String, Integer> countByMonth(Integer numOfMonth) {
		try {
			return recommendationDAO.countbyMonth(numOfMonth);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Map<String, Integer> countByMonth(Integer numOfMonth, Integer customerId) {
		try {
			return recommendationDAO.countbyMonth(numOfMonth, customerId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int countAllRecommendations() {
		try {
			return recommendationDAO.countAllRecommendations();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return 0;
	}

	@Override
	public String compareRecommendationWeekAgo() {
		try {
			DecimalFormat df = new DecimalFormat("#.##");
			return df.format(recommendationDAO.compareRecommendationWeekAgo());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "0.0";
	}

	@Override
	public int countRecommendationLastMonthOfCustomer(Integer customerId) {
		try {
			return recommendationDAO.countRecommendationLastMonthOfCustomer(customerId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int countWaiting(Integer customerId) {
		try {
			return recommendationDAO.countWaiting(customerId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int countSubmitted(Integer customerId) {
		try {
			return recommendationDAO.countSubmitted(customerId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int countRunning(Integer customerId) {
		try {
			return recommendationDAO.countRunning(customerId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int countCompleted(Integer customerId) {
		try {
			return recommendationDAO.countCompleted(customerId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
