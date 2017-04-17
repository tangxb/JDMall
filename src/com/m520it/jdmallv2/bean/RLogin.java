package com.m520it.jdmallv2.bean;

public class RLogin {
	
	private String id;
	private String userName;
	private String userIcon;
	private int waitPayCount;
	private int waitReceiveCount;
	private int userLevel;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserIcon() {
		return userIcon;
	}
	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}
	public int getWaitPayCount() {
		return waitPayCount;
	}
	public void setWaitPayCount(int waitPayCount) {
		this.waitPayCount = waitPayCount;
	}
	public int getWaitReceiveCount() {
		return waitReceiveCount;
	}
	public void setWaitReceiveCount(int waitReceiveCount) {
		this.waitReceiveCount = waitReceiveCount;
	}
	public int getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(int userLevel) {
		this.userLevel = userLevel;
	}
	
	
}
