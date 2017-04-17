package com.m520it.jdmallv2.activity;

import java.util.ArrayList;
import java.util.List;

import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.frag.AllOrderFragment;
import com.m520it.jdmallv2.frag.WaitPayFragment;
import com.m520it.jdmallv2.frag.WaitReceiveFragment;
import com.m520it.jdmallv2.frag.WaitSureFragment;
import com.m520it.jdmallv2.protocol.IViewContainer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;

public class OrderListActivity extends BaseActivity implements OnClickListener,
		OnPageChangeListener {

	private View mAllOrderView;
	private View mWaitPayView;
	private View mWaitReciveView;
	private View mWaitSureView;
	private ViewPager mContainerVp;
	private ContainerAdapter mContainerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_list);
		initUI();
	}

	private void initUI() {
		findViewById(R.id.all_order_ll).setOnClickListener(this);
		findViewById(R.id.wait_pay_ll).setOnClickListener(this);
		findViewById(R.id.wait_receive_ll).setOnClickListener(this);
		findViewById(R.id.wait_sure_ll).setOnClickListener(this);
		mAllOrderView = findViewById(R.id.all_order_view);
		mWaitPayView = findViewById(R.id.wait_pay_view);
		mWaitReciveView = findViewById(R.id.wait_receive_view);
		mWaitSureView = findViewById(R.id.wait_sure_view);

		mContainerVp = (ViewPager) findViewById(R.id.vp);
		mContainerAdapter = new ContainerAdapter(getSupportFragmentManager());
		mContainerVp.setAdapter(mContainerAdapter);
		mContainerVp.setOnPageChangeListener(this);
	}

	private List<Fragment> mFragments = new ArrayList<Fragment>();
	class ContainerAdapter extends FragmentPagerAdapter {

		public ContainerAdapter(FragmentManager fm) {
			super(fm);
			mFragments.add(new AllOrderFragment());
			mFragments.add(new WaitPayFragment());
			mFragments.add(new WaitReceiveFragment());
			mFragments.add(new WaitSureFragment());
		}

		@Override
		public Fragment getItem(int arg0) {
			return mFragments.get(arg0);
		}

		@Override
		public int getCount() {
			return mFragments.size();
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.all_order_ll:
			showIndicator(mAllOrderView);
			mContainerVp.setCurrentItem(0, true);
			((IViewContainer)(mFragments.get(0))).show();
			break;
		case R.id.wait_pay_ll:
			showIndicator(mWaitPayView);
			mContainerVp.setCurrentItem(1, true);
			((IViewContainer)(mFragments.get(1))).show();
			break;
		case R.id.wait_receive_ll:
			showIndicator(mWaitReciveView);
			mContainerVp.setCurrentItem(2, true);
			((IViewContainer)(mFragments.get(2))).show();
			break;
		case R.id.wait_sure_ll:
			showIndicator(mWaitSureView);
			mContainerVp.setCurrentItem(3, true);
			((IViewContainer)(mFragments.get(3))).show();
			break;
		}
	}

	private void showIndicator(View v) {
		mAllOrderView.setVisibility(View.INVISIBLE);
		mWaitPayView.setVisibility(View.INVISIBLE);
		mWaitReciveView.setVisibility(View.INVISIBLE);
		mWaitSureView.setVisibility(View.INVISIBLE);
		v.setVisibility(View.VISIBLE);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int position) {
		switch (position) {
		case 0:
			showIndicator(mAllOrderView);
			break;
		case 1:
			showIndicator(mWaitPayView);
			break;
		case 2:
			showIndicator(mWaitReciveView);
			break;
		case 3:
			showIndicator(mWaitSureView);
			break;
		}
	}

}
