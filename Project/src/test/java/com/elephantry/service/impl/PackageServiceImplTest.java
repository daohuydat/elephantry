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

import com.elephantry.entity.Configuration;
import com.elephantry.entity.Package;
import com.elephantry.service.CustomerService;
import com.elephantry.service.PackageService;


@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class PackageServiceImplTest {

	@Autowired
	private PackageService packageService;
	
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
	public void testFindAllPackage() {
		int count = packageService.findAll().size();
		assertEquals(3, count);
	}

	@Test
	public void testFindFreeTrialPackage() {
		Package package1 = packageService.findById(1);
		assertEquals(1, package1.getPackageId());
		assertEquals("Free Trial", package1.getPackageName());
		assertEquals(0, package1.getPrice(), 0.1);
		
	}
	
	@Test
	public void testFindStandardPackage() {
		Package package1 = packageService.findById(2);
		assertEquals(2, package1.getPackageId());
		assertEquals("Standard", package1.getPackageName());
		assertEquals(100, package1.getPrice(), 0.1);
		
	}
	
	@Test
	public void testFindPremiumPackage() {
		Package package1 = packageService.findById(3);
		assertEquals(3, package1.getPackageId());
		assertEquals("Premium", package1.getPackageName());
		assertEquals(200, package1.getPrice(), 0.1);
		
	}
	
	@Test
	public void testCountCustomerByPackageId() {
		int a = customerService.findAll().size();
		int freeTrail = packageService.countbyId(1);
		int standard = packageService.countbyId(2);
		int premium = packageService.countbyId(3);
		assertTrue(freeTrail >= 0);
		assertTrue(standard >= 0);
		assertTrue(premium >= 0);
		assertTrue(a == (freeTrail + standard + premium));
	}
	
	@Test
	public void testFindById(){
		assertNull(packageService.findById(null));
	}
	
	@Test
	public void testCountbyId(){
		assertEquals(0, packageService.countbyId(null));
	}
}
