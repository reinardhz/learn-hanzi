package com.reinard.learnhanzi.controllers;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.stereotype.Controller;

import com.reinard.learnhanzi.helper.utils.StringUtil;
import com.reinard.learnhanzi.service.impl.BookServiceImpl;


/**
 * A Controller that handles all http request coming from "Book" page.
 * 
 * @author reinard.santosa
 *
 */
@Controller
//To make this controller is not singleton, to support multithreading.
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@RequestMapping(value = "/book")
public class BookController {
	
	private final static Logger logger = Logger.getLogger(BookController.class);
	
	@Autowired
	private BookServiceImpl bookServiceImpl;
	
	/**
	 * This method is tested OK. <br/>
	 * 
	 * A method to handle http request to insert new book name into table "book_data". <br/><br/>
	 *  
	 * Http Request Example: <br/>
	 * <b>Request Header:</b> <br/>
	 * Content-Type: text/plain;charset=UTF-8 <br/>
	 * <b>Request Body:</b> <br/>
	 * 第一書  <br/><br/>
	 * 
	 * Http Response Example: <br/>
	 * <b>Response Header:</b> <br/>
	 * Access-Control-Allow-Origin: * <br/>
	 * Content-Type: text/plain;charset=UTF-8 <br/>
	 * <b>Response Body:</b> <br/>
	 * Book 第一書  inserted. <br/>
	 * <i>or</i> <br/>
	 * The request body cannot be read. <br/>
	 * <i>or</i> <br/>
	 * The request body cannot be empty. <br/>
	 * <i>or</i> <br/>
	 * Error: Cannot insert. Data already exist. <br/>
	 * <i>or</i> <br/>
	 * Error when inserting book name. <br/>
	 * <br/><br/>
	 * 
	 * If the http request is not specified the content encoding (charset=UTF-8) in the http header request, then this controller will read the byte with wrong encoding, and finally make the system behavior not as expected.<br/><br/>
	 * 
	 *  This controller will: <br/>
	 * 1. Read the data from http request. <br/>
	 * 2. If the data cannot be read, response to client with error String: "The request body cannot be read." <br/>
	 * 3. If the request body is a String empty, response to client with error String: "The request body cannot be empty." <br/>
	 * 4. Get the "book name" from http request. <br/>
	 * 5. Call "BookServiceImpl" to insert the book name into database. <br/>
	 * 6. Response the json String to client if the data is successfully inserted. Example: "Book 第一書  inserted." <br/>
	 * 7. If the data cannot be inserted because it already existed, response to client with error String: "Error: Cannot insert. Data already exist." <br/>
	 * 8. If error happened, response to client with error String: "Error when inserting book name." <br/>
	 * 
	 */
	@RequestMapping(value = "/insertBookName", method = RequestMethod.POST, consumes = {"text/plain"}, produces = {"text/plain"})
	public ResponseEntity<String> insertBookName(HttpServletRequest httpServletRequest){
		
		logger.info("Processing request to \"insertBookName\". Inserting Book Name now...");
		
		//enable "same cross origin", so this controller could response data to ajax
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Access-Control-Allow-Origin", "*");
						
		//response header: UTF-8 encoding, to tell the browser display it correctly
		responseHeaders.add("Content-Type", "text/plain;charset=UTF-8");
		
		try{
			
			logger.debug("Read the http request body raw byte, because jetty server could not bind the request using Spring @RequestBody annotation.");
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
					logger.info("Sending response:");
					logger.info("The request body cannot be read.");
					return new ResponseEntity<String>("The request body cannot be read.", responseHeaders, HttpStatus.OK);
				}
				
				//Add only the unicode CJK (Chinese Japanese Korean) characters:
				//the unicode CJK range is from U+4E00 (19968) to U+9FFF (40959)
				//source: http://www.fileformat.info/info/unicode/block/cjk_unified_ideographs/index.htm
				if( (resultInt>=19968) && (resultInt<=40959) ){
					resultChar = (char)resultInt;
					resultString.append(resultChar);
				}
			}
			
			
			logger.debug("The request body: "+ resultString.toString());
			String input = resultString.toString();
			
			if(StringUtil.isEmpty(input)){
				logger.info("Sending response: \"The request body cannot be empty.\"");
				
				return new ResponseEntity<String>("The request body cannot be empty.", responseHeaders, HttpStatus.OK);
			}
			
			String resultJson = bookServiceImpl.addNewBook(input);
			
			logger.info("Sending response:");
			logger.info(resultJson);
			return new ResponseEntity<String>(resultJson,responseHeaders,HttpStatus.OK);
			
		}catch(Exception e){
			logger.error("Unexpected error when inserting Book Name.", e);
			logger.info("Sending response: \"Error when inserting book name.\"");
			return new ResponseEntity<String>("Error when inserting book name.", responseHeaders, HttpStatus.OK);
		}
		
	}
	
	/**
	 * This method is tested OK. <br/>
	 * 
	 * A method to handle http request to get all book name. <br/><br/>
	 * 
	 * Http Response Example: <br/>
	 * <b>Response Header:</b> <br/>
	 * Access-Control-Allow-Origin: * <br/>
	 * Content-Type: text/plain;charset=UTF-8<br/>
	 * <b>Response Body:</b> <br/> 
	 * 第一書,第二書,第三書,第四書,第五書 <br/>
	 * 
	 * <i>or</i> <br/>
	 * 
	 * Not found. <br/>
	 * 
	 * <i>or</i> <br/>
	 * 
	 * Error when getting all book name. <br/><br/>
	 * 
	 * Important note: Make sure that the response body: <br/>
	 * * Do not contains the space character. <br/>
	 * * Each book name is separated by comma character. <br/>
	 * 
	 * This controller will: <br/>
	 * 1. Get all "book_name" from database. <br/>
	 * 2. Response the data to client if the data found, or string "Not found.", if the data is not found. <br/>
	 * 3. If error happened, response to client with error String: "Error when getting all book name."<br/>
	 */
	@RequestMapping(value = "/getAllBookName", method = RequestMethod.GET, produces = {"text/plain"})
	public ResponseEntity<String> getAllBookName(){
		
		//the service needed in this method is tested ok.
		logger.info("Processing request to getAllBookName. Get all Book Name now...");
		
		//enable "same cross origin", so this controller could response data to ajax
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Access-Control-Allow-Origin", "*");
				
		//response header: UTF-8 encoding, to tell the browser display it correctly
		responseHeaders.add("Content-Type", "text/plain;charset=UTF-8");
		
		try{
			String result = bookServiceImpl.getAllBookName();
			if(result==null){
				logger.info("Sending response: \"Not found.\"");
				return new ResponseEntity<String>("Not found.",responseHeaders,HttpStatus.OK);
			}
			
			logger.info("Sending response:");
			logger.info(result);
			return new ResponseEntity<String>(result,responseHeaders,HttpStatus.OK);
		}catch(Exception e){
			logger.error("Error when getting all book name.", e);
			logger.info("Sending response: \"Error when getting all book name.\"");
			return new ResponseEntity<String>("Error when getting all book name.", responseHeaders, HttpStatus.OK);
		}
		
	}
	
	
	/**
	 * This method is not yet tested. <br/>
	 * 
	 * A method to handle http request to search all hanzi stroke in the specified book name. <br/>
	 * 
	 * Http Request Example: <br/>
	 * <b>Request Header:</b> <br/>
	 * Content-Type: text/plain;charset=UTF-8 <br/>
	 * <b>Request Body:</b> <br/>
	 * 第一書  <br/><br/>
	 * 
	 * Http Response Example: <br/>
	 * <b>Response Header:</b> <br/>
	 * Access-Control-Allow-Origin: * <br/>
	 * Content-Type: text/plain;charset=UTF-8<br/>
	 * <b>Response Body:</b> <br/>
	 * {"book_name":"第一書", "hanzi_stroke_data":[{"hanzi_stroke":"營業員", "page_number":"一", "created_date":"1491448282654"},{"hanzi_stroke":"電子郵件", "page_number":"二", "created_date":"1492249841461"},{"hanzi_stroke":"發音", "page_number":"三", "created_date":"1492339814022"}]} <br/>
	 * 
	 * <i>or</i> <br/>
	 * 
	 * The request body cannot be read. <br/>
	 * 
	 * <i>or</i> <br/>
	 * 
	 * The request body cannot be empty. <br/>
	 * 
	 * <i>or</i> <br/>
	 * 
	 * Not found. <br/>
	 * 
	 * <i>or</i> <br/>
	 * 
	 * Error when searching all hanzi stroke in book name. <br/><br/>
	 * 
	 * Note: If the http request is not specified the content encoding (charset=UTF-8) in the http header request, then this controller will read the byte with wrong encoding, and finally make the system behavior not as expected. <br/><br/>
	 * 
	 * This controller will: <br/>
	 * 1. Read the data from http request. <br/>
	 * 2. If the data cannot be read, response to client with error String: "The request body cannot be read." <br/>
	 * 3. If the request body is a String empty, response to client with error String: "The request body cannot be empty." <br/>
	 * 4. Get the "book_name" from http request. <br/>
	 * 5. Search all "hanzi_stroke" in book_name. <br/>
	 * 6. Response the json data to client if the data found, or string "Not found.", if the data is not found. <br/>
	 * 7. If error happened, response to server with error String: "Error when searching all hanzi stroke in inputted book name." <br/>
	 * 
	 */
	@RequestMapping(value = "/getAllHanziStrokeInBookName", method = RequestMethod.POST, consumes = {"text/plain"}, produces = {"text/plain"})
	public ResponseEntity<String> getAllHanziStrokeInBookName(HttpServletRequest httpServletRequest){
		
		//TODO fix this method
		//TODO test this method
		
		//the service needed in this method is not yet tested.
		logger.info("Processing request to getAllHanziStrokeInBookName. Searching hanzi stroke now...");
		
		//enable "same cross origin", so this controller could response data to ajax
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Access-Control-Allow-Origin", "*");
						
		//response header: UTF-8 encoding, to tell the browser display it correctly
		responseHeaders.add("Content-Type", "text/plain;charset=UTF-8");
		
		try{
			
			logger.debug("Read the http request body raw byte, because jetty server could not bind the request using Spring @RequestBody annotation.");
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
					logger.info("Sending response:");
					logger.info("The request body cannot be read.");
					return new ResponseEntity<String>("The request body cannot be read.", responseHeaders, HttpStatus.OK);
				}
				
				//Add only the unicode CJK (Chinese Japanese Korean) characters:
				//the unicode CJK range is from U+4E00 (19968) to U+9FFF (40959)
				//source: http://www.fileformat.info/info/unicode/block/cjk_unified_ideographs/index.htm
				if( (resultInt>=19968) && (resultInt<=40959) ){
					resultChar = (char)resultInt;
					resultString.append(resultChar);
				}
				
			}
			
			logger.debug("The request body: "+ resultString.toString());
			String input = resultString.toString();
			
			if(StringUtil.isEmpty(input)){
				logger.info("Sending response: \"The request body cannot be empty.\"");
				return new ResponseEntity<String>("The request body cannot be empty.", responseHeaders, HttpStatus.OK);
			}
			
			String result = bookServiceImpl.getAllHanziStrokeInBook(input);
			if(result == null){
				logger.info("Sending response: \"Not found.\"");
				return new ResponseEntity<String>("Not found.",responseHeaders,HttpStatus.OK);
			}
			
			logger.info("Sending response:");
			logger.info(result);
			return new ResponseEntity<String>(result,responseHeaders,HttpStatus.OK);
		}catch(Exception e){
			logger.error("Error when searching all hanzi stroke in book name.");
			logger.info("Sending response: \"Error when searching all hanzi stroke in book name.\"");
			return new ResponseEntity<String>("Error when searching all hanzi stroke in book name.",responseHeaders,HttpStatus.OK);
		}
		
		
	}
	
	/**
	 * This method is not yet tested. <br/>
	 * 
	 * A method to insert one hanzi stroke in specified book name. <br/><br/>
	 * Http Request Example: <br/>
	 * <b>Request Header:</b> <br/>
	 * Content-Type: text/plain;charset=UTF-8 <br/>
	 * <b>Request Body:</b> <br/>
	 * 第一書:電子郵件;二 <br/><br/>
	 * 
	 * Http Response Example: <br/>
	 * <b>Response Header:</b> <br/>
	 * Access-Control-Allow-Origin: * <br/>
	 * Content-Type: text/plain;charset=UTF-8<br/>
	 * <b>Response Body:</b> <br/>
	 * {"book_name":"第一書", "hanzi_stroke_data":[{"hanzi_stroke":"電子郵件", "page_number":"二", "created_date":"1492249841461"}]} <br/>
	 * <i>or</i> <br/>
	 * The request body cannot be read. <br/>
	 * <i>or</i> <br/>
	 * The request body cannot be empty. <br/>
	 * <i>or</i> <br/>
	 * The request body must contain colon and semicolon character. <br/>
	 * <i>or</i> <br/>
	 * Error when inserting hanzi stroke. <br/>
	 * <br/><br/>
	 * 
	 * If the http request is not specified the content encoding (charset=UTF-8) in the http header request, then this controller will read the byte with wrong encoding, and finally make the system behavior not as expected.<br/><br/>
	 * 
	 * This controller will: <br/>
	 * 1. Read the data from http request. <br/>
	 * 2. If the data cannot be read, response to client with error String: "The request body cannot be read." <br/>
	 * 3. If the request body is a String empty, response to client with error String: "The request body cannot be empty." <br/>
	 * 4. If the request body does not contain ":" and ";" character, response to client with error String: "The request body must contain colon and semicolon character." <br/>
	 * 4. Get the "book_name", "hanzi_stroke" and "page_number" from http request. <br/>
	 * 5. Create the date, from current date. <br/>
	 * 6. Insert the data to database. <br/>
	 * 7. Response the json data to client if the data is successfully inserted. <br/>
	 * 8. If error happened, response to client with error String: "Error when inserting hanzi stroke."<br/>
	 */
	@RequestMapping(value = "/insertHanziStrokeInBook", method = RequestMethod.POST, consumes = {"text/plain"}, produces = {"text/plain"})
	public ResponseEntity<String> insertHanziStrokeInBook(HttpServletRequest httpServletRequest){
		//TODO fix this method
		//TODO test this method
		
		//the service needed in this method is not yet tested.
		
		logger.info("Processing request to insertHanziStrokeInBook...");
		
		//enable "same cross origin", so this controller could response data to ajax
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Access-Control-Allow-Origin", "*");
						
		//response header: UTF-8 encoding, to tell the browser display it correctly
		responseHeaders.add("Content-Type", "text/plain;charset=UTF-8");
		
		try{
			logger.debug("Read the http request body raw byte, because jetty server could not bind the request using Spring @RequestBody annotation.");
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
					logger.info("Sending response: \"The request body cannot be read.\"");
					return new ResponseEntity<String>("The request body cannot be read.", responseHeaders, HttpStatus.OK);
				}
				
				//Add only the unicode CJK (Chinese Japanese Korean) characters and colon character :
				//* the unicode CJK range is from U+4E00 (19968) to U+9FFF (40959)
				//* the unicode colon character is U+003A (58)
				//source: http://www.fileformat.info/info/unicode/block/cjk_unified_ideographs/index.htm
				if( ((resultInt>=19968) && (resultInt<=40959)) || (resultInt==58) ){
					resultChar = (char)resultInt;
					resultString.append(resultChar);
				}
			}
			
			logger.debug("The request body: "+ resultString.toString());
			String input = resultString.toString();
			
			if(StringUtil.isEmpty(input)){
				logger.info("Sending response: \"The request body cannot be empty.\"");
				return new ResponseEntity<String>("The request body cannot be empty.", responseHeaders, HttpStatus.OK);
			}
			
			if(!input.contains(":")){
				logger.info("Sending response: \"The request body must contain colon character.\"");
				return new ResponseEntity<String>("The request body must contain colon character.", responseHeaders, HttpStatus.OK);
			}
			
			String result = bookServiceImpl.insertHanziStrokeInBook(input);
			return new ResponseEntity<String>(result,responseHeaders,HttpStatus.OK);
			
		}catch(Exception e){
			logger.error("Error when inserting hanzi stroke.");
			logger.info("Sending response: \"Error when inserting hanzi stroke.\"");
			return new ResponseEntity<String>("Error when inserting hanzi stroke.", responseHeaders, HttpStatus.OK);
		}
		
	}
	
	/**
	 * This method is not yet tested. <br/>
	 * 
	 * A method to search hanzi stroke in specified book name. <br/><br/>
	 * 
	 * Http Request Example: <br/>
	 * <b>Request Header:</b> <br/>
	 * Content-Type: text/plain;charset=UTF-8 <br/>
	 * <b>Request Body:</b> <br/>
	 * 第二書:消防局 <br/><br/>
	 * 
	 *  Http Response Example: <br/>
	 * <b>Response Header:</b> <br/>
	 * Access-Control-Allow-Origin: * <br/>
	 * Content-Type: text/plain;charset=UTF-8<br/>
	 * <b>Response Body:</b> <br/>
	 * {"book_name":"第二書", "hanzi_stroke_data":[{"hanzi_stroke":"消防局", "page_number":"一", "created_date":"1492318783895"}]} <br/>
	 * 
	 * <i>or</i> <br/>
	 * 
	 * The request body cannot be read. <br/>
	 * 
	 * <i>or</i> <br/>
	 * 
	 * The request body cannot be empty. <br/>
	 * 
	 * <i>or</i> <br/>
	 * 
	 * Not found. <br/>
	 * 
	 * <i>or</i> <br/>
	 * 
	 * Error when searching one hanzi stroke in specified book name. <br/><br/>
	 * 
	 * Note: If the http request is not specified the content encoding (charset=UTF-8) in the http header request, then this controller will read the byte with wrong encoding, and finally make the system behavior not as expected. <br/><br/>
	 * 
	 * This controller will: <br/>
	 * 1. Read the data from http request. <br/>
	 * 2. If the data cannot be read, response to client with error String: "The request body cannot be read." <br/>
	 * 3. If the request body is a String empty, response to client with error String: "The request body cannot be empty."<br/>
	 * 4. Get the "book_name", "hanzi_stroke" from http request. <br/>
	 * 5. Select the data from database. <br/>
	 * 6. Response the json data to client if the data found, or string "Not found.", if the data is not found. <br/>
	 * 7. If error happened, response to client with error String: "Error when searching one hanzi stroke in specified book name."<br/>
	 * 
	 */
	@RequestMapping(value = "/searchHanziStrokeInBook", method = RequestMethod.POST, consumes = {"text/plain"}, produces = {"text/plain"})
	public ResponseEntity<String> searchHanziStrokeInBook(HttpServletRequest httpServletRequest){
		//TODO fix this method
		//TODO test this method
		
		//the service needed in this method is not yet tested.
		
		//test this method http request:
		//case 1: Space only (ok)
		//case 2: Non chinese character (ok)
		//case 3: Colon only (ok)
		//case 4: Chinese character without colon (ok)
		//case 5: Chinese character with colon (expected: provide result) (ok)
		//case 6: book_name is not exist in the database, service must return null (ok)
		//case 7: book_name is exist, but hanzi_stroke is not exist in database, service must return null (ok)
		
		logger.info("Processing request to searchHanziStrokeInBook...");
		
		//enable "same cross origin", so this controller could response data to ajax
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Access-Control-Allow-Origin", "*");
								
		//response header: UTF-8 encoding, to tell the browser display it correctly
		responseHeaders.add("Content-Type", "text/plain;charset=UTF-8");
		
		try{
			logger.debug("Read the http request body raw byte, because jetty server could not bind the request using Spring @RequestBody annotation.");
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
					logger.info("Sending response: \"The request body cannot be read.\"");
					return new ResponseEntity<String>("The request body cannot be read.", responseHeaders, HttpStatus.OK);
				}
				
				//Add only the unicode CJK (Chinese Japanese Korean) characters and colon character :
				//* the unicode CJK range is from U+4E00 (19968) to U+9FFF (40959)
				//* the unicode colon character is U+003A (58)
				//source: http://www.fileformat.info/info/unicode/block/cjk_unified_ideographs/index.htm
				if( ((resultInt>=19968) && (resultInt<=40959)) || (resultInt==58) ){
					resultChar = (char)resultInt;
					resultString.append(resultChar);
				}
			}
			
			logger.debug("The request body: "+ resultString.toString());
			String input = resultString.toString();
			
			if(StringUtil.isEmpty(input)){
				logger.info("Sending response: \"The request body cannot be empty.");
				return new ResponseEntity<String>("The request body cannot be empty.", responseHeaders, HttpStatus.OK);
			}
			
			if(!input.contains(":")){
				logger.info("Sending response: \"The request body must contain colon character.\"");
				return new ResponseEntity<String>("The request body must contain colon character.", responseHeaders, HttpStatus.OK);
			}
			
			String result = bookServiceImpl.searchHanziStrokeInBook(input);
			if(result == null){
				logger.info("Sending response: \"Not found.\"");
				return new ResponseEntity<String>("Not found.",responseHeaders,HttpStatus.OK);
			}
			
			logger.info("Sending response:");
			logger.info(result);
			return new ResponseEntity<String>(result,responseHeaders,HttpStatus.OK);
		}catch(Exception e){
			logger.error("Error when searching hanzi stroke in specified book.");
			logger.info("Sending response: \"Error when searching one hanzi stroke in specified book name.\"");
			return new ResponseEntity<String>("Error when searching one hanzi stroke in specified book name.", responseHeaders, HttpStatus.OK);
		}
		
	}

}
