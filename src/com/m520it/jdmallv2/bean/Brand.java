package com.m520it.jdmallv2.bean;

public class Brand {
	
	private long id;
	private String name;
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
	@Override
	public String toString() {
		return "Brand [id=" + id + ", name=" + name + "]";
	}
	
	
}
