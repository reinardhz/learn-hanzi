package com.reinard.learnhanzi.dao.impl;

import java.util.List;

import javax.persistence.PersistenceException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.reinard.learnhanzi.models.HanziData;
import org.apache.log4j.Logger;

@Repository(value="hanziDaoImpl")
public class HanziDaoImpl {
	
	private static final Logger logger = Logger.getLogger(HanziDaoImpl.class);
	
	@Autowired
	private SessionFactory hibernateSessionFactory;
	
	/**
	 * A method to select all data from "hanzi_data" table.
	 * 
	 * @return List&lt;HanziData&gt; All record from "hanzi_data" table. Return <i>null</i> if no data found.
	 * @throws Exception - If error happen when trying to select all data from database.
	 */
	@SuppressWarnings("all")
	public List<HanziData> selectAll() throws Exception{
		logger.info("Selecting all hanzi_data...");
		Session newSession = hibernateSessionFactory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = newSession.beginTransaction();
			List<HanziData> result = newSession.createCriteria(HanziData.class).list();
			transaction.commit();
			logger.info("Select all hanzi_data succeed.");
			logger.debug(result.toString());
			return result;
		}catch(Exception e){
			if (transaction != null) transaction.rollback();
			logger.error("Unexpected error occurred when trying to select all from \"hanzi_data\" table, rollback succeed", e);
			throw e;
		}finally{
			if(newSession.isOpen()) newSession.close();
		}
	}
	
	/**
	 * A method to select from "hanzi_data" table, by searching the unique hanzi.
	 * 
	 * @param hanzi - A String to search.
	 * @return HanziData - One record from "hanzi_data" table, that match the inputted hanzi. Return <i>null</i> if the data cannot be found.
	 * @throws Exception - If error happen when trying to select data from database.
	 */
	public HanziData selectBy(String hanzi) throws Exception{
		logger.info("Searching hanzi_data from inputted hanzi...");
		Session newSession = hibernateSessionFactory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = newSession.beginTransaction();
			//SELECT * FROM hanzi_data WHERE hanzi = "[inputted hanzi]"
			 Object resultObject = (newSession.createCriteria(HanziData.class).add(Restrictions.eq("hanzi", hanzi)).uniqueResult());
			 if(resultObject == null){
				 logger.info("HanziData not found.");
				 return null;
			 }else{
			 HanziData result = (HanziData)resultObject;
			 logger.info("HanziData found.");
			 logger.debug(result.toString());
			 return result;
			 }
		}catch(Exception e){
			if (transaction != null) transaction.rollback();
			
			logger.error("Unexpected error occurred when trying to select \"hanzi_data\" by inputted hanzi, rollback complete", e);
			throw e;
		}finally{
			if(newSession.isOpen()) newSession.close();
		}
	}
	
	/**
	 *  A method to insert data to "hanzi_data" table.
	 *  
	 * @param input - Object HanziData to be inserted.
	 * @return HanziData - the successfull inserted HanziData.
	 * @throws PersistenceException - If the data cannot inserted to the database, because it already exist.
	 * @throws Exception - If error happen when trying to insert data to database.
	 */
	public HanziData insert(HanziData input) throws Exception{
		logger.info("Inserting HanziData...");
		Session newSession = hibernateSessionFactory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = newSession.beginTransaction();
			newSession.save(input);
			transaction.commit();
			logger.info("Insert HanziData succeed.");
			logger.debug(input.toString());
			return input;
		}catch(Exception e){
			if (transaction != null) transaction.rollback();
			
			if(e instanceof PersistenceException){
				logger.error("Error: cannot insert the same HanziData, rollback succeed", e);
				throw e;
			}
			
			logger.error("Unexpected error occurred when inserting HanziData, rollback succeed",e);
			throw e;
		}finally{
			if(newSession.isOpen()) newSession.close();
			
		}
	}
	
}
