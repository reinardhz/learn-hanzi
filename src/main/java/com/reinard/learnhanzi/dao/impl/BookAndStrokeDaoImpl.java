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
	 * @param input - Object "BookAndStroke" to be inserted.
	 * @return BookAndStroke - the successfully inserted "BookAndStroke".
	 * @throws Exception - If error happen when trying to insert data to database.
	 */
	public BookAndStroke insert(BookAndStroke input) throws Exception{
		//TODO finish this method.
		//TODO add logger.error("") message if transaction get rollback and if transaction not get rollback.
		logger.info("Inserting data into \"book_and_stroke\" table...");
		
		Session newSession = hibernateSessionFactory.openSession();
		Transaction transaction = null;
		try{
			transaction = newSession.beginTransaction();
			newSession.save(input);
			transaction.commit();
			logger.info("Insert to \"book_and_stroke\" table succeed.");
			logger.debug(input);
			return input;
		}catch(Exception e){
			if (transaction != null) transaction.rollback();
			logger.error("Unexpected error occurred when inserting to \"book_data\" table, rollback succeed.",e);
			throw e;
		}finally{
			if(newSession.isOpen()) newSession.close();
		}
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
