package com.m520it.jdmallv2.adapter;

import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.bean.ShopCarListBean;
import com.m520it.jdmallv2.cons.NetworkConst;
import com.m520it.jdmallv2.protocol.IShopcarDeleteListener;

public class ShopCarAdapter extends BaseAdapter {

	private List<ShopCarListBean> mDatas;
	private List<Boolean> mChecked;
	private IShopcarDeleteListener mListener;


	public void setListener(IShopcarDeleteListener listener) {
		mListener=listener;
	}
	
	public void setDatas(List<ShopCarListBean> beans) {
		mDatas = beans;
		mChecked=new ArrayList<Boolean>();
		for (int i = 0; i <beans.size(); i++) {
			mChecked.add(false);
		}
	}
	
	/**
	 * 获取选中的商品列表
	 */
	public ArrayList<ShopCarListBean> getCheckedProduct(){
		ArrayList<ShopCarListBean> result=new ArrayList<ShopCarListBean>();
//		如果选项是被选中的 那么就从mDatas取出该数据 添加checkedProducts
		for (int i = 0; i <mChecked.size(); i++) {
			if (mChecked.get(i)) {
				result.add(mDatas.get(i));
			}
		}
		return result;
	}
	

	@Override
	public int getCount() {
		return mDatas != null ? mDatas.size() : 0;
	}
	
	/**
	 * 	选中某个Item
	 */
	public void setCheckPosition(int position){
//		set 修改List里面元素的值
		mChecked.set(position, !mChecked.get(position));
	}
	

	/**
	 * 全选
	 */
	public void checkeAll(boolean flag) {
		for (int i = 0; i <mChecked.size(); i++) {
			mChecked.set(i, flag);
		}
	}
	
	
	/**
	 * 获取选中的数量
	 */
	public int getCheckedCount(){
		int result=0;
		for (int i = 0; i <mChecked.size(); i++) {
			if (mChecked.get(i)) {
				result++;
			}
		}
		return result;
	}
	
	public double getCheckedPrice(){
		double result=0;
		for (int i = 0; i <mChecked.size(); i++) {
			if (mChecked.get(i)) {
//				计算 单价*数量
				ShopCarListBean bean = mDatas.get(i);
				result+=bean.getPprice()*bean.getBuyCount();
			}
		}
		return result;
	}

	class ViewHolder {
		public CheckBox cbx;
		public SmartImageView iconIv;
		public TextView nameTv;
		public TextView pversionTv;
		public TextView priceTv;
		public TextView countTv;
		public TextView deleteBtn;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			// 初始化布局
			convertView = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.shopcar_lv_item, null);
			// 初始化子控件
			holder = new ViewHolder();
			holder.cbx = (CheckBox) convertView
					.findViewById(R.id.cbx);
			holder.iconIv = (SmartImageView) convertView
					.findViewById(R.id.product_iv);
			holder.nameTv = (TextView) convertView
					.findViewById(R.id.name_tv);
			holder.pversionTv= (TextView) convertView
					.findViewById(R.id.version_tv);
			holder.priceTv = (TextView) convertView
					.findViewById(R.id.price_tv);
			holder.countTv = (TextView) convertView
					.findViewById(R.id.buyCount_tv);
			holder.deleteBtn = (TextView) convertView
					.findViewById(R.id.delete_product);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 初始化子项数据
		final ShopCarListBean bean = mDatas.get(position);
		// 绑定
		holder.iconIv.setImageUrl(NetworkConst.BASE_URL + bean.getPimageUrl());
		holder.nameTv.setText(bean.getPname());
		holder.pversionTv.setText(bean.getPversion());
		holder.priceTv.setText("￥ "+bean.getPprice());
		holder.countTv.setText(bean.getBuyCount()+"");
		if (mChecked!=null&&mChecked.size()!=0) {
			holder.cbx.setChecked(mChecked.get(position));
		}
		holder.deleteBtn.setOnClickListener(new OnClickListener() {
			
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
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

}
