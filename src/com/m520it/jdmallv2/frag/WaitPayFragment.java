package com.m520it.jdmallv2.frag;

import java.util.List;

import me.maxwin.view.XListView;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.activity.AlipayActivity;
import com.m520it.jdmallv2.adapter.WaitPayOrderAdapter;
import com.m520it.jdmallv2.bean.OrderListBean;
import com.m520it.jdmallv2.cons.IDiyMessage;
import com.m520it.jdmallv2.cons.IntentValues;
import com.m520it.jdmallv2.protocol.IOrderChangeListener;

public class WaitPayFragment extends OrderFragment implements IOrderChangeListener{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.frag_wait_pay, null);
	}

	@Override
	protected void initHandler() {
		mHandler = new Handler() {

			public void handleMessage(android.os.Message msg) {
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
				R.id.wait_pay_order_lv);
		mAdapter = new WaitPayOrderAdapter();
		((WaitPayOrderAdapter)mAdapter).setListener(this);
		mXListView.setAdapter(mAdapter);
//		1.设置刷新的时间
		mXListView.setRefreshTime(getCurrentTime());
//		2.设置一个监听器 监听下拉的手势
		mXListView.setPullRefreshEnable(true);
		mXListView.setXListViewListener(this);
	}

	@Override
//	用户已经下拉 并且手已经松开了
	public void onRefresh() {
		mController.sendAyncMessage(IDiyMessage.ACTION_GET_ORDER_LIST, 0);
	}

	@Override
	public void show(Object... values) {
		mController.sendAyncMessage(IDiyMessage.ACTION_GET_ORDER_LIST, 0);
	}

	@Override
	public void onChanged(String tn) {
		Intent intent=new Intent(getActivity(),AlipayActivity.class);
		intent.putExtra(IntentValues.TN, tn);
		startActivity(intent);
	}
}
