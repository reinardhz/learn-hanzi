package com.reinard.learnhanzi.service.impl;

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
	 * A method to handle menu "add new book", in "Book.html" page.
	 * 
	 * @param input - The new book name that will be added to database. Example: "第一書" (without double quotes).
	 * @return String - The message indicate the successfull action. Example: "Book 第一書  inserted." (without double quotes).
	 * @throws Exception If error happen when trying to insert data to database.
	 * @throws PersistenceException If error occurred because trying to insert the same "book_name".
	 */
	public String addNewBook(String input) throws PersistenceException, Exception{
		//TODO Test this service
		logger.info("binding the input with \"BookData\" object.");
		BookData model = new BookData();
		model.setBook_name(input);
		BookData result = bookDaoImpl.insert(model);
		
		return "Book " + result.getBook_name() + " inserted.";
	}

}
