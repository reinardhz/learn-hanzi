package com.reinard.learnhanzi.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
	 *  A method to insert data to "hanzi_data" table.
	 *  
	 * @param input - Object HanziData to be inserted.
	 * @return HanziData - the successfull inserted HanziData.
	 */
	/*public HanziData insert(Hanzidata input) throws Exception{
		Session newSession = hibernateSessionFactory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = newSession.beginTransaction();
			//...
			transaction.commit();
			return result;
		}catch(Exception e){
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace(System.out);
			throw e;
		}finally{
			if(newSession.isOpen()){
				newSession.close();
			}
		}
	}*/
	
}
