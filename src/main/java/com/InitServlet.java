package com;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * 初始化Servlet设置版本号
 *
 */
public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = -5424636924977603957L;

	@Override
	public void init() throws ServletException {
		String version = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		getServletContext().setAttribute("SysVersion", version);
		super.init();
	}

}
