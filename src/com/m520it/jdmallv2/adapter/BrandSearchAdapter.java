package com.m520it.jdmallv2.adapter;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.bean.Brand;

public class BrandSearchAdapter extends BaseAdapter {

	private List<Brand> mDatas;
	public int mPosition=-1;

	public void setDatas(List<Brand> obj) {
		mDatas=obj;
	}
	
	@Override
	public int getCount() {
		return mDatas!=null?mDatas.size():0;
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
		tv.setText(mDatas.get(position).getName());
		tv.setSelected(position==mPosition);
		return convertView;
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	


}
