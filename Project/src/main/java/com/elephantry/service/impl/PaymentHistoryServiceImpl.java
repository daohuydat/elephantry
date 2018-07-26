package com.elephantry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elephantry.dao.PaymentHistoryDAO;
import com.elephantry.entity.PaymentHistory;
import com.elephantry.service.PaymentHistoryService;

@Service
public class PaymentHistoryServiceImpl implements PaymentHistoryService {

	@Autowired
	private PaymentHistoryDAO paymentHistoryDAO;
	
	
	@Override
	public Integer save(PaymentHistory paymentHistory) {
		Integer result = 0;
		try {
			result = paymentHistoryDAO.save(paymentHistory);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<PaymentHistory> findbyCustomer(int customerId) {
		try {
			return paymentHistoryDAO.findbyCustomer(customerId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void update(PaymentHistory paymentHistory) {
		try {
			paymentHistoryDAO.saveOrUpdate(paymentHistory);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
