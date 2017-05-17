package com.demo.bootstrap.dao.mapper.module;

import com.demo.bootstrap.controller.userreport.UserReportBean;
import com.demo.bootstrap.controller.userreport.UserReportForm;
import java.util.List;

public interface UserReportMapper {

	public int selectUserReportBeanCount(UserReportForm form);

	public List<UserReportBean> selectUserReportBeanByLimit(UserReportForm form);

}