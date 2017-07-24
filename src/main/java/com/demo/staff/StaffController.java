package com.demo.staff;

import java.util.*;

import base.utils.XSessionUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import base.utils.jsonresult.XJsonResult;
import base.utils.jsonresult.XJsonResultFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin/StaffCtrl/")
public class StaffController {

    @Autowired
    private StaffService service;

    @Autowired
    protected SqlSessionFactory sqlSessionFactory;

    @RequestMapping(value = "search")
    public @ResponseBody XJsonResult retrieve(HttpServletRequest request, HttpServletResponse response, @RequestBody StaffEntry entry) throws Exception {
        int count = service.sumOne(request, response, XSessionUtil.getLoginUser(), entry);
        List<StaffEntry> list = service.retrieve(request, response, XSessionUtil.getLoginUser(), entry);
        return XJsonResultFactory.extgrid(list, count);
    }

    @RequestMapping(value = "save")
    public @ResponseBody XJsonResult create(HttpServletRequest request, HttpServletResponse response, @RequestBody final StaffEntry entry) throws Exception {
        return XJsonResultFactory.success();
    }

    @RequestMapping(value = "delete")
    public @ResponseBody XJsonResult delete(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") int id) {
        return XJsonResultFactory.success();
    }

    @RequestMapping(value = "retrieveOne")
    public @ResponseBody XJsonResult retrieveOne(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") int id) throws Exception {
        return null;
    }

}