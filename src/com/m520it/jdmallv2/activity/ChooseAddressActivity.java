package com.m520it.jdmallv2.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.adapter.ChooseAddressAdapter;
import com.m520it.jdmallv2.bean.RResult;
import com.m520it.jdmallv2.bean.ReceiverAddress;
import com.m520it.jdmallv2.cons.IDiyMessage;
import com.m520it.jdmallv2.cons.IntentValues;
import com.m520it.jdmallv2.controller.ShopcarController;
import com.m520it.jdmallv2.protocol.IAddressDeleteListener;
import com.m520it.jdmallv2.protocol.IModleChangeListener;

public class ChooseAddressActivity extends BaseActivity 
			implements IModleChangeListener, OnItemClickListener,IAddressDeleteListener {
	
	private ListView mAddressLv;
	private ChooseAddressAdapter mAddressAdapter;
	private ShopcarController mController;
	private Handler mHandler=new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case IDiyMessage.ACTION_CHOOSE_ADDRESS_RESULT:
				handleShowAddresses((List<ReceiverAddress>)msg.obj);
				break;
			case IDiyMessage.ACTION_DELETE_ADDRESS_RESULT:
				handleDeleteAddress((RResult)msg.obj);
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_address);
		initController();
		initUI();
		mController.sendAyncMessage(IDiyMessage.ACTION_CHOOSE_ADDRESS, 0);
	}

	protected void handleDeleteAddress(RResult obj) {
		if (obj.isSuccess()) {
//			发送一个网络请求刷新界面
			mController.sendAyncMessage(IDiyMessage.ACTION_CHOOSE_ADDRESS, 0);
			Toast.makeText(this,"删除收货人地址成功", 0).show();
		}else {
			Toast.makeText(this,"删除收货人地址失败", 0).show();
		}
	}

	protected void handleShowAddresses(List<ReceiverAddress> obj) {
		mAddressAdapter.setDatas(obj);
		mAddressAdapter.notifyDataSetChanged();
	}

	private void initController() {
		mController=new ShopcarController(this);
		mController.setIModelChangeListener(this);
	}

	private void initUI() {
		mAddressLv=(ListView)findViewById(R.id.lv);
		mAddressAdapter=new ChooseAddressAdapter();
		mAddressAdapter.setListener(this);
		mAddressLv.setAdapter(mAddressAdapter);
		mAddressLv.setOnItemClickListener(this);
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action,values[0]).sendToTarget();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent=new Intent();
		ReceiverAddress bean = (ReceiverAddress) mAddressAdapter.getItem(position);
		intent.putExtra(IntentValues.RETURN_CHOOSE_ADDRESS, bean);
		setResult(SettleActivity.TO_CHOOSE_ADDRESS_RES, intent);
		finish();
	}

	@Override
	public void onDetele(long id) {
		mController.sendAyncMessage(IDiyMessage.ACTION_DELETE_ADDRESS, id);
	}
	
}
