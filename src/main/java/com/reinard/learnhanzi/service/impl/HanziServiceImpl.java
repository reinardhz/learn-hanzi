package com.reinard.learnhanzi.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reinard.learnhanzi.dao.impl.HanziDaoImpl;
import com.reinard.learnhanzi.json.HanziDataJsonResponseObject;
import com.reinard.learnhanzi.json.Hanzi_data;
import com.reinard.learnhanzi.models.HanziData;

@Service("hanziService")
public class HanziServiceImpl {
//TODO finish this service
//TODO test this service
	
	private static final Logger logger = Logger.getLogger(HanziServiceImpl.class);
	
	@Autowired
	private HanziDaoImpl hanziDaoImpl;
	
	/**
	 * Search unique hanzi in "hanzi_data" table.
	 * 
	 * @param hanzi - String hanzi to search, return null if the hanzi is not found.
	 * @return String resultUniqueJson - A unique hanzi_data in json format, or \"null\" if the hanzi is not found.
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
			//get the time in GMT+0 time (GMT Time) from database.
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
	 * Select all hanzi from "hanzi_data" table. <br/><br/>
	 * Example of returned json: <br/>
	 * {"hanzi_data":[{"hanzi":"我","created_date":"1491448282654"},{"hanzi":"你","created_date":"1491451859750"}]} <br/>
	 * 
	 * @return String resultJson - All hanzi_data in json format, null if not found.
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
	
	
	//TODO insert method()
	
}
