package com.elephantry.service;

import java.util.List;

import com.elephantry.entity.Notification;

public interface NotificationService {
	Integer save(Notification entity);
	List<Notification> findbyCustomerId(Integer customerId);
}
