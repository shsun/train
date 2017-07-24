package com.demo.basic;

import base.utils.XReflectorUtil;
import com.demo.sys.LoginUserEntry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shsun on 7/11/17.
 */
public abstract class XBasicBeanConvertor<T> {

    /**
     * 
     * @param rep
     * @param res
     * @param u
     * @param p
     * @return
     */
    public Map<String, Object> convert(HttpServletRequest rep, HttpServletResponse res, LoginUserEntry u, T p) {
        Map<String, Object> result = new HashMap<>();

        XReflectorUtil.merge(p, result);

        for (Map.Entry<String, Object> entry : result.entrySet()) {
            if (entry.getValue() instanceof Date) {
                result.put(entry.getKey(), (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(entry.getValue()));
            }
        }

        this.doConvert(rep, res, u, p);

        return result;
    }

    protected abstract void doConvert(HttpServletRequest rep, HttpServletResponse res, LoginUserEntry u, T p);
}
