package com.m520it.jdmallv2.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.bean.RResult;
import com.m520it.jdmallv2.cons.IDiyMessage;
import com.m520it.jdmallv2.controller.LoginController;
import com.m520it.jdmallv2.protocol.IModleChangeListener;
import com.m520it.jdmallv2.util.ActivityUtil;

public class RegistActivity extends BaseActivity implements IModleChangeListener {
	
	private EditText mNameEt;
	private EditText mPwdEt;
	private EditText mSurePwdEt;
	private LoginController mController;
	private Handler mHandler=new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case IDiyMessage.ACTION_REGIST_RESULT:
					handlRegistResult((RResult)msg.obj);
					break;
			}
			
			
		}
		
	};
	
	private void handlRegistResult(RResult result) {
		Toast.makeText(this, result.isSuccess()?"注册成功":result.getErrorMsg(), 0).show();
		if (result.isSuccess()) {
			ActivityUtil.start(this, LoginActivity.class);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
		
		initController();
		initUI();
		
	}


	private void initUI() {
		mNameEt=(EditText)findViewById(R.id.name_et);
		mPwdEt=(EditText)findViewById(R.id.pwd_et);
		mSurePwdEt=(EditText)findViewById(R.id.sure_pwd_et);
	}
	
	private void initController() {
		mController=new LoginController(this);
		mController.setIModelChangeListener(this);
	}

	public void registClick(View v){
		String name=mNameEt.getText().toString();
		String pwd=mPwdEt.getText().toString();
		String surePwd=mSurePwdEt.getText().toString();
//		校验
		if (TextUtils.isEmpty(name)||TextUtils.isEmpty(pwd)||TextUtils.isEmpty(surePwd)) {
			Toast.makeText(this, "请输入账号密码", Toast.LENGTH_SHORT).show();
			return;
		}
		if (!pwd.equals(surePwd)) {
			Toast.makeText(this, "两次密码不一致!", Toast.LENGTH_SHORT).show();
			return;
		}
//		做一个注册的请求
		mController.sendAyncMessage(IDiyMessage.ACTION_REGIST, name,pwd);
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action, values[0]).sendToTarget();
	}
	
}
