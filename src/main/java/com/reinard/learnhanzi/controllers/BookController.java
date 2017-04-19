package com.reinard.learnhanzi.controllers;

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
	 * A method to handle http request to insert new book data into table "book_data". <br/><br/>
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
	 * Error when inserting hanzi data. <br/>
	 * <br/><br/>
	 * 
	 * If the http request is not specified the content encoding (charset=UTF-8) in the http header request, then this controller will read the byte with wrong encoding, and finally make the system behavior not as expected.<br/><br/>
	 * 
	 *  This controller will: <br/>
	 * 1. Get the "book name" from json. <br/>
	 * 2. Call "BookServiceImpl" to insert the book name into database. <br/>
	 * 3. Insert the data to database, and respond to client. <br/>
	 * 4. If the data cannot be read, response to client with error String: "The request body cannot be read." <br/>
	 * 5. If the request body is a String empty, response to client with error String: "The request body cannot be empty." <br/>
	 * 4. If the data cannot be inserted, response to client with error String: "Error: Cannot insert. Data already exist." <br/>
	 * 5. If error happened, response to client with error String: "Error when inserting book data." <br/>
	 * 
	 */
	@RequestMapping(value = "/insertBook", method = RequestMethod.POST, consumes = {"text/plain"}, produces = {"text/plain"})
	public ResponseEntity<String> insertBook(){
		//TODO finish this method
		return null;
	}
	
	/**
	 * A method to handle http request to get all book data. <br/><br/>
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
	 * not found.
	 * 
	 * Important note: Make sure that each book name separated by comma, and not spaced allow in the Http Response String.
	 * @return
	 */
	@RequestMapping(value = "/getAllBook", method = RequestMethod.GET, produces = {"text/plain"})
	public ResponseEntity<String> getAllBook(){
		//TODO finish this method
		return null;
	}
	
	
	/**
	 * A method to response all hanzi stroke written in specified book.
	 * 
	 * Http Request String Example: 第一書.
	 * Http Response Json String Example: {"第一書":[{"營業員"},{"電郵"},{"發音"}...]}
	 * 
	 * Important note: do not remove the 'space' character from http request.
	 * @return
	 */
	public ResponseEntity<String> getAllHanziStrokeInBook(){
		//TODO finish this method
		return null;
	}
	
	/**
	 * A method to insert one hanzi stroke in specified book.
	 * 
	 * Http Request Body Example: {"第一書":[{"郵局"}]}
	 * Http Response Json String Example: 郵局 added in 第一書.
	 * 
	 * @return
	 */
	public ResponseEntity<String> insertHanziStrokeInBook(){
		//TODO finish this method
		return null;
	}
	
	/**
	 * A method to search hanzi stroke in specified book.
	 * 
	 * Http Request Body Example : {"第一書":[{"郵局"}]}
	 * Http Response Json String Example： {"hanzi_stroke_data":[{"hanzi_stroke":"郵局","created_date":"1491448282654"}]} or "not found"
	 * @return
	 */
	public ResponseEntity<String> searchHanziStrokeInBook(){
		//TODO finish this method
		return null;
	}

}
