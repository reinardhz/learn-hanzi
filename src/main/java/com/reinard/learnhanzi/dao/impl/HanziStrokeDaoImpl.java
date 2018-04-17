package com.reinard.learnhanzi.dao.impl;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.reinard.learnhanzi.helper.utils.StringUtil;
import com.reinard.learnhanzi.models.BookAndStroke;
import com.reinard.learnhanzi.models.BookData;
import com.reinard.learnhanzi.models.HanziStrokeData;

/**
 * A class that provides many database operations on "hanzi_stroke_data" table. This class is already tested (OK).
 * 
 * @author reinard.santosa
 *
 */
@Repository(value="hanziStrokeDaoImpl")
@Scope(value = "singleton")
public class HanziStrokeDaoImpl {

	private static final Logger logger = Logger.getLogger(HanziStrokeDaoImpl.class);
	
	@Autowired
	private SessionFactory hibernateSessionFactory;
	
	/**
	 * A method to insert data to "hanzi_stroke_data" table. (tested OK).
	 * 
	 * @param input - Object "HanziStrokeData" to be inserted.
	 * @return HanziStrokeData - the successfully inserted "HanziStrokeData".
	 * @throws Exception - If error happen when trying to insert data to database.
	 */
	public HanziStrokeData insert(HanziStrokeData input) throws Exception{
		logger.info("Inserting data into \"hanzi_stroke_data\" table...");
		
		Session newSession = hibernateSessionFactory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = newSession.beginTransaction();
			newSession.save(input);
			transaction.commit();
			logger.info("Insert to \"hanzi_stroke_data\" table succeed.");
			logger.debug(input);
			return input;
		}catch(Exception e){
			if (transaction != null){ 
				transaction.rollback();
				logger.error("Unexpected error occurred when inserting to \"hanzi_stroke_data\" table, rollback succeed.",e);
			}else{
				logger.error("Unexpected error occurred when inserting to \"hanzi_stroke_data\" table.",e);
			}
			throw e;
		}finally{
			if(newSession.isOpen()) newSession.close();
		}
	}
	
	
	/**
	 * A method to select all data from "hanzi_stroke_data" table. (tested OK).
	 * 
	 * @return List&lt;HanziStrokeData&gt; All record from "book_data" table. Return <i>null</i> if no data found.
	 * @throws Exception
	 */
	public List<HanziStrokeData> selectAll() throws Exception{
		logger.info("Selecting all data from \"hanzi_stroke_data\" table...");
		
		Session newSession = hibernateSessionFactory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = newSession.beginTransaction();
			List<HanziStrokeData> result = newSession.createCriteria(HanziStrokeData.class).list();
			transaction.commit();
			logger.info("Selecting all data from \"hanzi_stroke_data\" table.");
			logger.debug(result.toString());
			
			if(result==null || result.isEmpty()){
				logger.info("Selecting all data from \"hanzi_stroke_data\" produced no data.");
				logger.info("Selecting all data from \"hanzi_stroke_data\" table succeed.");
				return null;
			}
			
			logger.info("Selecting all data from \"hanzi_stroke_data\" table succeed.");
			return result;
		}catch(Exception e){
			if (transaction != null){
				transaction.rollback();
				logger.error("Unexpected error occurred when trying to select all from \"hanzi_stroke_data\" table, rollback succeed.", e);
			}else{
				logger.error("Unexpected error occurred when trying to select all from \"hanzi_stroke_data\" table.", e);
			}
			
			throw e;
		}finally{
			if(newSession.isOpen()) newSession.close();
		}
	}
	
	
	/**
	 * A method to select all data from "hanzi_stroke_data" table that contains the specified "hanzi_stroke_id". (tested OK).
	 * 
	 * @param inputHanziStrokeId
	 * @return HanziStrokeData - One record from "hanzi_stroke_data" table. Return <i>null</i> if no data found.
	 */
	public HanziStrokeData selectBy(long inputHanziStrokeId) throws Exception{
		logger.info("Selecting data from \"hanzi_stroke_data\" table by specified \"hanzi_stroke_id\"...");
		
		Session newSession = hibernateSessionFactory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = newSession.beginTransaction();
			Criteria selectByHanziStrokeId = newSession.createCriteria(HanziStrokeData.class);
			
			//define the filter for the data:
			//1. hanzi_stroke_id equals the inputted hanzi_stroke_id (WHERE hanzi_stroke_id = [inputted hanzi_stroke_id])
			//because this Restrictions will be put in "HanziStrokeData" criteria:
			//"hanzi_stroke_id" point to variable "private long hanzi_stroke_id;" in entity "HanziStrokeData", which then point to column "hanzi_stroke_id" in table "hanzi_stroke_data".
			Criterion equalsInputtedHanziStrokeId = Restrictions.eq("hanzi_stroke_id", inputHanziStrokeId);
			selectByHanziStrokeId.add(equalsInputtedHanziStrokeId);
			
			//SELECT * FROM hanzi_stroke_data WHERE hanzi_stroke_id = [inputHanziStrokeId]
			//Ensure that there is only one unique result.
			Object resultObject = selectByHanziStrokeId.uniqueResult();
			if(resultObject == null){
				logger.info("\"hanzi_stroke_data\" not found.");
				logger.info("Selecting data from \"hanzi_stroke_data\" table by specified \"hanzi_stroke_id\" succeed.");
				return null;
			}else{
				HanziStrokeData result = (HanziStrokeData) resultObject;
				logger.info("\"hanzi_stroke_data\" found.");
				logger.debug(result.toString());
				return result;
			}
		}catch(Exception e){
			if (transaction != null){
				transaction.rollback();
				logger.error("Unexpected error occurred when trying to select \"hanzi_stroke_data\" by inputted hanzi_stroke_id, rollback succeed.", e);
			}else{
				logger.error("Unexpected error occurred when trying to select \"hanzi_stroke_data\" by inputted hanzi_stroke_id.", e);
			}
			throw e;
		}finally{
			if(newSession.isOpen()) newSession.close();
		}
		
	}

	
	/**
	 * A method to select data from "hanzi_stroke_data" table that contains the specified "hanzi_stroke". (tested OK)
	 * 
	 * @param inputHanziStroke
	 * @return HanziStrokeData - One record from "hanzi_stroke_data" table. Return <i>null</i> if no data found.
	 * @throws Exception If error happened when trying to search from database.
	 */
	public List<HanziStrokeData> selectBy(String inputHanziStroke) throws Exception{
		logger.info("Selecting data from \"hanzi_stroke_data\" table by specified \"hanzi_stroke\"...");
		
		if(inputHanziStroke==null || StringUtil.isEmpty(inputHanziStroke)) {
			return null;
		}
		
		Session newSession = hibernateSessionFactory.openSession();
		Transaction transaction = null;
		
		try {
			transaction = newSession.beginTransaction();
			Criteria selectByHanziStroke = newSession.createCriteria(HanziStrokeData.class);
			
			//define the filter for the data:
			//1. hanzi_stroke equals the inputted hanzi_stroke (WHERE hanzi_stroke = [inputted hanzi_stroke])
			//because this Restrictions will be put in "HanziStrokeData" criteria:
			//hanzi_stroke point to variable "private long hanzi_stroke;" in entity "HanziStrokeData", which then point to column "hanzi_stroke" in table "hanzi_stroke_data".
			Criterion equalsInputtedHanziStroke = Restrictions.eq("hanzi_stroke", inputHanziStroke);
			
			selectByHanziStroke.add(equalsInputtedHanziStroke);
			
			//SELECT * FROM hanzi_stroke_data WHERE hanzi_stroke = [inputHanziStroke]
			List<HanziStrokeData> result = selectByHanziStroke.list();
			if( (result!=null) && (result.isEmpty()==false) ) {
				for(HanziStrokeData current : result) {
					//force load the lazy fetch "private Set<BookAndStroke> bookAndStroke;"
					Hibernate.initialize(current.getBookAndStroke());
				}
			}
			transaction.commit();
			if(result == null || result.isEmpty()){
				logger.info("\"hanzi_stroke_data\" not found.");
				logger.info("Selecting data from \"hanzi_stroke_data\" table by specified \"hanzi_stroke\" succeed.");
				return null;
			}
			logger.info("\"hanzi_stroke_data\" found.");
			logger.debug(result.toString());
			return result;
		}catch(Exception e) {
			if (transaction != null){
				transaction.rollback();
				logger.error("Unexpected error occurred when trying to select \"hanzi_stroke_data\" by inputted hanzi_stroke, rollback succeed.", e);
			}else{
				logger.error("Unexpected error occurred when trying to select \"hanzi_stroke_data\" by inputted hanzi_stroke.", e);
			}
			throw e;
		}finally {
			if(newSession.isOpen()) newSession.close();
		}
	}
	
}
