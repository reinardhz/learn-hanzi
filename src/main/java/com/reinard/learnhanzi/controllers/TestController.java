package com.reinard.learnhanzi.controllers;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.beans.factory.annotation.Value;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reinard.learnhanzi.dao.impl.HanziDaoImpl;
import com.reinard.learnhanzi.json.Hanzi_data;
import com.reinard.learnhanzi.models.HanziData;

/**
 * A testing controller
 * 
 * @author reinard.santosa
 *
 */
@Controller
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@RequestMapping(value = "/test")
public class TestController {

		//@Autowired
		private DriverManagerDataSource postgreDataSource;

		//@Autowired
		private SessionFactory hibernateSessionFactory;
		
		//@Autowired
		private HanziDaoImpl hanziDaoImpl;
		
		//@Value("${text.helloWorld}")
		private String helloWorld;
		
		
		/**
		 * To see the root ServletContext.
		 */
		@RequestMapping(value = "/seeServletContextAttributeNames", method = RequestMethod.GET)
		@ResponseBody
		public String seeServletContext(HttpServletRequest httpServletRequest){
			ServletContext rootContext = httpServletRequest.getServletContext();
			Enumeration<?> attributesName = null;
			StringBuilder resultString = new StringBuilder();
			for(attributesName = rootContext.getAttributeNames(); attributesName.hasMoreElements();){
				resultString.append(attributesName.nextElement() + ";\n");
			}
			return resultString.toString();
		}
		
		/**
		 * To see the root ServletContext's name.
		 */
		@RequestMapping(value = "/seeServletContextName", method = RequestMethod.GET)
		@ResponseBody
		public String seeServletContextName(HttpServletRequest httpServletRequest){
			ServletContext rootContext = httpServletRequest.getServletContext();
			return rootContext.getServletContextName();
		}
		
		/**
		 * To see ServletContext init parameter names.
		 */
		@RequestMapping(value = "/seeInitParameterNames", method = RequestMethod.GET)
		@ResponseBody
		public String seeInitParameterNames(HttpServletRequest httpServletRequest){
			ServletContext rootContext = httpServletRequest.getServletContext();
			Enumeration<?> initParameterNames = null;
			StringBuilder resultString = new StringBuilder();
			for(initParameterNames = rootContext.getInitParameterNames(); initParameterNames.hasMoreElements();){
				resultString.append(initParameterNames.nextElement() + ";\n");
			}
			return resultString.toString();
		}
		
		/**
		 * Get parameter defined in ServletContext (web.xml)
		 */
		@RequestMapping(value = "/getInitParameterNames", method = RequestMethod.GET)
		@ResponseBody
		public String getInitParameterNames(HttpServletRequest httpServletRequest){
			ServletContext rootContext = httpServletRequest.getServletContext();
			return rootContext.getInitParameter("test");
			
		}
		
		
		/**
		 * To test get value in properties file.
		 */
		@RequestMapping(value = "/hello", method = RequestMethod.GET)
		@ResponseBody
		public String helloWorld(){
			return helloWorld;
		}
		
		
		/**
		 * To test connection to this controller.
		 */
		@RequestMapping(value="/conn", method = RequestMethod.GET)
		public ResponseEntity<String> testConnection(){
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("Content-Type", "text/html;charset=UTF-8");
			return new ResponseEntity<String>("Connection OK", responseHeaders, HttpStatus.OK);
		}
		
		/**
		 * Test for method shutdown the computer;
		 */
		@RequestMapping(value="/shutdown", method = RequestMethod.GET)
		public void testSelectBy() throws Exception{
			
			Runtime runtime = Runtime.getRuntime();
			
			//===force shutdown the computer===
			//equivalent execute command in cmd: shutdown.exe -s -t 5
			//force shutdown is automatically invoke if t>0 (read the help on cmd)
		    Process proc = runtime.exec("shutdown -s -t 5");
		    //==============
		    
		    System.exit(0);
			
		}
		
		//@RequestMapping(value = "/getAllHanzi", method = RequestMethod.GET)
		//@ResponseBody
		public String test2() throws Exception {

			Session newSession = hibernateSessionFactory.openSession();
			Transaction tx = null;
			try {
				tx = newSession.beginTransaction();
				List<HanziData> results = newSession.createCriteria(HanziData.class).list();
				tx.commit();
				List<Hanzi_data> hanzi_datas = new ArrayList<>();
				Hanzi_data hanzi_data = new Hanzi_data();
				for (HanziData curr : results) {
					//hanzi_data.setHanzi_id(String.valueOf(curr.getHanzi_id()));
					hanzi_data.setHanzi(curr.getHanzi());
					hanzi_datas.add(hanzi_data);
				}

				Hanzi_data[] resultHanziJson = hanzi_datas.<Hanzi_data>toArray(new Hanzi_data[0]);
				ObjectMapper mapper = new ObjectMapper();
				String resultJson = mapper.writeValueAsString(resultHanziJson);
				System.out.println(resultJson);
				return resultJson;
			} catch (Exception e) {
				if (tx != null)
					tx.rollback();
				return e.toString();
			} finally {
				if (newSession.isOpen())
					newSession.close();
			}
		}

		//@RequestMapping(value = "/test", method = RequestMethod.GET)
		//@ResponseBody
		public String test() throws Exception {
			// hibernateSessionFactory.
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
		 * 
		 * @return json
		 */
		//@RequestMapping(value = "/parseJson", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
		//@ResponseBody
		public String parseJson() throws Exception {
			HanziData hanziData = new HanziData();
			hanziData.setHanzi_id(1L);
			//hanziData.setCreated_date(new Timestamp(System.currentTimeMillis()));
			hanziData.setHanzi("\u6211");

			// An ObjectOutputStream writes primitive data types and graphs of Java
			// objects to an OutputStream
			OutputStream byteArrayOutputStream = new ByteArrayOutputStream();

			ObjectMapper objectMapper = new ObjectMapper();

			// write the hanziData as Json in byteArrayOutputStream
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
		//@RequestMapping(value = "/parseBigJson", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
		//@ResponseBody
		public String parseBigJson() throws Exception {

			StringBuilder stringbuilder = new StringBuilder();
			OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectMapper objectMapper = new ObjectMapper();
			for (int i = 0; i <= 3000; i++) {
				HanziData hanziData = new HanziData();
				hanziData.setHanzi_id(1L);
				//hanziData.setCreated_date(new Timestamp(System.currentTimeMillis()));
				hanziData.setHanzi("\u6211");

				byteArrayOutputStream = new ByteArrayOutputStream();

				objectMapper.writeValue(byteArrayOutputStream, hanziData);

				byte[] data = ((ByteArrayOutputStream) byteArrayOutputStream).toByteArray();

				String json = new String(data, "UTF-8");

				stringbuilder.append(json + "\n");
				// faster that calling byteArrayOutputStream.close();
				byteArrayOutputStream = null;
				return "";
			}

			return stringbuilder.toString();
		}
	
}
