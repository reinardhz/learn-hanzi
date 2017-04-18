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

import junit.framework.Assert;

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
	 * Case 1: Input new book_name, this method must return "Book "+ book_name + " inserted.";
	 */
	@Test
	public void addNewBookTest1() throws Exception{
		logger.debug("Test insert starting...");
		logger.debug("Preparing input...");
		String input = "第三書";
		logger.debug(input);
		logger.debug("Inserting new \"book_name\" into database...");
		String result = bookServiceImpl.addNewBook(input);
		Assert.assertEquals("Book 第三書 inserted.", result);
		logger.debug("Insert new \"book_name\" into database succeed.");
	}
	
	/**
	 * A method to test method "BookServiceImpl.addNewBook()".
	 * 
	 * Case 2: Input existing book_name, this method must throw PersistenceException.
	 */
	@Test(expected=javax.persistence.PersistenceException.class)
	public void addNewBookTest2() throws Exception{
		logger.debug("Test insert starting...");
		logger.debug("Preparing input...");
		String input = "第三書";
		logger.debug(input);
		logger.debug("Inserting same \"book_name\" into database...");
		String result = bookServiceImpl.addNewBook(input);
		Assert.assertNull(result);	
	}

}
