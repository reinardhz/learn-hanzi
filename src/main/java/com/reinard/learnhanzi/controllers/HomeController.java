package com.reinard.learnhanzi.controllers;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
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
import com.reinard.learnhanzi.json.Hanzi_data;
import com.reinard.learnhanzi.models.HanziData;

/**
 * A controller to provide experiment.
 * 
 * @author reinard.santosa
 *
 */
@Controller
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class HomeController {

	

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home() throws Exception {

		// TODO test this http response json
	}

}
