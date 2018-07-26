package com.elephantry.service;

import java.util.List;

import com.elephantry.entity.Feedback;

public interface FeedbackService {
	Integer save (Feedback f);
	List<Feedback> findReadByCreatedTime(String sortDesc, int pageNum, int itemPerPage);
	List<Feedback> findUnReadByCreatedTime(String sortDesc, int pageNum, int itemPerPage);
	int countRead();
	int countUnread();
	Feedback findById(Integer feedbackId);
	void update(Feedback f);
}
