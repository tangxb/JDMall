package com.m520it.jdmallv2.adapter;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.bean.RProductListBean;
import com.m520it.jdmallv2.cons.NetworkConst;

public class ProductListAdapter extends BaseAdapter {
	
	private List<RProductListBean> mDatas;

	public void setDatas(List<RProductListBean> beans) {
		mDatas=beans;
	}
	
	@Override
	public int getCount() {
		return mDatas!=null?mDatas.size():0;
	}
	
	@Override
	public long getItemId(int position) {
		return mDatas!=null?mDatas.get(position).getId():-1;
	}
	
	class ViewHolder{
		public SmartImageView smIv;
		public TextView nameTv;
		public TextView priceTv;
		public TextView commrateTv;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if (convertView==null) {
			convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.product_lv_item, null);
			holder=new ViewHolder();
			holder.smIv=(SmartImageView) convertView.findViewById(R.id.product_iv);
			holder.nameTv=(TextView) convertView.findViewById(R.id.name_tv);
			holder.priceTv=(TextView) convertView.findViewById(R.id.price_tv);
			holder.commrateTv=(TextView) convertView.findViewById(R.id.commrate_tv);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		RProductListBean bean = mDatas.get(position);
		holder.smIv.setImageUrl(NetworkConst.BASE_URL+bean.getIconUrl());
		holder.nameTv.setText(bean.getName());
		holder.priceTv.setText("￥"+bean.getPrice());
		holder.commrateTv.setText(bean.getCommentCount()+"条评论  好评 "+bean.getFavcomRate()+"%");
		
		return convertView;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}




}
