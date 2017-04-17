package com.m520it.jdmallv2.bean;

import java.io.Serializable;

public class ReceiverAddress implements Serializable{
	
	private static final long serialVersionUID = -3450741728880837116L;
	private long id;
	private boolean isDefault;
	private String receiverName;
	private String receiverPhone;
	private String receiverAddress;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public boolean isDefault() {
		return isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	@Override
	public String toString() {
		return "ReceiverAddress [id=" + id + ", isDefault=" + isDefault
				+ ", receiverName=" + receiverName + ", receiverPhone="
				+ receiverPhone + ", receiverAddress=" + receiverAddress + "]";
	}
	
}
