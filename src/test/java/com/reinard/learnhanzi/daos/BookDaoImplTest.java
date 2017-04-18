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

import com.reinard.learnhanzi.dao.impl.BookDaoImpl;
import com.reinard.learnhanzi.models.BookAndStroke;
import com.reinard.learnhanzi.models.BookData;
import com.reinard.learnhanzi.models.HanziStrokeData;

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
	
	/**
	 * case 1, test insert to "book_data" table and "book_and_stroke" table.
	 * 
	 * @throws Exception
	 */
	//@Test
	public void insertTest1() throws Exception{
		
		logger.debug("Test Insert starting...");
		logger.debug("Preparing BookData...");
		BookData secondBook = new BookData();
		secondBook.setBook_name("第二書");
		
		logger.debug("Preparing Child...");
		
		//preparing the child, to insert to table "book_and_stroke":
		Set<BookAndStroke> childSet = new HashSet<>();
		BookAndStroke child = new BookAndStroke();
		
		HanziStrokeData xiaoFangJu = new HanziStrokeData();
		//setting the primary key
		xiaoFangJu.setHanzi_stroke_id(3L);
		
		//set the data to insert to the "book_and_stroke.book_id". The "book_id" number is inside the "secondBook" new instance, which is an auto-generated number.
		child.setBookData(secondBook);
		
		//set the data to insert to the "book_and_stroke.hanzi_stroke_id", hanzi_stroke_id is taken from the "xiaoFangJu" new instance.
		child.setHanziStrokeData(xiaoFangJu);
		
		//add the child to parent
		childSet.add(child);
		secondBook.setBookAndStroke(childSet);
		
		logger.debug("BookData: ");
		logger.debug(secondBook.toString());
		
		logger.debug("Inserting \"BookData\" to database...");
		BookData result = bookDaoImpl.insert(secondBook);
		Assert.assertNotNull(result);
		Assert.assertEquals("第二書",secondBook.getBook_name());
		logger.debug("Testing Inserting \"BookData\" to succeed.");
		logger.debug("Result: ");
		logger.debug(result.toString());
	}
	
	
	/**
	 * Test method "BookDaoImpl.insert(BookData input)".
	 * 
	 * Case 2: insert the same "book_name", this method must throw "javax.persistence.PersistenceException;".
	 * 
	 */
	@Test(expected=javax.persistence.PersistenceException.class)
	public void insertTest2() throws Exception{
		logger.debug("Test Insert starting...");
		logger.debug("Preparing BookData...");
		BookData secondBook = new BookData();
		secondBook.setBook_name("第二書");
		logger.debug("Inserting \"BookData\" to database...");
		bookDaoImpl.insert(secondBook);
	}
	
	/**
	 * Test method "BookDaoImpl.selectAll()".
	 * 
	 * Case 1: There is a data in database, so this method must produce non-null result.
	 * 
	 */
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
