package com.elephantry.dao;

import java.util.List;

import com.elephantry.entity.Notification;

public interface NotificationDAO  extends GenericDAO<Notification, Integer>{
	List<Notification> findByCustomerId(int customerId);
}
