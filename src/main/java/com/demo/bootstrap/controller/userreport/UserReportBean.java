package com.demo.bootstrap.controller.userreport;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UserReportBean extends com.ac.base.dao.BaseEntity {
	private Integer id;	//ID
	private String name;	//员工名称
	private Integer age;	//年龄

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private java.util.Date birthday;	//员工生日
	private String email;	//邮箱地址
	private Integer departmentId;	//部门ID
	private String departmentName;	//部门名称
	private String phone;	//电话
	private String address;	//地址

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
	* 得到 年龄
	* @return Integer
	*/
	public Integer getAge() {
		return this.age;
	}
	/**
	 * 设置 年龄
	 * @param age,  : Integer
	*/
	public void setAge(Integer age) {
		this.age = age;
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

	/**
	* 得到 邮箱地址
	* @return String
	*/
	public String getEmail() {
		return this.email;
	}
	/**
	 * 设置 邮箱地址
	 * @param email,  : String
	*/
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	* 得到 部门ID
	* @return Integer
	*/
	public Integer getDepartmentId() {
		return this.departmentId;
	}
	/**
	 * 设置 部门ID
	 * @param departmentId,  : Integer
	*/
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	/**
	* 得到 部门名称
	* @return String
	*/
	public String getDepartmentName() {
		return this.departmentName;
	}
	/**
	 * 设置 部门名称
	 * @param departmentName,  : String
	*/
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	* 得到 电话
	* @return String
	*/
	public String getPhone() {
		return this.phone;
	}
	/**
	 * 设置 电话
	 * @param phone,  : String
	*/
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	* 得到 地址
	* @return String
	*/
	public String getAddress() {
		return this.address;
	}
	/**
	 * 设置 地址
	 * @param address,  : String
	*/
	public void setAddress(String address) {
		this.address = address;
	}

}