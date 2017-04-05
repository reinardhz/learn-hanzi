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
		
		Session newSession = hibernateSessionFactory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = newSession.beginTransaction();
			List<HanziData> result = newSession.createCriteria(HanziData.class).list();
			transaction.commit();
			return result;
		}catch(Exception e){
			logger.error("Exception when trying to select all from \"hanzi_data\" table", e);
			if (transaction != null) {
				transaction.rollback();
			}
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
		Session newSession = hibernateSessionFactory.openSession();
		Transaction transaction = null;
		
		try{
			logger.info("Selecting hanzi_data from inputted hanzi...");
			transaction = newSession.beginTransaction();
			//SELECT * FROM hanzi_data WHERE hanzi = "[inputted hanzi]"
			 Object resultObject = (newSession.createCriteria(HanziData.class).add(Restrictions.eq("hanzi", hanzi)).uniqueResult());
			 HanziData result = (HanziData)resultObject;
			 logger.info("Data HanziData is found");
			 logger.info(result.toString());
			 return result;
		}catch(Exception e){
			logger.error("Exception when trying to select \"hanzi_data\" by inputted hanzi", e);
			if (transaction != null) {
				transaction.rollback();
			}
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
		logger.info("Trying to insert Hanzidata...");
		Session newSession = hibernateSessionFactory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = newSession.beginTransaction();
			newSession.save(input);
			transaction.commit();
			return input;
		}catch(Exception e){
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error("Error when insert HanziData",e);
			throw e;
		}finally{
			if(newSession.isOpen()){
				newSession.close();
			}
		}
	}
	
}
