package com.m520it.jdmallv2.bean;

public class AdBean {
	//adUrl  截取的图片子地址  /image03.png
	//每个广告都有一种类型type，他是根据当前的类型 点击的时候判断类型 如果1跳转到网页
	//这时候获取网页的地址  2.跳转到商品详情 id就是商品的id 3.如果是分类 id就是分类的id
	private int id;
	private int type;	//1跳转到网页，2跳转到商品详情，3跳转到分类去
	private String adUrl;	//图片的地址
	private String webUrl;	//跳转的网页地址
	private int adKind;	//1为导航banner，2为广告banner
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getAdUrl() {
		return adUrl;
	}
	public void setAdUrl(String adUrl) {
		this.adUrl = adUrl;
	}
	public String getWebUrl() {
		return webUrl;
	}
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
	public int getAdKind() {
		return adKind;
	}
	public void setAdKind(int adKind) {
		this.adKind = adKind;
	}
	
	
	
	
}
