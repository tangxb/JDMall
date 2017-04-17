package com.m520it.jdmallv2.bean;

public class ProductInfoBean {
	
	private long id;
	private String imgUrls;//封装了所有图片地址的JSON
	private double price;
	private boolean ifSaleOneself;
	private String name;
	private String recomProductId;
	private String recomProduct;
	private int stockCount;
	private int commentCount;
	private String typeList;//封装了所有版本信息的的JSON
	private int favcomRate;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getImgUrls() {
		return imgUrls;
	}
	public void setImgUrls(String imgUrls) {
		this.imgUrls = imgUrls;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public boolean isIfSaleOneself() {
		return ifSaleOneself;
	}
	public void setIfSaleOneself(boolean ifSaleOneself) {
		this.ifSaleOneself = ifSaleOneself;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRecomProductId() {
		return recomProductId;
	}
	public void setRecomProductId(String recomProductId) {
		this.recomProductId = recomProductId;
	}
	public String getRecomProduct() {
		return recomProduct;
	}
	public void setRecomProduct(String recomProduct) {
		this.recomProduct = recomProduct;
	}
	public int getStockCount() {
		return stockCount;
	}
	public void setStockCount(int stockCount) {
		this.stockCount = stockCount;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public String getTypeList() {
		return typeList;
	}
	public void setTypeList(String typeList) {
		this.typeList = typeList;
	}
	public int getFavcomRate() {
		return favcomRate;
	}
	public void setFavcomRate(int favcomRate) {
		this.favcomRate = favcomRate;
	}
	
}
