package com.elephantry.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.sql.SQLException;
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

import com.elephantry.entity.Country;
import com.elephantry.service.CountryService;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CountryServiceImplTest {
	
	@Autowired
	private CountryService countryService;
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
		List<Country> lc = countryService.findAll();
		assertEquals(239, lc.size());
	}
	
	@Test
	public void testFindById() {
		Country c = countryService.findById("ABW");
		assertEquals("ABW", c.getCountryId());
		assertEquals("Aruba", c.getCountryName());
	}
	
	@Test
	public void testFindByIdException() {
		assertNull(countryService.findById(null));
	}

}
