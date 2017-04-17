package com.m520it.jdmallv2.bean;

public class ApliayOrderInfo {
	
	private String oinfo;//JD+订单号
	private String tn;
	private String pname;
	private String payTime;
	private double totalPrice;
	public String getOinfo() {
		return oinfo;
	}
	public void setOinfo(String oinfo) {
		this.oinfo = oinfo;
	}
	public String getTn() {
		return tn;
	}
	public void setTn(String tn) {
		this.tn = tn;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
}
