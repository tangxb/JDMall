package com.m520it.jdmallv2.ui.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.protocol.IAlipayClickListener;

public class AlipayPopupWindow implements IPopupWindown, OnClickListener {
	
	private PopupWindow mPopWindow;
	private Context mContext;
	private EditText mAccountEt;
	private EditText mPayPwdEt;
	private EditText mPwdEt;
	private IAlipayClickListener mListener;
	
	public void setListener(IAlipayClickListener mListener) {
		this.mListener = mListener;
	}

	public AlipayPopupWindow(Context c) {
		mContext=c;
		initView();
	}
	
	@Override
	public void initView() {
//		1.初始化布局   获取PopWindow内部所有控件
		LayoutInflater inflater=LayoutInflater.from(mContext);
		View contentView = inflater.inflate(R.layout.pay_dialog, null);
//		TODO初始化控件  全局变量
		mAccountEt=(EditText)contentView.findViewById(R.id.account_et);
		mPwdEt=(EditText)contentView.findViewById(R.id.pwd_et);
		mPayPwdEt=(EditText)contentView.findViewById(R.id.pay_pwd_et);
		contentView.findViewById(R.id.cancel_btn).setOnClickListener(this);
		contentView.findViewById(R.id.pay_btn).setOnClickListener(this);
		
//		2.初始化PopWindow
		mPopWindow=new PopupWindow(contentView, -2, -2);
//		内部默认不可点击
		mPopWindow.setFocusable(true);
//		外部又不可以点击了
		mPopWindow.setOutsideTouchable(true);
		mPopWindow.setBackgroundDrawable(new ColorDrawable());
//		刷新界面
		mPopWindow.update();
	}

	@Override
	public void show(View anchor) {
		if (mPopWindow!=null) {
			mPopWindow.showAtLocation(anchor, Gravity.CENTER, 0, 0);
		}
	}

	@Override
	public void dismiss() {
		if (mPopWindow!=null&&mPopWindow.isShowing()) {
			mPopWindow.dismiss();
		}
	}

	@Override
	public void onClick(View arg0) {
		if (arg0.getId()==R.id.cancel_btn) {
			dismiss();
		}else if (arg0.getId()==R.id.pay_btn) {
//			将网络请求交给AC来实现
			String account = mAccountEt.getText().toString().trim();
			String pwd = mPwdEt.getText().toString().trim();
			String payPwd = mPayPwdEt.getText().toString().trim();
			if (mListener!=null) {
				mListener.onPay(account, pwd, payPwd);
			}
		}
	}

}
