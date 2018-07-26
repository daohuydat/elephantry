package com.elephantry.service.impl;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elephantry.dao.NotificationDAO;
import com.elephantry.entity.Notification;
import com.elephantry.service.NotificationService;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class NotificationServiceImplTest {
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private NotificationDAO notificationDAO;
	
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
	public void testSave() {
		Notification n = new Notification();
		n.setMessage("message test");
		n.setUserId(3);
		n.setNotiTypeId(1);
		n.setRead(0);
		Integer id = notificationService.save(n);
		Notification n2 = notificationDAO.findById(id);
		assertEquals("message test", n2.getMessage());
		assertEquals(0, n2.getRead());
		assertEquals(1, n2.getNotiTypeId());
		assertEquals(3, n2.getUserId());
	}
	@Test
	public void testSaveEx() {
		Integer i = 0;
		assertEquals(i, notificationService.save(null));
	}
}
