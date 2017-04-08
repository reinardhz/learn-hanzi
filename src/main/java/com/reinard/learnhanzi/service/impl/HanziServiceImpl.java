package com.reinard.learnhanzi.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reinard.learnhanzi.dao.impl.HanziDaoImpl;
import com.reinard.learnhanzi.json.HanziDataJsonResponseObject;
import com.reinard.learnhanzi.json.Hanzi_data;
import com.reinard.learnhanzi.models.HanziData;
import com.reinard.learnhanzi.models.UserAndHanzi;
import com.reinard.learnhanzi.models.UserData;

@Service("hanziService")
public class HanziServiceImpl {
	
	private static final Logger logger = Logger.getLogger(HanziServiceImpl.class);
	
	@Autowired
	private HanziDaoImpl hanziDaoImpl;
	
	
	/**
	 * Select all hanzi from "hanzi_data" table. <br/><br/>
	 * Example of returned json: <br/>
	 * {"hanzi_data":[{"hanzi":"我","created_date":"1491448282654"},{"hanzi":"你","created_date":"1491451859750"}]} <br/>
	 * 
	 * @return String resultJson - All hanzi_data in json format, <i>null</i> if there is no data in database.
	 * @throws Exception - If errors occurs when select all data from "hanzi_data" table.
	 */
	public String selectAll() throws Exception{
		try{
			List<HanziData> allHanzi = hanziDaoImpl.selectAll();
			
			if(allHanzi == null || allHanzi.isEmpty()){
				return null;
			}
			
			//convert result to json array:
			List<Hanzi_data> hanziJsonList = new ArrayList<>();
			for(HanziData hanzi : allHanzi){
				Hanzi_data hanziJson = new Hanzi_data();
				//hanziJson.setHanzi_id(String.valueOf(hanzi.getHanzi_id()));
				hanziJson.setHanzi(hanzi.getHanzi());
				hanziJson.setCreated_date(String.valueOf(hanzi.getCreated_date()));
				hanziJsonList.add(hanziJson);
				
			}
			
			/* //insert data to 2000 (delete this after testing)
			for(int i=1; i<3000; ++i){
				Hanzi_data hanziJson = new Hanzi_data();
				hanziJson.setHanzi("愛");
				hanziJson.setCreated_date(new Timestamp(System.currentTimeMillis()).toLocaleString());
				hanziJsonList.add(hanziJson);
			}*/
			
			Hanzi_data[] resultArray = hanziJsonList.<Hanzi_data>toArray(new Hanzi_data[0]);
			
			HanziDataJsonResponseObject resultJsonObject = new HanziDataJsonResponseObject();
			resultJsonObject.setHanzi_data(resultArray);
			
			//Convert to json
			ObjectMapper mapper = new ObjectMapper();
			String resultJson = mapper.writeValueAsString(resultJsonObject);
			return resultJson;
			
		}catch(Exception e){
			logger.error("Error when trying to select all from \"hanzi_data\" table", e);
			throw e;
		}
	}
	
	/**
	 * Search unique hanzi in "hanzi_data" table. <br/><br/>
	 *  Example of returned json: <br/>
	 * {"hanzi_data":[{"hanzi":"我","created_date":"1491448282654"}]} <br/>
	 * 
	 * @param hanzi - String hanzi to search.
	 * @return String resultJson - A unique hanzi_data in json format, or <i>null</i> if the hanzi is not found.
	 * @throws Exception - If errors occurs when search data from "hanzi_data" table.
	 */
	public String selectBy(String hanzi) throws Exception{
		
		try {
			HanziData hanziData = hanziDaoImpl.selectBy(hanzi);
			
			if(hanziData == null){
				return null;
			}
			
			//prepare the child in json object:
			Hanzi_data hanziJson = new Hanzi_data();
			hanziJson.setHanzi(hanziData.getHanzi());
			hanziJson.setCreated_date(String.valueOf(hanziData.getCreated_date()));
			
			//convert child to array
			List<Hanzi_data> hanziJsonList = new ArrayList<>();
			hanziJsonList.add(hanziJson);
			Hanzi_data[] resultArray = hanziJsonList.<Hanzi_data>toArray(new Hanzi_data[0]);
			
			//prepare the json object
			HanziDataJsonResponseObject resultJsonObject = new HanziDataJsonResponseObject();
			resultJsonObject.setHanzi_data(resultArray);
			
			//Convert to json text
			ObjectMapper mapper = new ObjectMapper();
			String resultJson = mapper.writeValueAsString(resultJsonObject);
			
			return resultJson;
		} catch (Exception e) {
			logger.error("Exception when trying to select \"hanzi_data\" by inputted hanzi",e);
			e.printStackTrace(System.out);
			throw e;
		}
	}
	
	
	
	/**
	 * A method to insert inputted hanzi to database.
	 * 
	 * If the data successfully inserted to database , return the successfull inserted hanzi and its created date in json format.
	 * Response json string example: {"hanzi_data":[{"hanzi":"會", "created_date":"1491448282654"}]}. <br/>
	 * 
	 * <br/>
	 * 
	 * @param hanzi - String hanzi to insert.
	 * @return String result - The inserted hanzi data in json format, or String: "Error: Cannot Insert. Data Already Exist.", if trying to insert the already inserted data.
	 * @throws Exception - If errors occurs when inserting data to database
	 * 
	 */
	public String insertHanzi(String input) throws Exception{
		try{
			logger.info("Inserting hanzi to database...");
			
			logger.debug("preparing the data...");
			HanziData inputHanziData = new HanziData();
			inputHanziData.setHanzi(input);
			inputHanziData.setCreated_date(System.currentTimeMillis());
			
			logger.debug("preparing the child...");
			Set<UserAndHanzi> childs = new HashSet<UserAndHanzi>();
			UserAndHanzi child = new UserAndHanzi();
			UserData userData = new UserData();
			//manual input
			userData.setUser_id(1L);
			child.setUserData(userData);
			child.setHanziData(inputHanziData);
			childs.add(child);
			
			//add the child
			logger.debug("adding the child to the data...");
			inputHanziData.setUserAndHanzi(childs);
			
			logger.debug("call dao, to insert data to database");
			HanziData insertedHanziData = hanziDaoImpl.insert(inputHanziData);
			logger.info("Insert hanzi to database succeed");
			
			logger.info("Converting the inserted data to json object...");
			
			logger.debug("Preparing the json object...");
			HanziDataJsonResponseObject resultJsonObject = new HanziDataJsonResponseObject();
			
			logger.debug("Preparing the child json object...");
			Hanzi_data resultChildJsonObject = new Hanzi_data();
			resultChildJsonObject.setHanzi(insertedHanziData.getHanzi());
			resultChildJsonObject.setCreated_date(String.valueOf(insertedHanziData.getCreated_date()));
			
			logger.debug("Converting the child json object into array...");
			List<Hanzi_data> var = new ArrayList<>();
			var.add(resultChildJsonObject);
			Hanzi_data[] resultChildJsonArray = var.<Hanzi_data>toArray(new Hanzi_data[0]);
			
			logger.debug("Adding the array into json object...");
			resultJsonObject.setHanzi_data(resultChildJsonArray);
			
			logger.debug("Converting json object into json string...");
			ObjectMapper mapper = new ObjectMapper();
			String result = mapper.writeValueAsString(resultJsonObject);
			
			return result;
			
		}catch(PersistenceException pe){
			logger.error("Cannot Insert. Data Already Exist");
			return "Error: Cannot Insert. Data Already Exist.";
		}catch(Exception e){
			logger.error("Unexpected error occurred when inserting hanzi to database.");
			throw e;
		}
		
	}
	
}
