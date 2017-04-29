package com.reinard.learnhanzi.initial;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.context.ContextLoaderListener;

/**
 * This is a class to define configuration to start this web application.
 * 
 * A server that running sevlet 3.0+ configuration, will automatically search this class, and execute the "public void onStartup(ServletContext arg0) throws ServletException" method.
 * 
 * @author reinard.santosa
 *
 */
public class LearnHanziWebApplicationInitializer implements WebApplicationInitializer{
	
	/**
	 * 
	 */
	@Override
	public void onStartup(ServletContext rootContext) throws ServletException {
		
		//Create the 'root' Spring web application context, with XML configuration from file "/WEB-INF/spring/servlet-context.xml".
		XmlWebApplicationContext springRootContext = new XmlWebApplicationContext();
		springRootContext.setConfigLocation("/WEB-INF/spring/root-context.xml");
		
		//Add Listener (ContextLoaderListener) to start up and shut down Spring web application context (variable "springRootContext").
		rootContext.addListener(new ContextLoaderListener(springRootContext));
		
		
		
		//Create the Spring's DispatcherServlet application context, with XML configuration from file "/WEB-INF/spring/appServlet/servlet-context.xml".
		XmlWebApplicationContext dispatcherServletContext = new XmlWebApplicationContext();
		dispatcherServletContext.setConfigLocation("/WEB-INF/spring/appServlet/servlet-context.xml");
		
		//Register the spring's "DispatcherServlet" into the "rootContext" and map the dispatcher servlet with url "/api/*"
	    ServletRegistration.Dynamic dispatcher = rootContext.addServlet("dispatcher", new DispatcherServlet(dispatcherServletContext));
	    dispatcher.setLoadOnStartup(1);
	    /*Indicate that, all http request that start with '/api' (after context path), will be handle by "org.springframework.web.servlet.DispatcherServlet".
		Example: 
		* Your web application is running in server that is listening in ip 127.0.98, on port 90, and your Application Context path is "learn-hanzi",
		then, all http request starting with url: "http://127.0.98:90/learn-hanzi/api" is handled by Spring's DispatcherServlet.
		* Other request that is not start with /api, is not handle by spring's dispatcher servlet. Example: "http://127.0.98:90/learn-hanzi/index.html" is not handle by Spring's DispatcherServlet */
	    dispatcher.addMapping("/api/*");
	}

}
