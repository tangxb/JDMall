package com.m520it.jdmallv2.adapter;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.loopj.android.image.SmartImageView;
import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.adapter.HomeSkillAdapter.ViewHolder;
import com.m520it.jdmallv2.bean.CommentBean;
import com.m520it.jdmallv2.bean.SecKillBean;
import com.m520it.jdmallv2.cons.NetworkConst;
import com.m520it.jdmallv2.ui.RatingBar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CommentAdapter extends BaseAdapter {

	private List<CommentBean> mDatas;

	public void setDatas(List<CommentBean> beans) {
		mDatas=beans;
	}

	@Override
	public int getCount() {
		return mDatas != null ? mDatas.size() : 0;
	}

	class ViewHolder {
		public SmartImageView mIconIv;
		public TextView mUserNameTv;
		public TextView mCommentTimeTv;
		public RatingBar mRatingBar;
		public TextView mCommentContentTv;
		public LinearLayout mContainerLl;
		public TextView mBuyTimeTv;
		public TextView mVersionTv;
		public TextView mLoveCountTv;
		public TextView mSubCommentTv;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			// 初始化布局
			convertView = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.comment_item, null);
			// 初始化子控件
			holder = new ViewHolder();
			holder.mIconIv = (SmartImageView) convertView
					.findViewById(R.id.icon_iv);
			holder.mUserNameTv = (TextView) convertView
					.findViewById(R.id.name_tv);
			holder.mCommentTimeTv = (TextView) convertView
					.findViewById(R.id.time_tv);
			holder.mRatingBar = (RatingBar) convertView
					.findViewById(R.id.rating_bar);
			holder.mCommentContentTv = (TextView) convertView
					.findViewById(R.id.content_tv);
			holder.mContainerLl=(LinearLayout) convertView.findViewById(R.id.iamges_container);

			holder.mBuyTimeTv = (TextView) convertView
					.findViewById(R.id.buytime_tv);
			holder.mVersionTv = (TextView) convertView
					.findViewById(R.id.buyversion_tv);

			holder.mLoveCountTv = (TextView) convertView
					.findViewById(R.id.lovecount_tv);
			holder.mSubCommentTv = (TextView) convertView
					.findViewById(R.id.subcomment_tv);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 初始化子项数据
		CommentBean bean = mDatas.get(position);
		// 绑定
		holder.mIconIv.setImageUrl(NetworkConst.BASE_URL+bean.getUserImg());
		holder.mUserNameTv.setText(bean.getUserName());
		holder.mCommentTimeTv.setText(bean.getCommentTime());
		holder.mRatingBar.setRating(bean.getRate());
		holder.mCommentContentTv.setText(bean.getComment());
		initImageContainer(holder.mContainerLl, bean.getImgUrls());
		holder.mBuyTimeTv.setText(bean.getBuyTime());
		holder.mVersionTv.setText(bean.getProductType());
		holder.mLoveCountTv.setText("喜欢("+bean.getLoveCount()+")");
		holder.mSubCommentTv.setText("回复("+bean.getSubComment()+")");
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
