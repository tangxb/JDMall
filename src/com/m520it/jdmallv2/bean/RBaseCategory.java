package com.m520it.jdmallv2.bean;

public class RBaseCategory {
	
	private long id;
	private String bannerUrl;
	private String name;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getBannerUrl() {
		return bannerUrl;
	}
	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "RBaseCategory [id=" + id + ", bannerUrl=" + bannerUrl
				+ ", name=" + name + "]";
	}
	
	
}
