package com.m520it.jdmallv2.adapter;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.bean.RecommendProduct;
import com.m520it.jdmallv2.cons.NetworkConst;

public class RecomProductAdapter extends BaseAdapter {
	
	private List<RecommendProduct> mDatas;

	public void setDatas(List<RecommendProduct> beans) {
		mDatas=beans;
	}
	
	@Override
	public long getItemId(int position) {
		return mDatas!=null?mDatas.get(position).getProductId():-1;
	}
	
	@Override
	public int getCount() {
		return mDatas!=null?mDatas.size():0;
	}
	
	class ViewHolder{
		public SmartImageView iconIv;
		public TextView nameTv;
		public TextView priceTv;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if (convertView==null) {
			convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_gv_item,null);
			holder=new ViewHolder();
			holder.iconIv=(SmartImageView)convertView.findViewById(R.id.image_iv);
			holder.nameTv=(TextView)convertView.findViewById(R.id.name_tv);
			holder.priceTv=(TextView)convertView.findViewById(R.id.price_tv);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		RecommendProduct bean = mDatas.get(position);
		holder.iconIv.setImageUrl(NetworkConst.BASE_URL+bean.getIconUrl());
		holder.nameTv.setText(bean.getName());
		holder.priceTv.setText("￥"+bean.getPrice());
		return convertView;
	}


	@Override
	public Object getItem(int position) {
		return null;
	}


	
}
