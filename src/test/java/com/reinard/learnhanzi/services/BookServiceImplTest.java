package com.reinard.learnhanzi.services;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.reinard.learnhanzi.helper.utils.StringUtil;
import com.reinard.learnhanzi.service.impl.BookServiceImpl;

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
	 * Case 1: Input new "book_name", this method must return "Book "+ book_name + " inserted.";
	 */
	//@Test
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
	 * Case 2: Input existing "book_name", this method must return String: "Error: Cannot Insert. Data Already Exist".
	 */
	@Test
	public void addNewBookTest2() throws Exception{
		logger.debug("Test insert starting...");
		logger.debug("Preparing input...");
		String input = "第三書";
		logger.debug(input);
		logger.debug("Inserting same \"book_name\" into database...");
		String resultFailed = bookServiceImpl.addNewBook(input);
		Assert.assertNotNull(resultFailed);
		Assert.assertEquals("Error: Cannot insert. Data already exist.", resultFailed);
		
	}
	
	/**
	 * A method to test method "BookServiceImpl.getAllBookName()".
	 * 
	 */
	@Test
	public void getAllBookNameTest() throws Exception{
		logger.debug("Test select starting...");
		logger.debug("Getting all book name from database...");
		String result = bookServiceImpl.getAllBookName();
		Assert.assertNotNull(result);
		Assert.assertFalse(result.contains(" "));
		
		logger.debug(result);
	}
	
	/**
	 * A method to test method "BookServiceImpl.getAllHanziStrokeInBook(String inputBookName)".
	 * 
	 * Case 1: There is a "hanzi_stroke" related with the inputted book name. This method must return String.
	 */
	@Test
	public void getAllHanziStrokeInBookTest1() throws Exception{
		logger.debug("Test \"getAllHanziStrokeInBook\" starting...");
		logger.debug("preparing the input: ");
		String inputBookName = "第一書";
		logger.debug(inputBookName);
		logger.debug("getting all hanzi stroke in book "+ inputBookName);
		String result = bookServiceImpl.getAllHanziStrokeInBook(inputBookName);
		Assert.assertNotNull(result);
		Assert.assertFalse(result.contains(" "));
		logger.debug("Test getting all hanzi stroke in book "+ inputBookName + "succeed.");
		logger.debug(result);
	}
	
	/**
	 * A method to test method "BookServiceImpl.getAllHanziStrokeInBook(String inputBookName)".
	 * 
	 * Case 2: There is a book name in the database, but no "hanzi_stroke" related with the inputted book name, this method must return null.
	 */
	//@Test
	public void getAllHanziStrokeInBookTest2() throws Exception{
		logger.debug("Test \"getAllHanziStrokeInBook\" starting...");
		logger.debug("preparing the input: ");
		String inputBookName = "第三書";
		logger.debug(inputBookName);
		logger.debug("getting all hanzi stroke in book "+ inputBookName);
		String result = bookServiceImpl.getAllHanziStrokeInBook(inputBookName);
		Assert.assertNull(result);
		logger.debug("Test getting all hanzi stroke in book "+ inputBookName + "succeed.");
		logger.debug(result);
	}
	
	/**
	 * A method to test method "BookServiceImpl.getAllHanziStrokeInBook(String inputBookName)".
	 * 
	 * Case 3: There is no book name in the database, this method must return null.
	 */
	@Test
	public void getAllHanziStrokeInBookTest3() throws Exception{
		logger.debug("Test \"getAllHanziStrokeInBook\" starting...");
		logger.debug("preparing the input: ");
		String inputBookName = "第一百萬書";
		logger.debug(inputBookName);
		logger.debug("getting all hanzi stroke in book "+ inputBookName);
		String result = bookServiceImpl.getAllHanziStrokeInBook(inputBookName);
		Assert.assertNull(result);
		logger.debug("Test getting all hanzi stroke in book "+ inputBookName + "succeed.");
		logger.debug(result);
	}
	
	/**
	 * A method to test method "BookServiceImpl.insertHanziStroke(String inputBookNameAndHanziStroke)".
	 * 
	 * Case 1: The inputted \"book_name\" is exist in the database, the inputted \"hanzi_stroke\" is not exist in the database, the inputted \"page_number\" is not exist in the database,  this method should return the String.
	 * 
	 */
	//@Test
	public void insertHanziStrokeInBook1() throws Exception{
		
		logger.debug("Test insert new \"hanzi_stroke\" and \"page_number\" in specified \"book_name\" starting...");
		logger.debug("preparing the input: ");
		String inputBookNameAndHanziStroke = "第二書:學習:七十四";
		logger.debug(inputBookNameAndHanziStroke);
		
		String[] splitInput = inputBookNameAndHanziStroke.split(":");
		String inputBook_name = splitInput[0];
		String inputHanzi_stroke = splitInput[1];
		String inputPage_number = splitInput[2];
		logger.info("Inserting hanzi_stroke: "+ inputHanzi_stroke + " and page number: "+ inputPage_number + " that is related to book: " + inputBook_name + " ...");
		String result = bookServiceImpl.insertHanziStrokeInBook(inputBookNameAndHanziStroke);
		
		Assert.assertNotNull(result);
		Assert.assertFalse(result.contains(" "));
		logger.debug("Test \"insertHanziStroke\" succeed.");
		logger.debug(result);
	}
	
	/**
	 * A method to test method "BookServiceImpl.insertHanziStroke(String inputBookNameAndHanziStroke)".
	 * 
	 * Case 2: The inputted \"book_name\", \"hanzi_stroke\", \"page_number\" is exist in the database this method should return the String.
	 */
	@Test
	public void insertHanziStrokeInBook2() throws Exception{
		
		logger.debug("Test insert existing \"hanzi_stroke\" and existing \"page_number\" in specified \"book_name\" starting...");
		logger.debug("preparing the input: ");
		String inputBookNameAndHanziStroke = "第二書:學習:七十四";
		logger.debug(inputBookNameAndHanziStroke);
		
		String[] splitInput = inputBookNameAndHanziStroke.split(":");
		String inputBook_name = splitInput[0];
		String inputHanzi_stroke = splitInput[1];
		String inputPage_number = splitInput[2];
		logger.info("Inserting hanzi_stroke: "+ inputHanzi_stroke + " and page number: "+ inputPage_number + " that is related to book: " + inputBook_name + " ...");
		String result = bookServiceImpl.insertHanziStrokeInBook(inputBookNameAndHanziStroke);
		
		Assert.assertNotNull(result);
		Assert.assertFalse(result.contains(" "));
		logger.debug("Test insert existing \"hanzi_stroke\" and existing \"page_number\" in specified \"book_name\" succeed.");
		logger.debug(result);
	}
	
	
	/**
	 * A method to test method "BookServiceImpl.insertHanziStroke(String inputBookNameAndHanziStroke)".
	 * 
	 * Case 3: The inputted \"book_name\" is not exist in the database, this method must throw Exception.
	 */
	@Test(expected=java.lang.Exception.class)
	public void insertHanziStrokeInBook3() throws Exception{
		
		logger.debug("Test \"insertHanziStrokeInBook\" starting...");
		logger.debug("preparing the input: ");
		String inputBookNameAndHanziStroke = "第一百書:不再:一百萬";
		logger.debug(inputBookNameAndHanziStroke);
		
		String[] splitInput = inputBookNameAndHanziStroke.split(":");
		String inputBook_name = splitInput[0];
		String inputHanzi_stroke = splitInput[1];
		String inputPage_number = splitInput[2];
		logger.info("Inserting hanzi_stroke: "+ inputHanzi_stroke + " and page number: "+ inputPage_number + " that is related to book: " + inputBook_name + " ...");
		String result = bookServiceImpl.insertHanziStrokeInBook(inputBookNameAndHanziStroke);
	}
	
	/**
	 * A method to test method "BookServiceImpl.insertHanziStroke(String inputBookNameAndHanziStroke)".
	 * 
	 * Case 4: The inputted \"page_number\" is not specified in the request, this method must throw Exception.
	 */
	@Test(expected=java.lang.Exception.class)
	public void insertHanziStrokeInBook4() throws Exception{
		
		logger.debug("Test \"insertHanziStrokeInBook\" starting...");
		logger.debug("preparing the input: ");
		String inputBookNameAndHanziStroke = "第一百書:不再";
		logger.debug(inputBookNameAndHanziStroke);
		String result = bookServiceImpl.insertHanziStrokeInBook(inputBookNameAndHanziStroke);
		
	}
	
	/**
	 * A method to test method "BookServiceImpl.insertHanziStroke(String inputBookNameAndHanziStroke)".
	 * 
	 * Case 5: The inputted argument is not same as the requirements, this method must throw Exception.
	 */
	@Test(expected=java.lang.Exception.class)
	public void insertHanziStrokeInBook5() throws Exception{
		
		logger.debug("Test \"insertHanziStrokeInBook\" starting...");
		logger.debug("preparing the input: ");
		String inputBookNameAndHanziStroke = "第一百書不再";
		logger.debug(inputBookNameAndHanziStroke);
		String result = bookServiceImpl.insertHanziStrokeInBook(inputBookNameAndHanziStroke);
		
	}
	
	
	/**
	 * A method to test method "BookServiceImpl.searchHanziStrokeInBook(String inputBookNameAndHanziStroke)".
	 * 
	 * Case 1: The inputted "book_name" and "hanzi_stroke" is exist in the database. This method must return String result.
	 * 
	 */
	@Test
	public void searchHanziStrokeInBookTest1() throws Exception{
		logger.debug("Test \"searchHanziStrokeInBook\" starting...");
		logger.debug("preparing the input: ");
		String inputBookNameAndHanziStroke = "第二書:學習";
		logger.debug(inputBookNameAndHanziStroke);
		String[] splitInput = inputBookNameAndHanziStroke.split(":");
		logger.info("Searching hanzi_stroke: "+ splitInput[1] + " that is related to book: " + splitInput[0] + " ...");
		String result = bookServiceImpl.searchHanziStrokeInBook(inputBookNameAndHanziStroke);
		
		Assert.assertNotNull(result);
		Assert.assertFalse(result.contains(" "));
		logger.debug("Test searching \"hanzi_stroke\" in specified \"book_name\" succeed.");
		logger.debug(result);
	}
	
	
	/**
	 * A method to test method "BookServiceImpl.searchHanziStrokeInBook(String inputBookNameAndHanziStroke)".
	 * 
	 * Case 2: The inputted "book_name" is exist in the database, but the inputted "hanzi_stroke" is not in relation with the inputted "book_name". This method must return null.
	 */
	@Test
	public void searchHanziStrokeInBookTest2() throws Exception{
		logger.debug("Test \"searchHanziStrokeInBook\" starting...");
		logger.debug("preparing the input: ");
		String inputBookNameAndHanziStroke = "第三書:在一起";
		logger.debug(inputBookNameAndHanziStroke);
		String[] splitInput = inputBookNameAndHanziStroke.split(":");
		logger.info("Searching hanzi_stroke: "+ splitInput[1] + " that is related to book: " + splitInput[0] + " ...");
		String result = bookServiceImpl.searchHanziStrokeInBook(inputBookNameAndHanziStroke);
		Assert.assertNull(result);
	}
	
	
	/**
	 * A method to test method "BookServiceImpl.searchHanziStrokeInBook(String inputBookNameAndHanziStroke)".
	 * 
	 * Case 3: The inputted "book_name" is not exist in the database. This method must return null.
	 */
	@Test
	public void searchHanziStrokeInBookTest3() throws Exception{
		logger.debug("Test \"searchHanziStrokeInBook\" starting...");
		logger.debug("preparing the input: ");
		String inputBookNameAndHanziStroke = "第千書:在一起";
		logger.debug(inputBookNameAndHanziStroke);
		String[] splitInput = inputBookNameAndHanziStroke.split(":");
		logger.info("Searching hanzi_stroke: "+ splitInput[1] + " that is related to book: " + splitInput[0] + " ...");
		String result = bookServiceImpl.searchHanziStrokeInBook(inputBookNameAndHanziStroke);
		Assert.assertNull(result);
	}
	
	
	/**
	 * A method to test method "BookServiceImpl.searchHanziStrokeInAllBook(String inputHanziStroke)".
	 * 
	 * Case 1: The inputted "hanzi_stroke" exist in table "hanzi_stroke_data", so this method must produce result.
	 */
	@Test
	public void searchHanziStrokeInAllBookTest1() throws Exception{
		logger.info("Test \"searchHanziStrokeInAllBookTest1\" starting...");
		
		String inputHanziStroke = "學習";
		logger.debug("preparing the input: "+inputHanziStroke);
		String result = bookServiceImpl.searchHanziStrokeInAllBook(inputHanziStroke);
		Assert.assertNotNull(result);
		Assert.assertFalse(StringUtil.isEmpty(result));
		logger.info(result);
	}
	
	
	/**
	 * A method to test method "BookServiceImpl.searchHanziStrokeInAllBook(String inputHanziStroke)".
	 * 
	 * Case 2: The inputted "hanzi_stroke" not exist in table "hanzi_stroke_data", so this method must produce null.
	 */
	@Test
	public void searchHanziStrokeInAllBookTest2() throws Exception{
		logger.info("Test \"searchHanziStrokeInAllBookTest2\" starting...");
		
		String inputHanziStroke = "舊";
		logger.debug("preparing the input: "+inputHanziStroke);
		String result = bookServiceImpl.searchHanziStrokeInAllBook(inputHanziStroke);
		Assert.assertNull(result);
		logger.info("result");
	}
}
