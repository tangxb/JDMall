package com.m520it.jdmallv2.adapter;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.bean.ReceiverAddress;
import com.m520it.jdmallv2.protocol.IAddressDeleteListener;

public class ChooseAddressAdapter extends BaseAdapter {
	

	private List<ReceiverAddress> mDatas;
	private IAddressDeleteListener mListener;
	
	public void setListener(IAddressDeleteListener mListener) {
		this.mListener = mListener;
	}

	public void setDatas(List<ReceiverAddress> obj) {
		mDatas=obj;
	}

	@Override
	public int getCount() {
		return mDatas != null ? mDatas.size() : 0;
	}

	class ViewHolder {
		public ImageView defaultReceiverIv;
		public TextView nameTv;
		public TextView phoneTv;
		public TextView addressTv;
		public TextView deleteTv;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			// 初始化布局
			convertView = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.choose_address_item, null);
			// 初始化子控件
			holder = new ViewHolder();
			holder.defaultReceiverIv = (ImageView) convertView
					.findViewById(R.id.isDeafult_iv);
			holder.nameTv = (TextView) convertView
					.findViewById(R.id.name_tv);
			holder.phoneTv = (TextView) convertView
					.findViewById(R.id.phone_tv);
			holder.addressTv = (TextView) convertView
					.findViewById(R.id.address_tv);
			holder.deleteTv = (TextView) convertView
					.findViewById(R.id.delete_tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 初始化子项数据
		final ReceiverAddress bean = mDatas.get(position);
		// 绑定
		holder.defaultReceiverIv.setVisibility(bean.isDefault()?View.VISIBLE:View.INVISIBLE);
		holder.nameTv.setText(bean.getReceiverName());
		holder.phoneTv.setText(bean.getReceiverPhone());
		holder.addressTv.setText(bean.getReceiverAddress());
//		TODO 删除按钮
		holder.deleteTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mListener!=null) {
					mListener.onDetele(bean.getId());
				}
			}
		});
		return convertView;
	}

	@Override
	public Object getItem(int position) {
		return mDatas != null ? mDatas.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

}
