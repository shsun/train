package com.demo.basic;

import com.demo.sys.LoginUserEntry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by shsun on 7/11/17.
 */
public interface IBeanConvertor<T> {

    Map<String, Object> convert(HttpServletRequest rep, HttpServletResponse res, LoginUserEntry u, T p);

}
