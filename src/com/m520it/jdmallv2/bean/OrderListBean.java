package com.m520it.jdmallv2.bean;

public class OrderListBean {
	
	private String items;
	private long oid;
	private String orderNum;
	private boolean paid;
	private int status;
	private String tn;
	private double totalPrice;
	public String getItems() {
		return items;
	}
	public void setItems(String items) {
		this.items = items;
	}
	public long getOid() {
		return oid;
	}
	public void setOid(long oid) {
		this.oid = oid;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getTn() {
		return tn;
	}
	public void setTn(String tn) {
		this.tn = tn;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	@Override
	public String toString() {
		return "OrderListBean [items=" + items + ", oid=" + oid + ", orderNum="
				+ orderNum + ", paid=" + paid + ", status=" + status + ", tn="
				+ tn + ", totalPrice=" + totalPrice + "]";
	}
	
	
	
}
