package com.demo.user;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UserReportEntry extends base.dao.XBaseEntity {

    private Integer id; // ID
    private String name; // 员工名称
    private Integer age; // 年龄

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private java.util.Date birthday; // 员工生日
    private String email; // 邮箱地址
    private Integer departmentId; // 部门ID
    private String departmentName; // 部门名称
    private String phone; // 电话
    private String address; // 地址

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public java.util.Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(java.util.Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return this.departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}