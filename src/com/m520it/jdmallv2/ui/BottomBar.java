package com.m520it.jdmallv2.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.protocol.IBottomItemClickListener;

public class BottomBar extends LinearLayout implements OnClickListener {

	private TextView mHomeTv;
	private TextView mCategoryTv;
	private TextView mShopcarTv;
	private TextView mMineTv;
	private ImageView mHomeIv;
	private ImageView mCategoryIv;
	private ImageView mShopcarIv;
	private ImageView mMineIv;
	private IBottomItemClickListener mClickListener;

	public BottomBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	

	public void setIBottomBarClickListener(IBottomItemClickListener listener) {
		this.mClickListener=listener;
	}
	
	@Override
//	布局加载完成的时候调用 在该方法里面获取子控件
	protected void onFinishInflate() {
		super.onFinishInflate();
		findViewById(R.id.home_ll).setOnClickListener(this);
		findViewById(R.id.category_ll).setOnClickListener(this);
		findViewById(R.id.shopcar_ll).setOnClickListener(this);
		findViewById(R.id.mine_ll).setOnClickListener(this);
		
		mHomeTv=(TextView)findViewById(R.id.home_tv);
		mCategoryTv=(TextView)findViewById(R.id.category_tv);
		mShopcarTv=(TextView)findViewById(R.id.shopcar_tv);
		mMineTv=(TextView)findViewById(R.id.mine_tv);
		
		mHomeIv=(ImageView)findViewById(R.id.home_iv);
		mCategoryIv=(ImageView)findViewById(R.id.category_iv);
		mShopcarIv=(ImageView)findViewById(R.id.shopcar_iv);
		mMineIv=(ImageView)findViewById(R.id.mine_iv);
		
		mHomeTv.setSelected(true);
		mHomeIv.setSelected(true);
	}

	@Override
	public void onClick(View v) {
		changeItemStyle(v);
		if (mClickListener!=null) {
			mClickListener.onItemClick(v);
		}
	}


	private void changeItemStyle(View v) {
		mHomeTv.setSelected(v.getId()==R.id.home_ll);
		mCategoryTv.setSelected(v.getId()==R.id.category_ll);
		mShopcarTv.setSelected(v.getId()==R.id.shopcar_ll);
		mMineTv.setSelected(v.getId()==R.id.mine_ll);
		
		mHomeIv.setSelected(v.getId()==R.id.home_ll);
		mCategoryIv.setSelected(v.getId()==R.id.category_ll);
		mShopcarIv.setSelected(v.getId()==R.id.shopcar_ll);
		mMineIv.setSelected(v.getId()==R.id.mine_ll);
	}


}
