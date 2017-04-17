package com.m520it.jdmallv2.ui.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.bean.RAddOrderResult;
import com.m520it.jdmallv2.protocol.IAddOrderListener;

public class AddOrderPopupWindow implements IPopupWindown, OnClickListener {
	
	private PopupWindow mPopWindow;
	private Context mContext;
	private TextView mOrderNOtV;
	private TextView mTotalPriceTv;
	private TextView mFreightTv;
	private TextView mActualPriceTv;
	private IAddOrderListener mListener;
	private RAddOrderResult mRAddOrderResult;


	public void setListener(IAddOrderListener listener) {
		mListener=listener;
	}
	
	public AddOrderPopupWindow(Context c) {
		mContext=c;
		initView();
	}
	
	public void setDatas(RAddOrderResult obj) {
		mRAddOrderResult=obj;
		mOrderNOtV.setText("订单编号:"+obj.getOrderNum());
		mTotalPriceTv.setText("总价: ￥"+obj.getAllPrice());
		mFreightTv.setText("运费: ￥"+obj.getFreight());
		mActualPriceTv.setText("实付: ￥"+obj.getTotalPrice());
	}

	@Override
	public void initView() {
//		1.初始化布局   获取PopWindow内部所有控件
		LayoutInflater inflater=LayoutInflater.from(mContext);
		View contentView = inflater.inflate(R.layout.build_order_pop_view, null);
//		TODO初始化控件  全局变量
		mOrderNOtV=(TextView)contentView.findViewById(R.id.order_no_tv);
		mTotalPriceTv=(TextView)contentView.findViewById(R.id.total_price_tv);
		mFreightTv=(TextView)contentView.findViewById(R.id.freight_tv);
		mActualPriceTv=(TextView)contentView.findViewById(R.id.actual_price_tv);
		contentView.findViewById(R.id.cancal_btn).setOnClickListener(this);
		contentView.findViewById(R.id.sure_btn).setOnClickListener(this);
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
		switch (arg0.getId()) {
			case R.id.cancal_btn:
				if (mListener!=null) {
					mListener.onCancle();
				}
				break;
			case R.id.sure_btn:
				if (mListener!=null) {
					mListener.onSure(mRAddOrderResult.getTn());
				}
				break;
		}
	}

}
