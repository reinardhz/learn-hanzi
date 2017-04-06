package com.reinard.learnhanzi.controllers;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

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
//@Controller
//@Scope(value = WebApplicationContext.SCOPE_REQUEST)
//@RequestMapping(value = "/test")
public class TestController {

		//@Autowired
		private DriverManagerDataSource postgreDataSource;

		//@Autowired
		private SessionFactory hibernateSessionFactory;
		
		@Autowired
		private HanziDaoImpl hanziDaoImpl;
		
		
		/**
		 * Test for method HanziDaoImpl.selectBy();
		 */
		//@RequestMapping(value="/testSelectBy", method = RequestMethod.GET)
		//@ResponseBody
		public String testSelectBy() throws Exception{
			HanziData result = hanziDaoImpl.selectBy("我");
			return result.toString();
		}
		
		//@RequestMapping(value = "/getAllHanzi", method = RequestMethod.GET)
		//@ResponseBody
		public String test2() throws Exception {

			// TODO test this http response json
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
		@RequestMapping(value = "/parseBigJson", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
		@ResponseBody
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
