package com.elephantry.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.Calendar;

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

import com.elephantry.entity.Customer;
import com.elephantry.entity.Feedback;
import com.elephantry.service.CustomerService;
import com.elephantry.service.FeedbackService;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class FeedbackServiceImplTest {

	@Autowired
	private FeedbackService feedbackService;
	
	@Autowired
	private CustomerService customerService;
	
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
	public void testFindAndSave() {
		Feedback f = new Feedback();
		Customer c = customerService.findAll().get(0);
		f.setContent("for test");
		f.setCustomer(c);
		feedbackService.save(f);
		f.getFeedbackId();
		assertEquals("for test", feedbackService.findById(f.getFeedbackId()).getContent());
		assertEquals(c.getCustomerId(), feedbackService.findById(f.getFeedbackId()).getCustomer().getCustomerId());
	}
	@Test
	public void testFindAndSaveException() {
		Feedback f = null;
		feedbackService.save(f);
	}
	@Test
	@Transactional
	@Rollback(true)
	public void testFindByCreatedTime(){
		Feedback f1 = new Feedback();
		Feedback f2 = new Feedback();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		Customer c = customerService.findAll().get(0);
		f1.setContent("first");
		f1.setCustomer(c);
		f1.setRead(0);
		f2.setContent("second");
		f2.setCustomer(c);
		f2.setRead(1);
		f2.setCreatedTime(cal.getTime());
		feedbackService.save(f1);
		feedbackService.save(f2);
		assertNotNull(feedbackService.findUnReadByCreatedTime("ASC", 1, 5));
		assertNotNull(feedbackService.findReadByCreatedTime("ASC", 1, 5));
		assertEquals("second", feedbackService.findReadByCreatedTime("0", 1, 5).get(0).getContent());
	}
	@Test
	public void testFindByCreatedTimeException() {
		feedbackService.findUnReadByCreatedTime(null, 1, 5);
		feedbackService.findReadByCreatedTime(null, 1, 5);
	}
	@Test
	public void testFindByIdException() {
		feedbackService.findById(null);
	}
	@Test
	@Transactional
	@Rollback(true)
	public void testCount() {
		Feedback f1 = new Feedback();
		Feedback f2 = new Feedback();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		Customer c = customerService.findAll().get(0);
		f1.setContent("first");
		f1.setCustomer(c);
		f1.setRead(0);
		f2.setContent("second");
		f2.setCustomer(c);
		f2.setRead(1);
		f2.setCreatedTime(cal.getTime());
		feedbackService.save(f1);
		feedbackService.save(f2);
		assertNotNull(feedbackService.countRead());
		assertNotNull(feedbackService.countUnread());
	}
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdate(){
		Feedback f1 = new Feedback();
		Customer c = customerService.findAll().get(0);
		f1.setContent("first");
		f1.setCustomer(c);
		f1.setRead(0);
		feedbackService.save(f1);
		f1.setContent("second");
		feedbackService.update(f1);
		assertEquals("second", feedbackService.findById(f1.getFeedbackId()).getContent());
	}
	@Test
	public void testUpdateException() {
		feedbackService.update(null);
	}
}
