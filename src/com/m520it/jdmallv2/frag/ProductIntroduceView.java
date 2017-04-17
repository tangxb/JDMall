package com.m520it.jdmallv2.frag;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.image.SmartImageView;
import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.activity.ProductDetailsActivity;
import com.m520it.jdmallv2.adapter.GoodCommentAdapter;
import com.m520it.jdmallv2.adapter.ProductVersionAdapter;
import com.m520it.jdmallv2.bean.ProductGoodComment;
import com.m520it.jdmallv2.bean.ProductInfoBean;
import com.m520it.jdmallv2.bean.RResult;
import com.m520it.jdmallv2.cons.IDiyMessage;
import com.m520it.jdmallv2.cons.NetworkConst;
import com.m520it.jdmallv2.controller.ProductController;
import com.m520it.jdmallv2.protocol.IModleChangeListener;
import com.m520it.jdmallv2.protocol.INumberInputListener;
import com.m520it.jdmallv2.ui.NumberInputView;
import com.m520it.jdmallv2.util.FixedViewUtil;

public class ProductIntroduceView extends Fragment implements
		IModleChangeListener,INumberInputListener, OnItemClickListener{

	private ProductController mController;
	private ViewPager mAdViewPager;
	private TextView mVpIndicatorTv;
	private ImageAdapter mImageAdapter;
	private TextView mNameTv;
	private TextView mSelfSaleTv;
	private TextView mDescTv;
	private TextView mPriceTv;
	private GridView mProductVersionGv;
	private ProductVersionAdapter mProductVersionAdapter;
	private NumberInputView mNumberInputView;
	private TextView mGoodRateTv;
	private TextView mGoodCommentTv;
	private ListView mGoodCommentLv;
	private GoodCommentAdapter mGoodCommentAdapter;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case IDiyMessage.ACTION_PRODUCT_INTRODUCE_RESULT:
				handleProductIntroduce((RResult)msg.obj);
				break;
			case IDiyMessage.ACTION_SCROLL_PRODUCT_IMAGE:
				handleScrollAd();
				break;
			case IDiyMessage.ACTION_GET_GOOD_RECOMMENT_RESULT:
				handleGoodComment((List<ProductGoodComment>)msg.obj);
				break;
			}
		}
	};


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_product_introduce, null);
	}

	protected void handleGoodComment(List<ProductGoodComment> obj) {
		mGoodCommentAdapter.setDatas(obj);
		mGoodCommentAdapter.notifyDataSetChanged();
		FixedViewUtil.setListViewHeightBasedOnChildren(mGoodCommentLv);
	}

	/**
	 * 处理商品信息的显示
	 */
	protected void handleProductIntroduce(RResult resultBean) {
		if (resultBean.isSuccess()) {
//			解析数据
			ProductInfoBean bean = JSON.parseObject(resultBean.getResult(),ProductInfoBean.class);
//			显示界面
			initAdView(bean);
			mNameTv.setText(bean.getName());
			mSelfSaleTv.setVisibility(bean.isIfSaleOneself()?View.VISIBLE:View.INVISIBLE);
			mDescTv.setText(bean.getRecomProduct());
			mPriceTv.setText("￥ "+bean.getPrice());
			
			mProductVersionAdapter.setDatas(bean.getTypeList());
			mProductVersionAdapter.notifyDataSetChanged();
			FixedViewUtil.setListViewHeightBasedOnChildren(mProductVersionGv, 1);
			
			mNumberInputView.setMax(bean.getStockCount());//设置最大的库存
			mGoodRateTv.setText(bean.getFavcomRate()+"%好评");
			mGoodCommentTv.setText(bean.getCommentCount()+"人评价");
		}else {
//			TODO  显示一个找不到数据的页面
		}
	}

	/**
	 * 处理商品的图片
	 */
	private void initAdView(ProductInfoBean bean) {
		List<String> imageUrls = JSON.parseArray(bean.getImgUrls(),String.class);
		mImageAdapter.setDatas(imageUrls);
		mImageAdapter.notifyDataSetChanged();
		mVpIndicatorTv.setText("1/"+imageUrls.size());
//			每3秒移动一次
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				mHandler.sendEmptyMessage(IDiyMessage.ACTION_SCROLL_PRODUCT_IMAGE);
			}
		}, 3000, 3000);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// 实现Controller
		initController();
		// 拿到控件
		initUI();
		// 发送请求
		long pid = ((ProductDetailsActivity) getActivity()).mPid;
		mController.sendAyncMessage(IDiyMessage.ACTION_PRODUCT_INTRODUCE, pid);
		mController.sendAyncMessage(IDiyMessage.ACTION_GET_GOOD_RECOMMENT, pid);
	}

	private void initUI() {
		mAdViewPager = (ViewPager) getActivity().findViewById(R.id.asvp);
		mImageAdapter = new ImageAdapter();
		mAdViewPager.setAdapter(mImageAdapter);
		mVpIndicatorTv = (TextView) getActivity()
				.findViewById(R.id.vp_indic_tv);
		
		mNameTv=(TextView)getActivity().findViewById(R.id.name_tv);
		mSelfSaleTv=(TextView)getActivity().findViewById(R.id.self_sale_tv);
		
		mDescTv=(TextView)getActivity().findViewById(R.id.desc_tv);
		mPriceTv=(TextView)getActivity().findViewById(R.id.price_tv);
		
		mProductVersionGv=(GridView)getActivity().findViewById(R.id.product_versions_gv);
		mProductVersionAdapter=new ProductVersionAdapter();
		mProductVersionGv.setAdapter(mProductVersionAdapter);
		mProductVersionGv.setOnItemClickListener(this);
		
		mNumberInputView=(NumberInputView)getActivity().findViewById(R.id.number_input_et);
		mNumberInputView.setListener(this);

		mGoodRateTv=(TextView)getActivity().findViewById(R.id.good_rate_tv);
		mGoodCommentTv=(TextView)getActivity().findViewById(R.id.good_comment_tv);
		
		mGoodCommentLv=(ListView) getActivity().findViewById(R.id.good_comment_lv);
		mGoodCommentAdapter=new GoodCommentAdapter();
		mGoodCommentLv.setAdapter(mGoodCommentAdapter);
	}

	class ImageAdapter extends PagerAdapter {
		
		private ArrayList<SmartImageView> mViews=new ArrayList<SmartImageView>();
		
		@Override
		public int getCount() {
			return mViews.size();
		}

		public void setDatas(List<String> imageUrls) {
			for (int i = 0; i < imageUrls.size(); i++) {
				SmartImageView smiv=new SmartImageView(getActivity());
				smiv.setLayoutParams(new ViewGroup.LayoutParams(-1,-1));
				smiv.setImageUrl(NetworkConst.BASE_URL+imageUrls.get(i));
				mViews.add(smiv);
			}
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0==arg1;
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			SmartImageView childView = mViews.get(position);
			container.addView(childView);
			return childView;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			SmartImageView childView = mViews.get(position);
			container.removeView(childView);
		}

	}

	private void initController() {
		mController = new ProductController(getActivity());
		mController.setIModelChangeListener(this);
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action, values[0]).sendToTarget();
	}

	private void handleScrollAd() {
		int currentItem = mAdViewPager.getCurrentItem();
		currentItem++;
		if (currentItem>mImageAdapter.getCount()-1) {
			currentItem=0;
		}
		mAdViewPager.setCurrentItem(currentItem,true);
		mVpIndicatorTv.setText(currentItem+"/"+mImageAdapter.getCount());
	}

	@Override
	public void onTextChange(int value) {
//		当前还联系不到Activity
//		mBuyCount=value;
		((ProductDetailsActivity)getActivity()).mBuyCount=value;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		mProductVersionAdapter.mPosition=position;
		mProductVersionAdapter.notifyDataSetChanged();
		String pversion = (String) mProductVersionAdapter.getItem(position);
		((ProductDetailsActivity)getActivity()).mPversion=pversion;
	}

}
