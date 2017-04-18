package com.reinard.learnhanzi.controllers;

import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
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
	 * A method to handle http request to insert new book data in table "book_data". <br/><br/>
	 *  
	 * Http Request body example: 第一書
	 * Http Response body example: Book 第一書  inserted.
	 * 
	 * If the http request is not specified the content encoding (charset=UTF-8) in the http header request, then this controller will read the byte with wrong encoding, and finally make the system behavior not as expected.<br/><br/>
	 * 
	 *  This controller will: <br/>
	 * 1. Get the "book name" from json. <br/>
	 * 2. Call "BookServiceImpl" to insert the book name into database. <br/>
	 * 3. Insert the data to database, and respond to client.<br/>
	 * 4. If the data cannot be read, response to client with error String: "The request body cannot be read."<br/>
	 * 5. If the request body is a String empty, response to client with error String: "The request body cannot be empty."<br/>
	 * 4. If the data cannot be inserted, response to client with error String: "Error: Cannot Insert. Data Already Exist."<br/>
	 * 5. If error happened, response to client with error String: "Error when inserting hanzi data". <br/>
	 * 
	 */
	public ResponseEntity<String> insertBook(){
		//TODO finish this controller
		return null;
	}
	
	/**
	 * A method to response all book data in table "book_data".
	 * 
	 * Http Response Json String Example: {"book_data":[{"book_id":"1", "book_name":"第一書"},{"book_id":"2", "book_name":"第二書"},...]}
	 * 
	 * Important note: do not remove the 'space' character from http request.
	 * @return
	 */
	public ResponseEntity<String> getAllBook(){
		//TODO finish this controller
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
		//TODO finish this controller
		return null;
	}
	
	/**
	 * A method to insert one hanzi stroke in specified book.
	 * 
	 * Http Request Body Example: {"Book 1":[{"郵局"}]}
	 * Http Response Json String Example: 郵局 added.
	 * 
	 * @return
	 */
	public ResponseEntity<String> insertHanziStrokeInBook(){
		//TODO finish this controller
		return null;
	}
	
	/**
	 * A method to search hanzi stroke in specified book.
	 * 
	 * Http Request Body Example (""): {"Book 1":[{""}]}
	 * Http Response Json String Example： {"hanzi_stroke_data":[{"hanzi_stroke":"郵局","created_date":"1491448282654"}]} or "not found"
	 * @return
	 */
	public ResponseEntity<String> searchHanziStrokeInBook(){
		//TODO finish this controller
		return null;
	}

}
