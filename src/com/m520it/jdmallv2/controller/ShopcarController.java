package com.m520it.jdmallv2.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmallv2.JDApplication;
import com.m520it.jdmallv2.bean.AddressBean;
import com.m520it.jdmallv2.bean.RAddOrderResult;
import com.m520it.jdmallv2.bean.RResult;
import com.m520it.jdmallv2.bean.ReceiverAddress;
import com.m520it.jdmallv2.bean.SAddAddressParams;
import com.m520it.jdmallv2.bean.ShopCarListBean;
import com.m520it.jdmallv2.cons.IDiyMessage;
import com.m520it.jdmallv2.cons.NetworkConst;
import com.m520it.jdmallv2.util.NetWorkUtil;

public class ShopcarController extends BaseConttoller {

	private String mUserId;

	public ShopcarController(Context c) {
		super(c);
		JDApplication jdApplication=(JDApplication) ((Activity)c).getApplication();
		mUserId=jdApplication.mUserInfo.getId();
	}

	@Override
	protected void handleMessage(int action, Object... values) {
		switch (action) {
			case IDiyMessage.ACTION_SHOW_SHOPCAR_LIST:
				onModelChanged(IDiyMessage.ACTION_SHOW_SHOPCAR_LIST_RESULT, loadShopCarList());
				break;
			case IDiyMessage.ACTION_DELETE_SHOPCAR_ITEM:
				onModelChanged(IDiyMessage.ACTION_DELETE_SHOPCAR_ITEM_RESULT, deleteShopcarItem((Long)values[0]));
				break;
			case IDiyMessage.ACTION_GET_DEFAULT_RECEIVER_ADDRESS:
				onModelChanged(IDiyMessage.ACTION_GET_DEFAULT_RECEIVER_ADDRESS_RESULT, getDefaultReceiverAddress());
				break;
			case IDiyMessage.ACTION_CHOOSE_ADDRESS:
				onModelChanged(IDiyMessage.ACTION_CHOOSE_ADDRESS_RESULT, getReceiverAddresses());
				break;
			case IDiyMessage.ACTION_DELETE_ADDRESS:
				onModelChanged(IDiyMessage.ACTION_DELETE_ADDRESS_RESULT, deleteReceiverAddress((Long)values[0]));
				break;
			case IDiyMessage.ACTION_PROVINCE:
				onModelChanged(IDiyMessage.ACTION_PROVINCE_RESULT, getProvinceData());
				break;
			case IDiyMessage.ACTION_CITY:
				onModelChanged(IDiyMessage.ACTION_CITY_RESULT, getCityData((String)values[0]));
				break;
			case IDiyMessage.ACTION_DIST:
				onModelChanged(IDiyMessage.ACTION_DIST_RESULT, getAreaData((String)values[0]));
				break;
			case IDiyMessage.ACTION_ADD_ADDRESS:
				onModelChanged(IDiyMessage.ACTION_ADD_ADDRESS_RESULT, addAddress((SAddAddressParams)values[0]));
				break;
			case IDiyMessage.ACTION_ADD_ORDER:
				onModelChanged(IDiyMessage.ACTION_ADD_ORDER_RESULT, addOrder((String)values[0]));
				break;
			case IDiyMessage.ACTION_ADD_ORDER_ONLINE:
				onModelChanged(IDiyMessage.ACTION_ADD_ORDER_ONLINE_RESULT, addOrderOnline((String)values[0]));
				break;
			
		}
	}
	
	private RAddOrderResult addOrderOnline(String paramsStr) {
		HashMap<String, String> params=new HashMap<String, String>();
		params.put("detail", paramsStr);
		String jsonStr = NetWorkUtil.doPost(NetworkConst.ADDORDER_URL, params);
		RResult resultBean = JSON.parseObject(jsonStr,RResult.class);
		if (resultBean.isSuccess()) {
			return JSON.parseObject(resultBean.getResult(),RAddOrderResult.class);
		}
		return null;
	}

	private RResult addOrder(String paramsStr) {
		HashMap<String, String> params=new HashMap<String, String>();
		params.put("detail", paramsStr);
		String jsonStr = NetWorkUtil.doPost(NetworkConst.ADDORDER_URL, params);
		return JSON.parseObject(jsonStr,RResult.class);
	}

	private ReceiverAddress addAddress(SAddAddressParams bean) {
		HashMap<String, String> params=new HashMap<String, String>();
		params.put("userId", mUserId);
		params.put("name", bean.name);
		params.put("phone", bean.phone);
		params.put("provinceCode", bean.provinceCode);
		params.put("cityCode", bean.cityCode);
		params.put("distCode", bean.distCode);
		params.put("addressDetails", bean.addressDetails);
		params.put("isDefault", bean.isDefault+"");
		String jsonStr = NetWorkUtil.doPost(NetworkConst.ADD_ADDRESS_URL, params);
		RResult resultBean = JSON.parseObject(jsonStr,RResult.class);
		if (resultBean.isSuccess()) {
			return JSON.parseObject(resultBean.getResult(),ReceiverAddress.class);
		}
		return null;
	}

	private List<AddressBean> getAreaData(String fcode) {
		String jsonStr = NetWorkUtil.doGet(NetworkConst.GET_AREA_URL+"?fcode="+fcode);
		RResult resultBean=JSON.parseObject(jsonStr,RResult.class);
		if (resultBean.isSuccess()) {
			return JSON.parseArray(resultBean.getResult(),AddressBean.class);
		}
		return new ArrayList<AddressBean>();
	}

	private List<AddressBean> getCityData(String fcode) {
		String jsonStr = NetWorkUtil.doGet(NetworkConst.GET_CITY_URL+"?fcode="+fcode);
		RResult resultBean=JSON.parseObject(jsonStr,RResult.class);
		if (resultBean.isSuccess()) {
			return JSON.parseArray(resultBean.getResult(),AddressBean.class);
		}
		return new ArrayList<AddressBean>();
	}

	private List<AddressBean> getProvinceData() {
		String jsonStr = NetWorkUtil.doGet(NetworkConst.GET_PROVINCE_URL);
		RResult resultBean=JSON.parseObject(jsonStr,RResult.class);
		if (resultBean.isSuccess()) {
			return JSON.parseArray(resultBean.getResult(),AddressBean.class);
		}
		return new ArrayList<AddressBean>();
	}

	private RResult deleteReceiverAddress(long id) {
		HashMap<String, String> params=new HashMap<String, String>();
		params.put("userId", mUserId);
		params.put("id", id+"");
		String jsonStr = NetWorkUtil.doPost(NetworkConst.DELETE_RECEIVER_ADDRESS_URL, params);
		return JSON.parseObject(jsonStr,RResult.class);
	}

	private List<ReceiverAddress> getReceiverAddresses() {
		HashMap<String, String> params=new HashMap<String, String>();
		params.put("userId", mUserId);
		String jsonStr = NetWorkUtil.doPost(NetworkConst.DEFAULT_RECEIVER_ADDRESS_URL, params);
		RResult resultBean = JSON.parseObject(jsonStr,RResult.class);
		if (resultBean.isSuccess()) {
			return JSON.parseArray(resultBean.getResult(),ReceiverAddress.class);
		}
		return new ArrayList<ReceiverAddress>();
	}

	private List<ReceiverAddress> getDefaultReceiverAddress() {
		HashMap<String, String> params=new HashMap<String, String>();
		params.put("userId", mUserId);
		params.put("isDefault", true+"");
		String jsonStr = NetWorkUtil.doPost(NetworkConst.DEFAULT_RECEIVER_ADDRESS_URL, params);
		RResult resultBean = JSON.parseObject(jsonStr,RResult.class);
		if (resultBean.isSuccess()) {
			return JSON.parseArray(resultBean.getResult(),ReceiverAddress.class);
		}
		return new ArrayList<ReceiverAddress>();
	}

	private RResult deleteShopcarItem(long id) {
		HashMap<String, String> params=new HashMap<String, String>();
		params.put("userId", mUserId);
		params.put("id", id+"");
		String jsonStr = NetWorkUtil.doPost(NetworkConst.DELETE_SHOPCAR_ITEM_URL, params);
		return JSON.parseObject(jsonStr,RResult.class);
	}

	private List<ShopCarListBean> loadShopCarList() {
		HashMap<String, String> params=new HashMap<String, String>();
		params.put("userId", mUserId);
		String jsonStr = NetWorkUtil.doPost(NetworkConst.SHOPCAR_LIST_URL, params);
		RResult resultBean = JSON.parseObject(jsonStr,RResult.class);
		if (resultBean.isSuccess()) {
			return JSON.parseArray(resultBean.getResult(),ShopCarListBean.class);
		}
		return new ArrayList<ShopCarListBean>();
	}

}
