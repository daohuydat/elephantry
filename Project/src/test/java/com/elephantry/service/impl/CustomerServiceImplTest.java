package com.elephantry.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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

import com.elephantry.entity.Customer;
import com.elephantry.entity.Package;
import com.elephantry.entity.Province;
import com.elephantry.entity.User;
import com.elephantry.service.CustomerService;
import com.elephantry.service.PackageService;
import com.elephantry.service.ProvinceService;
import com.elephantry.service.UserService;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerServiceImplTest {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private PackageService packageService;
	
	@Autowired
	private ProvinceService provinceService;
	
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
	public void testFindByEmailException(){
		assertNull(customerService.findByEmail(null));
	}
	@Test
	public void testgetCustomerByIdException(){
		Customer c = customerService.getCustomerById(null);
		assertNull(c);
	}
	@Test
	@Transactional
	@Rollback(true)
	public void testSaveCustomerAndFindByEmail() {
		Package p = packageService.findById(2);
		Province pro = provinceService.findById(4);
		User u = new User();
		u.setActive(1);
		u.setEmail("nguyen@gmail.com");
		u.setPassword("12345678");
		u.setRoleId("ROLE_CUST");
		Customer c = new Customer();
		c.setAddress("Ha noi");
		c.setCompany("FPT");
		c.setFirstName("Dao");
		c.setLastName("Dat");
		c.setGender("male");
		c.setPackage1(p);
		c.setProvince(pro);
		c.setWebsite("elephantry.com");
		c.setUser(u);
		customerService.save(c);
		customerService.findByEmail(u.getEmail());
		assertEquals("Ha noi", c.getAddress());
		assertEquals("FPT", c.getCompany());
		assertEquals("Dao", c.getFirstName());
		assertEquals("Dat", c.getLastName());
		assertEquals("male", c.getGender());
		assertEquals(2, p.getPackageId());
		assertEquals("nguyen@gmail.com", u.getEmail());
		assertEquals("Standard", p.getPackageName());
		assertEquals(100, p.getPrice(), 0.1);
		assertEquals(200, p.getStorage(), 0.1);
		assertEquals(2, c.getPackage1().getPriority());
		assertEquals(4, pro.getProvinceId());
		assertEquals("Hà Nội", pro.getProvinceName());
	}
	@Test
	public void testSaveCustomerException(){
		assertEquals(0, (int)customerService.save(null));
	}
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateCustomer() {
		Customer c = customerService.findByEmail("nguyenadong19952@gmail.com");
		c.getUser().setEmail("testing@test.test");
		c.setFirstName("Test");
		customerService.update(c);
		assertEquals("Test", c.getFirstName());
		assertEquals("testing@test.test", c.getUser().getEmail());
	}
	@Test
	public void testUpdateCustomerException(){
		customerService.update(null);
	}
	@Test
	public void testFindAll(){
		assertNotNull(customerService.findAll());
	}
	@Test
	public void testGetCustomerCountCaseEmailEmptyAndKeywordEmpty() throws Exception{
		int count = customerService.getCustomerCount("", "");
		if (count > 5) {
			int loop = (int) Math.ceil(count/5.0);
			for (int i = 0; i < loop-1; i++) {
				assertEquals(5, customerService.search("", "", i+1, 5).size());
			}
		}else{
			assertEquals(count, customerService.search("", "", 1, 5).size());
		}
	}
	@Test
	public void testGetCustomerCountCaseEmailEmptyAndKeywordIsNotEmpty() throws Exception{
		int count = customerService.getCustomerCount("", "elephantry");
		if (count > 5) {
			int loop = (int) Math.ceil(count/5.0);
			for (int i = 0; i < loop-1; i++) {
				assertEquals(5, customerService.search("", "elephantry", i+1, 5).size());
			}
		}else{
			assertEquals(count, customerService.search("", "elephantry", 1, 5).size());
		}
	}
	@Test
	public void testGetCustomerCountCaseEmailIsNotEmptyAndKeywordIsNotEmpty() throws Exception{
		int count = customerService.getCustomerCount("hieu", "elephantry");
		if (count > 5) {
			int loop = (int) Math.ceil(count/5.0);
			for (int i = 0; i < loop-1; i++) {
				assertEquals(5, customerService.search("hieu", "elephantry", i+1, 5).size());
			}
		}else{
			assertEquals(count, customerService.search("hieu", "elephantry", 1, 5).size());
		}
	}
	@Test
	public void testGetCustomerCountCaseEmailIsNotEmptyAndKeywordEmpty() throws Exception{
		int count = customerService.getCustomerCount("hieu", "");
		if (count > 5) {
			int loop = (int) Math.ceil(count/5.0);
			for (int i = 0; i < loop-1; i++) {
				assertEquals(5, customerService.search("hieu", "", i+1, 5).size());
			}
		}else{
			assertEquals(count, customerService.search("hieu", "", 1, 5).size());
		}
	}
	@Test
	public void testGetCustomerCountCaseException() throws Exception{
		int count = customerService.getCustomerCount(null, "");
		assertEquals(0, count);
	}
	@Test
	public void testSearchCaseException() throws Exception{
		assertNull(customerService.search("hieu", "", 0, 5));
	}
}
