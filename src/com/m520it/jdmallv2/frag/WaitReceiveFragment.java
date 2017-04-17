package com.m520it.jdmallv2.frag;

import java.util.List;

import me.maxwin.view.XListView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.adapter.WaitReceiveOrderAdapter;
import com.m520it.jdmallv2.bean.OrderListBean;
import com.m520it.jdmallv2.bean.RResult;
import com.m520it.jdmallv2.cons.IDiyMessage;
import com.m520it.jdmallv2.protocol.IOrderChangeListener;

public class WaitReceiveFragment extends OrderFragment implements IOrderChangeListener{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.frag_wait_receive, null);
	}
	
	@Override
	public void onRefresh() {
		mController.sendAyncMessage(IDiyMessage.ACTION_GET_ORDER_LIST, 2);
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
					case IDiyMessage.ACTION_COMFIRM_ORDER_RESULT:
						handleComfirmOrder((RResult) msg.obj);
						break;
				}
			}
		};
	}

	protected void handleComfirmOrder(RResult obj) {
		Toast.makeText(getActivity(), obj.isSuccess()?"确认收货成功":"很抱歉 操作失败", 0).show();
		if (obj.isSuccess()) {
//			如果确认成功了 那么就要刷新界面
			mController.sendAyncMessage(IDiyMessage.ACTION_GET_ORDER_LIST, 2);
		}
	}

	@Override
	protected void initUI() {
		mXListView = (XListView) getActivity().findViewById(
				R.id.wait_receive_order_lv);
		mAdapter = new WaitReceiveOrderAdapter();
		((WaitReceiveOrderAdapter)mAdapter).setListener(this);
		mXListView.setAdapter(mAdapter);
//		1.设置刷新的时间
		mXListView.setRefreshTime(getCurrentTime());
//		2.设置一个监听器 监听下拉的手势
		mXListView.setPullRefreshEnable(true);
		mXListView.setXListViewListener(this);
	}

	@Override
	public void show(Object... values) {
		mController.sendAyncMessage(IDiyMessage.ACTION_GET_ORDER_LIST, 2);
	}

	@Override
	public void onChanged(String oid) {
		mController.sendAyncMessage(IDiyMessage.ACTION_COMFIRM_ORDER,oid);
	}

}
