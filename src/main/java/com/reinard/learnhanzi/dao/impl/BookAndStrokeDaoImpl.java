package com.reinard.learnhanzi.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.reinard.learnhanzi.models.BookAndStroke;
import com.reinard.learnhanzi.models.BookData;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * A class that provides many database operations on "book_and_stroke" table. This class is tested (OK)
 * 
 * @author reinard.santosa
 *
 */
@Repository(value="bookAndStrokeDaoImpl")
public class BookAndStrokeDaoImpl {
	
	private static final Logger logger = Logger.getLogger(BookAndStrokeDaoImpl.class);
	
	@Autowired
	private SessionFactory hibernateSessionFactory;
	
	/**
	 * 
	 * A method to insert data to "book_and_stroke" table. (tested OK).
	 * 
	 * @param input - Object "BookAndStroke" to be inserted.
	 * @return BookAndStroke - the successfully inserted "BookAndStroke".
	 * @throws Exception - If error happen when trying to insert data to database.
	 */
	public BookAndStroke insert(BookAndStroke input) throws Exception{
		
		logger.info("Inserting data into \"book_and_stroke\" table...");
		
		Session newSession = hibernateSessionFactory.openSession();
		Transaction transaction = null;
		try{
			//begin the transaction
			transaction = newSession.beginTransaction();
			newSession.save(input);
			transaction.commit();
			logger.info("Insert to \"book_and_stroke\" table succeed.");
			logger.debug(input);
			return input;
		}catch(Exception e){
			if (transaction != null){
				transaction.rollback();
				logger.error("Unexpected error occurred when inserting to \"book_and_stroke\" table, rollback succeed.",e);
			}else{
				logger.error("Unexpected error occurred when inserting to \"book_and_stroke\" table.",e);
			}
			throw e;
		}finally{
			if(newSession.isOpen()) newSession.close();
		}
	}
	
	/**
	 * A method to select all data from "book_and_stroke" table that contains the specified "book_id". (tested OK).
	 * 
	 * @param input_book_id - the inputted "book_id".
	 * @return List&lt;BookAndStroke&gt; - All row from "book_and_stroke" table, that containes the inputted "book_id", or null if no data.
	 * @throws Exception - If error happened when trying to select all data from database.
	 */
	public List<BookAndStroke> selectAllByBookId(long input_book_id) throws Exception{
		
		logger.info("Selecting data from \"book_and_stroke\" table by inputted \"book_id\"...");
		
		if(input_book_id<=0){
			logger.info("\"input_book_id\" cannot zero or negative number.");
			throw new Exception("\"input_book_id\" cannot zero or negative number.");
		}
		
		Session newSession = hibernateSessionFactory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = newSession.beginTransaction();
			
			Criteria selectAllByBookId =  newSession.createCriteria(BookAndStroke.class);
			
			//define the filter for the data:
			//1. book_id equals inputted book_id (WHERE book_id = [inputted book_id]):
			//because this Restrictions is will be put in "BookAndStroke" criteria:
			//"bookData.book_id" is point to variable "private long book_id;" in entity "BookData", which then point to column "book_id", in table "BookAndStroke".
			Criterion equalsBookId = Restrictions.eq("bookData.book_id", input_book_id);
			selectAllByBookId.add(equalsBookId);
			
			//get the filtered result (SELECT * FROM book_and_stroke WHERE book_id = [inputted book_id])
			List<BookAndStroke> result = selectAllByBookId.list();
			
			if(result==null || result.isEmpty()){
				logger.info("\"book_and_stroke\" not found.");
				logger.info("Selecting data from \"book_and_stroke\" table by inputted \"book_id\" succeed.");
				return null;
			}else{
				logger.info("\"book_and_stroke\" not found.");
				logger.debug(result);
				logger.info("Selecting data from \"book_and_stroke\" table by inputted \"book_id\" succeed.");
				return result;
			}
			
		}catch(Exception e){
			if (transaction != null){
				transaction.rollback();
				logger.error("Unexpected error occurred when trying to select data from \"book_and_stroke\" table by inputted \"book_id\", rollback succeed.", e);
			}else{
				logger.error("Unexpected error occurred when trying to select data from \"book_and_stroke\" table by inputted \"book_id\".", e);
			}
			throw e;
		}finally{
			if(newSession.isOpen()) newSession.close();
		}
		
	}
	
	
	/**
	 * A method to select all data from "book_and_stroke" table. (tested OK).
	 * 
	 * @return List&lt;BookAndStroke&gt; - All record from "book_and_stroke" table. Return <i>null</i> if no data found.
	 * @throws Exception - If error happened when trying to select all data from database.
	 */
	public List<BookAndStroke> selectAll() throws Exception{
		
		logger.info("Selecting all data from \"book_and_stroke\" table...");
		
		Session newSession = hibernateSessionFactory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = newSession.beginTransaction();
			List<BookAndStroke> result = newSession.createCriteria(BookAndStroke.class).list();
			transaction.commit();
			logger.info("Selecting all data from \"book_and_stroke\" table succeed.");
			logger.debug(result.toString());
			
			if(result==null || result.isEmpty()){
				logger.info("Select all \"book_and_stroke\" produce no data.");
				logger.info("Select all \"book_and_stroke\" succeed.");
				return null;
			}else{
				logger.info("Select all \"book_and_stroke\" succeed.");
				logger.debug(result.toString());
				return result;
			}
			
		}catch(Exception e){
			if (transaction != null){
				transaction.rollback();
				logger.error("Unexpected error occurred when trying to select all from \"book_and_stroke\" table, rollback succeed.", e);
			}else{
				logger.error("Unexpected error occurred when trying to select all from \"book_and_stroke\" table.", e);
			}
			throw e;
		}finally{
			if(newSession.isOpen()) newSession.close();
		}
		
	}

}
