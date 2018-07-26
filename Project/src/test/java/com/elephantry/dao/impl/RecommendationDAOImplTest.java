package com.elephantry.dao.impl;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.jws.soap.SOAPBinding.Use;
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
import com.elephantry.dao.PackageDAO;
import com.elephantry.dao.ProvinceDAO;
import com.elephantry.dao.RecommendationDAO;
import com.elephantry.entity.Customer;
import com.elephantry.entity.Package;
import com.elephantry.entity.Province;
import com.elephantry.entity.Recommendation;
import com.elephantry.entity.User;
import com.elephantry.model.E;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class RecommendationDAOImplTest {

	@Autowired
	private PackageDAO packageDAO;

	@Autowired
	private ProvinceDAO provinceDAO;

	@Autowired
	private CustomerDAO customerDAO;

	@Autowired
	private RecommendationDAO recommendationDAO;

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
	public void testListRecommendation() {
		List<Recommendation> lsRecommendation = recommendationDAO.listRecommendation();
		int a = lsRecommendation.size();
		assertTrue(a >= 0);
		assertNotNull(lsRecommendation);

	}

	@Test
	public void testFindRecommendationByStatus() {
		List<Recommendation> lsRecommendation = recommendationDAO
				.findRecommendationByStatus(E.RStatus.Waiting.getValue());
		int a = lsRecommendation.size();
		assertTrue(a >= 0);
		assertNotNull(lsRecommendation);
		if (lsRecommendation.size() > 0) {
			assertEquals(E.RStatus.Waiting.getValue(), lsRecommendation.get(0).getRecommendationStatusId());
		}
	}

	@Test
	public void testFindRecommendationByCustomerId() {
		List<Customer> lsCustomer = customerDAO.findAll();
		if (lsCustomer.size() > 0) {
			List<Recommendation> lsRecommendation = recommendationDAO
					.findRecommendationByCustomerId(lsCustomer.get(0).getCustomerId());
			int a = lsRecommendation.size();
			assertTrue(a >= 0);
			assertNotNull(lsRecommendation);
		}
	}

	@Test
	public void testFindRecommendationByCustomerIdAndStatus() {
		List<Customer> lsCustomer = customerDAO.findAll();
		if (lsCustomer.size() > 0) {
			List<Recommendation> lsRecommendation = recommendationDAO.findRecommendationByCustomerIdAndStatus(
					lsCustomer.get(0).getCustomerId(), E.RStatus.Waiting.getValue());
			int a = lsRecommendation.size();
			assertTrue(a >= 0);
			assertNotNull(lsRecommendation);
			if (lsRecommendation.size() > 0) {
				assertEquals(lsCustomer.get(0).getCustomerId(), lsRecommendation.get(0).getCustomer().getCustomerId());
				assertEquals(E.RStatus.Waiting.getValue(), lsRecommendation.get(0).getRecommendationStatusId());
			}
		}
	}

	@Test
	public void testFindWaitingTimerRecommendations() {

		List<Recommendation> lsRecommendation = recommendationDAO.findWaitingTimerRecommendations();
		int a = lsRecommendation.size();
		assertTrue(a >= 0);
		assertNotNull(lsRecommendation);

		if (lsRecommendation.size() > 0) {
			assertEquals(E.RStatus.Waiting.getValue(), lsRecommendation.get(0).getRecommendationStatusId());
			assertTrue(lsRecommendation.get(0).getTimer().before(Calendar.getInstance().getTime()));
		}
	}


	@Test
	@Transactional
	@Rollback(true)
	public void testGetAvgSecondPerRow() {
		
		Recommendation recommendation = new Recommendation();
		List<Customer> lsCustomer = customerDAO.findAll();
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		Customer c = new Customer();
		User u = new User();
		u.setActive(1);
		u.setEmail("testUser123@gmail.com");
		u.setCreatedTime(date);
		u.setRoleId(E.Role.CUSTOMER.getValue());
		u.setPassword("$2a$10$6dFAggJkiWvqjKDZ9Dmn5uNE23fyTdgN7loWtn644oeBrylqoAMZ6");
		c.setUser(u);
		c.setFirstName("test");
		c.setLastName("1");
		c.setGender("male");
		c.setPhone("123");
		Province p = provinceDAO.findById(1);
		c.setProvince(p);
		Package pac = packageDAO.findById(1);
		c.setPackage1(pac);
		customerDAO.save(c);
		lsCustomer = customerDAO.findAll();
		recommendation.setName("rTest");
		recommendation.setNumOfItem(10000);
		recommendation.setCustomer(lsCustomer.get(0));
		recommendation.setRecommendationStatusId(E.RStatus.Completed.getValue());
		recommendation.setRecommendTypeId(1);
		recommendation.setFinishedTime(date);
		recommendation.setTimer(date);
		calendar.add(Calendar.MINUTE, -3);
		recommendation.setCreatedTime(calendar.getTime());
		recommendation.setStartedTime(calendar.getTime());
		recommendation.setThreshold(0);
		recommendation.setFailedCount(0);
		recommendation.setRecordCount(10000);

		recommendationDAO.save(recommendation);
		double b = recommendationDAO.getAvgSecondPerRow();
		assertTrue(b != 10);
		
		
	}
	
	

	@Test
	public void testFindFailedRecommendations() {
		List<Recommendation> lsRecommendation = recommendationDAO.findFailedRecommendations(3);
		if (lsRecommendation.size() > 0) {
			Recommendation recommendation = recommendationDAO.findById(lsRecommendation.get(0).getRecommendationId());
			assertEquals(E.RStatus.Failed.getValue(), recommendation.getRecommendationStatusId());
			assertTrue(recommendation.getFailedCount() <= 3);
		}
	}
	
	@Test(expected = Exception.class)
	@Transactional
	@Rollback(true)
	public void testFindByEmailException() throws Exception{
		Recommendation recommendation = new Recommendation();
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		Customer c = new Customer();
		User u = new User();
		u.setActive(1);
		u.setEmail("testUser123@gmail.com");
		u.setCreatedTime(date);
		u.setRoleId(E.Role.CUSTOMER.getValue());
		u.setPassword("$2a$10$6dFAggJkiWvqjKDZ9Dmn5uNE23fyTdgN7loWtn644oeBrylqoAMZ6");
		c.setUser(u);
		c.setFirstName("test");
		c.setLastName("1");
		c.setGender("male");
		c.setPhone("123");
		Province p = provinceDAO.findById(1);
		c.setProvince(p);
		Package pac = packageDAO.findById(1);
		c.setPackage1(pac);
		customerDAO.save(c);
		Customer customer = customerDAO.findByEmail("testUser123@gmail.com");
		recommendation.setName("rTest");
		recommendation.setNumOfItem(10000);
		recommendation.setCustomer(customer);
		recommendation.setRecommendationStatusId(E.RStatus.Waiting.getValue());
		recommendation.setRecommendTypeId(1);
		recommendation.setFinishedTime(date);
		recommendation.setTimer(date);
		cal.add(Calendar.MINUTE, -3);
		recommendation.setCreatedTime(cal.getTime());
		recommendation.setStartedTime(cal.getTime());
		recommendation.setThreshold(0);
		recommendation.setFailedCount(0);
		recommendation.setRecordCount(10000);

		recommendationDAO.save(recommendation);
		Calendar calendar = Calendar.getInstance();
		Date endDate = calendar.getTime();
		calendar.add(Calendar.MONTH, -1);
		Date startDate = calendar.getTime();
		List<Recommendation> lsRecommendation = recommendationDAO.searchRecommendation(customer.getCustomerId(),
				E.RStatus.Waiting.getValue(), startDate.toString(), endDate.toString(), "", 0, 5);
		assertNotNull(lsRecommendation);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testSearchRecommendation() throws Exception {
		
		Recommendation recommendation = new Recommendation();
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		Customer c = new Customer();
		User u = new User();
		u.setActive(1);
		u.setEmail("testUser1a23@gmail.com");
		u.setCreatedTime(date);
		u.setRoleId(E.Role.CUSTOMER.getValue());
		u.setPassword("$2a$10$6dFAggJkiWvqjKDZ9Dmn5uNE23fyTdgN7loWtn644oeBrylqoAMZ6");
		c.setUser(u);
		c.setFirstName("test");
		c.setLastName("1");
		c.setGender("male");
		c.setPhone("123");
		Province p = provinceDAO.findById(1);
		c.setProvince(p);
		Package pac = packageDAO.findById(1);
		c.setPackage1(pac);
		customerDAO.save(c);
		Customer customer = customerDAO.findByEmail("testUser1a23@gmail.com");
		recommendation.setName("rTest");
		recommendation.setNumOfItem(10000);
		recommendation.setCustomer(customer);
		recommendation.setRecommendationStatusId(E.RStatus.Waiting.getValue());
		recommendation.setRecommendTypeId(1);
		recommendation.setFinishedTime(date);
		recommendation.setTimer(date);
		cal.add(Calendar.MINUTE, -3);
		recommendation.setCreatedTime(cal.getTime());
		recommendation.setStartedTime(cal.getTime());
		recommendation.setThreshold(0);
		recommendation.setFailedCount(0);
		recommendation.setRecordCount(10000);

		recommendationDAO.save(recommendation);
		
		Calendar calendar = Calendar.getInstance();
		Date endDate = calendar.getTime();
		calendar.add(Calendar.MONTH, -1);
		Date startDate = calendar.getTime();
		List<Recommendation> lsRecommendation = recommendationDAO.searchRecommendation(
				customer.getCustomerId(), E.RStatus.Waiting.getValue(), startDate.toString(),
				endDate.toString(), "a", 1, 5);
		assertNotNull(lsRecommendation);
		if(lsRecommendation.size() > 0){
			Recommendation recommendation1 = recommendationDAO.findById(lsRecommendation.get(0).getRecommendationId());
			assertEquals(E.RStatus.Waiting.getValue(), recommendation1.getRecommendationStatusId());
			assertEquals(customer.getCustomerId(), recommendation1.getCustomer().getCustomerId());
			assertTrue(recommendation1.getCreatedTime().after(startDate) && recommendation1.getCreatedTime().before(endDate));
			assertTrue(recommendation1.getName().contains("a"));
		}
		

		lsRecommendation = recommendationDAO.searchRecommendation(customer.getCustomerId(),
				E.RStatus.Waiting.getValue(), startDate.toString(), endDate.toString(), "", 1, 5);
		assertNotNull(lsRecommendation);

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testGetRecommendationCount() throws Exception {
		List<Customer> lsCustomer = customerDAO.findAll();
		Calendar calendar = Calendar.getInstance();
		Date endDate = calendar.getTime();
		calendar.add(Calendar.MONTH, -1);
		Date startDate = calendar.getTime();
		int count = recommendationDAO.getRecommendationCount(lsCustomer.get(0).getCustomerId(),
				E.RStatus.Waiting.getValue(), startDate.toString(), endDate.toString(), "a");
		assertTrue(count >= 0);
		count = recommendationDAO.getRecommendationCount(lsCustomer.get(0).getCustomerId(),
				E.RStatus.Waiting.getValue(), startDate.toString(), endDate.toString(), "");
		assertTrue(count >= 0);
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		Customer c = new Customer();
		User u = new User();
		u.setActive(1);
		u.setEmail("testUser123@gmail.com");
		u.setCreatedTime(date);
		u.setRoleId(E.Role.CUSTOMER.getValue());
		u.setPassword("$2a$10$6dFAggJkiWvqjKDZ9Dmn5uNE23fyTdgN7loWtn644oeBrylqoAMZ6");
		c.setUser(u);
		c.setFirstName("test");
		c.setLastName("1");
		c.setGender("male");
		c.setPhone("123");
		Province p = provinceDAO.findById(1);
		c.setProvince(p);
		Package pac = packageDAO.findById(1);
		c.setPackage1(pac);
		customerDAO.save(c);
		for (int i = 0; i <= 6; i++) {
			Recommendation recommendation = new Recommendation();
			Calendar cal1 = Calendar.getInstance();
			Date date1 = cal.getTime();
			lsCustomer = customerDAO.findAll();
			recommendation.setName("rTest");
			recommendation.setNumOfItem(10000);
			recommendation.setCustomer(lsCustomer.get(0));
			recommendation.setRecommendationStatusId(E.RStatus.Waiting.getValue());
			recommendation.setRecommendTypeId(1);
			recommendation.setFinishedTime(date1);
			recommendation.setTimer(date1);
			cal1.add(Calendar.MINUTE, -3);
			recommendation.setCreatedTime(cal.getTime());
			recommendation.setStartedTime(cal.getTime());
			recommendation.setThreshold(0);
			recommendation.setFailedCount(0);
			recommendation.setRecordCount(10000);
			recommendationDAO.save(recommendation);
		}

		count = recommendationDAO.getRecommendationCount(lsCustomer.get(0).getCustomerId(),
				E.RStatus.Waiting.getValue(), startDate.toString(), endDate.toString(), "a");
		assertTrue(count >= 0);
		count = recommendationDAO.getRecommendationCount(lsCustomer.get(0).getCustomerId(),
				E.RStatus.Waiting.getValue(), startDate.toString(), endDate.toString(), "");
		System.out.println(count);
		assertTrue(count >= 0);

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testCountbyMonth() {
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		Customer c = new Customer();
		User u = new User();
		u.setActive(1);
		u.setEmail("testUser1a23@gmail.com");
		u.setCreatedTime(date);
		u.setRoleId(E.Role.CUSTOMER.getValue());
		u.setPassword("$2a$10$6dFAggJkiWvqjKDZ9Dmn5uNE23fyTdgN7loWtn644oeBrylqoAMZ6");
		c.setUser(u);
		c.setFirstName("test");
		c.setLastName("1");
		c.setGender("male");
		c.setPhone("123");
		Province p = provinceDAO.findById(1);
		c.setProvince(p);
		Package pac = packageDAO.findById(1);
		c.setPackage1(pac);
		customerDAO.save(c);

		Customer customer = customerDAO.findByEmail("testUser1a23@gmail.com");
		Map<String, Integer> map = recommendationDAO.countbyMonth(1);
		Map<String, Integer> mapByCus = recommendationDAO.countbyMonth(1,customer.getCustomerId());
		assertNotNull(map);
		assertNotNull(mapByCus);
		Recommendation recommendation = new Recommendation();
		
		recommendation.setName("rTest");
		recommendation.setNumOfItem(10000);
		recommendation.setCustomer(customer);
		recommendation.setRecommendationStatusId(E.RStatus.Waiting.getValue());
		recommendation.setRecommendTypeId(1);
		recommendation.setFinishedTime(date);
		recommendation.setTimer(date);
		cal.add(Calendar.MONTH, -3);
		recommendation.setCreatedTime(cal.getTime());
		recommendation.setStartedTime(cal.getTime());
		recommendation.setThreshold(0);
		recommendation.setFailedCount(0);
		recommendation.setRecordCount(10000);

		recommendationDAO.save(recommendation);
		
		map = recommendationDAO.countbyMonth(1);
		mapByCus = recommendationDAO.countbyMonth(1,customer.getCustomerId());
		assertNotNull(map);
		assertNotNull(mapByCus);
	}
	
	@Test
	public void testCountAllRecommendations() {
		int a = recommendationDAO.countAllRecommendations();
		assertTrue(a >= 0);
	}

	@Test
	public void testCompareRecommendationWeekAgo() {
		double a = recommendationDAO.compareRecommendationWeekAgo();
		assertNotNull(a);
	}
}
