package com.reinard.learnhanzi.controllers;

import javax.json.stream.JsonParsingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * A controller to provide experiment.
 * 
 * @author reinard.santosa
 *
 */
@Controller
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class HomeController {
	
	@RequestMapping(value = "/testHtml", method = RequestMethod.GET)
	public String testHtml(){
		return "test";
	}

}
