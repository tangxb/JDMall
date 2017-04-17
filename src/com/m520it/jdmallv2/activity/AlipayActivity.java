package com.m520it.jdmallv2.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.bean.ApliayOrderInfo;
import com.m520it.jdmallv2.bean.RResult;
import com.m520it.jdmallv2.cons.IDiyMessage;
import com.m520it.jdmallv2.cons.IntentValues;
import com.m520it.jdmallv2.controller.AlipayController;
import com.m520it.jdmallv2.protocol.IAlipayClickListener;
import com.m520it.jdmallv2.protocol.IModleChangeListener;
import com.m520it.jdmallv2.ui.pop.AlipayPopupWindow;

public class AlipayActivity extends BaseActivity implements
		IModleChangeListener, IAlipayClickListener {

	private TextView mPayMoneyTv;
	private TextView mOrderDescValTv;
	private TextView mDealTypeValTv;
	private TextView mDealNOTv;
	private TextView mDealTimeTv;
	private AlipayPopupWindow mAlipayPopupWindow;
	private String mTn;
	private AlipayController mController;
	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case IDiyMessage.ACTION_GET_ALIPAY_INFO_RESULT:
				handleShowAlipayInfo(msg.obj);
				break;
			case IDiyMessage.ACTION_START_ALIPAY_RESULT:
				handleStartAlipay((RResult) msg.obj);
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alipay);
		// 拿到TN
		mTn = getIntent().getStringExtra(IntentValues.TN);
		if (mTn == null) {
			Toast.makeText(this, "系统异常", 0).show();
			finish();
			return;
		}
		initController();
		// 初始化控件
		initUI();
		// 发送网络请求
		mController.sendAyncMessage(IDiyMessage.ACTION_GET_ALIPAY_INFO, mTn);
		// 返回数据绑定到控件里面
	}

	protected void handleStartAlipay(RResult obj) {
		if (obj.isSuccess()) {
			try {
				// 跳转到新的AC 订单详情
				Toast.makeText(this, "支付成功 等待商家发货", 0).show();
				JSONObject jsonObject = new JSONObject(obj.getResult());
				Intent intent = new Intent(this, OrderDetailsActivity.class);
				intent.putExtra(IntentValues.OID, jsonObject.getLong("oid"));
				startActivity(intent);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			Toast.makeText(this, "支付失败 请去订单列表再次支付", 0).show();
		}
		finish();
	}

	protected void handleShowAlipayInfo(Object obj) {
		if (obj != null) {
			ApliayOrderInfo bean = (ApliayOrderInfo) obj;
			mPayMoneyTv.setText("￥" + bean.getTotalPrice());
			mOrderDescValTv.setText(bean.getPname());
			mDealTypeValTv.setText("担保交易");
			mDealTimeTv.setText(bean.getPayTime());
			mDealNOTv.setText(bean.getTn());
		} else {
			Toast.makeText(this, "系统异常", 0).show();
			finish();
			return;
		}
	}

	private void initUI() {
		mPayMoneyTv = (TextView) findViewById(R.id.pay_price_tv);
		mOrderDescValTv = (TextView) findViewById(R.id.order_desc_val_tv);
		mDealTypeValTv = (TextView) findViewById(R.id.deal_type_val_tv);
		mDealTimeTv = (TextView) findViewById(R.id.deal_time_tv);
		mDealNOTv = (TextView) findViewById(R.id.deal_no_val_tv);
	}

	private void initController() {
		mController = new AlipayController(this);
		mController.setIModelChangeListener(this);
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action, values[0]).sendToTarget();
	}

	public void payClick(View v) {
		// 弹出一个支付宝登录框 如果登录成功 就可以扣钱了。
		if (mAlipayPopupWindow == null) {
			mAlipayPopupWindow = new AlipayPopupWindow(this);
			mAlipayPopupWindow.setListener(this);
		}
		mAlipayPopupWindow.show(findViewById(R.id.container));
	}

	@Override
	public void onPay(String account, String pwd, String payPwd) {
		mAlipayPopupWindow.dismiss();
		mController.sendAyncMessage(IDiyMessage.ACTION_START_ALIPAY, account,
				pwd, payPwd, mTn);
	}

}
