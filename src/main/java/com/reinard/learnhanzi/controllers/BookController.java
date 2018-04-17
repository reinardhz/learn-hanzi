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
@Scope(value = "singleton")
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
		
		//do not let the browser store cache.
		//read "http://stackoverflow.com/questions/49547/how-to-control-web-page-caching-across-all-browsers".
		responseHeaders.add("Cache-Control", "no-cache, no-store, must-revalidate");
		responseHeaders.add("Pragma", "no-cache");
		responseHeaders.add("Expires", "0");
		
		try{
			
			logger.debug("Read the http request body raw byte, because jetty server could not get the request body using Spring @RequestBody annotation.");
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
		
		//do not let the browser store cache.
		//read "http://stackoverflow.com/questions/49547/how-to-control-web-page-caching-across-all-browsers".
		responseHeaders.add("Cache-Control", "no-cache, no-store, must-revalidate");
		responseHeaders.add("Pragma", "no-cache");
		responseHeaders.add("Expires", "0");
		
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
	 * This method is tested ok. <br/>
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
		//the service needed in this method is tested ok..
		
		//These are the case to test this controller:
		//Case 1: Space only, controller must return String "The request body cannot be empty." (ok)
		//Case 2: Non chinese character without space, controller must return String "The request body cannot be empty." (ok)
		//Case 3: Non chinese character with space, controller must return String "The request body cannot be empty." (ok)
		//Case 3: Chinese character (book_name) that exist in the database with space between characters, must return result. (ok)
		//Case 4: Chinese character (book_name) that exist in the database with no space between characters, must return result. (ok)
		//case 5: Chinese character (book_name) that is not exist in the database with space between characters, must return String "Not found." (ok)
		//case 6: Chinese character (book_name) that is not exist in the database with no space between characters, must return String "Not found." (ok)
		
		
		logger.info("Processing request to getAllHanziStrokeInBookName. Searching hanzi stroke now...");
		
		//enable "same cross origin", so this controller could response data to ajax
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Access-Control-Allow-Origin", "*");
						
		//response header: UTF-8 encoding, to tell the browser display it correctly
		responseHeaders.add("Content-Type", "text/plain;charset=UTF-8");
		
		//do not let the browser store cache.
		//read "http://stackoverflow.com/questions/49547/how-to-control-web-page-caching-across-all-browsers".
		responseHeaders.add("Cache-Control", "no-cache, no-store, must-revalidate");
		responseHeaders.add("Pragma", "no-cache");
		responseHeaders.add("Expires", "0");
		
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
	 * This method is tested ok. <br/>
	 * 
	 * A method to insert one hanzi stroke and page number in specified book name. <br/><br/>
	 * Http Request Example: <br/>
	 * <b>Request Header:</b> <br/>
	 * Content-Type: text/plain;charset=UTF-8 <br/>
	 * <b>Request Body:</b> <br/>
	 * 第一書:電子郵件:二 <br/><br/>
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
	 * The request body must contain colon character. <br/>
	 * <i>or</i> <br/>
	 * The inputted String is not match the requirement. <br/>
	 * <i>or</i> <br/>
	 * Error when inserting hanzi stroke. <br/>
	 * <br/><br/>
	 * 
	 * If the http request is not specified the content encoding (charset=UTF-8) in the http header request, then this controller will read the byte with wrong encoding, and finally make the system behavior not as expected. <br/><br/>
	 * 
	 * This controller will: <br/>
	 * 1. Read the data from http request. <br/>
	 * 2. If the data cannot be read, response to client with error String: "The request body cannot be read." <br/>
	 * 3. If the request body is a String empty, response to client with error String: "The request body cannot be empty." <br/>
	 * 4. If the request body does not contain ":" character, response to client with error String: "The request body must contain colon." <br/>
	 * 5. If there are no 3 words of chinese character separated by colon (:) character, response to client with error String: "The inputted String is not match the requirement." <br/>
	 * 6. Get the "book_name", "hanzi_stroke" and "page_number" from http request. <br/>
	 * 7. Create the date, from current date. <br/>
	 * 8. Insert the "hanzi_stroke" and "page_number" to the database (related with "book_name"). <br/>
	 * 9. Response the json data to client if the data is successfully inserted. <br/>
	 * 10. If error happened, response to client with error String: "Error when inserting hanzi stroke." <br/>
	 */
	@RequestMapping(value = "/insertHanziStrokeInBook", method = RequestMethod.POST, consumes = {"text/plain"}, produces = {"text/plain"})
	public ResponseEntity<String> insertHanziStrokeInBook(HttpServletRequest httpServletRequest){
		//the service needed in this method is tested OK.
		
		//These are the case to test this controller:
		//Case 1: Http request body: Space only, controller must return String "The request body cannot be empty." (ok)
		//Case 2: Http request body: Non chinese characters, without space between them, controller must return String "The request body cannot be empty.". (ok)
		//Case 3: Http request body: Non chinese characters with space between them, controller must return String "The request body cannot be empty.". (ok)
		
		//Case 4: Http request body: Colon only, must return String "The inputted String is not match the requirement." (ok)
		//Case 5: Http request body: Chinese words without colon, without space, must return String "The request body must contain colon character." (ok)
		
		//Case 6: Http request body: 3 Chinese words separated by the colon with no space between characters, Chinese character (book_name) is exist in the database, this controller must return result. (ok)
		//Case 7: Http request body: 3 Chinese words separated by the colon with space between characters, Chinese character (book_name) is exist in the database, this controller must return result. (ok)
		
		//Case 8: Http request body: 3 Chinese words separated by the colon with no space between characters, Chinese character (book_name) is not exist in the database, this controller must return String: "Error when inserting hanzi stroke.". (ok)
		//Case 9: Http request body: 3 Chinese words separated by the colon with space between characters, Chinese character (book_name) is not exist in the database, this controller must return String: "Error when inserting hanzi stroke.". (ok)
		
		
		logger.info("Processing request to insertHanziStrokeInBook...");
		
		//enable "same cross origin", so this controller could response data to ajax
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Access-Control-Allow-Origin", "*");
		
		//response header: UTF-8 encoding, to tell the browser display it correctly
		responseHeaders.add("Content-Type", "text/plain;charset=UTF-8");
		
		//do not let the browser store cache.
		//read "http://stackoverflow.com/questions/49547/how-to-control-web-page-caching-across-all-browsers".
		responseHeaders.add("Cache-Control", "no-cache, no-store, must-revalidate");
		responseHeaders.add("Pragma", "no-cache");
		responseHeaders.add("Expires", "0");
		
		try{
			logger.debug("Read the http request body raw byte, because jetty server could not bind the request using Spring @RequestBody annotation.");
			BufferedReader bufferedReader = httpServletRequest.getReader();
			
			int resultInt = 0;
			char resultChar = (char)0;
			
			StringBuilder resultString = new StringBuilder();
			
			while ((resultInt = bufferedReader.read()) != -1) {
				logger.debug("read the characters");
				// the resultInt, is a decimal number that point to the Unicode Character, using UTF-16 Big Endian. 
				// Example: int = 24859 point to the chinese traditional character for love (愛).
				
				//Cannot cast to char if the int is not in the char data type range.
				if((resultInt<0) || resultInt>65535){
					logger.info("Sending response: \"The request body cannot be read.\"");
					return new ResponseEntity<String>("The request body cannot be read.", responseHeaders, HttpStatus.OK);
				}
				
				//Add only the unicode CJK (Chinese Japanese Korean) characters, colon character and semicolon character :
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
			
			String[] splitInput = input.split(":");
			if(splitInput.length != 3){
				logger.error("The inputted String is not match the requirement.");
				return new ResponseEntity<String>("The inputted String is not match the requirement.", responseHeaders, HttpStatus.OK);
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
	 * This method is tested ok. <br/>
	 * 
	 * A method to search hanzi strokes in specified book name. <br/><br/>
	 * 
	 * Http Request Example: <br/>
	 * <b>Request Header:</b> <br/>
	 * Content-Type: text/plain;charset=UTF-8 <br/>
	 * <b>Request Body:</b> <br/>
	 * 第二書:消防局 <br/><br/>
	 * 
	 * Http Response Example: <br/>
	 * <b>Response Header:</b> <br/>
	 * Access-Control-Allow-Origin: * <br/>
	 * Content-Type: text/plain;charset=UTF-8<br/>
	 * <b>Response Body:</b> <br/>
	 * {"book_name":"第二書", "hanzi_stroke_data":[{"hanzi_stroke":"消防局", "page_number":"一", "created_date":"1492318783895"},{"hanzi_stroke":"消防局", "page_number":"三十", "created_date":"1502318783895"}]} <br/>
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
	 * The request body must contain colon character. <br/>
	 * 
	 * <i>or</i> <br/>
	 * 
	 * The inputted String is not match the requirement. <br/>
	 * 
	 * <i>or</i> <br/>
	 * 
	 * Not found. <br/>
	 * 
	 * <i>or</i> <br/>
	 * 
	 * Error when searching hanzi strokes in specified book name. <br/><br/>
	 * 
	 * Note: If the http request is not specified the content encoding (charset=UTF-8) in the http header request, then this controller will read the byte with wrong encoding, and finally make the system behavior not as expected. <br/><br/>
	 * 
	 * This controller will: <br/>
	 * 1. Read the data from http request. <br/>
	 * 2. If the data cannot be read, response to client with error String: "The request body cannot be read." <br/>
	 * 3. If the request body is a String empty, response to client with error String: "The request body cannot be empty."<br/>
	 * 4. If the request body does not contain ":" character, response to client with error String: "The request body must contain colon." <br/>
	 * 5. If there are no 3 words of chinese character separated by colon (:) character, response to client with error String: "The inputted String is not match the requirement." <br/>
	 * 6. Get the "book_name", "hanzi_stroke" from http request. <br/>
	 * 7. Select the data from database. <br/>
	 * 8. Response the json data to client if the data found, or string "Not found.", if the data is not found. <br/>
	 * 9. If error happened, response to client with error String: "Error when searching hanzi strokes in specified book name." <br/>
	 * 
	 */
	@RequestMapping(value = "/searchHanziStrokeInBook", method = RequestMethod.POST, consumes = {"text/plain"}, produces = {"text/plain"})
	public ResponseEntity<String> searchHanziStrokeInBook(HttpServletRequest httpServletRequest){
		
		//the service needed in this method is tested ok.
		
		//These are the case to test this controller:
		//Case 1: Http request body: Space only, controller must return String "The request body cannot be empty." (ok)
		//Case 2: Http request body: Non chinese characters, without space between them, controller must return String "The request body cannot be empty.". (ok)
		//Case 3: Http request body: Non chinese characters with space between them, controller must return String "The request body cannot be empty.". (ok)
		
		//case 4: Http request body: Colon only, must return String "The inputted String is not match the requirement.". (ok)
		//case 5: Http request body: Chinese words without colon, without space, must return String "The request body must contain colon character.". (ok)
		
		//Case 6: Http request body: 2 Chinese words separated by the colon with no space between characters, Chinese character (book_name) and Chinese character (hanzi_stroke) are exist in the database, this controller must return result. (ok)
		//Case 7: Http request body: 2 Chinese words separated by the colon with space between characters, Chinese character (book_name) and Chinese character (hanzi_stroke) are exist in the database, this controller must return result. (ok)
		
		//Case 8: Http request body: 2 Chinese words separated by the colon with no space between characters, Chinese character (book_name) is exist in the database, but Chinese character (hanzi_stroke) is not exist in the database, this controller must return String: "Not found.". (ok)
		//Case 9: Http request body: 2 Chinese words separated by the colon with no space between characters, Chinese character (book_name) is not exist in the database, but Chinese character (hanzi_stroke) is exist in the database, this controller must return String: "Not found.". (ok)
		//Case 10: Http request body: 2 Chinese words separated by the colon with no space between characters, Chinese character (book_name) and Chinese character (hanzi_stroke) are not exist in the database, this controller must return String: "Not found.". (ok)
		
		//Case 11: Http request body: 2 Chinese words separated by the colon with space between characters, Chinese character (book_name) is exist in the database, but Chinese character (hanzi_stroke) is not exist in the database, this controller must return String: "Not found.". (ok)
		//Case 12: Http request body: 2 Chinese words separated by the colon with space between characters, Chinese character (book_name) is not exist in the database, but Chinese character (hanzi_stroke) is exist in the database, this controller must return String: "Not found.". (ok)
		//Case 13: Http request body: 2 Chinese words separated by the colon with space between characters, Chinese character (book_name) and Chinese character (hanzi_stroke) are not exist in the database, this controller must return error String: "Error when searching hanzi strokes in specified book name.". (ok)
		
		
		logger.info("Processing request to searchHanziStrokeInBook...");
		
		//enable "same cross origin", so this controller could response data to ajax
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Access-Control-Allow-Origin", "*");
								
		//response header: UTF-8 encoding, to tell the browser display it correctly
		responseHeaders.add("Content-Type", "text/plain;charset=UTF-8");
		
		//do not let the browser store cache.
		//read "http://stackoverflow.com/questions/49547/how-to-control-web-page-caching-across-all-browsers".
		responseHeaders.add("Cache-Control", "no-cache, no-store, must-revalidate");
		responseHeaders.add("Pragma", "no-cache");
		responseHeaders.add("Expires", "0");
		
		BufferedReader bufferedReader = null;
		try{
			logger.debug("Read the http request body raw byte, because jetty server could not bind the request using Spring @RequestBody annotation.");
			bufferedReader = httpServletRequest.getReader();
			
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
			
			bufferedReader.close();
			
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
			
			String[] splitInput = input.split(":");
			if(splitInput.length != 2){
				logger.error("The inputted String is not match the requirement.");
				return new ResponseEntity<String>("The inputted String is not match the requirement.", responseHeaders, HttpStatus.OK);
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
			logger.error("Error when searching hanzi strokes in specified book name.");
			logger.info("Sending response: \"Error when searching hanzi strokes in specified book name.\"");
			return new ResponseEntity<String>("Error when searching hanzi strokes in specified book name.", responseHeaders, HttpStatus.OK);
		}finally {
			if(bufferedReader!=null)bufferedReader=null;
		}
		
	}
	
	
	/**
	 * This method is tested ok. <br/>
	 * 
	 * A method to search hanzi strokes in all book name. <br/><br/>
	 * 
	 * Http Request Example: <br/>
	 * <b>Request Header:</b> <br/>
	 * POST <br/>
	 * Content-Type: text/plain;charset=UTF-8 <br/>
	 * <b>Request Body:</b> <br/>
	 * 學習<br/><br/>
	 * 
	 * Http Response Example: <br/>
	 * <b>Response Header:</b> <br/>
	 * Access-Control-Allow-Origin: * <br/>
	 * Content-Type: text/plain;charset=UTF-8<br/>
	 * <b>Response Body:</b> <br/>
	 * {"hanzi_stroke_data_all_book":[{"hanzi_stroke":"學習","book_name":"第二書","page_number":"七十三"}]}
	 * 
	 * <i>or</i> <br/>
	 * 
	 * The request body cannot be read. <br/>
	 * 
	 * <i>or</i> <br/>
	 * 
	 * The request body must only contain Chinese characters, with no space. <br/>
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
	 * Error when searching hanzi strokes in all book name. <br/><br/>
	 * 
	 * Note: If the http request is not specified the content encoding (charset=UTF-8) in the http header request, then this controller will read the byte with wrong encoding, and finally make the system behavior not as expected. <br/><br/>
	 * 
	 * This controller will: <br/>
	 * 1. Read the data from http request. <br/>
	 * 2. If the data cannot be read, response to client with error String: "The request body cannot be read." <br/>
	 * 3. If the data contains space or other non Chinese characters, respond: "The request body must only contain Chinese characters, with no space." <br/>
	 * 4. If the request body is a String empty, response to client with error String: "The request body cannot be empty."<br/>
	 * 5. Search the data from database. <br/>
	 * 6. Response the json data to client if the data found, or string "Not found.", if the data is not found. <br/>
	 * 7. If error happened, response to client with error String: "Error when searching hanzi strokes in all book name." <br/>
	 * 
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/searchHanziStrokeInAllBook", method = RequestMethod.POST, consumes = {"text/plain"}, produces = {"text/plain"})
	public ResponseEntity<String> searchHanziStrokeInAllBook(HttpServletRequest httpServletRequest){
		//TODO test this method
		
		//These are the case to test this controller:
		//Case 1: Http request body: Space only or empty String, controller must return String "The request body cannot be empty." (ok)
		//Case 2: Http request body: Non chinese characters, controller must return String "The request body must only contain Chinese characters, with no space.". (ok)
		//Case 3: Http request body: Chinese characters with space, controller must return String "The request body must only contain Chinese characters, with no space." (ok)
		//Case 4: Http request body: Chinese characters (hanzi_stroke) that is exist in "hanzi_stroke_data" table, controller must return String result. (ok)
		
		logger.info("Processing request to searchHanziStrokeInAllBook...");
		
		//enable "same cross origin", so this controller could response data to ajax
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Access-Control-Allow-Origin", "*");
		
		//response header: UTF-8 encoding, to tell the browser display it correctly
		responseHeaders.add("Content-Type", "text/plain;charset=UTF-8");
	
		//do not let the browser store cache.
		//read "http://stackoverflow.com/questions/49547/how-to-control-web-page-caching-across-all-browsers".
		responseHeaders.add("Cache-Control", "no-cache, no-store, must-revalidate");
		responseHeaders.add("Pragma", "no-cache");
		responseHeaders.add("Expires", "0");
		
		BufferedReader bufferedReader = null;
		try {
			logger.debug("Read the http request body raw byte, because jetty server could not bind the request using Spring @RequestBody annotation.");
			bufferedReader = httpServletRequest.getReader();
			
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
				//source: http://www.fileformat.info/info/unicode/block/cjk_unified_ideographs/index.htm
				if( ((resultInt>=19968) && (resultInt<=40959)) ){
					resultChar = (char)resultInt;
					resultString.append(resultChar);
				}else {
					logger.info("Sending response: \"The request body must only contain Chinese characters, with no space.\"");
					return new ResponseEntity<String>("The request body must only contain Chinese characters, with no space.", responseHeaders, HttpStatus.OK);
				}
			}
			
			bufferedReader.close();
			
			logger.debug("The request body: "+ resultString.toString());
			String input = resultString.toString();
			
			if(StringUtil.isEmpty(input)){
				logger.info("Sending response: \"The request body cannot be empty.");
				return new ResponseEntity<String>("The request body cannot be empty.", responseHeaders, HttpStatus.OK);
			}
			
			String result = bookServiceImpl.searchHanziStrokeInAllBook(input);
			if(result == null){
				logger.info("Sending response: \"Not found.\"");
				return new ResponseEntity<String>("Not found.",responseHeaders,HttpStatus.OK);
			}
			
			logger.info("Sending response:");
			logger.info(result);
			return new ResponseEntity<String>(result,responseHeaders,HttpStatus.OK);
		}catch(Exception e) {
			logger.error("Error when searching hanzi strokes in all book name.");
			logger.info("Sending response: \"Error when searching hanzi strokes in all book name.\"");
			return new ResponseEntity<String>("Error when searching hanzi strokes in all book name.", responseHeaders, HttpStatus.OK);
		}finally {
			if(bufferedReader!=null)bufferedReader=null;
		}
	}

}
