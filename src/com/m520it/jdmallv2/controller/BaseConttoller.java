package com.m520it.jdmallv2.controller;

import android.content.Context;

import com.m520it.jdmallv2.protocol.IModleChangeListener;


/**
 * 	总的功能：所有的耗时操作都需要在这个类里面去处理
 */
public abstract class BaseConttoller {
	
	protected Context mContext;

	public BaseConttoller(Context c) {
		this.mContext=c;
	}

	protected IModleChangeListener mListener;

	public void setIModelChangeListener(IModleChangeListener listener) {
		mListener = listener;
	}
	
	protected void onModelChanged(int action,Object... values){
		if (mListener!=null) {
			mListener.onModelChanged(action, values);
		}
	}
	
//	让所有的子类来实现
	protected abstract void handleMessage(int action,Object... values);
	
//	发送一个异步请求
//	action 一个界面可能发送不同的请求过来
//	values 请求的时候携带的请求参数
//	实现了一个多线程
	public void sendAyncMessage(final int action,final Object... values){
		new Thread(){
			public void run() {
//				所有的耗时操作都在这里面运行
				handleMessage(action, values);
			}
		}.start();
	}
	
	public void sendMessage(final int action,final Object... values){
		handleMessage(action, values);
	}
	
	
	
}
