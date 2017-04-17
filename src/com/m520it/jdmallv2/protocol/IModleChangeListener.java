package com.m520it.jdmallv2.protocol;

public interface IModleChangeListener {

//	当数据加载完毕的时候 回调下面的方法
	public void onModelChanged(int action,Object... values);
	
}
