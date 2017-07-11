package com.demo.user;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import com.demo.sys.UserReportMapper;

import base.utils.jsonresult.XJsonResult;
import base.utils.jsonresult.XJsonResultFactory;

/**
 * 员工报表
 */
@Controller
@RequestMapping("/userreport/UserReportCtrl/")
public class UserReportController {

	@Autowired
	private UserReportMapper userReportMapper;
	
	
	/**
	 * 查询
	 */
	@RequestMapping(value="search")
	public @ResponseBody XJsonResult search(@RequestBody UserReportForm userReportForm) throws Exception {
		int count = userReportMapper.selectUserReportBeanCount(userReportForm);
		List<UserReportBean> list = userReportMapper.selectUserReportBeanByLimit(userReportForm);
		return XJsonResultFactory.extgrid(list, count);
	}
}