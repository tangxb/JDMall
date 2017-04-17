package com.m520it.jdmallv2.controller;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmallv2.JDApplication;
import com.m520it.jdmallv2.bean.ApliayOrderInfo;
import com.m520it.jdmallv2.bean.RResult;
import com.m520it.jdmallv2.cons.IDiyMessage;
import com.m520it.jdmallv2.cons.NetworkConst;
import com.m520it.jdmallv2.util.NetWorkUtil;

public class AlipayController extends BaseConttoller {

	private String mUserId;

	public AlipayController(Context c) {
		super(c);
		JDApplication jdApplication=(JDApplication) ((Activity)c).getApplication();
		mUserId=jdApplication.mUserInfo.getId();
	}

	@Override
	protected void handleMessage(int action, Object... values) {
		switch (action) {
		case IDiyMessage.ACTION_GET_ALIPAY_INFO:
			onModelChanged(IDiyMessage.ACTION_GET_ALIPAY_INFO_RESULT, loadAlipayInfo((String)values[0]));
			break;
		case IDiyMessage.ACTION_START_ALIPAY:
			onModelChanged(IDiyMessage.ACTION_START_ALIPAY_RESULT,
					startAlipay((String)values[0],(String)values[1],(String)values[2],(String)values[3]));
			break;
		}
	}

	private RResult startAlipay(String account, String pwd, String paypwd,String tn) {
		HashMap<String, String> params=new HashMap<String, String>();
		params.put("account", account);
		params.put("apwd", pwd);
		params.put("ppwd", paypwd);
		params.put("tn", tn);
		params.put("userId", mUserId);
		String jsonStr = NetWorkUtil.doPost(NetworkConst.PAY_URL, params);
		return JSON.parseObject(jsonStr,RResult.class);
	}

	private ApliayOrderInfo loadAlipayInfo(String tn) {
		HashMap<String, String> params=new HashMap<String, String>();
		params.put("userId", mUserId);
		params.put("tn", tn);
		String jsonStr = NetWorkUtil.doPost(NetworkConst.GETPAYINFO_URL, params);
		System.out.println(jsonStr);
		RResult resultBean = JSON.parseObject(jsonStr,RResult.class);
		if (resultBean.isSuccess()) {
			return JSON.parseObject(resultBean.getResult(),ApliayOrderInfo.class);
		}
		return null;
	}

}
