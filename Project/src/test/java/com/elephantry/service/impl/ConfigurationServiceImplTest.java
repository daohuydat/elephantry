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
import com.elephantry.service.ConfigurationService;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ConfigurationServiceImplTest {

	@Autowired
	private ConfigurationService configurationService;
	
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
	public void testFindNotNull() {
		Configuration c = configurationService.find("elephantry.priority.scale");
		assertNotNull(c);
	}
	
	@Test
	public void testFindGoToCatch() {
		Configuration c = configurationService.find(null);
		assertNull(c);
	}
	
	@Test
	public void testFindNull() {
		Configuration c = configurationService.find("elephantry.priority.scale1");
		assertNull(c);
	}

	@Test
	public void testGetStringNotNull() {
		String s = configurationService.getString("elephantry.queue.prepared.size.min");
		assertNotNull(s);
	}
	
	@Test
	public void testGetStringNull() {
		String s = configurationService.getString("elephantry.queue.prepared.size.min2");
		assertNull(s);
	}

	@Test
	public void testGetStringWithDefaultWasNotReturned() {
		String s = configurationService.getString("elephantry.queue.prepared.size.min", "10");
		
		assertEquals(configurationService.find("elephantry.queue.prepared.size.min").getValue(), s);
	}
	
	@Test
	public void testGetStringWithDefaultWasReturned() {
		String s = configurationService.getString("elephantry.queue.prepared.size.min3", "10");
		assertEquals("10", s);
	}

	@Test
	public void testGetIntStringOk() {
		int scale  = configurationService.getInt("elephantry.priority.scale");
		assertTrue(scale >= 2);
	}
	
	@Test
	public void testGetIntStringReturnNegative() {
		int scale  = configurationService.getInt("elephantry.priority.scale1");
		assertTrue(scale < 0);
	}

	@Test
	public void testGetIntStringIntOk() {
		int scale  = configurationService.getInt("elephantry.priority.scale", 10);
		assertTrue(scale >= 2);
	}
	
	@Test
	public void testGetIntStringIntDefault() {
		int scale  = configurationService.getInt("elephantry.priority.scale1", 2);
		assertEquals(2, scale);
	}

	@Test
	public void testGetDoubleStringOk() {
		double threshold = configurationService.getDouble("elephantry.threshold");
		assertTrue(threshold >= 0.0);
	}
	
	@Test
	public void testGetDoubleStringReturnNegative() {
		double threshold = configurationService.getDouble("elephantry.threshold1");
		assertTrue(threshold < 0.0);
	}

	@Test
	public void testGetDoubleStringDoubleOk() {
		double threshold = configurationService.getDouble("elephantry.threshold", 0.1);
		assertTrue(threshold >= 0.0);
	}
	
	@Test
	public void testGetDoubleStringDoubleDefault() {
		double threshold = configurationService.getDouble("elephantry.threshold1", 0.1);
		assertEquals(0.1, threshold, 0.01);
	}

	@Test
	public void testUpdateOk() {
		Configuration conf = configurationService.find("elephantry.queue.final.size.max");
		if (conf != null) {
			conf.setValue("10");
			configurationService.update(conf);
			
			conf = configurationService.find("elephantry.queue.final.size.max");
			assertEquals("elephantry.queue.final.size.max", conf.getKey());
			assertEquals("10", conf.getValue());
		}
		assertNotNull(conf);
	}
	
	@Test
	public void testUpdateFailed() {
		Configuration conf = configurationService.find("elephantry.queue.final.size.max");
		if (conf != null) {
			String value = "";
			for (int i = 0; i < 52; i++) {
				value += "0123456789";
			}
			String oldVal = conf.getValue();
			
			conf.setValue(value);
			configurationService.update(conf);
			
			conf = configurationService.find("elephantry.queue.final.size.max");
			assertEquals("elephantry.queue.final.size.max", conf.getKey());
			assertEquals(oldVal, conf.getValue());
		}
		assertNotNull(conf);
	}

}
