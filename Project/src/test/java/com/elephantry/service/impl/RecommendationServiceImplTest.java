package com.elephantry.service.impl;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.transaction.TransactionalException;

import org.hibernate.PropertyValueException;
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

import com.elephantry.dao.ProvinceDAO;
import com.elephantry.entity.Customer;
import com.elephantry.entity.Package;
import com.elephantry.entity.Province;
import com.elephantry.entity.Recommendation;
import com.elephantry.entity.User;
import com.elephantry.model.E;
import com.elephantry.service.CustomerService;
import com.elephantry.service.PackageService;
import com.elephantry.service.ProvinceService;
import com.elephantry.service.RecommendationService;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class RecommendationServiceImplTest {

	@Autowired
	private RecommendationService recommendationService;
	
	@Autowired
	private ProvinceService provinceService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private PackageService packageService;
	
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
	public void testFindAll() {
		List<Recommendation> lsRecommendation = recommendationService.findAll();
		int a = lsRecommendation.size();
		assertTrue(a >= 0);
		assertNotNull(lsRecommendation);

	}
	
	@Test
	public void testFindRecommendationByStatus() {
		List<Recommendation> lsRecommendation = recommendationService
				.findRecommendationByStatus(E.RStatus.Waiting.getValue());
		int a = lsRecommendation.size();
		assertTrue(a >= 0);
		assertNotNull(lsRecommendation);
		if (lsRecommendation.size() > 0) {
			assertEquals(E.RStatus.Waiting.getValue(), lsRecommendation.get(0).getRecommendationStatusId());
		}
	}
	
	@Test
	public void testFindRecommendationByStatusEx() {
		List<Recommendation> lsRecommendation = recommendationService
				.findRecommendationByStatus(null);
		assertNull(lsRecommendation);
		
	}
	
	@Test
	public void testFindByIdEx() {
		Recommendation Recommendation = recommendationService.findById(null);
		assertNull(Recommendation);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdate() {
		Recommendation recommendation = new Recommendation();
		List<Customer> lsCustomer = customerService.findAll();
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
		Province p = provinceService.findById(1);
		c.setProvince(p);
		Package pac = packageService.findById(1);
		c.setPackage1(pac);
		customerService.save(c);
		lsCustomer = customerService.findAll();
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

		recommendationService.save(recommendation);
		List<Recommendation> lsRecommendation = recommendationService.findAll();
		recommendation = new Recommendation();
		int rId = lsRecommendation.get(0).getRecommendationId();
		recommendation = recommendationService.findById(rId);
		recommendation.setName("changeTestName");
		recommendation.setNumOfItem(20000);
		recommendationService.update(recommendation);
		recommendation = new Recommendation();
		recommendation = recommendationService.findById(rId);
		assertEquals("changeTestName", recommendation.getName());
		assertEquals(20000, recommendation.getNumOfItem());
		
	}
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateEx() {
		Recommendation recommendation = new Recommendation();
		recommendationService.update(recommendation);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSave(){
		Recommendation recommendation = new Recommendation();
		List<Customer> lsCustomer = customerService.findAll();
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
		Province p = provinceService.findById(1);
		c.setProvince(p);
		Package pac = packageService.findById(1);
		c.setPackage1(pac);
		customerService.save(c);
		lsCustomer = customerService.findAll();
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
		
		int a = recommendationService.save(recommendation);
		assertTrue(a > 0);
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSaveEx(){
		Recommendation recommendation = new Recommendation();
		int a = recommendationService.save(recommendation);
		assertEquals(0, a);
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testFindRecommendationByCustomerId(){
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
		Province p = provinceService.findById(1);
		c.setProvince(p);
		Package pac = packageService.findById(1);
		c.setPackage1(pac);
		customerService.save(c);
		Customer customer = customerService.findByEmail("testUser123@gmail.com");
		List<Recommendation> lsRecommendations = recommendationService.findRecommendationByCustomerId(customer.getCustomerId());
		assertNotNull(lsRecommendations);
		
				
	}
	
	@Test
	public void testFindRecommendationByCustomerIdEx(){
		List<Recommendation> lsRecommendations = recommendationService.findRecommendationByCustomerId(null);
		assertNull(lsRecommendations);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetRecommendationByCustomerIdAndStatus(){
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
		Province p = provinceService.findById(1);
		c.setProvince(p);
		Package pac = packageService.findById(1);
		c.setPackage1(pac);
		customerService.save(c);
		Customer customer = customerService.findByEmail("testUser123@gmail.com");
		List<Recommendation> lsRecommendations = recommendationService.getRecommendationByCustomerIdAndStatus(customer.getCustomerId(), E.RStatus.Waiting.getValue());
		assertNotNull(lsRecommendations);
		if(lsRecommendations.size() > 0){
			assertEquals(E.RStatus.Waiting.getValue(), lsRecommendations.get(0).getRecommendationStatusId());	
		}	
	}
	
	@Test
	public void testGetRecommendationByCustomerIdAndStatusEx(){
		List<Recommendation> lsRecommendations = recommendationService.getRecommendationByCustomerIdAndStatus(null,null);
		assertNull(lsRecommendations);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testFindWaitingTimerRecommendations(){
		List<Recommendation> lsre = recommendationService.findWaitingTimerRecommendations();
		assertNotNull(lsre);
		if(lsre.size() > 0){
			assertEquals(E.RStatus.Waiting.getValue(), lsre.get(0).getRecommendationStatusId());
		}
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetAvgSecondPerRow(){
		Recommendation recommendation = new Recommendation();
		List<Customer> lsCustomer = customerService.findAll();
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
		Province p = provinceService.findById(1);
		c.setProvince(p);
		Package pac = packageService.findById(1);
		c.setPackage1(pac);
		customerService.save(c);
		lsCustomer = customerService.findAll();
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

		recommendationService.save(recommendation);
		double b = recommendationService.getAvgSecondPerRow();
		assertTrue(b != 10);
	}
	
	@Test
	public void testFindFailedRecommendations(){
		List<Recommendation> lsRecommendation = recommendationService.findFailedRecommendations(3);
		if (lsRecommendation.size() > 0) {
			Recommendation recommendation = recommendationService.findById(lsRecommendation.get(0).getRecommendationId());
			assertEquals(E.RStatus.Failed.getValue(), recommendation.getRecommendationStatusId());
			assertTrue(recommendation.getFailedCount() <= 3);
		}
	}
	
	@Test
	public void testFindFailedRecommendationsEx(){
		List<Recommendation> lsRecommendation = recommendationService.findFailedRecommendations(null);
		assertNull(lsRecommendation);
		
	}
	
	@Test
	public void testSearchRecommendationEx()throws Exception{
		List<Recommendation> lsRecommendation = recommendationService.searchRecommendation(1,1,null,null,null,1,1);
		assertNull(lsRecommendation);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSearchRecommendation() throws Exception{
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
		Province p = provinceService.findById(1);
		c.setProvince(p);
		Package pac = packageService.findById(1);
		c.setPackage1(pac);
		customerService.save(c);
		Customer customer = customerService.findByEmail("testUser1a23@gmail.com");
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

		recommendationService.save(recommendation);
		
		Calendar calendar = Calendar.getInstance();
		Date endDate = calendar.getTime();
		calendar.add(Calendar.MONTH, -1);
		Date startDate = calendar.getTime();
		List<Recommendation> lsRecommendation = recommendationService.searchRecommendation(
				customer.getCustomerId(), E.RStatus.Waiting.getValue(), startDate.toString(),
				endDate.toString(), "a", 1, 5);
		assertNotNull(lsRecommendation);
		if(lsRecommendation.size() > 0){
			Recommendation recommendation1 = recommendationService.findById(lsRecommendation.get(0).getRecommendationId());
			assertEquals(E.RStatus.Waiting.getValue(), recommendation1.getRecommendationStatusId());
			assertEquals(customer.getCustomerId(), recommendation1.getCustomer().getCustomerId());
			assertTrue(recommendation1.getCreatedTime().after(startDate) && recommendation1.getCreatedTime().before(endDate));
			assertTrue(recommendation1.getName().contains("a"));
		}
		

		lsRecommendation = recommendationService.searchRecommendation(customer.getCustomerId(),
				E.RStatus.Waiting.getValue(), startDate.toString(), endDate.toString(), "", 1, 5);
		assertNotNull(lsRecommendation);
	}
	
	@Test
	public void testGetRecommendationCountEx()throws Exception{
		int count = recommendationService.getRecommendationCount(1,1,null,null,null);
		assertEquals(0,count);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetRecommendationCount() throws Exception {
		List<Customer> lsCustomer = customerService.findAll();
		Calendar calendar = Calendar.getInstance();
		Date endDate = calendar.getTime();
		calendar.add(Calendar.MONTH, -1);
		Date startDate = calendar.getTime();
		int count = recommendationService.getRecommendationCount(lsCustomer.get(0).getCustomerId(),
				E.RStatus.Waiting.getValue(), startDate.toString(), endDate.toString(), "a");
		assertTrue(count >= 0);
		count = recommendationService.getRecommendationCount(lsCustomer.get(0).getCustomerId(),
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
		Province p = provinceService.findById(1);
		c.setProvince(p);
		Package pac = packageService.findById(1);
		c.setPackage1(pac);
		customerService.save(c);
		for (int i = 0; i <= 6; i++) {
			Recommendation recommendation = new Recommendation();
			Calendar cal1 = Calendar.getInstance();
			Date date1 = cal.getTime();
			lsCustomer = customerService.findAll();
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
			recommendationService.save(recommendation);
		}

		count = recommendationService.getRecommendationCount(lsCustomer.get(0).getCustomerId(),
				E.RStatus.Waiting.getValue(), startDate.toString(), endDate.toString(), "a");
		assertTrue(count >= 0);
		count = recommendationService.getRecommendationCount(lsCustomer.get(0).getCustomerId(),
				E.RStatus.Waiting.getValue(), startDate.toString(), endDate.toString(), "");
		System.out.println(count);
		assertTrue(count >= 0);

	}
	
	@Test
	public void testCountbyMonthEx()throws Exception{
		Map<String, Integer> map = recommendationService.countByMonth(null);
		assertNull(map);
	}
	
	@Test
	public void testCountbyMonth2Ex()throws Exception{
		Map<String, Integer> map = recommendationService.countByMonth(null,null);
		assertNull(map);
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
		Province p = provinceService.findById(1);
		c.setProvince(p);
		Package pac = packageService.findById(1);
		c.setPackage1(pac);
		customerService.save(c);

		Customer customer = customerService.findByEmail("testUser1a23@gmail.com");
		Map<String, Integer> map = recommendationService.countByMonth(1);
		Map<String, Integer> mapByCus = recommendationService.countByMonth(1, customer.getCustomerId());
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

		recommendationService.save(recommendation);
		
		map = recommendationService.countByMonth(1);
		mapByCus = recommendationService.countByMonth(1, customer.getCustomerId());
		assertNotNull(map);
		assertNotNull(mapByCus);
	}
	
	@Test
	public void testCountAllRecommendations() {
		int a = recommendationService.countAllRecommendations();
		assertTrue(a >= 0);
	}

	@Test
	public void testCompareRecommendationWeekAgo() {
		String a = recommendationService.compareRecommendationWeekAgo();
		assertNotNull(a);
	}
}
