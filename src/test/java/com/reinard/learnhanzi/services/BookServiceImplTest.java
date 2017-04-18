package com.reinard.learnhanzi.services;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.reinard.learnhanzi.dao.impl.BookDaoImpl;
import com.reinard.learnhanzi.dao.impl.HanziDaoImpl;
import com.reinard.learnhanzi.service.impl.BookServiceImpl;
import com.reinard.learnhanzi.service.impl.HanziServiceImpl;

/**
 * Provide test for "BookServiceImplTest". Run it with JUnit in eclipse
 * 
 * @author reinard.santosa
 *
 */

//This test use configuration in "test-context.xml", to use bean defined in there.
//put the file "test-context.xml" in "src/main/resources"
@ContextConfiguration(locations = "classpath:test-context.xml")

//To let spring run this unit test.
@RunWith(SpringJUnit4ClassRunner.class)
public class BookServiceImplTest {
	
	private static final Logger logger = Logger.getLogger(BookServiceImplTest.class);
	
	@Autowired
	private BookServiceImpl bookServiceImpl;
	
	
	/**
	 * A method to test method "BookServiceImpl.addNewBook()".
	 * 
	 * Case 1: 
	 * 
	 * @param input
	 * @throws PersistenceException
	 * @throws Exception
	 */
	@Test
	public void addNewBookTest() throws PersistenceException, Exception{
		//TODO finish this test
	}

}
