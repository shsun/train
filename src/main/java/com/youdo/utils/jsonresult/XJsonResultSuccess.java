package com.youdo.utils.jsonresult;

public class XJsonResultSuccess<T> implements XJsonResult {
public T data;
	
	private String result = "success";
	private boolean success = true;
	
	public XJsonResultSuccess(T bean) {
		this.data = bean;
	}
	public XJsonResultSuccess() {
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
}
