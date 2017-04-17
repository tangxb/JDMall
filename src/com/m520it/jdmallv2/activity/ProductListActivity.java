package com.m520it.jdmallv2.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.adapter.BrandSearchAdapter;
import com.m520it.jdmallv2.adapter.ProductListAdapter;
import com.m520it.jdmallv2.bean.Brand;
import com.m520it.jdmallv2.bean.RProductListBean;
import com.m520it.jdmallv2.bean.SProductList;
import com.m520it.jdmallv2.cons.IDiyMessage;
import com.m520it.jdmallv2.cons.IntentValues;
import com.m520it.jdmallv2.controller.CategoryController;
import com.m520it.jdmallv2.protocol.IModleChangeListener;
import com.m520it.jdmallv2.protocol.IProductSortListener;
import com.m520it.jdmallv2.ui.FlexiScrollView;
import com.m520it.jdmallv2.ui.pop.ProductsSortPopupWindow;

public class ProductListActivity extends BaseActivity implements
		IModleChangeListener, OnClickListener, OnItemClickListener,
		IProductSortListener {

	private long mTopCid;
	private long mThirdCid;
	private CategoryController mController;
	private TextView mJDTakeTv;
	private TextView mPayWhenReceiveTv;
	private TextView mJustHasStockTv;
	private EditText mMinPriceEt;
	private EditText mMaxPriceEt;
	private GridView mBrandGv;
	private DrawerLayout mDrawerLayout;
	private FlexiScrollView mSlideView;
	private TextView mAllIndicatorTv;
	private TextView mSaleIndicatorTv;
	private TextView mPriceIndicatorTv;
	private TextView mChooseIndicatorTv;
	private ProductsSortPopupWindow mProductSortsPopupWindow;
	private View mDividerView;
	private BrandSearchAdapter mBrandAdapter;
	private SProductList mSendArgs;
	private ListView mProductsLv;
	private ProductListAdapter mProductsAdapter;
	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case IDiyMessage.ACTION_BRAND_SEARCH_RESULT:
					handleBrandSearchResult((List<Brand>) msg.obj);
					break;
				case IDiyMessage.ACTION_PRODUCT_LIST_RESULT:
					handlLoadProductList((List<RProductListBean>)msg.obj);
					break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_list);

		// 1.获取别的页面传过来的参数
		mSendArgs = new SProductList();
		mTopCid = getIntent().getLongExtra(IntentValues.TOPCID, 0);
		mThirdCid = getIntent().getLongExtra(IntentValues.THIRDCID, 0);
		mSendArgs.setCategoryId(mThirdCid + "");
		// 2.初始化Controller
		initController();
		// 3.初始化控件
		initUI();
		// 4.发送请求
		mController.sendAyncMessage(IDiyMessage.ACTION_BRAND_SEARCH, mTopCid);
		
//		初始化界面
		mController.sendAyncMessage(IDiyMessage.ACTION_PRODUCT_LIST,
				mSendArgs);
		mAllIndicatorTv.setSelected(true);
	}

	protected void handlLoadProductList(List<RProductListBean> obj) {
		mProductsAdapter.setDatas(obj);
		mProductsAdapter.notifyDataSetChanged();
	}

	/**
	 * 处理品牌的搜索结果
	 */
	protected void handleBrandSearchResult(List<Brand> obj) {
		mBrandAdapter.setDatas(obj);
		mBrandAdapter.notifyDataSetChanged();
	}

	private void initUI() {

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
		mSlideView = (FlexiScrollView) findViewById(R.id.slide_view);
		mDividerView = findViewById(R.id.divider_view);

		mJDTakeTv = (TextView) findViewById(R.id.jd_take_tv);
		mPayWhenReceiveTv = (TextView) findViewById(R.id.paywhenreceive_tv);
		mJustHasStockTv = (TextView) findViewById(R.id.justhasstock_tv);
		mJDTakeTv.setOnClickListener(this);
		mPayWhenReceiveTv.setOnClickListener(this);
		mJustHasStockTv.setOnClickListener(this);
		mMinPriceEt = (EditText) findViewById(R.id.minPrice_et);
		mMaxPriceEt = (EditText) findViewById(R.id.maxPrice_et);
		mBrandGv = (GridView) findViewById(R.id.gv);
		mBrandAdapter = new BrandSearchAdapter();
		mBrandGv.setAdapter(mBrandAdapter);
		mBrandGv.setOnItemClickListener(this);
		// 底层布局
		mAllIndicatorTv = (TextView) findViewById(R.id.all_indicator);
		mSaleIndicatorTv = (TextView) findViewById(R.id.sale_indicator);
		mPriceIndicatorTv = (TextView) findViewById(R.id.price_indicator);
		mChooseIndicatorTv = (TextView) findViewById(R.id.choose_indicator);
		mAllIndicatorTv.setOnClickListener(this);
		mSaleIndicatorTv.setOnClickListener(this);
		mPriceIndicatorTv.setOnClickListener(this);
		mChooseIndicatorTv.setOnClickListener(this);
		mProductsLv=(ListView) findViewById(R.id.product_lv);
		mProductsAdapter=new ProductListAdapter();
		mProductsLv.setAdapter(mProductsAdapter);
		mProductsLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//				需要将商品的id传给商品详情页
				Intent intent=new Intent(ProductListActivity.this,ProductDetailsActivity.class);
				long pid = mProductsAdapter.getItemId(position);
				intent.putExtra(IntentValues.PRODUCTID, pid);
				startActivity(intent);
			}
		});
		// 创建一个综合弹出框
		mProductSortsPopupWindow = new ProductsSortPopupWindow(this);
		mProductSortsPopupWindow.setListener(this);
	}

	private void initController() {
		mController = new CategoryController(this);
		mController.setIModelChangeListener(this);
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action, values[0]).sendToTarget();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.jd_take_tv:
			v.setSelected(!v.isSelected());
			break;
		case R.id.paywhenreceive_tv:
			v.setSelected(!v.isSelected());
			break;
		case R.id.justhasstock_tv:
			v.setSelected(!v.isSelected());
			break;
		case R.id.all_indicator:
			changeChooseProductIndicStyle(v);
			// 综合 每次点击一次都要创建一个？？
			mProductSortsPopupWindow.show(mDividerView);
			break;
		case R.id.sale_indicator:
			changeChooseProductIndicStyle(v);
			int sortType = mSendArgs.getSortType();
			if (sortType != SProductList.SEARCH_SALE) {
				mSendArgs.setSortType(SProductList.SEARCH_SALE);
				mController.sendAyncMessage(IDiyMessage.ACTION_PRODUCT_LIST,
						mSendArgs);
			}
			break;
		case R.id.price_indicator:
			changeChooseProductIndicStyle(v);
			// 怎么区分 是价格高的列表还是价格低的列表
			sortType = mSendArgs.getSortType();
			if (sortType == 0 || sortType == SProductList.SEARCH_PRICE_UP
					|| sortType == SProductList.SEARCH_SALE) {
				mPriceIndicatorTv.setSelected(false);
				mSendArgs.setSortType(SProductList.SEARCH_PRICE_DOWN);
				mController.sendAyncMessage(IDiyMessage.ACTION_PRODUCT_LIST,
						mSendArgs);
			} else if (sortType == 0
					|| sortType == SProductList.SEARCH_PRICE_DOWN
					|| sortType == SProductList.SEARCH_SALE) {
				mPriceIndicatorTv.setSelected(true);
				mSendArgs.setSortType(SProductList.SEARCH_PRICE_UP);
				mController.sendAyncMessage(IDiyMessage.ACTION_PRODUCT_LIST,
						mSendArgs);
			}
			break;
		case R.id.choose_indicator:
			// 打开右边的布局
			mDrawerLayout.openDrawer(mSlideView);
			break;
		}
	}

	private void changeChooseProductIndicStyle(View v) {
		mAllIndicatorTv.setSelected(false);
		mSaleIndicatorTv.setSelected(false);
		mPriceIndicatorTv.setSelected(false);
		v.setSelected(true);
	}

	/**
	 * 顶层确定按钮
	 */
	public void chooseSearchClick(View v) {
		mJDTakeTv.setOnClickListener(this);
		mPayWhenReceiveTv.setOnClickListener(this);
		mJustHasStockTv.setOnClickListener(this);
		int deliverChoose = 0;
		if (mJDTakeTv.isSelected()) {
			deliverChoose += 1;
		}
		if (mPayWhenReceiveTv.isSelected()) {
			deliverChoose += 2;
		}
		if (mJustHasStockTv.isSelected()) {
			deliverChoose += 4;
		}
		mSendArgs.setDeliverChoose(deliverChoose);
		// 拿到价格区间
		String minPriceStr = mMinPriceEt.getText().toString().trim();
		String maxPriceStr = mMaxPriceEt.getText().toString().trim();
		if (!TextUtils.isEmpty(minPriceStr) && !TextUtils.isEmpty(maxPriceStr)) {
			int minPrice = Integer.parseInt(minPriceStr);
			int maxPrice = Integer.parseInt(maxPriceStr);
			if (minPrice < maxPrice && minPrice > 0) {
				mSendArgs.setMinPrice(minPrice);
				mSendArgs.setMaxPrice(maxPrice);
			}
		}
		if (mBrandAdapter.mPosition != -1) {
			Brand brand = (Brand) mBrandAdapter
					.getItem(mBrandAdapter.mPosition);
			mSendArgs.setBrandId(brand.getId() + "");
		}
		mController.sendAyncMessage(IDiyMessage.ACTION_PRODUCT_LIST, mSendArgs);
		mDrawerLayout.closeDrawer(mSlideView);
	}

	/**
	 * 顶层重置按钮
	 */
	public void resetClick(View v) {
		mSendArgs = new SProductList();
		mController.sendAyncMessage(IDiyMessage.ACTION_PRODUCT_LIST, mSendArgs);
		mDrawerLayout.closeDrawer(mSlideView);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		mBrandAdapter.mPosition = position;
		mBrandAdapter.notifyDataSetChanged();
	}

	@Override
	public void onSortChange(int type) {
		if (type==1) {
			mAllIndicatorTv.setText("综合");
		}else if (type==2) {
			mAllIndicatorTv.setText("新品");
		}else if (type==3) {
			mAllIndicatorTv.setText("评论");
		}
		// 应该讲参数设置到mSendArgs
		mSendArgs.setFilterType(type);
		// 隐藏PoppupWindow
		mProductSortsPopupWindow.dismiss();
		// 而且发送请求
		mController.sendAyncMessage(IDiyMessage.ACTION_PRODUCT_LIST, mSendArgs);
	}

}
