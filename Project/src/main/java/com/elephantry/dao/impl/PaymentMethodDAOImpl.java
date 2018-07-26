package com.elephantry.dao.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.elephantry.dao.AbstractGenericDAO;
import com.elephantry.dao.PaymentMethodDAO;
import com.elephantry.entity.PaymentMethod;

@Repository
@Transactional
public class PaymentMethodDAOImpl extends AbstractGenericDAO<PaymentMethod, Integer> implements PaymentMethodDAO{

}
