package com.elephantry.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.elephantry.dao.AbstractGenericDAO;
import com.elephantry.dao.NotificationDAO;
import com.elephantry.entity.Feedback;
import com.elephantry.entity.Notification;

@Repository
@Transactional
public class NotificationDAOImpl extends AbstractGenericDAO<Notification, Integer> implements NotificationDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<Notification> findByCustomerId(int customerId) {
		Query query = getSession().createSQLQuery("select * from notification where UserId = :customerId and `Read` = :read order by CreatedTime DESC")
				.addEntity(Notification.class);
		query.setInteger("customerId", customerId);
		query.setInteger("read", 0);
		return query.list();
	}

}
