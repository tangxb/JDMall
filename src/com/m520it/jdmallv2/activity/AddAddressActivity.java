package com.m520it.jdmallv2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.bean.AddressBean;
import com.m520it.jdmallv2.bean.ReceiverAddress;
import com.m520it.jdmallv2.bean.SAddAddressParams;
import com.m520it.jdmallv2.cons.IDiyMessage;
import com.m520it.jdmallv2.cons.IntentValues;
import com.m520it.jdmallv2.controller.ShopcarController;
import com.m520it.jdmallv2.protocol.IModleChangeListener;
import com.m520it.jdmallv2.protocol.IaddressChooseListener;
import com.m520it.jdmallv2.ui.pop.ChooseAddressPopupWindow;

public class AddAddressActivity extends BaseActivity implements
		OnClickListener, IaddressChooseListener, IModleChangeListener {

	private EditText mNameEt;
	private EditText mPhoneEt;
	private TextView mChooseProvinceTv;
	private EditText mAddressDetailsEt;
	private CheckBox mDefautlCbx;
	private ChooseAddressPopupWindow mPopWindow;
	private View mParentView;
	private ShopcarController mController;
	private Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
//			IDiyMessage.ACTION_ADD_ADDRESS_RESULT
			Toast.makeText(AddAddressActivity.this,
					msg.obj!=null?"添加成功":"添加失败", 0).show();
			if (msg.obj!=null) {
//				将数据传递给 前面的AC
				ReceiverAddress bean=(ReceiverAddress) msg.obj;
				Intent data=new Intent();
				data.putExtra(IntentValues.RETURN_CHOOSE_ADDRESS, bean);
				setResult(SettleActivity.TO_ADD_ADDRESS_RES, data);
				finish();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_address);
		initController();
		initUI();
	}

	private void initController() {
		mController=new ShopcarController(this);
		mController.setIModelChangeListener(this);
	}

	private void initUI() {
		mParentView = findViewById(R.id.parent_view);
		mNameEt = (EditText) findViewById(R.id.name_et);
		mPhoneEt = (EditText) findViewById(R.id.phone_et);
		mChooseProvinceTv = (TextView) findViewById(R.id.choose_province_tv);
		mChooseProvinceTv.setOnClickListener(this);
		mAddressDetailsEt = (EditText) findViewById(R.id.address_details_et);
		mDefautlCbx = (CheckBox) findViewById(R.id.default_cbx);
		// 预加载
		mPopWindow = new ChooseAddressPopupWindow(this);
		mPopWindow.setListener(this);
		// 现在持有mPopWindow 可以获取当前mPopWindow所持有的省市区对象
	}

	public void saveAddress(View v) {
		String name = mNameEt.getText().toString();
		String phone = mPhoneEt.getText().toString();
		String addressDetails = mAddressDetailsEt.getText().toString();
		if (TextUtils.isEmpty(name)||TextUtils.isEmpty(phone)||TextUtils.isEmpty(addressDetails)) {
			Toast.makeText(this, "请添加收货人信息", 0).show();
			return;
		}
		AddressBean province = mPopWindow.mProvince;
		AddressBean city = mPopWindow.mCity;
		AddressBean area = mPopWindow.mArea;
		if (province==null||city==null||area==null) {
			Toast.makeText(this, "请选择省市区", 0).show();
			return;
		}
		
//		发送网络请求 封装
		SAddAddressParams bean=new SAddAddressParams(name, phone, 
				province.getCode(), city.getCode(), area.getCode(), addressDetails, mDefautlCbx.isChecked());
		System.out.println(mDefautlCbx.isChecked());
		mController.sendAyncMessage(IDiyMessage.ACTION_ADD_ADDRESS, bean);
	}

	@Override
	public void onClick(View v) {
		mPopWindow.show(mParentView);
	}

	@Override
	public void onAddressChoose() {
//		修改Tv的文本
		AddressBean province = mPopWindow.mProvince;
		AddressBean city = mPopWindow.mCity;
		AddressBean area = mPopWindow.mArea;
		if (province!=null&&city!=null&&area!=null) {
			mChooseProvinceTv.setText(province.getName()+city.getName()+area.getName());
		}
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action,values[0]).sendToTarget();
	}
}
