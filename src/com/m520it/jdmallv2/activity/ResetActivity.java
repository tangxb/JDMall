package com.m520it.jdmallv2.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.bean.RResult;
import com.m520it.jdmallv2.cons.IDiyMessage;
import com.m520it.jdmallv2.controller.LoginController;
import com.m520it.jdmallv2.protocol.IModleChangeListener;
import com.m520it.jdmallv2.util.ActivityUtil;

public class ResetActivity extends BaseActivity implements IModleChangeListener {

	private EditText mNameEt;
	private LoginController mController;
	private Handler mHandler=new Handler(){
		
		public void handleMessage(android.os.Message msg) {
//			判断不同的处理UI的请求
			switch (msg.what) {
				case IDiyMessage.ACTION_RESET_RESULT:
					handleResetResult((RResult)msg.obj);
					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset);
		
		initController();
		mNameEt=(EditText)findViewById(R.id.name_et);
	}
	
	private void handleResetResult(RResult result) {
		Toast.makeText(this, result.isSuccess()?"当前的密码为:123456":result.getErrorMsg(), 0).show();
		if (result.isSuccess()) {
			ActivityUtil.start(this, LoginActivity.class);
		}
	}

	private void initController() {
		mController=new LoginController(this);
		mController.setIModelChangeListener(this);
	}

	public void resetClick(View v){
		String name=mNameEt.getText().toString();
//		TODO 忽略判断
		mController.sendAyncMessage(IDiyMessage.ACTION_RESET, name);
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action, values[0]).sendToTarget();
	}
	
}
