package com.reinard.learnhanzi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reinard.learnhanzi.json.HanziDataJson;
import com.reinard.learnhanzi.json.Hanzi_data;
import com.reinard.learnhanzi.json.Hanzi_data;
import com.reinard.learnhanzi.service.impl.HanziServiceImpl;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Controller to handle http request that related with "hanzi_data table
 * 
 * @author reinard.santosa
 *
 */
@Controller
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@RequestMapping(value = "/hanzi")
public class HanziController{
	
	private final static Logger logger = Logger.getLogger(HanziController.class);
	
	@Autowired
	private HanziServiceImpl hanziService;
	
	
	
	/**
	 * Response http request with all hanzi data (using json format). <br/><br/>
	 * 
	 * Http response json example: <br/>
	 * 
	 * {"hanzi_data":[{"hanzi":"我", "created_date":"2017-04-04 09:15"}]}
	 * 
	 */
	@RequestMapping(value = "/getAllHanzi", method = RequestMethod.POST)
	public ResponseEntity<String> getAllHanzi(){
		try{
			logger.info("Get all hanzi data...");
			Hanzi_data[] allHanzi = hanziService.selectAll();
			
			HanziDataJson result = new HanziDataJson();
			result.setHanzi_data(allHanzi);
			
			//Convert to json
			ObjectMapper mapper = new ObjectMapper();
			String resultJson = mapper.writeValueAsString(result);
			
			//enable "same cross origin", so this controller could response data to ajax
			HttpHeaders headers = new HttpHeaders();
			headers.add("Access-Control-Allow-Origin", "*");
			
			//Set encoding to UTF-8, to let the browser display it correctly
			headers.add("Content-Type", "application/json;charset=UTF-8");
			
			
			logger.info("Response result:");
			logger.info(resultJson);
			return new ResponseEntity<String>(resultJson,headers,HttpStatus.OK);
		}catch(Exception e){
			logger.error("Error when getting all hanzi data", e);
			return new ResponseEntity<String>("Error when getting all hanzi data", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * This is a controller to handle http request to save data hanzi to database.  <br/>
	 * 
	 * Save the hanzi to database.Json http request example: {"hanzi":"他"} <br/><br/>
	 * 
	 * This controller will: <br/>
	 * 1. Get the hanzi data from json. <br/>
	 * 2. Create the timestamp, from current timestamp
	 * 3. Insert the data to database.
	 * 4. If the data cannot inserted, response to server with error json.
	 */
	//TODO enable "same cross origin", to let this controller accessed by ajax.
	@RequestMapping(value = "/saveHanzi", method = RequestMethod.POST)
	public ResponseEntity<String> savehanzi(){
		//TODO finish this method
		return null;
	}
	
}
