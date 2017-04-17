package com.m520it.jdmallv2.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmallv2.bean.Brand;
import com.m520it.jdmallv2.bean.RBaseCategory;
import com.m520it.jdmallv2.bean.RProductListBean;
import com.m520it.jdmallv2.bean.RResult;
import com.m520it.jdmallv2.bean.RSubCategory;
import com.m520it.jdmallv2.bean.SProductList;
import com.m520it.jdmallv2.cons.IDiyMessage;
import com.m520it.jdmallv2.cons.NetworkConst;
import com.m520it.jdmallv2.util.NetWorkUtil;

//所有的分类的请求封装在这里
public class CategoryController extends BaseConttoller {

	public CategoryController(Context c) {
		super(c);
	}

	@Override
	protected void handleMessage(int action, Object... values) {
		switch (action) {
		case IDiyMessage.ACTION_TOP_CATEGORY:
			onModelChanged(IDiyMessage.ACTION_TOP_CATEGORY_RESULT,
					getTopCategory());
			break;
		case IDiyMessage.ACTION_SUB_CATEGORY:
			onModelChanged(IDiyMessage.ACTION_SUB_CATEGORY_RESULT,
					getSubCategory((Long) values[0]));
			break;
		case IDiyMessage.ACTION_BRAND_SEARCH:
			onModelChanged(IDiyMessage.ACTION_BRAND_SEARCH_RESULT,
					getBrandSearch((Long) values[0]));
			break;
		case IDiyMessage.ACTION_PRODUCT_LIST:
			onModelChanged(IDiyMessage.ACTION_PRODUCT_LIST_RESULT,
					getProductList((SProductList) values[0]));
			break;
		}
	}

	private List<RProductListBean> getProductList(SProductList sendArgs) {
		try {
			Map<String, String> params = initSearchProductListArgs(sendArgs);
			String jsonStr = NetWorkUtil.doPost(NetworkConst.PRODUCT_LIST_URL, params);
			RResult resultBean=JSON.parseObject(jsonStr, RResult.class);
			if (resultBean.isSuccess()) {
				JSONObject jsonObject=new JSONObject(resultBean.getResult());
				String rowStr = jsonObject.getString("rows");
				return JSON.parseArray(rowStr, RProductListBean.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<RProductListBean>();
	}

	private Map<String, String> initSearchProductListArgs(SProductList sendArgs) {
		Map<String, String> params=new HashMap<String, String>();
		if (!TextUtils.isEmpty(sendArgs.getCategoryId())){
			params.put("categoryId", sendArgs.getCategoryId());
		}
		params.put("filterType", sendArgs.getFilterType()+"");
		if (sendArgs.getSortType()!=0) {
			
			params.put("sortType", sendArgs.getSortType()+"");
		}
		params.put("deliverChoose", sendArgs.getDeliverChoose()+"");
		if (sendArgs.getMinPrice()!=0&&sendArgs.getMaxPrice()!=0) {
			params.put("minPrice", sendArgs.getMinPrice()+"");
			params.put("maxPrice", sendArgs.getMaxPrice()+"");
		}
		if (!TextUtils.isEmpty(sendArgs.getBrandId())) {
			params.put("brandId", sendArgs.getBrandId());
		}
		return params;
	}

	/**
	 * 品牌搜索
	 */
	private List<Brand> getBrandSearch(long cid) {
		String jsonStr = NetWorkUtil.doGet(NetworkConst.BRAND_SEARCH_URL+"?categoryId="+cid);
		RResult resultBean = JSON.parseObject(jsonStr, RResult.class);
		if (resultBean.isSuccess()) {
			return JSON.parseArray(resultBean.getResult(), Brand.class);
		}
		return new ArrayList<Brand>();
	}

	/**
	 * 获取2级分类
	 */
	private List<RSubCategory> getSubCategory(long cid) {
		String jsonStr = NetWorkUtil.doGet(NetworkConst.TOP_CATEGORY_URL
				+ "?parentId=" + cid);
		RResult resultBean = JSON.parseObject(jsonStr, RResult.class);
		if (resultBean.isSuccess()) {
			return JSON.parseArray(resultBean.getResult(), RSubCategory.class);
		}
		return new ArrayList<RSubCategory>();
	}

	/**
	 * 获取一级分类的数据
	 */
	private List<RBaseCategory> getTopCategory() {
		String jsonStr = NetWorkUtil.doGet(NetworkConst.TOP_CATEGORY_URL);
		RResult resultBean = JSON.parseObject(jsonStr, RResult.class);
		if (resultBean.isSuccess()) {
			return JSON.parseArray(resultBean.getResult(), RBaseCategory.class);
		}
		return new ArrayList<RBaseCategory>();
	}

}
