package com.reinard.learnhanzi.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;


/**
 * A Controller that handles all http request coming from "Book" page.
 * 
 * @author reinard.santosa
 *
 */
//@Controller
//To make this controller is not singleton, to support multithreading.
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@RequestMapping(value = "/book")
public class BookController {
	
	/**
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
	 * 6. Response the json data to client if the data is successfully inserted.
	 * 7. If the data cannot be inserted because it already existed, response to client with error String: "Error: Cannot insert. Data already exist." <br/>
	 * 8. If error happened, response to client with error String: "Error when inserting book name." <br/>
	 * 
	 */
	@RequestMapping(value = "/insertBookName", method = RequestMethod.POST, consumes = {"text/plain"}, produces = {"text/plain"})
	public ResponseEntity<String> insertBookName(){
		//TODO finish this method
		return null;
	}
	
	/**
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
		//TODO finish this method
		//the service needed in this method is tested ok.
		return null;
	}
	
	
	/**
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
	 * {"book_name":"第一書", "hanzi_stroke":[{"hanzi_stroke":"營業員", "created_date":"1491448282651"},{"hanzi_stroke":"電郵", "created_date":"1491448282652"},{"hanzi_stroke":"發音", "created_date":"1491448282653"}]} <br/>
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
	 * 7. If error happened, response to server with error String: "Error when searching all hanzi stroke in book name." <br/>
	 * 
	 */
	@RequestMapping(value = "/getAllHanziStrokeInBookName", method = RequestMethod.POST, consumes = {"text/plain"}, produces = {"text/plain"})
	public ResponseEntity<String> getAllHanziStrokeInBookName(HttpServletRequest httpServletRequest){
		//TODO finish this method
		//the service needed in this method is tested ok.
		return null;
	}
	
	/**
	 * A method to insert one hanzi stroke in specified book name. <br/><br/>
	 * Http Request Example: <br/>
	 * <b>Request Header:</b> <br/>
	 * Content-Type: text/plain;charset=UTF-8 <br/>
	 * <b>Request Body:</b> <br/>
	 * 第一書:郵局 <br/><br/>
	 * 
	 * Http Response Example: <br/>
	 * <b>Response Header:</b> <br/>
	 * Access-Control-Allow-Origin: * <br/>
	 * Content-Type: text/plain;charset=UTF-8<br/>
	 * <b>Response Body:</b> <br/>
	 * {"book_name":"第一書", "hanzi_stroke_data":[{"hanzi_stroke":"郵局", "created_date":"1491448282651"}]} <br/>
	 * <i>or</i> <br/>
	 * The request body cannot be read. <br/>
	 * <i>or</i> <br/>
	 * The request body cannot be empty. <br/>
	 * <i>or</i> <br/>
	 * Error: Cannot insert. Data already exist. <br/>
	 * <i>or</i> <br/>
	 * Error when inserting hanzi stroke. <br/>
	 * <br/><br/>
	 * 
	 * If the http request is not specified the content encoding (charset=UTF-8) in the http header request, then this controller will read the byte with wrong encoding, and finally make the system behavior not as expected.<br/><br/>
	 * 
	 * This controller will: <br/>
	 * 1. Read the data from http request.
	 * 2. If the data cannot be read, response to client with error String: "The request body cannot be read." <br/>
	 * 3. If the request body is a String empty, response to client with error String: "The request body cannot be empty."<br/>
	 * 4. Get the book_name and hanzi_stroke from http request.
	 * 5. Create the date, from current date. <br/>
	 * 6. Insert the data to database. <br/>
	 * 7. Response the json data to client if the data is successfully inserted.
	 * 8. If the data cannot be inserted, response to client with error String: "Error: Cannot insert. Data already exist."<br/>
	 * 9. If error happened, response to client with error String: "Error when inserting hanzi stroke."<br/>
	 */
	@RequestMapping(value = "/insertHanziStrokeInBook", method = RequestMethod.POST, consumes = {"text/plain"}, produces = {"text/plain"})
	public ResponseEntity<String> insertHanziStrokeInBook(HttpServletRequest httpServletRequest){
		//TODO finish this method
		//the service needed in this method is tested ok.
		return null;
	}
	
	/**
	 * A method to search one hanzi stroke in specified book name. <br/>
	 * 
	 * Http Request Example: <br/>
	 * <b>Request Header:</b> <br/>
	 * Content-Type: text/plain;charset=UTF-8 <br/>
	 * <b>Request Body:</b> <br/>
	 * 第一書:郵局 <br/><br/>
	 * 
	 *  Http Response Example: <br/>
	 * <b>Response Header:</b> <br/>
	 * Access-Control-Allow-Origin: * <br/>
	 * Content-Type: text/plain;charset=UTF-8<br/>
	 * <b>Response Body:</b> <br/>
	 * {"book_name":"第一書", "hanzi_stroke_data":[{"hanzi_stroke":"郵局","created_date":"1491448282651"}]} <br/>
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
	 * 1. Read the data from http request.
	 * 2. If the data cannot be read, response to client with error String: "The request body cannot be read." <br/>
	 * 3. If the request body is a String empty, response to client with error String: "The request body cannot be empty."<br/>
	 * 4. Get the "book_name" and "hanzi_stroke" from http request.
	 * 5. Select the data from database. <br/>
	 * 6. Response the json data to client if the data found, or string "Not found.", if the data is not found. <br/>
	 * 7. If error happened, response to client with error String: "Error when searching one hanzi stroke in specified book name."<br/>
	 * 
	 */
	@RequestMapping(value = "/searchHanziStrokeInBook", method = RequestMethod.POST, consumes = {"text/plain"}, produces = {"text/plain"})
	public ResponseEntity<String> searchHanziStrokeInBook(){
		//TODO finish this method
		//the service needed in this method is not yet tested.
		return null;
	}

}
