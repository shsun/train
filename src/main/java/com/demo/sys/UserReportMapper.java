package com.demo.sys;

import java.util.List;

import com.demo.user.UserReportEntry;
import com.demo.user.UserReportForm;

public interface UserReportMapper {

    int selectUserReportBeanCount(UserReportForm form);

    List<UserReportEntry> selectUserReportBeanByLimit(UserReportForm form);

}