package com.m520it.jdmallv2.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmallv2.JDApplication;
import com.m520it.jdmallv2.bean.OrderListBean;
import com.m520it.jdmallv2.bean.ROderDetailsBean;
import com.m520it.jdmallv2.bean.RResult;
import com.m520it.jdmallv2.cons.IDiyMessage;
import com.m520it.jdmallv2.cons.NetworkConst;
import com.m520it.jdmallv2.util.NetWorkUtil;

public class OrderController extends BaseConttoller {

	private String mUserId;

	public OrderController(Context c) {
		super(c);
		JDApplication jdApplication = (JDApplication) ((Activity) c)
				.getApplication();
		mUserId = jdApplication.mUserInfo.getId();
	}

	@Override
	protected void handleMessage(int action, Object... values) {
		switch (action) {
			case IDiyMessage.ACTION_GET_ORDER_LIST:
				onModelChanged(IDiyMessage.ACTION_GET_ORDER_LIST_RESULT,
						loadOrderList((Integer) values[0]));
				break;
			case IDiyMessage.ACTION_COMFIRM_ORDER:
				onModelChanged(IDiyMessage.ACTION_COMFIRM_ORDER_RESULT,
						confirmOrder((String) values[0]));
				break;
			case IDiyMessage.ACTION_GET_ORDER_DETAILS:
				onModelChanged(IDiyMessage.ACTION_GET_ORDER_DETAILS_RESULT,
						getOrderDetails((Long) values[0]));
				break;
		}
	}

	private ROderDetailsBean getOrderDetails(long oid) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userId", mUserId);
		params.put("id", oid+"");
		String jsonStr = NetWorkUtil.doPost(NetworkConst.GETORDERDETAIL_URL, params);
		RResult resultBean = JSON.parseObject(jsonStr,RResult.class);
		if (resultBean.isSuccess()) {
			return JSON.parseObject(resultBean.getResult(),ROderDetailsBean.class);
		}
		return null;
	}

	/**
	 * 	确认收货
	 */
	private RResult confirmOrder(String oid) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userId", mUserId);
		params.put("oid", oid);
		String jsonStr = NetWorkUtil.doPost(NetworkConst.CONFIRMORDER_URL, params);
		return JSON.parseObject(jsonStr, RResult.class);
	}

	private List<OrderListBean> loadOrderList(int status) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userId", mUserId);
		if (status!=-2) {
			params.put("status", status + "");
		}
		String jsonStr = NetWorkUtil
				.doPost(NetworkConst.ORDER_LIST_URL, params);
		System.out.println(jsonStr);
		RResult resultBean = JSON.parseObject(jsonStr, RResult.class);
		if (resultBean.isSuccess()) {
			return JSON.parseArray(resultBean.getResult(), OrderListBean.class);
		}
		return new ArrayList<OrderListBean>();
	}

}
