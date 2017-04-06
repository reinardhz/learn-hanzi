package com.reinard.learnhanzi.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.reinard.learnhanzi.dao.impl.HanziDaoImpl;
import com.reinard.learnhanzi.models.HanziData;
import com.reinard.learnhanzi.models.UserAndHanzi;
import com.reinard.learnhanzi.models.UserData;
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
		String result = hanziService.selectAll();
		assertNotNull(result);
		System.out.println(result);
	}
	
	/**
	 * Case 1: Search inputted hanzi from table that already had data, the data is found, then return the data.
	 * Case 2: Search inputted hanzi from table that already had data, the data is not found, then return "null".
	 * Case 3: Select inputted hanzi from table that has no data, must return ? 
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSelectBy() throws Exception{
		String result1 = hanziService.selectBy("我");
		assertNotNull(result1);
		System.out.println(result1);
		String result2 = hanziService.selectBy("愛");
		assertNull(result2);
	}
	
	/**
	 * Case 1: Insert new data that is not same as previous data, return the successfull hanzi + its created date.
	 * Response json string example: {"hanzi_data":[{"hanzi":"我", "created_date":"1491448282654"}]}.
	 * <br/><br/>
	 * 
	 * Case 2: Insert data that is same as previous data. Return error: "Data already exist".
	 */
	

}
