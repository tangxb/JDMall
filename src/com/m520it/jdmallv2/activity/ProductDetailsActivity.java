package com.m520it.jdmallv2.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.bean.RResult;
import com.m520it.jdmallv2.cons.IDiyMessage;
import com.m520it.jdmallv2.cons.IntentValues;
import com.m520it.jdmallv2.controller.ProductController;
import com.m520it.jdmallv2.frag.ProductCommentView;
import com.m520it.jdmallv2.frag.ProductDetailsView;
import com.m520it.jdmallv2.frag.ProductIntroduceView;
import com.m520it.jdmallv2.protocol.IModleChangeListener;

public class ProductDetailsActivity extends BaseActivity 
					implements OnClickListener, OnPageChangeListener, IModleChangeListener {
	
	private View mIntroduceView;
	private View mDetailsView;
	private View mCommentView;
	private ViewPager mContainerVp;
	private ContainerAdapter mContainerAdapter;
	public long mPid;
	public int mBuyCount=1;
	public String mPversion="";
	private ProductController mController;
	private Handler mHanlder=new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case IDiyMessage.ACTION_ADD_2_SHOPCAR_RESULT:
					handleAdd2ShopcarResult((RResult)msg.obj);
					break;
			}
		}
		
	};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_details);
		mPid = getIntent().getLongExtra(IntentValues.PRODUCTID, -1);
		if (mPid==-1) {
			Toast.makeText(this, "数据出现异常..", 0).show();
			finish();
			return;
		}
		initController();
		initUI();
	}

	protected void handleAdd2ShopcarResult(RResult result) {
		Toast.makeText(this, 
				result.isSuccess()?"加入购物车成功":"加入购物车失败"+result.getErrorMsg(), 
						0).show();
	}

	private void initController() {
		mController=new ProductController(this);
		mController.setIModelChangeListener(this);
	}

	private void initUI() {
		findViewById(R.id.introduce_ll).setOnClickListener(this);
		findViewById(R.id.details_ll).setOnClickListener(this);
		findViewById(R.id.comment_ll).setOnClickListener(this);
		mIntroduceView=findViewById(R.id.introduce_view);
		mDetailsView=findViewById(R.id.details_view);
		mCommentView=findViewById(R.id.comment_tv);
		
		mContainerVp=(ViewPager) findViewById(R.id.vp);
		mContainerAdapter=new ContainerAdapter(getSupportFragmentManager());
		mContainerVp.setAdapter(mContainerAdapter);
		mContainerVp.setOnPageChangeListener(this);
	}
	
	private ArrayList<Fragment> mFragments=new ArrayList<Fragment>();
	class ContainerAdapter extends FragmentPagerAdapter{
		
		public ContainerAdapter(FragmentManager fm) {
			super(fm);
			mFragments.add(new ProductIntroduceView());
			mFragments.add(new ProductDetailsView());
			mFragments.add(new ProductCommentView());
		}

		@Override
		public Fragment getItem(int position) {
			return mFragments.get(position);
		}

		@Override
		public int getCount() {
			return mFragments.size();
		}
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.introduce_ll:
				defaultIndicator();
				mIntroduceView.setVisibility(View.VISIBLE);
				mContainerVp.setCurrentItem(0, true);
				break;
			case R.id.details_ll:
				defaultIndicator();
				mDetailsView.setVisibility(View.VISIBLE);
				mContainerVp.setCurrentItem(1, true);
				break;
			case R.id.comment_ll:
				defaultIndicator();
				mCommentView.setVisibility(View.VISIBLE);
				mContainerVp.setCurrentItem(2, true);
				break;
		}
	}
	
	public void defaultIndicator(){
		mIntroduceView.setVisibility(View.INVISIBLE);
		mDetailsView.setVisibility(View.INVISIBLE);
		mCommentView.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int position) {
		defaultIndicator();
		switch (position) {
		case 0:
			mIntroduceView.setVisibility(View.VISIBLE);
			break;
		case 1:
			mDetailsView.setVisibility(View.VISIBLE);
			break;
		case 2:
			mCommentView.setVisibility(View.VISIBLE);
			break;
		}
	}
	
	public void add2ShopCar(View v){
//		加入购物车 TODO
		if (!TextUtils.isEmpty(mPversion)) {
			mController.sendAyncMessage(IDiyMessage.ACTION_ADD_2_SHOPCAR, mPid,mBuyCount,mPversion);
		}else {
			Toast.makeText(this, "请选择版本!", 0).show();
		}
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHanlder.obtainMessage(action,values[0]).sendToTarget();
	}
	
}
