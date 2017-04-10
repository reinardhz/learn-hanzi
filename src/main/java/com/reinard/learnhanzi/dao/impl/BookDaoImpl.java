package com.reinard.learnhanzi.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.reinard.learnhanzi.models.HanziData;

@Repository(value="bookDaoImpl")
public class BookDaoImpl {
	
	private static final Logger logger = Logger.getLogger(HanziDaoImpl.class);
	
	@Autowired
	private SessionFactory hibernateSessionFactory;
	
	/**
	 * A method to insert data to "book_data" table.
	 * 
	 * @param input - Object HanziData to be inserted.
	 * @return BookData - the successfull inserted BookData.
	 */
	public BookData insert(BookData input){
		//TODO finish this method
		
	}
	
}
