package com.m520it.jdmallv2.bean;

import java.io.Serializable;

public class ShopCarListBean implements Serializable{
	
	private static final long serialVersionUID = -8103464844873272141L;
	private long id;//购物车
	private int buyCount;
	private int storeId;
	private String storeName;
	private double pprice;
	private String pimageUrl;
	private String pname;
	private long pid;//商品id
	private int stockCount;
	private String pversion;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}
	public int getStoreId() {
		return storeId;
	}
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public double getPprice() {
		return pprice;
	}
	public void setPprice(double pprice) {
		this.pprice = pprice;
	}
	public String getPimageUrl() {
		return pimageUrl;
	}
	public void setPimageUrl(String pimageUrl) {
		this.pimageUrl = pimageUrl;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public long getPid() {
		return pid;
	}
	public void setPid(long pid) {
		this.pid = pid;
	}
	public int getStockCount() {
		return stockCount;
	}
	public void setStockCount(int stockCount) {
		this.stockCount = stockCount;
	}
	public String getPversion() {
		return pversion;
	}
	public void setPversion(String pversion) {
		this.pversion = pversion;
	}
	
	
}
