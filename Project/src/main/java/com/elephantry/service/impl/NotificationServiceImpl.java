package com.elephantry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elephantry.dao.NotificationDAO;
import com.elephantry.entity.Notification;
import com.elephantry.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {
	
	@Autowired
	private NotificationDAO notificationDAO;

	@Override
	public Integer save(Notification entity) {
		Integer result = 0;
		try {
			result = notificationDAO.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Notification> findbyCustomerId(Integer customerId) {
		try {
			return notificationDAO.findByCustomerId(customerId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
