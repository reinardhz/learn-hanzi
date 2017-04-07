package com.reinard.learnhanzi.services;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.reinard.learnhanzi.service.impl.HanziServiceImpl;

/**
 * Provide test for "HanziServiceImplTest". Run it with JUnit in eclipse
 * 
 * @author reinard.santosa
 *
 */
//This test use configuration in "test-context.xml", to use bean defined in there.
//put the file "test-context.xml" in "src/main/resources"
@ContextConfiguration(locations = "classpath:test-context.xml")

//To let spring run this unit test.
@RunWith(SpringJUnit4ClassRunner.class)
public class HanziServiceImplTest {
	
	private final static Logger logger = Logger.getLogger(HanziServiceImplTest.class);
	
	@Autowired
	HanziServiceImpl hanziService;
	
	/**
	 * Case 1: Select all data from table that already had data, must return the data.
	 * Case 2: Select all data from table that has no data, must return null.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSelectAll() throws Exception{
		logger.debug("Start test");
		String result = hanziService.selectAll();
		Assert.assertNotNull(result);
		logger.debug(result);
	}
	
	/**
	 * Case 1: Search inputted hanzi from table that already had data, the data is found, must return the data.
	 * Case 2: Search inputted hanzi from table that already had data, the data is not found, must return "null".
	 * Case 3: Select inputted hanzi from table that has no data, must return "null";
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSelectBy() throws Exception{
		logger.debug("Start test");
		//Case 1: Data found
		String result1 = hanziService.selectBy("我");
		Assert.assertNotNull(result1);
		Assert.assertTrue(result1.contains("我"));
		logger.debug(result1);
		
		//Case 2: Data not found
		String result2 = hanziService.selectBy("鳱");
		Assert.assertNull(result2);
		
		//Case 3: ?
	}
	
	/**
	 * Case 1: Insert new data that is not same as previous data, return the successfull inserted hanzi and its created date.
	 * Response json string example: {"hanzi_data":[{"hanzi":"會", "created_date":"1491448282654"}]}.
	 * <br/><br/>
	 */
	@Test
	public void insertHanzi1() throws Exception{
		logger.debug("Start test");
		String input = "沙";
		
		//Case 1:
		String insertSucceed = hanziService.insertHanzi(input);
		Assert.assertNotNull(insertSucceed);
		Assert.assertTrue(insertSucceed.contains(input));
		logger.debug(insertSucceed);
		
	}
	/**
	 * 
	 * Case 2: Insert data that is same as previous data. Return String: "Error: Cannot Insert. Data Already Exist".
	 */
	@Test
	public void insertHanzi2() throws Exception{
		logger.debug("Start test");
		String input = "沙";
		
		//Case 2:
		String insertFailed = hanziService.insertHanzi(input);
		Assert.assertNotNull(insertFailed);
		Assert.assertEquals("Error: Cannot Insert. Data Already Exist", insertFailed);
	}

}
