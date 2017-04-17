package com.m520it.jdmallv2.adapter;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.loopj.android.image.SmartImageView;
import com.m520it.jdmallv2.bean.OrderListBean;
import com.m520it.jdmallv2.cons.NetworkConst;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

public abstract class OrderAdapter extends BaseAdapter {

	protected List<OrderListBean> mDatas;
	
	public void setDatas(List<OrderListBean> obj) {
		mDatas=obj;
	}
	
	@Override
	public int getCount() {
		return mDatas!=null?mDatas.size():0;
	}
	
	protected void initProductsContainer(LinearLayout pcontainerLl, String items) {
		List<String> urlPaths = JSON.parseArray(items,String.class);
		int count=pcontainerLl.getChildCount()>urlPaths.size()?urlPaths.size():
			pcontainerLl.getChildCount();
		for (int i = 0; i < count; i++) {
			SmartImageView smiv=(SmartImageView) pcontainerLl.getChildAt(i);
			smiv.setImageUrl(NetworkConst.BASE_URL+urlPaths.get(i));
			smiv.setVisibility(View.VISIBLE);
		}
	}
	
}
