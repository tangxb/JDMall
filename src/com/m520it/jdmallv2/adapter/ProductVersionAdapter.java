package com.m520it.jdmallv2.adapter;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmallv2.R;

public class ProductVersionAdapter extends BaseAdapter {

	private List<String> mDatas;
	public int mPosition=-1;

	public void setDatas(String typeList) {
		mDatas=JSON.parseArray(typeList, String.class);
	}
	
	@Override
	public int getCount() {
		return mDatas!=null?mDatas.size():0;
	}
	
	@Override
	public Object getItem(int position) {
		return mDatas!=null?mDatas.get(position):"";
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView tv=null;
		if (convertView==null) {
			convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_item_view, null);
			tv=(TextView) convertView.findViewById(R.id.brand_name_tv);
			convertView.setTag(tv);
		}else {
			tv=(TextView) convertView.getTag();
		}
		tv.setText(mDatas.get(position));
		tv.setSelected(position==mPosition);
		return convertView;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}


}
