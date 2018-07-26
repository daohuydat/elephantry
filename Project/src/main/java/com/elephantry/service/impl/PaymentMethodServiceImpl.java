package com.elephantry.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elephantry.dao.PaymentMethodDAO;
import com.elephantry.entity.PaymentMethod;
import com.elephantry.service.PaymentMethodService;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

	@Autowired
	private PaymentMethodDAO paymentMethodDAO;
	
	@Override
	public PaymentMethod findById(int id) {
		PaymentMethod paymentMethod = null;
		try {
			paymentMethod =  paymentMethodDAO.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paymentMethod;
	}

}
