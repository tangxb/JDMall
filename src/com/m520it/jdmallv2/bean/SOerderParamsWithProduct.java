package com.m520it.jdmallv2.bean;

public class SOerderParamsWithProduct {
	
	private int buyCount;
	private String type;
	private long pid;
	
	public SOerderParamsWithProduct() {
		
	}
	
	public SOerderParamsWithProduct(int buyCount, String type, long pid) {
		this.buyCount = buyCount;
		this.type = type;
		this.pid = pid;
	}
	public int getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getPid() {
		return pid;
	}
	public void setPid(long pid) {
		this.pid = pid;
	}
	
	
}
