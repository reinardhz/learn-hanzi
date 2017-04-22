package com.reinard.learnhanzi.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.PersistenceException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reinard.learnhanzi.dao.impl.BookAndStrokeDaoImpl;
import com.reinard.learnhanzi.dao.impl.BookDaoImpl;
import com.reinard.learnhanzi.dao.impl.HanziStrokeDaoImpl;
import com.reinard.learnhanzi.json.AllHanziStrokeInBookName;
import com.reinard.learnhanzi.json.Hanzi_stroke_data;
import com.reinard.learnhanzi.models.BookAndStroke;
import com.reinard.learnhanzi.models.BookData;
import com.reinard.learnhanzi.models.HanziStrokeData;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A class to provide service related with "Book.html" page.
 * 
 * @author reinard.santosa
 *
 */
@Service("bookServiceImpl")
public class BookServiceImpl {
	
	private static final Logger logger = Logger.getLogger(BookServiceImpl.class);
	
	@Autowired
	private BookDaoImpl bookDaoImpl;
	
	@Autowired
	private BookAndStrokeDaoImpl bookAndStrokeDaoImpl;
	
	@Autowired
	private HanziStrokeDaoImpl hanziStrokeDaoImpl;
	
	
	/**
	 * A method to handle menu "add new book", in "Book.html" page. (tested OK).
	 * 
	 * @param input - The new book name that will be added to database. Example: "第一書" (without double quotes).
	 * @return String - The message indicate the successfull action. Example: "Book 第一書  inserted." (without double quotes).
	 * @throws Exception If error happen when trying to insert data to database.
	 * @throws PersistenceException If error occurred because trying to insert the same "book_name".
	 */
	public String addNewBook(String input) throws PersistenceException, Exception{
		logger.info("binding the input with \"BookData\" object.");
		BookData model = new BookData();
		model.setBook_name(input);
		BookData result = bookDaoImpl.insert(model);
		logger.info("Book " + result.getBook_name() + " inserted.");
		return "Book " + result.getBook_name() + " inserted.";
	}
	
	/**
	 * A method to get all book name from database. (tested OK).
	 * 
	 * @return String All book name separated by comma. Example: 第一書,第二書,第三書,第四書,第五書 <br/>
	 * or null, if no data found.
	 * @throws Exception If error happened when trying to select all data from database.
	 */
	public String getAllBookName() throws Exception{
		logger.info("getting all book name from database...");
		
		
		logger.debug("getting all BookData ...");
		List<BookData> bookDataList = bookDaoImpl.selectAll();
		
		if(bookDataList==null){
			logger.debug("No BookData found.");
			return null;
		}
		
		logger.debug("All BookData found:");
		logger.debug(bookDataList);
		
		logger.debug("converting all BookData to String...");
		StringBuilder resultStringBuilder = new StringBuilder();
		
		Iterator<BookData> iteratorBookData = bookDataList.iterator();
		
		//get the max number index of the list.
		int maxIndex = bookDataList.size()-1;
		
		for(int i=0; i<=maxIndex; ++i){
			BookData bookData = bookDataList.get(i);
			resultStringBuilder.append(bookData.getBook_name());
			//put the comma character only it there is a next element.
			//this is to ensure that there is always an element after the comma sign.
			if( (i+1) <= maxIndex ){
				resultStringBuilder.append(",");
			}
		}
		
		
		String result = resultStringBuilder.toString();
		
		logger.debug("converting succeed:");
		logger.debug(result);
		return result;
	}
	
	/**
	 * A method to response all hanzi_stroke written in specified book_name. (tested OK).
	 * 
	 * @param inputBookName - The book_name Example: "第一書" (without double quotes).
	 * @return String result - All hanzi stroke in the book_name. Example: {"book_name":"第一書", "hanzi_stroke_data":[{"hanzi_stroke":"營業員", "created_date":"1491448282651"},{"hanzi_stroke":"電郵", "created_date":"1491448282652"},{"hanzi_stroke":"發音", "created_date":"1491448282653"}]}
	 * @return Null, if not data found.
	 * @throws Exception If error happened when trying to select all hanzi_stroke from database.
	 */
	public String getAllHanziStrokeInBook(String inputBookName) throws Exception{
		
		logger.info("Getting all hanzi stroke in book " + inputBookName + " ...");
		
		
		logger.debug("1. Get the \"book_id\" of the \"book_name\".");
		BookData bookData = bookDaoImpl.selectBy(inputBookName);
		if(bookData == null){
			logger.info("bookData is null. Return null.");
			return null;
		}
		long book_id = bookData.getBook_id();
		
		
		logger.debug("2. Get the \"BookAndStroke\" in the \"book_name\".");
		List<BookAndStroke> listOfbookAndStroke = bookAndStrokeDaoImpl.selectAllByBookId(book_id);
		if(listOfbookAndStroke==null || listOfbookAndStroke.isEmpty()){
			logger.info("\"BookAndStroke\" in the \"book_name\" is null. Return null.");
			return null;
		}
		
		
		logger.debug("3. Get the \"hanzi_stroke\" and \"created_date\" in the \"BookAndStroke\" then, add it into \"List<Hanzi_stroke_data>\".");
		List<Hanzi_stroke_data> listOfHanzi_stroke_data = new ArrayList<>();;
		for(BookAndStroke bookAndStroke : listOfbookAndStroke){
			
			if(bookAndStroke.getHanziStrokeData() == null){
				logger.info("\"HanziStrokeData\" in the \"BookAndStroke\" is null. Return null.");
				return null;
			}
			
			if(bookAndStroke.getHanziStrokeData().getHanzi_stroke() == null){
				logger.info("\"hanzi_stroke\" in the \"HanziStrokeData\" is null. Return null.");
				return null;
			}
			
			String hanzi_stroke = bookAndStroke.getHanziStrokeData().getHanzi_stroke();
			long created_date = bookAndStroke.getHanziStrokeData().getCreated_date();
			
			Hanzi_stroke_data hanzi_stroke_data = new Hanzi_stroke_data();
			hanzi_stroke_data.setHanzi_stroke(hanzi_stroke);
			hanzi_stroke_data.setCreated_date(String.valueOf(created_date));
			listOfHanzi_stroke_data.add(hanzi_stroke_data);
		}
		
		
		logger.debug("4. Convert \"List<Hanzi_stroke_data>\" into \"Hanzi_stroke_data[]\"");
		Hanzi_stroke_data[] arrayOfHanziStrokeData = listOfHanzi_stroke_data.toArray(new Hanzi_stroke_data[0]);
		
		logger.debug("5. Prepare the \"AllHanziStrokeInBookName\" class to convert to json");
		AllHanziStrokeInBookName allHanziStrokeInBookName = new AllHanziStrokeInBookName();
		allHanziStrokeInBookName.setBook_name(inputBookName);
		allHanziStrokeInBookName.setHanzi_stroke_data(arrayOfHanziStrokeData);
		
		
		logger.debug("6. Convert the \"AllHanziStrokeInBookName\" class into json string");
		ObjectMapper mapper = new ObjectMapper();
		String result = mapper.writeValueAsString(allHanziStrokeInBookName);
		
		logger.debug("7. Return the result: ");
		logger.debug(result);
		return result;
		
	}
	
	/**
	 * A method to insert hanzi_stroke that is related to the book_name. (tested OK).
	 * 
	 * @param inputBookNameAndHanziStroke - The book_name and hanzi_stroke to be inserted. Example: "第一書:郵局" (without double quotes). Do not change the order, as this could cause system behaviour is not working as expected.
	 * @return String result - The successfully inserted hanzi_stroke that is related to the book_name. Example: {"book_name":"第一書", "hanzi_stroke_data":[{"hanzi_stroke":"郵局", "created_date":"1491448282651"}]}
	 * @throws Exception If error happened when trying to insert hanzi_stroke into database.
	 */
	public String insertHanziStroke(String inputBookNameAndHanziStroke) throws Exception{
		
		String[] splitInput = inputBookNameAndHanziStroke.split(":");
		logger.info("Inserting hanzi_stroke: "+ splitInput[1] + " that is related to book: " + splitInput[0] + " ...");
		
		logger.debug("1. Make sure that the \"book_name\" is exist in the database by selecting from table \"book_data\" by inputted \"book_name\".");
		BookData bookData = bookDaoImpl.selectBy(splitInput[0]);
		
		if(bookData == null){
			throw new Exception("The inputted book_name is not exist in the database.");
		}
		
		logger.debug("2. Insert the \"hanzi_stroke\" into table \"hanzi_stroke_data\", then get the \"hanzi_stroke_id\".");
		HanziStrokeData hanziStrokeData = new HanziStrokeData();
		hanziStrokeData.setHanzi_stroke(splitInput[1]);
		long created_date = System.currentTimeMillis();
		hanziStrokeData.setCreated_date(created_date);
		HanziStrokeData insertedHanziStroke = hanziStrokeDaoImpl.insert(hanziStrokeData);
		
		logger.debug("3. Insert the \"book_id\" and \"hanzi_stroke_id\" into table \"book_and_stroke\".");
		BookAndStroke bookAndStroke = new BookAndStroke();
		bookAndStroke.setBookData(bookData);
		bookAndStroke.setHanziStrokeData(insertedHanziStroke);
		BookAndStroke insertedBookAndStroke = bookAndStrokeDaoImpl.insert(bookAndStroke);
		
		
		logger.debug("4. Set the \"book_name\", \"hanzi_stroke\", \"created_date\" inside \"AllHanziStrokeInBookName\" object.");
		Hanzi_stroke_data hanzi_stroke_data = new Hanzi_stroke_data();
		hanzi_stroke_data.setHanzi_stroke(insertedHanziStroke.getHanzi_stroke());
		hanzi_stroke_data.setCreated_date(String.valueOf(created_date));
		Hanzi_stroke_data[] arrayOfHanziStrokeData = new Hanzi_stroke_data[1];
		arrayOfHanziStrokeData[0] = hanzi_stroke_data;
		AllHanziStrokeInBookName allHanziStrokeInBookName = new AllHanziStrokeInBookName();
		allHanziStrokeInBookName.setBook_name(splitInput[0]);
		allHanziStrokeInBookName.setHanzi_stroke_data(arrayOfHanziStrokeData);
		
		
		logger.debug("5. Convert the \"AllHanziStrokeInBookName\" class into json string");
		ObjectMapper mapper = new ObjectMapper();
		String result = mapper.writeValueAsString(allHanziStrokeInBookName);
		
		
		logger.debug("6. Return the result: ");
		logger.debug(result);
		return result;
	}
	
}
