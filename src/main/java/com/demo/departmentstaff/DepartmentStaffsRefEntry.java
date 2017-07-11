package com.demo.departmentstaff;

import base.dao.XBaseEntity;

public class DepartmentStaffsRefEntry extends XBaseEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer iD;	//ID
	private Integer departmentId;	//客户ID
	private Integer staffId;	//用户ID
	/**
	* 得到 ID
	* @return Integer
	*/
	public Integer getID() {
		return this.iD;
	}
	/**
	 * 设置 ID
	 * @param iD,  : Integer
	*/
	public void setID(Integer iD) {
		this.iD = iD;
	}

	/**
	* 得到 客户ID
	* @return Integer
	*/
	public Integer getDepartmentId() {
		return this.departmentId;
	}
	/**
	 * 设置 客户ID
	 * @param departmentId,  : Integer
	*/
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	/**
	* 得到 用户ID
	* @return Integer
	*/
	public Integer getStaffId() {
		return this.staffId;
	}
	/**
	 * 设置 用户ID
	 * @param staffId,  : Integer
	*/
	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

}