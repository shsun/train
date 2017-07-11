package com.demo.sys;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import base.utils.XSessionUtil;
import base.utils.jsonresult.XJsonResult;
import base.utils.jsonresult.XJsonResultFactory;

@Controller
@RequestMapping("/sys/")
public class AppCtrl {
	
	@RequestMapping(value="login")
	public @ResponseBody XJsonResult login(@RequestBody Map<String, String> paraMap) {
		String userNmae = paraMap.get("username");
		XSessionUtil.login(userNmae);
		return XJsonResultFactory.success();
	}
	
	@RequestMapping(value="logout")
	public String login() {
		XSessionUtil.logout();
		return "redirect:/login";
	}
	
	@RequestMapping("route")
	public @ResponseBody List<Map<String, String>> route(HttpServletRequest request) throws Exception {
		List<Map<String, String>> routeList = Lists.newArrayList();
		Map<String, String> route = null;
	
		route = Maps.newHashMap();
		routeList.add(route);
		route.put("name", "员工管理");
		route.put("ctrl", "StaffCtrl");
		route.put("path", "/admin/staff");
		route.put("templateUrl", request.getContextPath() + "/templates/admin/StaffListTpl.html?v=");
		route.put("files", request.getContextPath() + "/ctrl/admin/StaffCtrl.js");
	
		route = Maps.newHashMap();
		routeList.add(route);
		route.put("name", "部门管理");
		route.put("ctrl", "DepartmentCtrl");
		route.put("path", "/admin/department");
		route.put("templateUrl", request.getContextPath() + "/templates/admin/DepartmentListTpl.html?v=");
		route.put("files", request.getContextPath() + "/ctrl/admin/DepartmentCtrl.js");
	
		route = Maps.newHashMap();
		routeList.add(route);
		route.put("name", "员工报表");
		route.put("ctrl", "UserReportCtrl");
		route.put("path", "/module/UserReport");
		route.put("templateUrl", request.getContextPath() + "/templates/module/UserReportListTpl.html?v=");
		route.put("files", request.getContextPath() + "/ctrl/module/UserReportCtrl.js");
		return routeList;
	}
	
	@RequestMapping("menus")
	public @ResponseBody List<Map<String, Object>> ngMenus() throws Exception {
		List<Map<String, Object>> menuList = Lists.newArrayList();
		Map<String, Object> menu = null;
		Map<String, String> subMenu = null;
		List<Map<String, String>> subMenuList = null;
	
		menu = Maps.newHashMap();
		menuList.add(menu);
		menu.put("name", "后台管理");
		menu.put("type", "toggle");
		menu.put("showTip", "false");
		menu.put("icon", "fa-folder");
		
		subMenuList = Lists.newArrayList();
		menu.put("subMenus", subMenuList);
			
		subMenu = Maps.newHashMap();
		subMenu.put("name", "员工管理");
		subMenu.put("type", "link");
		subMenu.put("showTip", "false");
		subMenu.put("icon", "fa-file-o");
		subMenu.put("path", "/admin/staff");
		subMenuList.add(subMenu);
			
		subMenu = Maps.newHashMap();
		subMenu.put("name", "部门管理");
		subMenu.put("type", "link");
		subMenu.put("showTip", "false");
		subMenu.put("icon", "fa-file-o");
		subMenu.put("path", "/admin/department");
		subMenuList.add(subMenu);
		
	
		menu = Maps.newHashMap();
		menuList.add(menu);
		menu.put("name", "员工报表");
		menu.put("type", "link");
		menu.put("showTip", "false");
		menu.put("icon", "fa-file-o");
		menu.put("path", "/module/UserReport");
		
		
		return menuList;
	}
}
