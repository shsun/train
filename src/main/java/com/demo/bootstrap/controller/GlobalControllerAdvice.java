package com.demo.bootstrap.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.youdo.utils.XAjaxOut;
import com.youdo.utils.jsonresult.XJsonResult;
import com.youdo.utils.jsonresult.XJsonResultFactory;

@ControllerAdvice
@EnableWebMvc
public class GlobalControllerAdvice {

	private Logger logger = LogManager.getLogger(getClass());

	@ExceptionHandler(Exception.class)
	public @ResponseBody void handle(Exception e, HttpServletResponse response) {
		logger.error(e.getMessage(), e);
		
		XJsonResult re = XJsonResultFactory.error(e.getMessage());
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = "";
		try {
			json = ow.writeValueAsString(re);
		} catch (JsonProcessingException ex) {
			logger.error(ex);
		}
		XAjaxOut.responseText(response, json);
	}

}
