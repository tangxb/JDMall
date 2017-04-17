package com.m520it.jdmallv2.controller;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmallv2.bean.RResult;
import com.m520it.jdmallv2.cons.IDiyMessage;
import com.m520it.jdmallv2.cons.NetworkConst;
import com.m520it.jdmallv2.util.NetWorkUtil;

import android.content.Context;

public class HomeController extends BaseConttoller{

	public HomeController(Context c) {
		super(c);
	}

	@Override
	protected void handleMessage(int action, Object... values) {
		switch (action) {
		case IDiyMessage.ACTION_GET_AD:
			onModelChanged(IDiyMessage.ACTION_GET_AD_RESULT, getAdBar(1));
			break;
		case IDiyMessage.ACTION_GET_RECOMMEND_AD:
			onModelChanged(IDiyMessage.ACTION_GET_RECOMMEND_AD_RESULT, getAdBar(2));
			break;
		case IDiyMessage.ACTION_GET_SEC_KILL:
			onModelChanged(IDiyMessage.ACTION_GET_SEC_KILL_RESULT, getSeckill());
			break;
		case IDiyMessage.ACTION_GET_RECOMMEND_PRODUCT:
			onModelChanged(IDiyMessage.ACTION_GET_RECOMMEND_PRODUCT_RESULT, getRecommendProduct());
			break;
		}
	}

	private RResult getRecommendProduct() {
		String jsonStr = NetWorkUtil.doGet(NetworkConst.Fav_URL);
		return JSON.parseObject(jsonStr,RResult.class);
	}

	//秒杀模块
	private RResult getSeckill() {
		String jsonStr = NetWorkUtil.doGet(NetworkConst.SECKILL_URL);
		return JSON.parseObject(jsonStr,RResult.class);
	}

	private RResult getAdBar(int type) {
		String jsonStr = NetWorkUtil.doGet(NetworkConst.BANNER_URL+"?adKind="+type);
		return JSON.parseObject(jsonStr, RResult.class);
	}

}
