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
 * 
 * @author reinard.santosa
 *
 */
public class BookController {
	
	/**
	 * A method to response all book data in table "book_data".
	 * 
	 * Http Response Json String Example: {"book data":[{"book 1"},{"book 2"},...]}
	 * 
	 * Important note: do not remove the 'space' character from http request.
	 * @return
	 */
	public ResponseEntity<String> getAllBook(){
		//TODO finish this controller
		return null;
	}
	
	
	/**
	 * A method to response all hanzi written in specified book.
	 * 
	 * Http Request String Example: book 1.
	 * Http Response Json String Example: {"book 1":[{"營業員"},{"電郵"},{"發音"}...]}
	 * 
	 * Important note: do not remove the 'space' character from http request.
	 * @return
	 */
	public ResponseEntity<String> getAllHanziInBook(){
		//TODO finish this controller
		return null;
	}

}
