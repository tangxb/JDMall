package com.m520it.jdmallv2.frag;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;

import com.m520it.jdmallv2.adapter.OrderAdapter;
import com.m520it.jdmallv2.bean.OrderListBean;
import com.m520it.jdmallv2.controller.OrderController;
import com.m520it.jdmallv2.protocol.IModleChangeListener;
import com.m520it.jdmallv2.protocol.IViewContainer;

public abstract class OrderFragment extends Fragment 
		implements IModleChangeListener,IXListViewListener,IViewContainer{
	
	protected OrderController mController;
	protected XListView mXListView;
	protected OrderAdapter mAdapter;
	protected Handler mHandler;

	protected abstract void initHandler();
	protected abstract void initUI();
	
	protected void initController() {
		mController=new OrderController(getActivity());
		mController.setIModelChangeListener(this);
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action,values[0]).sendToTarget();
	}
	
	protected String getCurrentTime(){
		SimpleDateFormat formatter=new SimpleDateFormat("yy-MM-dd hh:mm");
		return formatter.format(new Date());
	}

	@Override
	public void onLoadMore() {
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initHandler();
		initController();
		initUI();
	}
	
	protected void handleShowLv(List<OrderListBean> obj) {
		mAdapter.setDatas(obj);
		mAdapter.notifyDataSetChanged();
//		隐藏头部
		mXListView.stopRefresh();
		mXListView.setRefreshTime(getCurrentTime());
	}
	
}
