package com.demo.bootstrap.controller.userreport;
public class UserReportForm extends com.ac.base.dao.BaseEntity {
	private Integer id;	//ID
	private String name;	//员工名称
	private java.util.Date birthday;	//员工生日

	/**
	* 得到 ID
	* @return Integer
	*/
	public Integer getId() {
		return this.id;
	}
	/**
	 * 设置 ID
	 * @param id,  : Integer
	*/
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	* 得到 员工名称
	* @return String
	*/
	public String getName() {
		return this.name;
	}
	/**
	 * 设置 员工名称
	 * @param name,  : String
	*/
	public void setName(String name) {
		this.name = name;
	}

	/**
	* 得到 员工生日
	* @return java.util.Date
	*/
	public java.util.Date getBirthday() {
		return this.birthday;
	}
	/**
	 * 设置 员工生日
	 * @param birthday,  : java.util.Date
	*/
	public void setBirthday(java.util.Date birthday) {
		this.birthday = birthday;
	}

}