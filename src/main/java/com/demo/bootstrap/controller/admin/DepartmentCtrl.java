package com.demo.bootstrap.controller.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import com.ac.util.jsonresult.JsonResult;
import com.ac.util.jsonresult.JsonResultFactory;


import com.demo.bootstrap.dao.entity.DepartmentEty;
import com.demo.bootstrap.dao.mapper.base.DepartmentMapper;

/**
 * 部门管理
 */
@Controller
@RequestMapping("/admin/DepartmentCtrl/")
public class DepartmentCtrl {

	@Autowired
	private DepartmentMapper departmentMapper;
	
	
	/**
	 * 查询
	 */
	@RequestMapping(value="search")
	public @ResponseBody JsonResult search(@RequestBody DepartmentEty departmentEty) throws Exception {
		int count = departmentMapper.selectLimitCount(departmentEty);
		List<DepartmentEty> list = departmentMapper.selectByLimit(departmentEty);
		return JsonResultFactory.extgrid(list, count);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(value="save")
	public @ResponseBody JsonResult save(@RequestBody DepartmentEty departmentEty) throws Exception {
		if(departmentEty.getId() == null) {
			departmentMapper.insert(departmentEty);
		}
		else {
			departmentMapper.updateById(departmentEty);
		}
		return JsonResultFactory.success();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="delete")
	public @ResponseBody JsonResult delete(@RequestParam("id") int id) {
		departmentMapper.deleteById(id);
		return JsonResultFactory.success();
	}
	
	/**
	 * 得到详细信息
	 */
	@RequestMapping(value="getDetailInfo")
	public @ResponseBody JsonResult getDetailInfo(@RequestParam("id") int id) throws Exception {
		DepartmentEty departmentEty = departmentMapper.selectById(id);
		return JsonResultFactory.success(departmentEty);
	}
	
}