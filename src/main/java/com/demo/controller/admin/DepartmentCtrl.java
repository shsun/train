package com.demo.controller.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import com.demo.dao.entity.DepartmentEty;
import com.demo.dao.mapper.base.DepartmentMapper;

import base.utils.jsonresult.XJsonResult;
import base.utils.jsonresult.XJsonResultFactory;

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
	public @ResponseBody XJsonResult search(@RequestBody DepartmentEty departmentEty) throws Exception {
		int count = departmentMapper.selectLimitCount(departmentEty);
		List<DepartmentEty> list = departmentMapper.selectByLimit(departmentEty);
		return XJsonResultFactory.extgrid(list, count);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(value="save")
	public @ResponseBody XJsonResult save(@RequestBody DepartmentEty departmentEty) throws Exception {
		if(departmentEty.getId() == null) {
			departmentMapper.insert(departmentEty);
		}
		else {
			departmentMapper.updateById(departmentEty);
		}
		return XJsonResultFactory.success();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="delete")
	public @ResponseBody XJsonResult delete(@RequestParam("id") int id) {
		departmentMapper.deleteById(id);
		return XJsonResultFactory.success();
	}
	
	/**
	 * 得到详细信息
	 */
	@RequestMapping(value="getDetailInfo")
	public @ResponseBody XJsonResult getDetailInfo(@RequestParam("id") int id) throws Exception {
		DepartmentEty departmentEty = departmentMapper.selectById(id);
		return XJsonResultFactory.success(departmentEty);
	}
	
}