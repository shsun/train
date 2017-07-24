package com.demo.user;

public class UserReportForm extends base.dao.XBaseEntity {
    private Integer id; // ID
    private String name; // 员工名称
    private java.util.Date birthday; // 员工生日

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

    public java.util.Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(java.util.Date birthday) {
        this.birthday = birthday;
    }

}