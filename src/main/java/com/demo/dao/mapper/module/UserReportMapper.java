package com.demo.dao.mapper.module;

import java.util.List;

import com.demo.controller.userreport.UserReportBean;
import com.demo.controller.userreport.UserReportForm;

public interface UserReportMapper {

	public int selectUserReportBeanCount(UserReportForm form);

	public List<UserReportBean> selectUserReportBeanByLimit(UserReportForm form);

}