package com.reinard.learnhanzi.controllers;

import java.math.BigDecimal;
import java.sql.Timestamp;

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
import com.reinard.learnhanzi.entities.HanziData;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	/**
	 * Convert Java Object to Json.
	 * @return json
	 */
	@RequestMapping(value = "/parseJson", method = RequestMethod.GET, produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String parseJson() throws Exception{
		HanziData hanziData = new HanziData();
		hanziData.setId(new BigDecimal(1));
		hanziData.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		hanziData.setHanzi("\u6211");
		
		//An ObjectOutputStream writes primitive data types and graphs of Java objects to an OutputStream
		OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		//write the hanziData as Json in byteArrayOutputStream
		objectMapper.writeValue(byteArrayOutputStream, hanziData);
		
		byte[] data = ((ByteArrayOutputStream) byteArrayOutputStream).toByteArray();
		String json = new String(data, "UTF-8");
		System.out.println(json);
		return json;
	}
	
	public static void main(String[] args) throws Exception{
		String hanzi = "\u6211";
		//An ObjectOutputStream writes primitive data types and graphs of Java objects to an OutputStream
		OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(byteArrayOutputStream, hanzi);
		byte[] data = ((ByteArrayOutputStream) byteArrayOutputStream).toByteArray();
		String json = new String(data, "UTF-8");
		System.out.println(json);
	}

}
