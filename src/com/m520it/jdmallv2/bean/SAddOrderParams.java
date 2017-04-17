package com.m520it.jdmallv2.bean;

import java.util.ArrayList;

public class SAddOrderParams {
	
	private ArrayList<SOerderParamsWithProduct> products;
	private long addrId;
	private int payWay;
	private long userId;
	public ArrayList<SOerderParamsWithProduct> getProducts() {
		return products;
	}
	public void setProducts(ArrayList<SOerderParamsWithProduct> products) {
		this.products = products;
	}
	public long getAddrId() {
		return addrId;
	}
	public void setAddrId(long addrId) {
		this.addrId = addrId;
	}
	public int getPayWay() {
		return payWay;
	}
	public void setPayWay(int payWay) {
		this.payWay = payWay;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	
}
