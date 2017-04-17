package com.m520it.jdmallv2.bean;

public class SecKillBean {
	
	private String allPrice;
	private String pointPrice;
	private String iconUrl;
	private int timeLeft;
	private int type;//（1抢年货，2超值，3热卖）
	private int productId;
	
	public String getAllPrice() {
		return allPrice;
	}
	public void setAllPrice(String allPrice) {
		this.allPrice = allPrice;
	}
	public String getPointPrice() {
		return pointPrice;
	}
	public void setPointPrice(String pointPrice) {
		this.pointPrice = pointPrice;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public int getTimeLeft() {
		return timeLeft;
	}
	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	@Override
	public String toString() {
		return "SecKillBean [allPrice=" + allPrice + ", pointPrice="
				+ pointPrice + ", iconUrl=" + iconUrl + ", timeLeft="
				+ timeLeft + ", type=" + type + ", productId=" + productId
				+ "]";
	}
	
	
}
