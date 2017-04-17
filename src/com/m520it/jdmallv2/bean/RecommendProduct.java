package com.m520it.jdmallv2.bean;

public class RecommendProduct {
	
	private double price;//一般使用Double
	private String name;
	private String iconUrl;
	private long productId;
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	@Override
	public String toString() {
		return "RecommendProduct [price=" + price + ", name=" + name
				+ ", iconUrl=" + iconUrl + ", productId=" + productId + "]";
	}
	
	
}
