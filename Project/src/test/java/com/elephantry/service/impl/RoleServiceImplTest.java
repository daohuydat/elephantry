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

import com.elephantry.entity.Recommendation;
import com.elephantry.entity.Role;
import com.elephantry.service.RoleService;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class RoleServiceImplTest {
	@Autowired
	private RoleService roleService;

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
		List<Role> lsRole = roleService.findAll();
		int a = lsRole.size();
		assertTrue(a >= 0);
		assertNotNull(lsRole);

	}
	
	@Test
	public void testFindById() {
		Role role = roleService.findById("ROLE_CUST");
		assertEquals("ROLE_CUST", role.getRoleId());

	}
}
