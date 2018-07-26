package com.elephantry.service.impl;

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

import com.elephantry.entity.User;
import com.elephantry.model.E;
import com.elephantry.service.UserService;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceImplTest {
	
	@Autowired
	private UserService userService;

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
		userService.save(user);

		User u = userService.findUserByEmail("12345@gmail.com");
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
		userService.save(user);

		int a = userService.getAdminCount("12345@gmail.com");
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
		userService.save(user);

		List<User> lsUser = userService.searchAdmin("12345@gmail.com", 1, 5);
		assertNotNull(lsUser);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSearchAdminEx() throws Exception {
		User user = new User();
		user.setActive(1);
		user.setEmail("12345@gmail.com");
		user.setPassword("12345555555555");
		user.setRoleId(E.Role.ADMIN.getValue());
		userService.save(user);

		List<User> lsUser = userService.searchAdmin("12345@gmail.com", -1, 5);
		assertNull(lsUser);
	}
	
	@Test
	public void testCountCustomerByCreatedTime() throws Exception {
		int a = userService.countCustomerByCreatedTime(1);
		assertNotNull(a);
		assertTrue(a >= 0);
	}
	
	@Test
	public void testCountCustomerByCreatedTimeEx() throws Exception {
		int a = userService.countCustomerByCreatedTime(null);
		assertEquals(0, a);
	}
	
	@Test
	public void testSummaryCustomerByMonth() throws Exception {
		Map<String, Integer>  map = userService.summaryCustomerByMonth(1);
		assertNotNull(map);
	}
	
	@Test
	public void testSummaryCustomerByMonthEx() throws Exception {
		Map<String, Integer>  map = userService.summaryCustomerByMonth(null);
		assertNull(map);
	}
	
	@Test
	public void testCountCustomerLastWeek() throws Exception {
		int a = userService.countTotalCustomerLastWeek();
		assertNotNull(a);
		assertTrue(a >= 0);
	}
	
	
	@Test
	public void testFindAll(){
		List<User> ls = userService.findAll();
		assertNotNull(ls);
	}
	

	@Test
	@Transactional
	@Rollback(true)
	public void testSave() {
		User user = new User();
		user.setActive(1);
		user.setEmail("12345@gmail.com");
		user.setPassword("12345555555555");
		user.setRoleId(E.Role.ADMIN.getValue());
		int a = userService.save(user);
		assertNotNull(a);
		assertTrue(a >= 0);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testFindByEmail() {
		User user = new User();
		user.setActive(1);
		user.setEmail("12345@gmail.com");
		user.setPassword("12345555555555");
		user.setRoleId(E.Role.CUSTOMER.getValue());
		userService.save(user);

		User u = userService.findUserByEmail("12345@gmail.com");
		assertEquals("12345@gmail.com", u.getEmail());
		assertEquals("12345555555555", u.getPassword());
		assertEquals(E.Role.CUSTOMER.getValue(), u.getRoleId());
	}

	@Test
	public void testFindByEmailEx() {

		User u = userService.findByEmail(null);
		assertNull(u);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testFindById() {
		User user = new User();
		user.setActive(1);
		user.setEmail("12345@gmail.com");
		user.setPassword("12345555555555");
		user.setRoleId(E.Role.CUSTOMER.getValue());
		userService.save(user);

		User u1 = userService.findUserByEmail("12345@gmail.com");
		User u2 = userService.findById(u1.getUserId());
		assertEquals(u1, u2);
	}
	
	@Test
	public void testFindByIdEx() {
		User u = userService.findById(null);
		assertNull(u);
	}
	
	@Test
	public void testGetAdminCountEx() {
		int a = userService.getAdminCount(null);
		assertEquals(0, a);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdate() {
		User user = new User();
		user.setActive(1);
		user.setEmail("12345@gmail.com");
		user.setPassword("12345555555555");
		user.setRoleId(E.Role.CUSTOMER.getValue());
		userService.save(user);

		User u1 = userService.findUserByEmail("12345@gmail.com");
		u1.setRoleId(E.Role.ADMIN.getValue());
		userService.update(u1);
		User u2 = userService.findUserByEmail("12345@gmail.com");
		
		assertEquals(E.Role.ADMIN.getValue(), u2.getRoleId());
		assertEquals("12345555555555", u2.getPassword());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateEx() {
		
		userService.update(null);
		
	}
	
	
}
