package com.demo.sys;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import base.utils.jsonresult.XJsonResult;
import base.utils.jsonresult.XJsonResultFactory;

@Controller
@RequestMapping("/userreport/UserReportCtrl/")
public class UserReportController {

    @Autowired
    private UserReportMapper userReportMapper;

    @RequestMapping(value = "search")
    public @ResponseBody XJsonResult search(@RequestBody UserReportEntry form) throws Exception {
        int count = userReportMapper.selectUserReportBeanCount(form);
        List<UserReportEntry> list = userReportMapper.selectUserReportBeanByLimit(form);
        return XJsonResultFactory.extgrid(list, count);
    }
}