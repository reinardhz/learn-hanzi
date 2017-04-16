package com.reinard.learnhanzi.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.reinard.learnhanzi.models.BookAndStroke;
import com.reinard.learnhanzi.models.HanziStrokeData;

/**
 * A class that provides many database operations on "book_and_stroke" table.
 * 
 * @author reinard.santosa
 *
 */
//TODO Finish this dao
@Repository(value="bookAndStrokeDaoImpl")
public class BookAndStrokeDaoImpl {
	
	private static final Logger logger = Logger.getLogger(BookAndStrokeDaoImpl.class);
	
	@Autowired
	private SessionFactory hibernateSessionFactory;
	
	/**
	 * 
	 * A method to insert data to "book_and_stroke" table.
	 * 
	 * @param input - Object "BookAndStrokeData" to be inserted.
	 * @return BookAndStrokeData - the successfully inserted "BookAndStrokeData".
	 * @throws Exception - If error happen when trying to insert data to database.
	 */
	public BookAndStroke insert(BookAndStroke input) throws Exception{
		
		logger.info("Inserting data into \"hanzi_stroke_data\" table...");
		//TODO finish this method
		return null;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<HanziStrokeData> selectAll() throws Exception{
		logger.info("Inserting data into \"hanzi_stroke_data\" table...");
		return null;
	}

}
