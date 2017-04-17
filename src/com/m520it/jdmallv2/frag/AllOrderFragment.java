package com.m520it.jdmallv2.frag;

import java.util.List;

import me.maxwin.view.XListView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.adapter.AllOrderAdapter;
import com.m520it.jdmallv2.bean.OrderListBean;
import com.m520it.jdmallv2.cons.IDiyMessage;

public class AllOrderFragment extends OrderFragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.frag_all_order, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mController.sendAyncMessage(IDiyMessage.ACTION_GET_ORDER_LIST,-2);
	}
	
	@Override
	public void show(Object... values) {
		mController.sendAyncMessage(IDiyMessage.ACTION_GET_ORDER_LIST,-2);
	}
	
	@Override
	public void onRefresh() {
		mController.sendAyncMessage(IDiyMessage.ACTION_GET_ORDER_LIST, -2);
	}

	@Override
	protected void initHandler() {
		mHandler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
					case IDiyMessage.ACTION_GET_ORDER_LIST_RESULT:
						handleShowLv((List<OrderListBean>) msg.obj);
						break;
				}
			}
		};
	}

	@Override
	protected void initUI() {
		mXListView = (XListView) getActivity().findViewById(
				R.id.all_order_lv);
		mAdapter = new AllOrderAdapter();
		mXListView.setAdapter(mAdapter);
//		1.设置刷新的时间
		mXListView.setRefreshTime(getCurrentTime());
//		2.设置一个监听器 监听下拉的手势
		mXListView.setPullRefreshEnable(true);
		mXListView.setXListViewListener(this);
	}

	
	
}
