package com.reinard.learnhanzi.controllers;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import javax.persistence.criteria.CriteriaBuilder;

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
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reinard.learnhanzi.models.HanziData;

import org.hibernate.Session;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * A controller to provide experiment.
 * 
 * @author reinard.santosa
 *
 */
@Controller
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class HomeController {
	
	@Autowired
	DriverManagerDataSource postgreDataSource;
	
	@Autowired
	SessionFactory hibernateSessionFactory;
	
	@RequestMapping(value = "/test2", method = RequestMethod.GET)
	@ResponseBody
	public String test2() throws Exception{
		
		Session sess = hibernateSessionFactory.openSession();
		Transaction tx = null;
		 try {
		     tx = sess.beginTransaction();
		     List<HanziData> results = sess.createCriteria(HanziData.class).list();
		     tx.commit();
		     for(HanziData curr : results){
		    	 System.out.println(curr.toString());
		     }
		 }
		 catch (Exception e) {
		     if (tx!=null) tx.rollback();
		     
		     return e.toString();
		 }
		 finally {
			 sess.close();
			 return "success";
		 }
		
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public String test() throws Exception{
		//hibernateSessionFactory.
		Connection connection = postgreDataSource.getConnection();
		
		Statement statement = connection.createStatement();
		String sql = "SELECT * FROM learnhanzi_schema.hanzi_data";
		ResultSet resultSet = statement.executeQuery(sql);
		
		resultSet.next();
		String hanzi = resultSet.getString(3);
		System.out.println(hanzi);
		
		return "look at console";
		
		
	}
	
	/**
	 * Convert Java Object to Json.
	 * @return json
	 */
	@RequestMapping(value = "/parseJson", method = RequestMethod.GET, produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String parseJson() throws Exception{
		HanziData hanziData = new HanziData();
		hanziData.setHanzi_id(new BigDecimal(1));
		hanziData.setCreated_date(new Timestamp(System.currentTimeMillis()));
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
	
	/**
	 * Use this code, it is the fastest to parse big json data.
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/parseBigJson", method = RequestMethod.GET, produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String parseBigJson() throws Exception{
		
		StringBuilder stringbuilder = new StringBuilder();
		OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectMapper objectMapper = new ObjectMapper();
		for(int i=0; i<=3000; i++){
			HanziData hanziData = new HanziData();
			hanziData.setHanzi_id(new BigDecimal(i));
			hanziData.setCreated_date(new Timestamp(System.currentTimeMillis()));
			hanziData.setHanzi("\u6211");
			
			byteArrayOutputStream = new ByteArrayOutputStream();
			
			objectMapper.writeValue(byteArrayOutputStream, hanziData);
			
			byte[] data = ((ByteArrayOutputStream) byteArrayOutputStream).toByteArray();
			
			String json = new String(data, "UTF-8");
			
			stringbuilder.append(json + "\n");
			//faster that calling byteArrayOutputStream.close();
			byteArrayOutputStream = null;
			
		}
		
		return stringbuilder.toString();
	}

}
