package com.elephantry.dao;

import java.util.List;

import com.elephantry.entity.PaymentHistory;

public interface PaymentHistoryDAO extends GenericDAO<PaymentHistory, Integer> {
		List<PaymentHistory> findbyCustomer(int customerId);
}
