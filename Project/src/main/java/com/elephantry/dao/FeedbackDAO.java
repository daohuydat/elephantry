package com.elephantry.dao;

import java.util.List;

import com.elephantry.entity.Feedback;

public interface FeedbackDAO extends GenericDAO<Feedback, Integer>{
	List<Feedback> findByCreatedTime(String orderDesc, int pageNum, int itemPerPage, int read);

	int countByStatus(Integer read);
}
