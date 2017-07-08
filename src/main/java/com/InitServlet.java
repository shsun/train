package com;

import org.apache.log4j.PropertyConfigurator;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 *
 */
public class InitServlet extends HttpServlet {

    private static final long serialVersionUID = -5424636924977603957L;

    @Override
    public void init() throws ServletException {
        String version = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        getServletContext().setAttribute("SysVersion", version);

        PropertyConfigurator.configure(getServletContext().getRealPath("/") + getInitParameter("log4j"));
        super.init();
    }
}
