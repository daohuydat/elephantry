package com.elephantry.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import com.elephantry.dao.UserDAO;
import com.elephantry.entity.Customer;
import com.elephantry.entity.Package;
import com.elephantry.entity.Province;
import com.elephantry.entity.User;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerDAOImplTest {

	@Autowired
	private CustomerDAO customerDAO;

	@Autowired
	private PackageDAO packageDAO;

	@Autowired
	private ProvinceDAO provinceDAO;

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
	public void testSaveCustomerAndFindByEmail() {
		Package p = packageDAO.findById(2);
		Province pro = provinceDAO.findById(4);
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
		customerDAO.save(c);
		customerDAO.findById(c.getCustomerId());
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
	@Transactional
	@Rollback(true)
	public void testUpdateProfile() {
		Customer c = customerDAO.findByEmail("nguyen@gmail.com");
		c.getUser().setEmail("huydat@gmail.com");
		c.setGender("female");
		customerDAO.updateProfile(c);
		assertEquals("huydat@gmail.com", c.getUser().getEmail());
		assertEquals("female", c.getGender());
	}

	@Test (expected = Exception.class)
	public void testGetCustomerCountException() throws Exception{
		customerDAO.searchCustomer("hieu", "elephantry", 0, 5);
	}
	
	@Test
	public void testGetCustomerCaseEmailEmptyAndKeywordEmpty() throws Exception{
		int count = customerDAO.getCustomerCount("", "");
		if (count > 5) {
			int loop = (int) Math.ceil(count/5.0);
			for (int i = 0; i < loop-1; i++) {
				assertEquals(5, customerDAO.searchCustomer("", "", i+1, 5).size());
			}
		}else{
			assertEquals(count, customerDAO.searchCustomer("", "", 1, 5).size());
		}
	}
	@Test
	public void testGetCustomerCaseEmailEmptyAndKeywordIsNotEmpty() throws Exception{
		int count = customerDAO.getCustomerCount("", "elephantry");
		if (count > 5) {
			int loop = (int) Math.ceil(count/5.0);
			for (int i = 0; i < loop-1; i++) {
				assertEquals(5, customerDAO.searchCustomer("", "elephantry", i+1, 5).size());
			}
		}else{
			assertEquals(count, customerDAO.searchCustomer("", "elephantry", 1, 5).size());
		}
	}
	@Test
	public void testGetCustomerCaseEmailIsNotEmptyAndKeywordIsNotEmpty() throws Exception{
		int count = customerDAO.getCustomerCount("hieu", "elephantry");
		if (count > 5) {
			int loop = (int) Math.ceil(count/5.0);
			for (int i = 0; i < loop-1; i++) {
				assertEquals(5, customerDAO.searchCustomer("hieu", "elephantry", i+1, 5).size());
			}
		}else{
			assertEquals(count, customerDAO.searchCustomer("hieu", "elephantry", 1, 5).size());
		}
	}
	@Test
	public void testGetCustomerCaseEmailIsNotEmptyAndKeywordEmpty() throws Exception{
		int count = customerDAO.getCustomerCount("hieu", "");
		if (count > 5) {
			int loop = (int) Math.ceil(count/5.0);
			for (int i = 0; i < loop-1; i++) {
				assertEquals(5, customerDAO.searchCustomer("hieu", "", i+1, 5).size());
			}
		}else{
			assertEquals(count, customerDAO.searchCustomer("hieu", "", 1, 5).size());
		}
	}
}
