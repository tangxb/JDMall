package com.m520it.jdmallv2.adapter;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.bean.SecKillBean;
import com.m520it.jdmallv2.cons.NetworkConst;

public class HomeSkillAdapter extends BaseAdapter {

	private List<SecKillBean> mDatas;

	public void setDatas(List<SecKillBean> beans) {
		mDatas = beans;
	}

	@Override
	public int getCount() {
		return mDatas != null ? mDatas.size() : 0;
	}

	class ViewHolder {
		public SmartImageView mIconIv;
		public TextView mDiscoutPriceTv;
		public TextView mOriPriceTv;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			// 初始化布局
			convertView = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.home_seckill_item, null);
			// 初始化子控件
			holder = new ViewHolder();
			holder.mIconIv = (SmartImageView) convertView
					.findViewById(R.id.image_iv);
			holder.mDiscoutPriceTv = (TextView) convertView
					.findViewById(R.id.nowprice_tv);
			holder.mOriPriceTv = (TextView) convertView
					.findViewById(R.id.normalprice_tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 初始化子项数据
		SecKillBean bean = mDatas.get(position);
		// 绑定
		holder.mIconIv.setImageUrl(NetworkConst.BASE_URL + bean.getIconUrl());
		holder.mDiscoutPriceTv.setText("￥ "+bean.getPointPrice());
		holder.mOriPriceTv.setText("￥ "+bean.getAllPrice());
		com.m520it.jdmallv2.util.TextUtil.setStrike(holder.mOriPriceTv);
		return convertView;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return mDatas != null ? mDatas.get(position).getProductId() :-1;
	}

}
