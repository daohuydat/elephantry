package com.elephantry.service.impl;

import static org.junit.Assert.*;

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
import com.elephantry.entity.UploadedFile;
import com.elephantry.entity.User;
import com.elephantry.service.CustomerService;
import com.elephantry.service.PackageService;
import com.elephantry.service.ProvinceService;
import com.elephantry.service.UploadedFileService;


@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class UploadedFileServiceImplTest {
	
	@Autowired
	private UploadedFileService uploadedFileService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ProvinceService provinceService;

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
	@Transactional
	@Rollback(true)
	public void testSave() {
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
		Customer customer = customerService.findByEmail("nguyen@gmail.com");
		UploadedFile uploadedFile = new UploadedFile();
		uploadedFile.setFileName("fileTest");
		uploadedFile.setFileSize(20);
		uploadedFile.setCustomer(customer);
		uploadedFile.setHDFSURL("hdfsurl");
		int a = uploadedFileService.save(uploadedFile);
		assertTrue(a >= 0);
	}
	
	@Test
	public void testSaveEx() {
		assertEquals(0, (int)uploadedFileService.save(null));
	}
}
