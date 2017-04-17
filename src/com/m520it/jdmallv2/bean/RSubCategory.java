package com.m520it.jdmallv2.bean;

import java.util.List;

public class RSubCategory {
	
	private long id;
	private String name;
	private List<RBaseCategory> thirdCategory;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<RBaseCategory> getThirdCategory() {
		return thirdCategory;
	}
	public void setThirdCategory(List<RBaseCategory> thirdCategory) {
		this.thirdCategory = thirdCategory;
	}
	@Override
	public String toString() {
		return "RSubCategory [id=" + id + ", name=" + name + ", thirdCategory="
				+ thirdCategory + "]";
	}
	
}
