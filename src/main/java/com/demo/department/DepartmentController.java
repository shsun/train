package com.demo.department;

import java.util.List;

import com.demo.basic.XBasicController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import base.utils.jsonresult.XJsonResult;
import base.utils.jsonresult.XJsonResultFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin/DepartmentCtrl/")
public class DepartmentController extends XBasicController {

    @Autowired
    private DepartmentMapper departmentMapper;

    @RequestMapping(value = "search")
    public @ResponseBody XJsonResult search(HttpServletRequest request, HttpServletResponse response, @RequestBody DepartmentEntry entry) throws Exception {
        int count = departmentMapper.selectLimitCount(entry);
        List<DepartmentEntry> list = departmentMapper.selectByLimit(entry);
        return XJsonResultFactory.extgrid(list, count);
    }

    @RequestMapping(value = "save")
    public @ResponseBody XJsonResult save(HttpServletRequest request, HttpServletResponse response, @RequestBody DepartmentEntry entry) throws Exception {
        if (entry.getId() == null) {
            departmentMapper.insert(entry);
        } else {
            departmentMapper.updateById(entry);
        }
        return XJsonResultFactory.success();
    }

    @RequestMapping(value = "delete")
    public @ResponseBody XJsonResult delete(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") int id) {
        departmentMapper.deleteById(id);
        return XJsonResultFactory.success();
    }

    @RequestMapping(value = "retrieveOne")
    public @ResponseBody XJsonResult retrieveOne(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") int id) throws Exception {
        DepartmentEntry departmentEntry = departmentMapper.selectById(id);
        return XJsonResultFactory.success(departmentEntry);
    }

}