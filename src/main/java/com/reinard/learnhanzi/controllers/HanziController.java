package com.reinard.learnhanzi.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;

import com.reinard.learnhanzi.json.SearchHanziJsonRequestObject;
import com.reinard.learnhanzi.service.impl.HanziServiceImpl;

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
	@RequestMapping(value = "/getAllHanzi", method = RequestMethod.POST, produces = {"application/json"})
	public ResponseEntity<String> getAllHanzi(){
		try{
			logger.info("Get all hanzi data...");
			String resultJson = hanziService.selectAll();
			
			//enable "same cross origin", so this controller could response data to ajax
			HttpHeaders headers = new HttpHeaders();
			headers.add("Access-Control-Allow-Origin", "*");
			
			//response header: UTF-8 encoding, to tell the browser display it correctly
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
	 * This is a controller to to handle http request to search the inputted hanzi from database. <br/><br/>
	 * 
	 * Request json example: {"search_hanzi":"愛"}
	 * Response json example: {"hanzi_data":[{"hanzi":"我", "created_date":"2017-04-04 09:15"}]} or 
	 * {"errors": [{"error_code": "704","error_message": "The requested hanzi is not found."}]}
	 * 
	 * This controller will: <br/>
	 * 1. Get the hanzi from json http request. <br/>
	 * 2. Search from the table \"hanzi_data\" that match the inputted hanzi, then convert to json.
	 * 3. Response the json data to client if the data found, or error json if the data is not found.
	 */
	@RequestMapping(value = "/searchHanzi", method = RequestMethod.POST, consumes = {"application/json"})
	public ResponseEntity<String> searchHanzi(@RequestBody SearchHanziJsonRequestObject input){
		//TODO finish this method
		
		try {
			String resultJson = hanziService.selectBy(input.getSearch_hanzi());
			
			//enable "same cross origin", so this controller could response data to ajax
			HttpHeaders headers = new HttpHeaders();
			headers.add("Access-Control-Allow-Origin", "*");
			
			//Set encoding to UTF-8, to let the browser display it correctly
			headers.add("Content-Type", "application/json;charset=UTF-8");
			
			logger.info("Response result:");
			logger.info(resultJson);
			return new ResponseEntity<String>(resultJson,headers,HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error when searching hanzi data", e);
			return new ResponseEntity<String>("Error when searching hanzi data", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	/**
	 * This is a controller to handle http request to save data hanzi to database.  <br/>
	 * 
	 * Save the hanzi to database.Json http request example: {"hanzi":"他"} <br/><br/>
	 * 
	 * This controller will: <br/>
	 * 1. Get the hanzi data from json. <br/>
	 * 2. Create the timestamp, from current timestamp. <br/>
	 * 3. Insert the data to database. <br/>
	 * 4. If the data cannot inserted, response to server with error json. <br/>
	 */
	@RequestMapping(value = "/saveHanzi", method = RequestMethod.POST, consumes = {"application/json"})
	public ResponseEntity<String> savehanzi(){
		//TODO finish this method
		return null;
	}
	
}
