package com.m520it.jdmallv2.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmallv2.JDApplication;
import com.m520it.jdmallv2.bean.CommentBean;
import com.m520it.jdmallv2.bean.CommentCount;
import com.m520it.jdmallv2.bean.ProductGoodComment;
import com.m520it.jdmallv2.bean.RResult;
import com.m520it.jdmallv2.cons.IDiyMessage;
import com.m520it.jdmallv2.cons.NetworkConst;
import com.m520it.jdmallv2.util.NetWorkUtil;

public class ProductController extends BaseConttoller {
	
	public static final int TYPE_ALL=0;
	public static final int TYPE_GOOD=1;
	public static final int TYPE_CENTER=2;
	public static final int TYPE_BAD=3;
	public static final int TYPE_HASIMG=4;
	
	public ProductController(Context c) {
		super(c);
	}

	@Override
	protected void handleMessage(int action, Object... values) {
		switch (action) {
			case IDiyMessage.ACTION_PRODUCT_INTRODUCE:
				onModelChanged(IDiyMessage.ACTION_PRODUCT_INTRODUCE_RESULT, getProductInfo((Long)values[0]));
				break;
			case IDiyMessage.ACTION_GET_GOOD_RECOMMENT:
				onModelChanged(IDiyMessage.ACTION_GET_GOOD_RECOMMENT_RESULT, getGoodComment((Long)values[0],1));
				break;
			case IDiyMessage.ACTION_COMMENT_COUNT:
				onModelChanged(IDiyMessage.ACTION_COMMENT_COUNT_RESULT, getCommentCount((Long)values[0]));
				break;
			case IDiyMessage.ACTION_GET_COMMENT_BY_TYPE:
				onModelChanged(IDiyMessage.ACTION_GET_COMMENT_BY_TYPE_RESULT,getCommentByType((Long)values[0],(Integer)values[1]));
				break;
			case IDiyMessage.ACTION_ADD_2_SHOPCAR:
				RResult resultBean = add2Shopcar((Long)values[0],(Integer)values[1],(String)values[2]);
				onModelChanged(IDiyMessage.ACTION_ADD_2_SHOPCAR_RESULT,resultBean);
				break;
		}
	}

	private RResult add2Shopcar(long pid, int buyCount, String pversion) {
//		获取Application 需要通过Context
		JDApplication jdApplication=(JDApplication) ((Activity)mContext).getApplication();
		HashMap<String, String> params=new HashMap<String, String>();
		params.put("userId", jdApplication.mUserInfo.getId());
		params.put("productId", pid+"");
		params.put("buyCount", buyCount+"");
		params.put("pversion", pversion);
		String jsonStr = NetWorkUtil.doPost(NetworkConst.ADD_2_SHOPCAR_URL, params);
		return JSON.parseObject(jsonStr,RResult.class);
	}

	private List<CommentBean> getCommentByType(long pid,int type) {
		HashMap<String, String> params=new HashMap<String, String>();
		params.put("productId", pid+"");
		if (type==4) {
			params.put("type", "0");
			params.put("hasImgCom", "true");
		}else {
			params.put("type", type+"");
		}
		String jsonStr = NetWorkUtil.doPost(NetworkConst.PRODUCT_COMMENTDETAIL, params);
		RResult resultBean = JSON.parseObject(jsonStr,RResult.class);
		if (resultBean.isSuccess()) {
			return JSON.parseArray(resultBean.getResult(),CommentBean.class);
		}
		return new ArrayList<CommentBean>();
	}

	private CommentCount getCommentCount(long pid) {
		HashMap<String, String> params=new HashMap<String, String>();
		params.put("productId", pid+"");
		String jsonStr = NetWorkUtil.doPost(NetworkConst.PRODUCT_COMMENTCOUNT, params);
		RResult resultBean = JSON.parseObject(jsonStr,RResult.class);
		if (resultBean.isSuccess()) {
			return JSON.parseObject(resultBean.getResult(), CommentCount.class);
		}
		return null;
	}

	private List<ProductGoodComment> getGoodComment(long pid,int type) {
		HashMap<String, String> params=new HashMap<String, String>();
		params.put("productId", pid+"");
		params.put("type", type+"");
		String jsonStr = NetWorkUtil.doPost(NetworkConst.PRODUCT_GOOD_COMMENT, params);
		RResult resultBean = JSON.parseObject(jsonStr,RResult.class);
		if (resultBean.isSuccess()) {
			return JSON.parseArray(resultBean.getResult(), ProductGoodComment.class);
		}
		return new ArrayList<ProductGoodComment>();
	}

	private RResult getProductInfo(long pid) {
		String jsonStr = NetWorkUtil.doGet(NetworkConst.PRODUCT_INFO_URL+"?id="+pid);
		return JSON.parseObject(jsonStr, RResult.class);
	}

}
