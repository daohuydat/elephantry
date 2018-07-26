package com.elephantry.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elephantry.dao.FeedbackDAO;
import com.elephantry.entity.Feedback;
import com.elephantry.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService{
	@Autowired
	private FeedbackDAO feedbackDAO;

	@Override
	public Integer save(Feedback f) {
		try {
			return feedbackDAO.save(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<Feedback> findReadByCreatedTime(String sortDesc, int pageNum, int itemPerPage) {
		List<Feedback> listFeedback = null;
		try {
			listFeedback = feedbackDAO.findByCreatedTime(sortDesc,pageNum, itemPerPage,1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listFeedback;
	}
	
	@Override
	public List<Feedback> findUnReadByCreatedTime(String sortDesc, int pageNum, int itemPerPage) {
		List<Feedback> listFeedback = null;
		try {
			listFeedback = feedbackDAO.findByCreatedTime(sortDesc,pageNum, itemPerPage,0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listFeedback;
	}

	@Override
	public int countRead() {
		try {
			return feedbackDAO.countByStatus(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int countUnread() {
		try {
			return feedbackDAO.countByStatus(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Feedback findById(Integer feedbackId) {
		try {
			return feedbackDAO.findById(feedbackId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void update(Feedback f) {
		try {
			feedbackDAO.saveOrUpdate(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
