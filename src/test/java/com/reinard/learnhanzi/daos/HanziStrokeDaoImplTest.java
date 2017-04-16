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
import com.reinard.learnhanzi.dao.impl.HanziStrokeDaoImpl;
import com.reinard.learnhanzi.models.BookAndStroke;
import com.reinard.learnhanzi.models.BookData;
import com.reinard.learnhanzi.models.HanziStrokeData;

import junit.framework.Assert;

/**
 * Provide test for "HanziStrokeDaoImpl". Run it with JUnit in eclipse.
 * 
 * @author reinard.santosa
 *
 */
//This test use configuration in "test-context.xml", to use bean defined in there.
//put the file "test-context.xml" in "src/main/resources"
@ContextConfiguration(locations = "classpath:test-context.xml")

//To let spring run this unit test.
@RunWith(SpringJUnit4ClassRunner.class)
public class HanziStrokeDaoImplTest {

	private final static Logger logger = Logger.getLogger(HanziStrokeDaoImplTest.class);
	
	@Autowired
	private HanziStrokeDaoImpl hanziStrokeDaoImpl;
	
	//@Test
	public void insertTest() throws Exception{
		logger.debug("Test Insert starting...");
		logger.debug("Preparing HanziStroke...");
		HanziStrokeData faYin = new HanziStrokeData();
		faYin.setHanzi_stroke("發音");
		faYin.setCreated_date(System.currentTimeMillis());
		
		logger.debug("Preparing Child...");
		
		//to insert to table "book_and_stroke":
		Set<BookAndStroke> childSet = new HashSet<>();
		BookAndStroke child = new BookAndStroke();
		
		BookData firstBook = new BookData();
		//setting the primary key
		firstBook.setBook_id(1L);
		
		//set the data to insert to the "book_and_stroke.hanzi_stroke_id". The "hanzi_stroke_id" number is inside the "faYin" new instance, which is an auto-generated number.
		child.setHanziStrokeData(faYin);
		
		//set the data to insert to the "book_and_stroke.book_id", book_id is taken from the "firstBook" new instance.
		child.setBookData(firstBook);
		
		//add the child to parent
		childSet.add(child);
		faYin.setBookAndStroke(childSet);
		
		logger.debug("HanziStrokeData: ");
		logger.debug(faYin.toString());
		
		logger.debug("Inserting \"HanziStrokeData\" to database...");
		HanziStrokeData result = hanziStrokeDaoImpl.insert(faYin);
		Assert.assertNotNull(result);
		Assert.assertEquals("發音",faYin.getHanzi_stroke());
		logger.debug("Testing Inserting \"HanziStrokeData\" to succeed.");
		logger.debug("Result: ");
		logger.debug(result.toString());
	}
	
	@Test
	public void selectAllTest() throws Exception{
		logger.debug("Test Select All starting...");
		logger.debug("selecting all data from \"hanzi_stroke_data\" table");
		List<HanziStrokeData> result = hanziStrokeDaoImpl.selectAll();
		Assert.assertNotNull(result);
		logger.debug("Testing select all data from \"hanzi_stroke_data\" table");
		logger.debug("Result: ");
		logger.debug(result);
		
	}
	
}
