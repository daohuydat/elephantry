package com.elephantry.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.elephantry.dao.AbstractGenericDAO;
import com.elephantry.dao.PaymentHistoryDAO;
import com.elephantry.entity.PaymentHistory;

@Repository
@Transactional
public class PaymentHistoryDAOImpl extends AbstractGenericDAO<PaymentHistory, Integer> implements PaymentHistoryDAO {

	@Override
	public List<PaymentHistory> findbyCustomer(int customerId) {
		Query query = getSession().createQuery("from paymenthistory where CustomerId = :customerId order by CreatedTime asc");
		query.setInteger("customerId", customerId);
		return query.list();
	}
	
}
