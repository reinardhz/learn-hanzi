package com.reinard.learnhanzi.daos;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.reinard.learnhanzi.controllers.HanziController;
import com.reinard.learnhanzi.dao.impl.HanziDaoImpl;
import com.reinard.learnhanzi.models.HanziData;
import com.reinard.learnhanzi.models.UserAndHanzi;
import com.reinard.learnhanzi.models.UserData;

import junit.framework.Assert;

/**
 * Provide test for "HanziDaoImpl". Run it with JUnit in eclipse
 * 
 * @author reinard.santosa
 *
 */
//This test use configuration in "test-context.xml", to use bean defined in there.
//put the file "test-context.xml" in "src/main/resources"
@ContextConfiguration(locations = "classpath:test-context.xml")

//To let spring run this unit test.
@RunWith(SpringJUnit4ClassRunner.class)
public class HanziDaoImplTest {
	
	private final static Logger logger = Logger.getLogger(HanziDaoImplTest.class);
	
	@Autowired
	private HanziDaoImpl hanziDaoImpl;
	
	@Test
	public void currentTime(){
		System.out.println(System.currentTimeMillis());
		System.out.println(System.currentTimeMillis()/1000L);
	}
	
	@SuppressWarnings("all")
	@Test
	public void testSelectAll() throws Exception{
		logger.debug("Start test");
		List<HanziData> result = hanziDaoImpl.selectAll();
		Assert.assertNotNull(result);
		Assert.assertFalse(result.isEmpty());
		logger.debug("Result: "+ result.toString());
	}
	
	@SuppressWarnings("all")
	@Test
	public void testSelectBy() throws Exception{
		logger.debug("Start test");
		//Case 1: Data Found:
		HanziData resultFound = hanziDaoImpl.selectBy("我");
		Assert.assertNotNull(resultFound);
		Assert.assertEquals(resultFound.getHanzi(), "我");
		logger.debug("Result: " + resultFound.toString());
		
		//Case 2: Data Not Found:
		HanziData resultNotFound = hanziDaoImpl.selectBy("鳱");
		Assert.assertNull(resultNotFound);
		
	}
	
	//Case 1: Insert a new data that is not exist in the database.
	//@Test
	public void testInsert1() throws Exception{
		logger.debug("Start test");
		String input = "對";
		HanziData hanziData = new HanziData();
		hanziData.setHanzi(input);
		hanziData.setCreated_date(System.currentTimeMillis());
		
		Set<UserAndHanzi> childs = new HashSet<UserAndHanzi>();
		
		UserAndHanzi child = new UserAndHanzi();
		UserData userData = new UserData();
		userData.setUser_id(1L);
		
		child.setUserData(userData);
		child.setHanziData(hanziData);
		
		childs.add(child);
		
		hanziData.setUserAndHanzi(childs);
		
		hanziDaoImpl.insert(hanziData);
		
		//make sure that data is inserted:
		HanziData result = hanziDaoImpl.selectBy(input);
		Assert.assertNotNull(result);
		Assert.assertEquals(result.getHanzi(), input);
		logger.debug("Result: " + result.toString());
		
	}
	
	//Case 2: Insert the already existing data in database
	@Test(expected=javax.persistence.PersistenceException.class)
	public void testInsert2() throws Exception{
		logger.debug("Start test");
		HanziData hanziData = new HanziData();
		hanziData.setHanzi("愛");
		hanziData.setCreated_date(System.currentTimeMillis());
		
		//prepare the child
		Set<UserAndHanzi> childs = new HashSet<UserAndHanzi>();
		UserAndHanzi child = new UserAndHanzi();
		UserData userData = new UserData();
		userData.setUser_id(1L);
		child.setUserData(userData);
		child.setHanziData(hanziData);
		childs.add(child);
		
		//add the child
		hanziData.setUserAndHanzi(childs);
		
		hanziDaoImpl.insert(hanziData);
	}

}
