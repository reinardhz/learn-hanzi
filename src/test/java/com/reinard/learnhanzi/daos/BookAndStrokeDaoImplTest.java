package com.reinard.learnhanzi.daos;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.reinard.learnhanzi.dao.impl.BookAndStrokeDaoImpl;
import com.reinard.learnhanzi.models.BookAndStroke;
import com.reinard.learnhanzi.models.BookData;
import com.reinard.learnhanzi.models.HanziStrokeData;

import junit.framework.Assert;

/**
 * Provide test for "BookAndStrokeDaoImpl". Run it with JUnit in eclipse.
 * 
 * @author reinard.santosa
 *
 */
//This test use configuration in "test-context.xml", to use bean defined in there.
//put the file "test-context.xml" in "src/main/resources"
@ContextConfiguration(locations = "classpath:test-context.xml")

//To let spring run this unit test.
@RunWith(SpringJUnit4ClassRunner.class)
public class BookAndStrokeDaoImplTest {
	
	private final static Logger logger = Logger.getLogger(BookAndStrokeDaoImplTest.class);
	
	@Autowired
	private BookAndStrokeDaoImpl bookAndStrokeDaoImpl;
	
	//@Test
	public void insertTest() throws Exception{
		logger.debug("Test Insert starting...");
		
		logger.debug("Preparing \"BookData\" (Child of BookAndStroke):");
		BookData child1 = new BookData();
		child1.setBook_id(1L);
		logger.debug(child1);
		
		logger.debug("Preparing \"HanziStrokeData\" (Child of BookAndStroke):");
		HanziStrokeData child2 = new HanziStrokeData();
		child2.setHanzi_stroke_id(2L);
		logger.debug(child2);
		
		logger.debug("Preparing \"BookAndStroke\":");
		BookAndStroke model = new BookAndStroke();
		model.setBookData(child1);
		model.setHanziStrokeData(child2);
		logger.debug(model);
		
		logger.debug("Inserting \"BookAndStroke\" to database...");
		BookAndStroke result = bookAndStrokeDaoImpl.insert(model);
		Assert.assertNotNull(result);
		Assert.assertEquals(1L,result.getBookData().getBook_id());
		Assert.assertEquals(2L,result.getHanziStrokeData().getHanzi_stroke_id());
		logger.debug("Testing Insert \"BookAndStroke\" succeed.");
		logger.debug("Result: ");
		logger.debug(result.toString());
		
	}
	
	/**
	 * Test for method "BookAndStrokeDaoImpl.selectAllByBookId(long input_book_id)".
	 * Case 1: inputted book_id is 0, must throw Exception
	 * 
	 */
	@Test(expected=java.lang.Exception.class)
	public void selectAllByBookIdTest1() throws Exception{
		
		logger.debug("Test \"selectAllByBookId\" starting...");
		logger.debug("Preparing \"book_id\":");
		long book_id = 0;
		logger.debug(book_id);
		
		logger.debug("Selecting data from \"book_and_stroke\" table by inputted \"book_id\"...");
		bookAndStrokeDaoImpl.selectAllByBookId(book_id);
	}
	
	/**
	 * Test for method "BookAndStrokeDaoImpl.selectAllByBookId(long input_book_id)".
	 * Case 2: inputted book_id is negative number, must throw Exception
	 */
	@Test(expected=java.lang.Exception.class)
	public void selectAllByBookIdTest2() throws Exception{
		
		logger.debug("Test \"selectAllByBookId\" starting...");
		logger.debug("Preparing \"book_id\":");
		long book_id = -1L;
		logger.debug(book_id);
		logger.debug("Selecting data from \"book_and_stroke\" table by inputted \"book_id\"...");
		bookAndStrokeDaoImpl.selectAllByBookId(book_id);
		
	}
	
	/**
	 * Test for method "BookAndStrokeDaoImpl.selectAllByBookId(long input_book_id)".
	 * Case 3: inputted book_id is not in the database, must return null.
	 */
	@Test
	public void selectAllByBookIdTest3() throws Exception{
		logger.debug("Test \"selectAllByBookId\" starting...");
		logger.debug("Preparing \"book_id\":");
		long book_id = 9999L;
		logger.debug(book_id);
		logger.debug("Selecting data from \"book_and_stroke\" table by inputted \"book_id\"...");
		List<BookAndStroke> result = bookAndStrokeDaoImpl.selectAllByBookId(book_id);
		Assert.assertNull(result);
	}
	
	/**
	 * Test for method "BookAndStrokeDaoImpl.selectAllByBookId(long input_book_id)".
	 * Case 4: inputted "book_id" is in the database, must return all record that contains the "book_id".
	 */
	@Test
	public void selectAllByBookIdTest4() throws Exception{
		logger.debug("Test \"selectAllByBookId\" starting...");
		logger.debug("Preparing \"book_id\":");
		long book_id = 1L;
		logger.debug(book_id);
		logger.debug("Selecting data from \"book_and_stroke\" table by inputted \"book_id\"...");
		List<BookAndStroke> result = bookAndStrokeDaoImpl.selectAllByBookId(book_id);
		Assert.assertNotNull(result);
		logger.debug(result);
	}
	
	/**
	 * Test for method "BookAndStrokeDaoImpl.selectAll()".
	 * Case 4: inputted "book_id" is in the database, must return all record that contains the "book_id".
	 */
	@Test
	public void selectAll() throws Exception{
		logger.debug("Test \"selectAll\" starting...");
		List<BookAndStroke> result = bookAndStrokeDaoImpl.selectAll();
		Assert.assertNotNull(result);
		logger.debug(result);
	}
	
}
