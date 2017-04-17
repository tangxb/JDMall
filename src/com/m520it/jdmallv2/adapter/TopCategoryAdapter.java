package com.m520it.jdmallv2.adapter;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.bean.RBaseCategory;

public class TopCategoryAdapter extends BaseAdapter {

	private List<RBaseCategory> mDatas;
	public int c  = -1;
	public int mPosition=-1;

	public void setDatas(List<RBaseCategory> obj) {
		mDatas = obj;
	}

	@Override
	public int getCount() {
		return mDatas != null ? mDatas.size() : 0;
	}

	class ViewHolder {
		public View mLeftView;
		public TextView mCategroyItemTv;
	}
	
	@Override
	public Object getItem(int position) {
		return mDatas != null ? mDatas.get(position):null;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.top_category_item, null);
			holder = new ViewHolder();
			holder.mLeftView = convertView.findViewById(R.id.divider);
			holder.mCategroyItemTv = (TextView) convertView
					.findViewById(R.id.tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		RBaseCategory bean = mDatas.get(position);
		// 拿到当前选中的那一项
		// 选中状态 修改左边线 背景 字体颜色
		holder.mLeftView.setVisibility(position == mPosition ? View.INVISIBLE
				: View.VISIBLE);
		holder.mCategroyItemTv.setSelected(position == mPosition);
		if (mPosition == position) {
			holder.mCategroyItemTv
					.setBackgroundResource(R.drawable.tongcheng_all_bg01);
		} else {
			holder.mCategroyItemTv.setBackgroundColor(0xFAFAFB);
		}

		holder.mCategroyItemTv.setText(bean.getName());
		return convertView;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

}
