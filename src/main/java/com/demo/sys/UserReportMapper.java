package com.demo.sys;

import java.util.List;

import com.demo.user.UserReportEntry;

public interface UserReportMapper {

    int selectUserReportBeanCount(UserReportEntry form);

    List<UserReportEntry> selectUserReportBeanByLimit(UserReportEntry form);

}