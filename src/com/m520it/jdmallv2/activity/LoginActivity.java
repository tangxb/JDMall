package com.m520it.jdmallv2.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmallv2.JDApplication;
import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.bean.RLogin;
import com.m520it.jdmallv2.bean.RResult;
import com.m520it.jdmallv2.bean.User;
import com.m520it.jdmallv2.cons.IDiyMessage;
import com.m520it.jdmallv2.controller.LoginController;
import com.m520it.jdmallv2.protocol.IModleChangeListener;
import com.m520it.jdmallv2.util.AESUtils;
import com.m520it.jdmallv2.util.ActivityUtil;

public class LoginActivity extends BaseActivity implements IModleChangeListener {

	private EditText mNameEt;
	private EditText mPwdEt;
	private LoginController mController;
	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
				case IDiyMessage.ACTION_LOGIN_RESULT:
					handleLoginResult(msg);
					break;
				case IDiyMessage.ACTION_GET_USER_RESULT:
					handleGetUserInfo((User)msg.obj);
					break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
//		1.初始化Controller;
		initController();
//		2.初始化控件
		mNameEt = (EditText) findViewById(R.id.name_et);
		mPwdEt = (EditText) findViewById(R.id.pwd_et);
//		3.为控件赋值
		mController.sendAyncMessage(IDiyMessage.ACTION_GET_USER, 0);
	}

	private void handleGetUserInfo(User obj) {
		try {
			if (obj!=null) {
				mNameEt.setText(obj.name);
				mPwdEt.setText(AESUtils.decrypt(obj.pwd));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initController() {
		mController = new LoginController(this);
		// 2.将IModleChangeListener接口传给Controller
		mController.setIModelChangeListener(this);
	}

	public void loginClick(View v) {
		String name = mNameEt.getText().toString();
		String pwd = mPwdEt.getText().toString();
		if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
			Toast.makeText(this, "账号或密码不能为空", 800).show();
			return;
		}
		// 做一个登录操作 耗时操作实现多线程
		// 1.发送一个网络请求
		// 3.返回修改UI效果
			mController.sendAyncMessage(IDiyMessage.ACTION_LOGIN, name, pwd);

	}

	public void registClick(View v) {
		ActivityUtil.start(this, RegistActivity.class);
	}

	public void resetClick(View v) {
		ActivityUtil.start(this, ResetActivity.class);
	}

	@Override
	// 1.实现了回调方法
	// 4.当有数据返回的时候 回调到该方法
	public void onModelChanged(int action, Object... values) {
		// 处理返回的数据的UI操作--子线程
		// 优化的做法 obtainMessage() 从一个池子里面取了一个Message 如果用完了 就扔回到池子里面
		// mHandler.sendMessage(messgae);
		mHandler.obtainMessage(action, values[0]).sendToTarget();
	}

	/**
	 * 处理登录结果
	 */
	private void handleLoginResult(Message msg) {
		
		RResult result = (RResult) msg.obj;
		// 1.登录成功了 提示一下 跳转到主页
		// 2.如果是失败 提示登录的失败的原因
		if (result.isSuccess()) {
			Toast.makeText(LoginActivity.this, "登录成功", 800).show();
//			从RResult取出RLogin对象
			RLogin loginBean = JSON.parseObject(result.getResult(), RLogin.class);
//			1.保存成全局变量
			((JDApplication)getApplication()).mUserInfo=loginBean;
//			2.保存账号密码到数据库
			saveUser2Db();
			ActivityUtil.start(LoginActivity.this, MainActivity.class);
		} else {
			Toast.makeText(LoginActivity.this, result.getErrorMsg(), 800).show();
//			Dialog dialog = new Dialog(this);
//			dialog.show();
		}
	}

	private void saveUser2Db() {
		String name = mNameEt.getText().toString();
		String pwd = mPwdEt.getText().toString();
		mController.sendAyncMessage(IDiyMessage.ACTION_SAVE_USER, name,pwd);
	}

}
