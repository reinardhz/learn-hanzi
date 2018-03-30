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
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.reinard.learnhanzi.models.HanziData;

@Repository(value="hanziDaoImpl")
@Scope(value = "singleton")
public class HanziDaoImpl {
	
	private static final Logger logger = Logger.getLogger(HanziDaoImpl.class);
	
	@Autowired
	private SessionFactory hibernateSessionFactory;
	
	/**
	 * A method to select all data from "hanzi_data" table. This dao is tested.
	 * 
	 * @return List&lt;HanziData&gt; All record from "hanzi_data" table. Return <i>null</i> if no data found.
	 * @throws Exception - If error happen when trying to select all data from database.
	 */
	@SuppressWarnings("all")
	public List<HanziData> selectAll() throws Exception{
		
		logger.info("Selecting all \"hanzi_data\"...");
		Session newSession = hibernateSessionFactory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = newSession.beginTransaction();
			List<HanziData> result = newSession.createCriteria(HanziData.class).addOrder(Order.desc("created_date")).list();
			transaction.commit();
			
			if(result==null || result.isEmpty()){
				logger.info("Select all \"hanzi_data\" produce no data.");
				logger.info("Select all \"hanzi_data\" succeed.");
				return null;
			}else{
				logger.info("Select all \"hanzi_data\" succeed.");
				logger.debug(result.toString());
				return result;
			}
		}catch(Exception e){
			if (transaction != null){
				transaction.rollback();
				logger.error("Unexpected error occurred when trying to select all from \"hanzi_data\" table, rollback succeed", e);
			}else{
				logger.error("Unexpected error occurred when trying to select all from \"hanzi_data\" table.", e);
			}
			
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
	public HanziData selectBy(String input_hanzi) throws Exception{
		logger.info("Selecting data from \"hanzi_data\" table by inputted hanzi...");
		Session newSession = hibernateSessionFactory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = newSession.beginTransaction();
			
			Criteria selectByHanzi = newSession.createCriteria(HanziData.class);
			
			//define the filter for the data:
			//1. hanzi equals the inputted hanzi (WHERE hanzi = [inputted_hanzi])
			//because this Restrictions is will be put in "HanziData" criteria:
			//"hanzi" is point to variable "private String hanzi;" in Entity "HanziData", which then point to column "hanzi" in table "hanzi_data".
			Criterion equalsInputtedHanzi = Restrictions.eq("hanzi", input_hanzi);
			selectByHanzi.add(equalsInputtedHanzi);
			
			//SELECT * FROM hanzi_data WHERE hanzi = "[inputted hanzi]"
			//Ensure that there is only one unique result.
			Object resultObject = selectByHanzi.uniqueResult();
			
			if(resultObject == null){
				logger.info("\"hanzi_data\" not found.");
				logger.info("Searching \"hanzi_data\" from inputted hanzi succeed.");
				return null;
			}else{
				HanziData result = (HanziData)resultObject;
				logger.info("\"hanzi_data\" found.");
				logger.debug(result.toString());
				logger.info("Searching \"hanzi_data\" from inputted hanzi succeed.");
				return result;
			}
		}catch(Exception e){
			if (transaction != null){
				transaction.rollback();
				logger.error("Unexpected error occurred when trying to select \"hanzi_data\" by inputted hanzi, rollback succeed.", e);
			}else{
				logger.error("Unexpected error occurred when trying to select \"hanzi_data\" by inputted hanzi.", e);
			}
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
		logger.info("Inserting \"HanziData\"...");
		Session newSession = hibernateSessionFactory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = newSession.beginTransaction();
			newSession.save(input);
			transaction.commit();
			logger.info("Insert \"hanzi_data\" succeed.");
			logger.debug(input.toString());
			return input;
		}catch(Exception e){
			if ((transaction != null) && (e instanceof PersistenceException)){
				transaction.rollback();
				logger.error("Error: cannot insert the same \"hanzi_data\", rollback succeed.", e);
			}else if(e instanceof PersistenceException){
				logger.error("Error: cannot insert the same \"hanzi_data\".", e);
			}else if(transaction != null){
				transaction.rollback();
				logger.error("Unexpected error occurred when inserting to \"hanzi_data\" table rollback succeed", e);
			}else{
				logger.error("Unexpected error occurred when inserting to \"hanzi_data\" table.", e);
			}
			throw e;
		}finally{
			if(newSession.isOpen()) newSession.close();
		}
	}
	
}
