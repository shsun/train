package com.demo.sys;

import java.util.List;

public interface UserReportMapper {

    int selectUserReportBeanCount(UserReportEntry form);

    List<UserReportEntry> selectUserReportBeanByLimit(UserReportEntry form);

}