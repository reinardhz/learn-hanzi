package com.reinard.learnhanzi.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import java.io.BufferedReader;

import com.reinard.learnhanzi.helper.utils.StringUtil;
import com.reinard.learnhanzi.service.impl.HanziServiceImpl;

/**
 * A Controller that handles http request coming from "Hanzi" page.
 * 
 * @author reinard.santosa
 *
 */
@Controller
//To make this controller is not singleton, to support multithreading.
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@RequestMapping(value = "/hanzi")
public class HanziController{
	
	private final static Logger logger = Logger.getLogger(HanziController.class);
	
	@Autowired
	private HanziServiceImpl hanziServiceImpl;
	
	
	
	/**
	 * A method to handle http request to get all hanzi data. <br/><br/>
	 * 
	 * Http Response Example: <br/>
	 * <b>Response Header:</b> <br/>
	 * Access-Control-Allow-Origin: * <br/>
	 * Content-Type: text/plain;charset=UTF-8<br/>
	 * <b>Response Body:</b> <br/>
	 * {"hanzi_data":[{"hanzi":"我", "created_date":"1491448282654"},{"hanzi":"你", "created_date":"1491449282654"}]}<br/>
	 * "not found".
	 * 
	 */
	@RequestMapping(value = "/getAllHanzi", method = RequestMethod.GET, produces = {"text/plain"})
	public ResponseEntity<String> getAllHanzi(){
		try{
			logger.info("Get all hanzi data...");
			String resultJson = hanziServiceImpl.selectAll();
			
			//enable "same cross origin", so this controller could response data to ajax
			HttpHeaders headers = new HttpHeaders();
			headers.add("Access-Control-Allow-Origin", "*");
			
			//response header: UTF-8 encoding, to tell the browser display it correctly
			headers.add("Content-Type", "text/plain;charset=UTF-8");
			
			if(resultJson == null){
				logger.info("Response result:");
				logger.info("Not found");
				return new ResponseEntity<String>("not found.",headers,HttpStatus.OK);
			}
			
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
	 * Http Request Example: <br/>
	 * <b>Request Header:</b> <br/>
	 * Content-Type: text/plain;charset=UTF-8 <br/>
	 * <b>Request Body:</b> <br/>
	 * 我 <br/><br/>
	 * 
	 * Http Response Example: <br/>
	 * <b>Response Header:</b> <br/>
	 * Access-Control-Allow-Origin: * <br/>
	 * Content-Type: text/plain;charset=UTF-8<br/>
	 * <b>Response Body:</b> <br/>
	 * {"hanzi_data":[{"hanzi":"我", "created_date":"1491448282654"}]} <br/>
	 * not found
	 * <br/><br/>
	 * 
	 * If the http request is not specified the content encoding (charset=UTF-8) in the http header request, then this controller will read the byte with wrong encoding, and finally make the system behavior not as expected.
	 * 
	 * This controller will: <br/>
	 * 1. Get the hanzi http request. <br/>
	 * 2. Search from the table \"hanzi_data\" that match the inputted hanzi, then convert to json.
	 * 3. Response the json data to client if the data found, or string "not found", if the data is not found.
	 * 4. If error happened, response to server with error String: "Error when searching hanzi data".
	 */
	@RequestMapping(value = "/searchHanzi", method = RequestMethod.POST, consumes = {"text/plain"}, produces = {"text/plain"})
	public ResponseEntity<String> searchHanzi(HttpServletRequest httpServletRequest){
		
		try {
			logger.info("processing request to searchHanzi. Searching hanzi now...");
			
			logger.debug("read the request body (because jetty server could not bind the request that using @RequestBody) ...");
			BufferedReader bufferedReader = httpServletRequest.getReader();
			
			int resultInt = 0;
			char resultChar = (char)0;
			
			StringBuilder resultString = new StringBuilder();
			
			while ((resultInt = bufferedReader.read()) != -1) {
				// read the characters:
				// the resultInt, is a decimal number that point to the Unicode Character, using UTF-16 Big Endian. 
				// Example: int = 24859 point to the chinese traditional character for love.
				
				//Cannot cast to char if the int is not in the char data type range.
				if((resultInt<0) || resultInt>65535){
					return new ResponseEntity<String>("The request cannot be read.", HttpStatus.INTERNAL_SERVER_ERROR);
				}
				
				//Add only the character needed (do not add system character, space character, del character):
				if( (resultInt>32) && (resultInt!=127) ){
					resultChar = (char)resultInt;
					resultString.append(resultChar);
				}
				
			}
			
			logger.debug("The request body: "+ resultString.toString());
			String hanzi = resultString.toString();
			
			String resultJson = hanziServiceImpl.selectBy(hanzi);
			
			if(resultJson == null){
				resultJson="not found";
			}
			
			//enable "same cross origin", so this controller could response data to ajax
			HttpHeaders headers = new HttpHeaders();
			headers.add("Access-Control-Allow-Origin", "*");
			
			//Set encoding to UTF-8, to let the browser display it correctly
			headers.add("Content-Type", "text/plain;charset=UTF-8");
			
			logger.info("Sending result: "+ resultJson);
			return new ResponseEntity<String>(resultJson,headers,HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Error when searching hanzi data", e);
			return new ResponseEntity<String>("Error when searching hanzi data", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	/**
	 * A method to handle http request to save data hanzi to database.  <br/><br/>
	 *  Http Request Example: <br/>
	 * <b>Request Header:</b> <br/>
	 * Content-Type: text/plain;charset=UTF-8 <br/>
	 * <b>Request Body:</b> <br/>
	 * 會 <br/><br/>
	 * 
	 * Http Response Example: <br/>
	 * <b>Response Header:</b> <br/>
	 * Access-Control-Allow-Origin: * <br/>
	 * Content-Type: text/plain;charset=UTF-8<br/>
	 * <b>Response Body:</b> <br/>
	 * {"hanzi_data":[{"hanzi":"會", "created_date":"1491448282654"}]}<br/>
	 * The request body cannot be read. <br/>
	 * The request body cannot be empty. <br/>
	 * Error: Cannot Insert. Data Already Exist. <br/>
	 * Error when inserting hanzi data. <br/>
	 * <br/><br/>
	 * 
	 * If the http request is not specified the content encoding (charset=UTF-8) in the http header request, then this controller will read the byte with wrong encoding, and finally make the system behavior not as expected.<br/><br/>
	 * 
	 * This controller will: <br/>
	 * 1. Get the hanzi data from json. <br/>
	 * 2. Create the date, from current date. <br/>
	 * 3. Insert the data to database. <br/>
	 * 4. If the data cannot be read, response to client with error String: "The request body cannot be read."<br/>
	 * 5. If the request body is a String empty, response to client with error String: "The request body cannot be empty."<br/>
	 * 4. If the data cannot be inserted, response to client with error String: "Error: Cannot Insert. Data Already Exist."<br/>
	 * 5. If error happened, response to client with error String: "Error when inserting hanzi data."<br/>
	 */
	@RequestMapping(value = "/insertHanzi", method = RequestMethod.POST, consumes = {"text/plain"}, produces = {"text/plain"})
	public ResponseEntity<String> insertHanzi(HttpServletRequest httpServletRequest){
		
		try{
			logger.info("processing request to insertHanzi. Inserting hanzi now...");
			logger.debug("reading the request body (because jetty server could not bind the request that using @RequestBody) ...");
			BufferedReader bufferedReader = httpServletRequest.getReader();
			
			int resultInt = 0;
			char resultChar = (char)0;
			
			StringBuilder resultString = new StringBuilder();
			
			while ((resultInt = bufferedReader.read()) != -1) {
				logger.debug("read the characters");
				// the resultInt, is a decimal number that point to the Unicode Character, using UTF-16 Big Endian. 
				// Example: int = 24859 point to the chinese traditional character for love.
				
				//Cannot cast to char if the int is not in the char data type range.
				if((resultInt<0) || resultInt>65535){
					return new ResponseEntity<String>("The request body cannot be read.", HttpStatus.INTERNAL_SERVER_ERROR);
				}
				
				//Add only the character needed (do not add system character, space character, del character):
				if( (resultInt>32) && (resultInt!=127) ){
					resultChar = (char)resultInt;
					resultString.append(resultChar);
				}
			}
			
			
			logger.debug("The request body: "+ resultString.toString());
			String input = resultString.toString();
			
			if(StringUtil.isEmpty(input)){
				return new ResponseEntity<String>("The request body cannot be empty.", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			String resultJson = hanziServiceImpl.insertHanzi(input);
			
			//enable "same cross origin", so this controller could response data to ajax
			HttpHeaders headers = new HttpHeaders();
			headers.add("Access-Control-Allow-Origin", "*");
			
			//Set encoding to UTF-8, to let the browser display it correctly
			headers.add("Content-Type", "text/plain;charset=UTF-8");
			
			logger.info("Sending result: "+ resultJson);
			return new ResponseEntity<String>(resultJson,headers,HttpStatus.OK);
			
		}catch(Exception e){
			logger.error("Unexpected error when inserting hanzi data.", e);
			return new ResponseEntity<String>("Error when inserting hanzi data.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
}
