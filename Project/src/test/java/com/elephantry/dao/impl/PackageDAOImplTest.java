package com.elephantry.dao.impl;

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

import com.elephantry.dao.CustomerDAO;
import com.elephantry.dao.PackageDAO;
import com.elephantry.entity.Configuration;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class PackageDAOImplTest {
	
	@Autowired
	private PackageDAO packageDAO;
	
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
	public void testCountTotalCustomerByPackageId() {
		int a = customerDAO.findAll().size();
		int freeTrail = packageDAO.countbyId(1);
		int standard = packageDAO.countbyId(2);
		int premium = packageDAO.countbyId(3);
		assertTrue(freeTrail >= 0);
		assertTrue(standard >= 0);
		assertTrue(premium >= 0);
		assertTrue(a == (freeTrail + standard + premium));
		
	}
}
