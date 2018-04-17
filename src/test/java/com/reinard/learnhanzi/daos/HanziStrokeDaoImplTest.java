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
	
	@Test
	public void insertTest() throws Exception{
		logger.debug("Test Insert starting...");
		logger.debug("Preparing HanziStroke...");
		HanziStrokeData yinNiDun = new HanziStrokeData();
		yinNiDun.setHanzi_stroke("印尼盾");
		yinNiDun.setPage_number("五");
		yinNiDun.setCreated_date(System.currentTimeMillis());
		
		logger.debug("Preparing Child...");
		
		//to insert to table "book_and_stroke":
		Set<BookAndStroke> childSet = new HashSet<>();
		BookAndStroke child = new BookAndStroke();
		
		BookData firstBook = new BookData();
		//setting the primary key, refer to the book_name: "第一書"
		firstBook.setBook_id(1L);
		
		//set the data to insert to the "book_and_stroke.hanzi_stroke_id". The "hanzi_stroke_id" number is inside the "yinNiDun" new instance, which is an auto-generated number.
		child.setHanziStrokeData(yinNiDun);
		
		//set the data to insert to the "book_and_stroke.book_id", book_id is taken from the "firstBook" new instance.
		child.setBookData(firstBook);
		
		//add the child to parent
		childSet.add(child);
		yinNiDun.setBookAndStroke(childSet);
		
		logger.debug("HanziStrokeData: ");
		logger.debug(yinNiDun.toString());
		
		logger.debug("Inserting \"HanziStrokeData\" to database...");
		HanziStrokeData result = hanziStrokeDaoImpl.insert(yinNiDun);
		Assert.assertNotNull(result);
		Assert.assertEquals("印尼盾", result.getHanzi_stroke());
		Assert.assertEquals("五", result.getPage_number());
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
		logger.debug("Testing select all data from \"hanzi_stroke_data\" table succeed.");
		logger.debug("Result: ");
		logger.debug(result);
	}
	
	/**
	 * Test for method "HanziStrokeDaoImpl.selectBy(long inputHanziStrokeId)"
	 * 
	 * Case 1: Table "hanzi_stroke_data" contains data and the "hanzi_stroke_id" exist in table book_data, so this method must produce non-null result.
	 */
	@Test
	public void selectByHanziStrokeId1() throws Exception{
		logger.debug("Test Select \"hanzi_stroke_data\" by \"hanzi_stroke_id\" starting...");
		
		long inputHanziStrokeId = 1L;
		logger.debug("Selecting \"hanzi_stroke_data\" by \"hanzi_stroke_id\": " +inputHanziStrokeId+ "...");
		HanziStrokeData result =  hanziStrokeDaoImpl.selectBy(inputHanziStrokeId);
		Assert.assertNotNull(result);
		Assert.assertEquals("營業員", result.getHanzi_stroke());
		Assert.assertEquals("一", result.getPage_number());
		logger.debug("Test Select \"hanzi_stroke_data\" by \"hanzi_stroke_id\" succeed.");
		logger.debug("Result: ");
		logger.debug(result);
	}
	
	/**
	 * Test for method "HanziStrokeDaoImpl.selectBy(long inputHanziStrokeId)"
	 * 
	 * Case 2: Table "hanzi_stroke_data" contains data and the "hanzi_stroke_id" not exist in table book_data, so this method must produce null result.
	 */
	@Test
	public void selectByHanziStrokeId2() throws Exception{
		logger.debug("Test Select \"hanzi_stroke_data\" by \"hanzi_stroke_id\" starting...");
		long inputHanziStrokeId = 9999L;
		logger.debug("Selecting \"hanzi_stroke_data\" by \"hanzi_stroke_id\": " +inputHanziStrokeId+ "...");
		HanziStrokeData result =  hanziStrokeDaoImpl.selectBy(inputHanziStrokeId);
		Assert.assertNull(result);
		logger.debug("Test Select \"hanzi_stroke_data\" by \"hanzi_stroke_id\" succeed.");
		logger.debug("Result: ");
		logger.debug(result);
	}
	
	/**
	 * Test for method "HanziStrokeDaoImpl.selectBy(long inputHanziStrokeId)"
	 * 
	 * Case 3: Table "hanzi_stroke_data" contains no data, so this method must produce null result.
	 */
	//@Test
	public void selectByHanziStrokeId3() throws Exception{
		
	}
	
	/**
	 * Test for method "HanziStrokeDaoImpl.selectBy(String inputHanziStroke)"
	 * 
	 * Case 1: The inputted hanzi_stroke exist in table "hanzi_stroke_data" and "book_and_stroke", so this method must produce result.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void selectByHanziStrokeTest1() throws Exception{
		logger.info("Test Select \"hanzi_stroke_data\" by \"hanzi_stroke_data\" starting...");
		String inputHanziStroke = "生詞";
		logger.info("Selecting \"hanzi_stroke_data\" by \"hanzi_stroke\": " +inputHanziStroke+ "...");
		List<HanziStrokeData> result = hanziStrokeDaoImpl.selectBy(inputHanziStroke);
		Assert.assertNotNull(result);
		Assert.assertFalse(result.isEmpty());
		
		for(HanziStrokeData current : result) {
			logger.info(current);
			Set<BookAndStroke> setOfBookAndStroke = current.getBookAndStroke();
			
			Assert.assertNotNull(setOfBookAndStroke);
			Assert.assertFalse(setOfBookAndStroke.isEmpty());
			logger.info("child:");
			for(BookAndStroke current2 : setOfBookAndStroke) {
				BookData bookData = current2.getBookData();
				Assert.assertNotNull(bookData);
				String bookName = bookData.getBook_name();
				Assert.assertNotNull(bookData);
				Assert.assertFalse(bookName.isEmpty());
				logger.info(bookData);
				logger.info("");
			}
			logger.info("");
		}
		
		logger.info("Test Select \"hanzi_stroke_data\" by \"hanzi_stroke\" succeed.");
		logger.info("Result: ");
		logger.info(result);
	}
	
	/**
	 * Test for method "HanziStrokeDaoImpl.selectBy(String inputHanziStroke)".
	 * 
	 * Case 2: The inputted hanzi_stroke not exist in table "hanzi_stroke_data", so this method must produce null result.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void selectByHanziStrokeTest2() throws Exception{
		logger.info("Test Select \"hanzi_stroke_data\" by \"hanzi_stroke_data\" starting...");
		String inputHanziStroke = "舊";
		logger.info("Selecting \"hanzi_stroke_data\" by \"hanzi_stroke\": " +inputHanziStroke+ "...");
		List<HanziStrokeData> result = hanziStrokeDaoImpl.selectBy(inputHanziStroke);
		Assert.assertNull(result);
	}
	
	
}
