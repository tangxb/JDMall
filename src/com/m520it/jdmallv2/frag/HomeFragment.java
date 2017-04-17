package com.m520it.jdmallv2.frag;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.loopj.android.image.SmartImageView;
import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.activity.ProductDetailsActivity;
import com.m520it.jdmallv2.adapter.AdAdapter;
import com.m520it.jdmallv2.adapter.HomeSkillAdapter;
import com.m520it.jdmallv2.adapter.RecomProductAdapter;
import com.m520it.jdmallv2.bean.AdBean;
import com.m520it.jdmallv2.bean.RResult;
import com.m520it.jdmallv2.bean.RecommendProduct;
import com.m520it.jdmallv2.bean.SecKillBean;
import com.m520it.jdmallv2.cons.IDiyMessage;
import com.m520it.jdmallv2.cons.IntentValues;
import com.m520it.jdmallv2.cons.NetworkConst;
import com.m520it.jdmallv2.controller.HomeController;
import com.m520it.jdmallv2.protocol.IModleChangeListener;
import com.m520it.jdmallv2.ui.HorizontalListView;
import com.m520it.jdmallv2.util.FixedViewUtil;

public class HomeFragment extends BaseFragment implements IModleChangeListener {

	private ViewPager mAdVp;
	private LinearLayout mAdIndicatorLl;
	private AdAdapter mAdAdapter;
	private SmartImageView mAd2Iv;
	private HorizontalListView mSecondKillLv;
	private HomeSkillAdapter mSecondKillAdapter;
	private GridView mRecommendProductGv;
	private HomeController mController;
	private Handler mHanlder = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case IDiyMessage.ACTION_GET_AD_RESULT:
				handleGetAd((RResult) msg.obj);
				break;
			case IDiyMessage.ACTION_SCROLL_AD:
				handleAdScroll();
				break;
			case IDiyMessage.ACTION_GET_RECOMMEND_AD_RESULT:
				handleRecommendAd((RResult) msg.obj);
				break;
			case IDiyMessage.ACTION_GET_SEC_KILL_RESULT:
				handlSecKillResult((RResult) msg.obj);
				break;
			case IDiyMessage.ACTION_GET_RECOMMEND_PRODUCT_RESULT:
				handleRecommendProductResult((RResult) msg.obj);
				break;
			}
		}

	};
	private RecomProductAdapter mRecommendProductAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home, null);
	}

	protected void handleRecommendProductResult(RResult resultBean) {
		try {
			// 如果返回数据成功
			if (resultBean.isSuccess()) {
				// 1.将数据转换成对象
				JSONObject jsonObject = new JSONObject(resultBean.getResult());
				String jsonArr = jsonObject.getString("rows");
				// 2.设置到mRecommendProductAdapter
				List<RecommendProduct> beans = JSON.parseArray(jsonArr, RecommendProduct.class);
				mRecommendProductAdapter.setDatas(beans);
				// 3.计算高度
				FixedViewUtil.setListViewHeightBasedOnChildren(mRecommendProductGv, 2);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	protected void handlSecKillResult(RResult resultBean) {
		try {
			// 对接resultBean 和mSecondKillAdapter
			if (resultBean.isSuccess()) {
				// 安卓系统原生的JSON解析类
				JSONObject jsonObject = new JSONObject(resultBean.getResult());
				String jsonArr = jsonObject.getString("rows");
				List<SecKillBean> beans = JSON.parseArray(jsonArr,
						SecKillBean.class);
				mSecondKillAdapter.setDatas(beans);
				mSecondKillAdapter.notifyDataSetChanged();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	protected void handleRecommendAd(RResult result) {
		if (result.isSuccess()) {
			List<AdBean> parseArray = JSON.parseArray(result.getResult(),
					AdBean.class);
			mAd2Iv.setImageUrl(NetworkConst.BASE_URL
					+ parseArray.get(0).getAdUrl());
		}
	}

	protected void handleAdScroll() {
		// 1.获取当前展示项
		int currentItem = mAdVp.getCurrentItem();
		// 2.+1
		currentItem++;
		if (currentItem > mAdAdapter.getCount() - 1) {
			currentItem = 0;
		}
		// 3.设置当前选项
		mAdVp.setCurrentItem(currentItem, true);
		// 4.设置当前的指示器
		int childCount = mAdIndicatorLl.getChildCount();
		for (int i = 0; i < childCount; i++) {
			mAdIndicatorLl.getChildAt(i).setEnabled(i == currentItem);
		}
	}

	/**
	 * 处理显示导航
	 */
	protected void handleGetAd(RResult obj) {
		if (obj.isSuccess()) {
			List<AdBean> adBeans = JSON.parseArray(obj.getResult(),
					AdBean.class);
			// 1.将数据设置到 Adapter里面
			mAdAdapter.setDatas(getActivity(), adBeans);
			mAdAdapter.notifyDataSetChanged();
			// 2.初始化指示器
			iniAdIndicator(adBeans);
			// 3.自动滚动 每三秒滚动一次
			new Timer().schedule(new TimerTask() {

				@Override
				public void run() {
					// 开始滚动 现在的代码是在子线程里面的
					mHanlder.sendEmptyMessage(IDiyMessage.ACTION_SCROLL_AD);
				}
			}, 3000, 3000);

		}
	}

	private void iniAdIndicator(List<AdBean> adBeans) {
		mAdIndicatorLl = (LinearLayout) getActivity().findViewById(
				R.id.ad_indicator);
		for (int i = 0; i < adBeans.size(); i++) {
			// 1.创建指示器
			ImageView iv = new ImageView(getActivity());
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					20, 20);
			params.setMargins(20, 0, 0, 0);
			iv.setLayoutParams(params);
			iv.setBackgroundResource(R.drawable.ad_indicator_bg);
			iv.setEnabled(i == 0 ? true : false);
			// 2.添加到mAdIndicatorLl
			mAdIndicatorLl.addView(iv, i);
		}
	}

	@Override
	// 实现获取HomeFragment里面控件
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initController();
		initUI();
	}

	private void initController() {
		mController = new HomeController(getActivity());
		mController.setIModelChangeListener(this);
	}

	private void initUI() {
		mAdVp = (ViewPager) getActivity().findViewById(R.id.ad_vp);
		mAdAdapter = new AdAdapter();
		mAdVp.setAdapter(mAdAdapter);
		mAd2Iv = (SmartImageView) getActivity().findViewById(R.id.ad2_iv);
		mSecondKillLv = (HorizontalListView) getActivity().findViewById(
				R.id.horizon_listview);
		mSecondKillAdapter = new HomeSkillAdapter();
		mSecondKillLv.setAdapter(mSecondKillAdapter);
		mSecondKillLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//				需要将商品的id传给商品详情页
				Intent intent=new Intent(getActivity(),ProductDetailsActivity.class);
				long pid =mSecondKillAdapter.getItemId(position);
				intent.putExtra(IntentValues.PRODUCTID, pid);
				startActivity(intent);
			}
		});
		mRecommendProductGv = (GridView) getActivity().findViewById(
				R.id.recommend_gv);
		mRecommendProductAdapter = new RecomProductAdapter();
		mRecommendProductGv.setAdapter(mRecommendProductAdapter);
		mRecommendProductGv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//				需要将商品的id传给商品详情页
				Intent intent=new Intent(getActivity(),ProductDetailsActivity.class);
				long pid =mRecommendProductAdapter.getItemId(position);
				intent.putExtra(IntentValues.PRODUCTID, pid);
				startActivity(intent);
			}
		});
		// 有个接口 初始化控件后 发送异步请求
		mController.sendAyncMessage(IDiyMessage.ACTION_GET_AD, 0);
		mController.sendAyncMessage(IDiyMessage.ACTION_GET_RECOMMEND_AD, 0);
		mController.sendAyncMessage(IDiyMessage.ACTION_GET_SEC_KILL, 0);
		mController
				.sendAyncMessage(IDiyMessage.ACTION_GET_RECOMMEND_PRODUCT, 0);
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHanlder.obtainMessage(action, values[0]).sendToTarget();
	}

}
