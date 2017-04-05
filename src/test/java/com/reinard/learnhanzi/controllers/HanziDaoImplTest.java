package com.reinard.learnhanzi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.reinard.learnhanzi.dao.impl.HanziDaoImpl;
import com.reinard.learnhanzi.models.HanziData;
import com.reinard.learnhanzi.models.UserAndHanzi;
import com.reinard.learnhanzi.models.UserData;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

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
	
	@Autowired
	private HanziDaoImpl hanziDaoImpl;
	
	@Test
	public void testSelectBy() throws Exception{
		HanziData result = hanziDaoImpl.selectBy("我");
		System.out.println(result.toString());
	}
	
	//@Test
	public void testInsert() throws Exception{
		HanziData input = new HanziData();
		input.setHanzi("買");
		input.setCreated_date(new Timestamp(System.currentTimeMillis()));
		
		Set<UserAndHanzi> childs = new HashSet<UserAndHanzi>();
		UserAndHanzi child = new UserAndHanzi();
		UserData userData = new UserData();
		userData.setUser_id(1L);
		child.setUserData(userData);
		child.setHanziData(input);
		childs.add(child);
		
		input.setUserAndHanzi(childs);
		hanziDaoImpl.insert(input);
	}
	

}
