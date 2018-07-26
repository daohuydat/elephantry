package com.elephantry.service.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elephantry.entity.PaymentMethod;
import com.elephantry.entity.Province;
import com.elephantry.service.ProvinceService;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ProvinceServiceImplTest {
	
	@Autowired
	private  ProvinceService provinceService;
	
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
	public void testFindByCountryId() {
		List<Province> lsProvince =  provinceService.findByCountryId("VNM");
		int a = lsProvince.size();
		assertTrue(a >= 0);
		
	}
	
	@Test
	public void testFindByCountryIdNotNull() {
		List<Province> lsProvince =  provinceService.findByCountryId("VNM");
		assertNotNull(lsProvince);
	}
	
	@Test
	public void testFindByIdl() {
		Province province =  provinceService.findById(1);
		assertEquals("Cần Thơ", province.getProvinceName());
		assertEquals("VNM", province.getCountry().getCountryId());
	}
	
	@Test
	public void testFindByIdNotNull() {
		Province province =  provinceService.findById(1);
		assertNotNull(province);
	}
	
	@Test
	public void testFindByIdException(){
		assertNull(provinceService.findById(null));
	}
	
	
}
