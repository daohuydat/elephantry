package com.elephantry.service;

import java.util.List;

import com.elephantry.entity.PaymentHistory;

public interface PaymentHistoryService {
	Integer save(PaymentHistory paymentHistory);
	List<PaymentHistory> findbyCustomer(int customerId);
	void update(PaymentHistory paymentHistory);
}
