package com.elephantry.dao.impl;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

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

import com.elephantry.dao.UserDAO;
import com.elephantry.entity.Recommendation;
import com.elephantry.entity.User;
import com.elephantry.model.E;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserDAOImplTest {

	@Autowired
	private UserDAO userDAO;

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
	public void testFindUserByEmail() {
		User user = new User();
		user.setActive(1);
		user.setEmail("12345@gmail.com");
		user.setPassword("12345555555555");
		user.setRoleId(E.Role.CUSTOMER.getValue());
		userDAO.save(user);

		User u = userDAO.findUserByEmail("12345@gmail.com");
		assertEquals("12345@gmail.com", u.getEmail());
		assertEquals("12345555555555", u.getPassword());
		assertEquals(E.Role.CUSTOMER.getValue(), u.getRoleId());

	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetAdminCount() {
		User user = new User();
		user.setActive(1);
		user.setEmail("12345@gmail.com");
		user.setPassword("12345555555555");
		user.setRoleId(E.Role.ADMIN.getValue());
		userDAO.save(user);

		int a = userDAO.getAdminCount("12345@gmail.com");
		assertNotNull(a);
		assertTrue(a >= 0);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSearchAdmin() throws Exception {
		User user = new User();
		user.setActive(1);
		user.setEmail("12345@gmail.com");
		user.setPassword("12345555555555");
		user.setRoleId(E.Role.ADMIN.getValue());
		userDAO.save(user);

		List<User> lsUser = userDAO.searchAdmin("12345@gmail.com", 1, 5);
		assertNotNull(lsUser);
	}
	
	@Test(expected =  Exception.class)
	@Transactional
	@Rollback(true)
	public void testSearchAdminEx() throws Exception {
		User user = new User();
		user.setActive(1);
		user.setEmail("12345@gmail.com");
		user.setPassword("12345555555555");
		user.setRoleId(E.Role.ADMIN.getValue());
		userDAO.save(user);

		List<User> lsUser = userDAO.searchAdmin("12345@gmail.com", -1, 5);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSearchAdminEmpty() throws Exception {

		List<User> lsUser = userDAO.searchAdmin("", 1, 5);
		assertNotNull(lsUser);
	}
	
	@Test
	public void testCountCustomerByCreatedTime() throws Exception {
		int a = userDAO.countCustomerByCreatedTime(1);
		assertNotNull(a);
		assertTrue(a >= 0);
	}
	
	@Test
	public void testSummaryCustomerByMonth() throws Exception {
		Map<String, Integer>  map = userDAO.summaryCustomerByMonth(1);
		assertNotNull(map);
	}
	
	@Test
	public void testCountCustomerLastWeek() throws Exception {
		int a = userDAO.countCustomerLastWeek();
		assertNotNull(a);
		assertTrue(a >= 0);
	}
}
