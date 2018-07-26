package com.elephantry.service.impl;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elephantry.dao.PaymentHistoryDAO;
import com.elephantry.entity.Customer;
import com.elephantry.entity.Package;
import com.elephantry.entity.PaymentHistory;
import com.elephantry.entity.PaymentMethod;
import com.elephantry.service.CustomerService;
import com.elephantry.service.PaymentHistoryService;
import com.elephantry.service.PaymentMethodService;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class PaymentHistoryServiceImplTest {

	@Autowired
	private PaymentHistoryService paymentHistoryService;

	@Autowired
	private PaymentMethodService paymentMethodService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private PaymentHistoryDAO paymentHistoryDAO;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testSavePaymentHistoryWithEx() {
		PaymentHistory paymentHistory = new PaymentHistory();
		paymentHistory.setAmount(100.0);
		paymentHistory.setCustomerId(1);
		PaymentMethod paymentMethod = new PaymentMethod();
		paymentMethod = paymentMethodService.findById(1);
		System.out.println(paymentMethod);
		System.out.println(paymentMethodService);
		paymentHistory.setPaymentMethod(paymentMethod);
		Integer a = paymentHistoryService.save(paymentHistory);
		assertTrue(a == 0);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testSavePaymentHistory() {
		PaymentHistory paymentHistory = new PaymentHistory();

		List<Customer> lsCustomer = customerService.findAll();
		if (lsCustomer.size() > 0) {
			Customer customer = lsCustomer.get(0);
			paymentHistory.setAmount(100.0);
			paymentHistory.setCustomerId(customer.getCustomerId());
			PaymentMethod paymentMethod = new PaymentMethod();
			paymentMethod = paymentMethodService.findById(1);
			paymentHistory.setPaymentMethod(paymentMethod);
			Integer a = paymentHistoryService.save(paymentHistory);
			assertTrue(a > 0);
			PaymentHistory paymentHistory2 = paymentHistoryDAO.findById(a);
			assertTrue(100.0 == paymentHistory2.getAmount());
			assertTrue(paymentMethod.getPaymentMethodId() == paymentHistory2.getPaymentMethod().getPaymentMethodId());
			assertTrue(customer.getCustomerId() == paymentHistory2.getCustomerId());
		}

	}
}
