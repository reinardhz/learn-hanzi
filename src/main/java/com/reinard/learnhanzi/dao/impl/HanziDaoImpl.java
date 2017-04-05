package com.reinard.learnhanzi.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.reinard.learnhanzi.models.HanziData;
import org.apache.log4j.Logger;

@Repository("hanziDaoImpl")
public class HanziDaoImpl {
	
	private static final Logger logger = Logger.getLogger(HanziDaoImpl.class);
	
	@Autowired
	private SessionFactory hibernateSessionFactory;
	
	/**
	 * A method to select all data from "hanzi_data" table.
	 * 
	 * @param id
	 * @return List&lt;HanziData&gt; All record from "hanzi_data" table.
	 */
	public List<HanziData> selectAll() throws Exception{
		logger.info("Selecting all hanzi_data...");
		Session newSession = hibernateSessionFactory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = newSession.beginTransaction();
			List<HanziData> result = newSession.createCriteria(HanziData.class).list();
			transaction.commit();
			logger.info("Select all hanzi_data succeed.");
			logger.info(result.toString());
			return result;
		}catch(Exception e){
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error("Exception when trying to select all from \"hanzi_data\" table, rollback succeed", e);
			throw e;
		}finally{
			if(newSession.isOpen()){
				newSession.close();
			}
		}
	}
	
	/**
	 * A method to select from "hanzi_data" table, by searching the unique hanzi.
	 * 
	 * @param input - Hanzi to search.
	 * @return HanziData - One record from "hanzi_data" table, that match the inputted hanzi..
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
			 logger.info(result.toString());
			 return result;
			 }
		}catch(Exception e){
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error("Error when trying to select \"hanzi_data\" by inputted hanzi, rollback complete", e);
			throw e;
		}finally{
			if(newSession.isOpen()){
				newSession.close();
			}
		}
	}
	
	/**
	 *  A method to insert data to "hanzi_data" table.
	 *  
	 * @param input - Object HanziData to be inserted.
	 * @return HanziData - the successfull inserted HanziData.
	 */
	public HanziData insert(HanziData input) throws Exception{
		logger.info("Inserting Hanzidata...");
		Session newSession = hibernateSessionFactory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = newSession.beginTransaction();
			newSession.save(input);
			transaction.commit();
			logger.info("Insert Hanzidata succeed.");
			return input;
		}catch(Exception e){
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error("Error when insert HanziData, rollback succeed",e);
			throw e;
		}finally{
			if(newSession.isOpen()){
				newSession.close();
			}
		}
	}
	
}
