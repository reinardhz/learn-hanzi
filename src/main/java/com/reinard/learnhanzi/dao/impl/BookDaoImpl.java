package com.reinard.learnhanzi.dao.impl;

import java.util.List;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.reinard.learnhanzi.models.BookData;

/**
 * A class that provides many database operations on "book_data" table.
 * 
 * @author reinard.santosa
 *
 */
@Repository(value="bookDaoImpl")
@Scope(value = "singleton")
public class BookDaoImpl {
	
	private static final Logger logger = Logger.getLogger(BookDaoImpl.class);
	
	@Autowired
	private SessionFactory hibernateSessionFactory;
	
	
	/**
	 * A method to insert data to "book_data" table. (tested OK)
	 * 
	 * @param input - Object BookData to be inserted.
	 * @return BookData - the successfully inserted BookData.
	 * @throws Exception If error happen when trying to insert data to database.
	 * @throws PersistenceException If error occurred because trying to insert the same book_name.
	 */
	public BookData insert(BookData input) throws PersistenceException, Exception{
		logger.info("Inserting data \"book_data\" table...");
		Session newSession = hibernateSessionFactory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = newSession.beginTransaction();
			newSession.save(input);
			transaction.commit();
			logger.info("Insert to \"book_data\" table succeed.");
			logger.debug(input);
			return input;
		}catch(Exception e){
			if ((transaction != null) && (e instanceof PersistenceException)){
				transaction.rollback();
				logger.error("Error: cannot insert the same book_data.book_name, rollback succeed.", e);
			}else if(e instanceof PersistenceException){
				logger.error("Error: cannot insert the same book_data.book_name.", e);
			}else if(transaction != null){
				transaction.rollback();
				logger.error("Unexpected error occurred when inserting to \"book_data\" table, rollback succeed",e);
			}else{
				logger.error("Unexpected error occurred when inserting to \"book_data\" table.",e);
			}
			throw e;
		}finally{
			if(newSession.isOpen()) newSession.close();
		}
	}
	
	/**
	 * A method to select all data from "book_data" table. (tested OK)
	 * 
	 * @return List&lt;BookData&gt; All record from "book_data" table. Return <i>null</i> if no data found.
	 * @throws Exception If error happened when trying to select all data from database.
	 */
	@SuppressWarnings("all")
	public List<BookData> selectAll() throws Exception{
		logger.info("Selecting all data from \"book_data\" table...");
		Session newSession = hibernateSessionFactory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = newSession.beginTransaction();
			List<BookData> result = newSession.createCriteria(BookData.class).list();
			transaction.commit();
			logger.info("Selecting all data from \"book_data\" table succeed.");
			logger.debug(result.toString());
			
			if(result==null || result.isEmpty()){
				logger.info("Selecting all data from \"book_data\" produced no data.");
				logger.info("Selecting all data from \"book_data\" table succeed.");
				return null;
			}
			logger.info("Selecting all data from \"book_data\" table succeed.");
			return result;
		}catch(Exception e){
			if (transaction != null){
				transaction.rollback();
				logger.error("Unexpected error occurred when trying to select all from \"book_data\" table, rollback succeed.", e);
			}else{
				logger.error("Unexpected error occurred when trying to select all from \"book_data\" table.", e);
			}
			throw e;
		}finally{
			if(newSession.isOpen()) newSession.close();
		}
		
	}
	
	/**
	 * A method to select one record from "book_data" table by specified book_name. (tested OK)
	 * 
	 * @return BookData One record from "book_data" table. Return <i>null</i> if no data found.
	 * @throws Exception If error happened when trying to select data from database.
	 */
	public BookData selectBy(String inputBookName){
		
		logger.info("Selecting all data from \"book_data\" table by \"book_name\"...");
		Session newSession = hibernateSessionFactory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = newSession.beginTransaction();
			Criteria selectByBookName = newSession.createCriteria(BookData.class);
			
			//define the filter for the data:
			//1. book_name equals the inputted book_name (WHERE book_name = [inputted book_name])
			//because this Restrictions is will be put in "BookData" criteria:
			//book_name is point to variable "private String book_name;" in entity BookData, which then point to column "book_name" in table "book_data".
			Criterion equalsInputtedBookName = Restrictions.eq("book_name", inputBookName);
			selectByBookName.add(equalsInputtedBookName);
			
			//SELECT * FROM book_data WHERE book_name = [input]
			//Ensure that there is only one unique result.
			Object resultObject = selectByBookName.uniqueResult();
			
			if(resultObject == null){
				logger.info("\"book_data\" not found.");
				logger.info("Searching \"book_data\" from inputted book_name succeed.");
				return null;
			}else{
				BookData result = (BookData) resultObject;
				logger.info("\"book_data\" found.");
				logger.debug(result.toString());
				logger.info("Selecting all data from \"book_data\" table by \"book_name\" succeed.");
				return result;
			}
			
		}catch(Exception e){
			if (transaction != null){
				transaction.rollback();
				logger.error("Unexpected error occurred when trying to select \"book_data\" by inputted book_name, rollback succeed.", e);
			}else{
				logger.error("Unexpected error occurred when trying to select \"book_data\" by inputted book_name.", e);
			}
			throw e;
		}finally{
			if(newSession.isOpen()) newSession.close();
		}
		
	}
}
