package com.elephantry.dao.impl;

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

import com.elephantry.dao.CustomerDAO;
import com.elephantry.dao.FeedbackDAO;
import com.elephantry.entity.Customer;
import com.elephantry.entity.Feedback;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class FeedbackDAOImplTest {
	
	@Autowired
	private FeedbackDAO feedbackDAO;
	
	@Autowired
	private CustomerDAO customerDAO;
	
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
	public void testCountbyCreateTime(){
		Feedback f = new Feedback();
		Customer c = customerDAO.findAll().get(0);
		f.setContent("for test");
		f.setCustomer(c);
		feedbackDAO.save(f);
		assertNotNull(feedbackDAO.countByStatus(0));
		assertEquals(0, f.getRead());
		assertEquals("for test", f.getContent());
	}
	@Test
	@Transactional
	@Rollback(true)
	public void testFindByCreatedTime(){
		Feedback f1 = new Feedback();
		Feedback f2 = new Feedback();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);
		Customer c = customerDAO.findAll().get(0);
		f1.setContent("first");
		f1.setCustomer(c);
		f1.setRead(0);
		f2.setContent("second");
		f2.setCustomer(c);
		f2.setCreatedTime(cal.getTime());
		f2.setRead(0);
		feedbackDAO.save(f1);
		feedbackDAO.save(f2);
		int lf = feedbackDAO.countByStatus(0);
		assertNotNull(feedbackDAO.findByCreatedTime("ASC", 1, 5, 0));
		assertEquals("first",feedbackDAO.findByCreatedTime("ASC", 1, 5, 0).get(lf-2).getContent());
//		assertEquals("second", feedbackDAO.findByCreatedTime("ASC", 1, 5, 0).get(lf-1).getContent());
//		assertEquals("second", feedbackDAO.findByCreatedTime("1", 1, 5, 0).get(lf-2).getContent());
	}
}
