package com.demo.bootstrap.dao.entity;

import com.ac.base.dao.BaseEntity;

public class DepartmentEty extends BaseEntity {
	private Integer id;	//部门ID
	private String name;	//部门名称
	private String phone;	//电话
	private String address;	//地址
	/**
	* 得到 部门ID
	* @return Integer
	*/
	public Integer getId() {
		return this.id;
	}
	/**
	 * 设置 部门ID
	 * @param id,  : Integer
	*/
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	* 得到 部门名称
	* @return String
	*/
	public String getName() {
		return this.name;
	}
	/**
	 * 设置 部门名称
	 * @param name,  : String
	*/
	public void setName(String name) {
		this.name = name;
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