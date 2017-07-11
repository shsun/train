package com.demo.sys;

import java.util.List;

import com.demo.user.UserReportEntry;
import com.demo.user.UserReportForm;

public interface UserReportMapper {

	public int selectUserReportBeanCount(UserReportForm form);

	public List<UserReportEntry> selectUserReportBeanByLimit(UserReportForm form);

}