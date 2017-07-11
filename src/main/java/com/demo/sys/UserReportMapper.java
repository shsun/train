package com.demo.sys;

import java.util.List;

import com.demo.user.UserReportBean;
import com.demo.user.UserReportForm;

public interface UserReportMapper {

	public int selectUserReportBeanCount(UserReportForm form);

	public List<UserReportBean> selectUserReportBeanByLimit(UserReportForm form);

}