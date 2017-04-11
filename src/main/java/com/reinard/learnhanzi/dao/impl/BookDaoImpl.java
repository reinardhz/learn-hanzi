package com.reinard.learnhanzi.dao.impl;

import java.util.List;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.reinard.learnhanzi.models.BookData;
import com.reinard.learnhanzi.models.HanziData;

//TODO Finish this dao
@Repository(value="bookDaoImpl")
public class BookDaoImpl {
	
	private static final Logger logger = Logger.getLogger(BookDaoImpl.class);
	
	@Autowired
	private SessionFactory hibernateSessionFactory;
	
	/**
	 * A method to insert data to "book_data" table.
	 * 
	 * @param input - Object BookData to be inserted.
	 * @return BookData - the successfully inserted BookData.
	 * @throws Exception - If error happen when trying to insert data to database.
	 */
	public BookData insert(BookData input) throws Exception{
		//TODO test this method
		logger.info("Inserting data book_data table...");
		Session newSession = hibernateSessionFactory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = newSession.beginTransaction();
			newSession.save(input);
			transaction.commit();
			logger.info("Insert to book_data table succeed.");
			logger.debug(input);
			return input;
		}catch(Exception e){
			if (transaction != null) transaction.rollback();
			
			if(e instanceof PersistenceException){
				logger.error("Error: cannot insert the same book_data.book_name, rollback succeed", e);
				throw e;
			}
			
			logger.error("Unexpected error occurred when inserting to book_data table, rollback succeed",e);
			throw e;
		}finally{
			if(newSession.isOpen()) newSession.close();
		}
	}
	
	/**
	 * A method to select all data from "book_data" table.
	 * 
	 * @return List&lt;BookData&gt; All record from "book_data" table. Return <i>null</i> if no data found.
	 * @throws Exception
	 */
	@SuppressWarnings("all")
	public List<BookData> selectAll() throws Exception{
		//TODO test this method
		logger.info("Selecting all book_data...");
		Session newSession = hibernateSessionFactory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = newSession.beginTransaction();
			List<BookData> result = newSession.createCriteria(BookData.class).list();
			transaction.commit();
			logger.info("Select all book_data succeed.");
			logger.debug(result.toString());
			return result;
		}catch(Exception e){
			if (transaction != null) transaction.rollback();
			logger.error("Unexpected error occurred when trying to select all from \"book_data\" table, rollback succeed", e);
			throw e;
		}finally{
			if(newSession.isOpen()) newSession.close();
		}
	}
	
	
	
}
