package com.reinard.learnhanzi.daos;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.reinard.learnhanzi.dao.impl.BookDaoImpl;
import com.reinard.learnhanzi.models.BookData;

import junit.framework.Assert;

/**
 * Provide test for "BookDaoImpl". Run it with JUnit in eclipse
 * 
 * @author reinard.santosa
 *
 */
//This test use configuration in "test-context.xml", to use bean defined in there.
//put the file "test-context.xml" in "src/main/resources"
@ContextConfiguration(locations = "classpath:test-context.xml")

//To let spring run this unit test.
@RunWith(SpringJUnit4ClassRunner.class)
public class BookDaoImplTest {
	
	private final static Logger logger = Logger.getLogger(BookDaoImplTest.class);
	
	@Autowired
	private BookDaoImpl bookDaoImpl;
	
	//@Test
	public void insertTest() throws Exception{
		BookData model = new BookData();
		model.setBook_name("第二課");
		logger.debug("Test Insert starting...");
		logger.debug("Preparing BookData...");
		logger.debug("BookData: ");
		logger.debug(model.toString());
		
		logger.debug("Inserting \"BookData\" to database...");
		BookData result = bookDaoImpl.insert(model);
		Assert.assertNotNull(result);
		Assert.assertEquals("第二課",model.getBook_name());
		logger.debug("Testing Inserting \"BookData\" to succeed.");
		logger.debug("Result: ");
		logger.debug(result.toString());
	}
	
	@Test
	public void selectAllTest() throws Exception{
		logger.debug("Test Select All starting...");
		logger.debug("selecting all data from \"book_data\" table");
		List<BookData> result = bookDaoImpl.selectAll();
		Assert.assertNotNull(result);
		logger.debug("Result: ");
		logger.debug(result);
	}
	
}
