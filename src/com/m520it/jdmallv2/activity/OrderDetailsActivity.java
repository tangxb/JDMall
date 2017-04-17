package com.m520it.jdmallv2.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.bean.ROderDetailsBean;
import com.m520it.jdmallv2.bean.ReceiverAddress;
import com.m520it.jdmallv2.cons.IDiyMessage;
import com.m520it.jdmallv2.cons.IntentValues;
import com.m520it.jdmallv2.controller.OrderController;
import com.m520it.jdmallv2.protocol.IModleChangeListener;

public class OrderDetailsActivity extends BaseActivity implements IModleChangeListener {
	
	private OrderController mController;
	private TextView mOrderNOTv;
	private TextView mOrderStatusTv;
	private TextView mReceiveNameTv;
	private TextView mReceiveAddressTv;
	private TextView mReceivePhoneTv;
	private ListView mProductsLv;
	private TextView mActualPriceValTv;
	private long mOid;
	private Handler mHandler=new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case IDiyMessage.ACTION_GET_ORDER_DETAILS_RESULT:
				handleShowOrderDetails(msg.obj);
				break;
			}
		}
		
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_details);
		
		mOid = getIntent().getLongExtra(IntentValues.OID, 0);
		if (mOid==0) {
			Toast.makeText(this, "获取订单明细失败", 0).show();
			finish();
			return ;
		}
		initController();
		initUI();
		mController.sendAyncMessage(IDiyMessage.ACTION_GET_ORDER_DETAILS, mOid);
		
	}

	protected void handleShowOrderDetails(Object obj) {
		if (obj!=null) {
			ROderDetailsBean bean=(ROderDetailsBean) obj;
			mOrderNOTv.setText("订单编号:"+bean.getOrderNum());
			initStatus(mOrderStatusTv, bean.getStatus());
			
			String addressJsonStr=bean.getAddress();
			ReceiverAddress receiverAddress = JSON.parseObject(addressJsonStr,ReceiverAddress.class);
			
			mReceiveNameTv.setText(receiverAddress.getReceiverName());
			mReceivePhoneTv.setText(receiverAddress.getReceiverPhone());
			mReceiveAddressTv.setText(receiverAddress.getReceiverAddress());
			mActualPriceValTv.setText("实付款: ￥"+bean.getTotalPrice());
		}
	}
	
	private void initStatus(TextView orderStatusTv, int status) {
//		状态关联到按钮 根据不同状态 需要不同点击事件 
		switch (status) {
		case -1:
			orderStatusTv.setText("取消订单");
			break;
		case 0:
			orderStatusTv.setText("待支付");
			break;
		case 1:
			orderStatusTv.setText("待发货");
			break;
		case 2:
			orderStatusTv.setText("待收货 ");
			break;
		case 3:
			orderStatusTv.setText("完成交易 ");
			break;
		}
	}

	private void initController() {
		mController=new OrderController(this);
		mController.setIModelChangeListener(this);
	}

	private void initUI() {
		mOrderNOTv = (TextView) findViewById(R.id.order_no_tv);
		mOrderStatusTv = (TextView) findViewById(R.id.status_tv);
		mReceiveNameTv = (TextView) findViewById(R.id.receive_name_tv);
		mReceivePhoneTv = (TextView) findViewById(R.id.receive_phone_tv);
		mReceiveAddressTv = (TextView) findViewById(R.id.receive_address_tv);
//		mProductsLv = (ListView) findViewById(R.id.products_lv);
//		实付款
		mActualPriceValTv = (TextView) findViewById(R.id.actual_price_val_tv);
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action,values[0]).sendToTarget();
	}
	
}
