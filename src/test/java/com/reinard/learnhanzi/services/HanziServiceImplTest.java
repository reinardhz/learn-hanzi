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
	
	@Test
	public void testSelectAll() throws Exception{
		String result = hanziService.selectAll();
		assertNotNull(result);
		System.out.println(result);
	}
	
	@Test
	public void testSelectBy() throws Exception{
		String result1 = hanziService.selectBy("我");
		assertNotNull(result1);
		System.out.println(result1);
		String result2 = hanziService.selectBy("愛");
		assertNull(result2);
	}

}
