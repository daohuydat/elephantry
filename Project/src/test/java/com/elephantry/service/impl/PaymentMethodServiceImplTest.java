package com.elephantry.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Matchers.intThat;

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

import com.elephantry.entity.PaymentHistory;
import com.elephantry.entity.PaymentMethod;
import com.elephantry.service.PaymentMethodService;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class PaymentMethodServiceImplTest {
	
	@Autowired
	private PaymentMethodService paymentMethodService;

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
	public void testPaymentMethodfindById() {
		PaymentMethod paymentMethod =  paymentMethodService.findById(1);
		assertEquals(1, paymentMethod.getPaymentMethodId());
		assertEquals("Paypal", paymentMethod.getPaymentMethodName());
	}
	
	@Test
	public void testPaymentMethodfindByIdNotNull() {
		PaymentMethod paymentMethod =  paymentMethodService.findById(1);
		assertNotNull(paymentMethod);
	}
}
