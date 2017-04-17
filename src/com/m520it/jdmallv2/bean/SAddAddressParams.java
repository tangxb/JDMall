package com.m520it.jdmallv2.bean;

public class SAddAddressParams {
	
	public String name;
	public String phone;
	public String provinceCode;
	public String cityCode;
	public String distCode;
	public String addressDetails;
	public boolean isDefault;
	public SAddAddressParams(String name, String phone, String provinceCode,
			String cityCode, String distCode, String addressDetails,
			boolean isDefault) {
		this.name = name;
		this.phone = phone;
		this.provinceCode = provinceCode;
		this.cityCode = cityCode;
		this.distCode = distCode;
		this.addressDetails = addressDetails;
		this.isDefault = isDefault;
	}
	
	
}
