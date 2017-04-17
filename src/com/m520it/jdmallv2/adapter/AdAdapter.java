package com.m520it.jdmallv2.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.loopj.android.image.SmartImageView;
import com.m520it.jdmallv2.bean.AdBean;
import com.m520it.jdmallv2.cons.NetworkConst;

public class AdAdapter extends PagerAdapter {

	private List<SmartImageView> mViews=new ArrayList<SmartImageView>();

	public void setDatas(Context c,List<AdBean> adBeans) {
//		将一系列的数据封装到ArrayList<ImageView>里面
		for (int i = 0; i < adBeans.size(); i++) {
			AdBean adBean = adBeans.get(i);
//			创建一个SmartImageView
			SmartImageView sm=new SmartImageView(c);
			sm.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			sm.setImageUrl(NetworkConst.BASE_URL+adBean.getAdUrl());
//			添加到集合mViews里面
			mViews.add(sm);
		}
	}
	
	@Override
	public int getCount() {
		return mViews!=null?mViews.size():0;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==arg1;
	}
	
	@Override
//	创建子布局  应该返回一个显示的子View
	public Object instantiateItem(ViewGroup container, int position) {
		SmartImageView child = mViews.get(position);
		container.addView(child);
		return child;
	}
	
//	销毁子布局
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
//		super.destroyItem(container, position, object);
		SmartImageView child = mViews.get(position);
		container.removeView(child);
	}


}
