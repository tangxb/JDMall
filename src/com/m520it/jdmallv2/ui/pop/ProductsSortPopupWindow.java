package com.m520it.jdmallv2.ui.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.protocol.IProductSortListener;

public class ProductsSortPopupWindow implements IPopupWindown, OnClickListener {
	
	private PopupWindow mPopWindow;
	private Context mContext;
	private IProductSortListener mListener;
	
	public void setListener(IProductSortListener mListener) {
		this.mListener = mListener;
	}

	public ProductsSortPopupWindow(Context c) {
		mContext=c;
		initView();
	}

	@Override
	public void initView() {
//		1.初始化布局   获取PopWindow内部所有控件
		LayoutInflater inflater=LayoutInflater.from(mContext);
		View contentView = inflater.inflate(R.layout.product_sort_pop_view, null);
		TextView allSortTv=(TextView) contentView.findViewById(R.id.all_sort);
		allSortTv.setOnClickListener(this);
		TextView newSortTv=(TextView) contentView.findViewById(R.id.new_sort);
		newSortTv.setOnClickListener(this);
		TextView commentSortTv=(TextView) contentView.findViewById(R.id.comment_sort);
		commentSortTv.setOnClickListener(this);
//		2.初始化PopWindow
		mPopWindow=new PopupWindow(contentView, -1, -2);
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
			mPopWindow.showAsDropDown(anchor);
		}
	}

	@Override
	public void dismiss() {
		if (mPopWindow!=null&&mPopWindow.isShowing()) {
			mPopWindow.dismiss();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.all_sort:
				if (mListener!=null) {
					mListener.onSortChange(1);
				}
				break;
			case R.id.new_sort:
				if (mListener!=null) {
					mListener.onSortChange(2);
				}
				break;
			case R.id.comment_sort:
				if (mListener!=null) {
					mListener.onSortChange(3);
				}
				break;
		}
	}

}
