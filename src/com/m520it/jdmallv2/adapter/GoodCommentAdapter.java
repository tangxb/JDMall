package com.m520it.jdmallv2.adapter;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.loopj.android.image.SmartImageView;
import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.adapter.HomeSkillAdapter.ViewHolder;
import com.m520it.jdmallv2.bean.ProductGoodComment;
import com.m520it.jdmallv2.bean.SecKillBean;
import com.m520it.jdmallv2.cons.NetworkConst;
import com.m520it.jdmallv2.ui.RatingBar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GoodCommentAdapter extends BaseAdapter {

	private List<ProductGoodComment> mDatas;

	public void setDatas(List<ProductGoodComment> obj) {
		mDatas=obj;
	}
	
	@Override
	public int getCount() {
		return mDatas != null ? mDatas.size() : 0;
	}

	class ViewHolder {
		public RatingBar ratingBar;
		public TextView nameTv;
		public TextView contentTv;
		public LinearLayout containerLl;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			// 初始化布局
			convertView = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.recommend_comment_item, null);
			// 初始化子控件
			holder = new ViewHolder();
			holder.ratingBar = (RatingBar) convertView
					.findViewById(R.id.rating_bar);
			holder.nameTv = (TextView) convertView
					.findViewById(R.id.name_tv);
			holder.contentTv = (TextView) convertView
					.findViewById(R.id.content_tv);
			holder.containerLl = (LinearLayout) convertView
					.findViewById(R.id.iamges_container);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 初始化子项数据
		ProductGoodComment bean = mDatas.get(position);
		
		// 绑定
		holder.ratingBar.setRating(bean.getRate());
		holder.nameTv.setText(bean.getUserName());
		holder.contentTv.setText(bean.getComment());
		initImageContainer(holder.containerLl,bean.getImgUrls());
		return convertView;
	}
	
//	给定一个图片的容器 根据值往容器里面塞图片
	private void initImageContainer(LinearLayout containerLl, String imgUrls) {
		List<String> imageUrls = JSON.parseArray(imgUrls, String.class);
		if (imageUrls.size()!=0) {
			containerLl.setVisibility(View.VISIBLE);
		}else {
			containerLl.setVisibility(View.GONE);
		}
		for (int i = 0; i < imageUrls.size(); i++) {
//			往子容器里面添加图片来源
			SmartImageView smiv=(SmartImageView) containerLl.getChildAt(i);
			smiv.setImageUrl(NetworkConst.BASE_URL+imageUrls.get(i));
		}
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
