package com.demo.department;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

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
	public @ResponseBody XJsonResult search(@RequestBody DepartmentEntry departmentEntry) throws Exception {
		int count = departmentMapper.selectLimitCount(departmentEntry);
		List<DepartmentEntry> list = departmentMapper.selectByLimit(departmentEntry);
		return XJsonResultFactory.extgrid(list, count);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(value="save")
	public @ResponseBody XJsonResult save(@RequestBody DepartmentEntry departmentEntry) throws Exception {
		if(departmentEntry.getId() == null) {
			departmentMapper.insert(departmentEntry);
		}
		else {
			departmentMapper.updateById(departmentEntry);
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
		DepartmentEntry departmentEntry = departmentMapper.selectById(id);
		return XJsonResultFactory.success(departmentEntry);
	}
	
}