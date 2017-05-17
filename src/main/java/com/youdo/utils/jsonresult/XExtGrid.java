package com.youdo.utils.jsonresult;

import java.util.List;

public class XExtGrid<T> implements XJsonResult {
	private List<T> invdata;
	private int total;
	
	public XExtGrid() {
	}
	
	public XExtGrid(List<T> invdata) {
		this.invdata = invdata;
		this.total = invdata.size();
	}
	
	public XExtGrid(List<T> invdata, int total) {
		this.invdata = invdata;
		this.total = total;
	}
	
	public List<T> getInvdata() {
		return invdata;
	}
	public void setInvdata(List<T> invdata) {
		this.invdata = invdata;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
}
