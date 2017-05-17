package com.demo;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;


/**
 * 
 * @author shsun
 * 
 */
public class Log4jInitServlet extends HttpServlet {
	
	
	private static final long serialVersionUID = -3167173039573763258L;

	public Log4jInitServlet() {
		super();
	}

	@Override
	public void init( ServletConfig config ) throws ServletException {
		//String version = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		//super.getServletContext().setAttribute("SysVersion", version);
		
		/*
		String prefix = config.getServletContext().getRealPath("/");
		String log4jCfgFilePath = prefix + config.getInitParameter("log4j");
		try {
			
			//
			Properties p = new Properties();
			FileInputStream istream = new FileInputStream(log4jCfgFilePath);
			p.load(istream);
			istream.close();
			//
			String logFilePath = prefix + p.getProperty("log4j.appender.D.File");
			p.setProperty("log4j.appender.D.File", logFilePath);
			PropertyConfigurator.configure(p);
		} catch (IOException e) {
			System.out.println("Could not read configuration file [" + log4jCfgFilePath + "].");
			System.out.println("Ignoring configuration file [" + log4jCfgFilePath + "].");
		}
		 */
		super.init();
	}
}
