package com.reinard.learnhanzi.service.impl;

import java.util.Iterator;
import java.util.List;

import javax.persistence.PersistenceException;
import org.springframework.stereotype.Service;
import com.reinard.learnhanzi.dao.impl.BookDaoImpl;
import com.reinard.learnhanzi.models.BookData;

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
	
	/**
	 * A method to handle menu "add new book", in "Book.html" page. (tested OK)
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
	 * A method to get all book name from database. (not-yet tested)
	 * 
	 * @return String All book name separated by comma. Example: 第一書,第二書,第三書,第四書,第五書 <br/>
	 * or null, if no data found.
	 * @throws Exception If error happened when trying to select all data from database.
	 */
	public String getAllBookName() throws Exception{
		//TODO test this method.
		
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
}
