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

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Controller
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@RequestMapping(value = "/hanzi")
public class HanziController{
	
	private final static Logger logger = Logger.getLogger(HanziController.class);
	
	@Autowired
	private HanziServiceImpl hanziService;
	
	@RequestMapping(value = "/getAllHanzi", method = RequestMethod.GET)
	public ResponseEntity<String> getAllHanzi() throws Exception {
		try{
			logger.info("Get all hanzi data...");
			Hanzi_data[] allHanzi = hanziService.selectAll();
			
			HanziDataJson result = new HanziDataJson();
			result.setHanzi_data(allHanzi);
			
			//Convert to json
			ObjectMapper mapper = new ObjectMapper();
			String resultJson = mapper.writeValueAsString(result);
			logger.info("Result:");
			logger.info(resultJson);
			
			return new ResponseEntity<String>(resultJson,HttpStatus.OK);
		}catch(Exception e){
			logger.error("Error when getting all hanzi data", e);
			return new ResponseEntity<String>("Error when getting all hanzi data", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
