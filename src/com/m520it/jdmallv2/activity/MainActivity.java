package com.m520it.jdmallv2.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.frag.CategoryFragment;
import com.m520it.jdmallv2.frag.HomeFragment;
import com.m520it.jdmallv2.frag.MineFragment;
import com.m520it.jdmallv2.frag.ShopCarFragment;
import com.m520it.jdmallv2.protocol.IBottomItemClickListener;
import com.m520it.jdmallv2.ui.BottomBar;

public class MainActivity extends BaseActivity implements IBottomItemClickListener{
	
	private BottomBar mBottomBar;
	private HomeFragment mHomeFragment;
	private CategoryFragment mCategoryFragment;
	private ShopCarFragment mShopCarFragment;
	private MineFragment mMineFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		1.获取BottomBar
		mBottomBar=(BottomBar)findViewById(R.id.bottom_bar_id);
//		2.将IBottomItemClickListener 设置给BottomBar
		mBottomBar.setIBottomBarClickListener(this);
//		3.在BottomBar点击的时候调用onClick.   
//			BottomBar就间接调用 IBottomItemClickListener里面的onItemClick
//		4. MainActivity就会实现该方法
//		5. 切换Fragment.
		

//		实例化Fragment
		mHomeFragment=new HomeFragment();
		mCategoryFragment=new CategoryFragment();
		mShopCarFragment=new ShopCarFragment();
		mMineFragment=new MineFragment();
//		默认显示的Fragment
		replaceFrgament(mHomeFragment);
	}
	
	private void replaceFrgament(Fragment f){
		FragmentManager fmanager = getFragmentManager();
		FragmentTransaction transaction = fmanager.beginTransaction();
		transaction.replace(R.id.container_rl, f);
//		预防状态丢失报错
		transaction.commitAllowingStateLoss();
	}

	@Override
//	点击事件在BottomBar里面  管理顶部界面在MainActivity
	public void onItemClick(View v) {
		switch (v.getId()) {
		case R.id.home_ll:
			replaceFrgament(mHomeFragment);
			break;
		case R.id.category_ll:
			replaceFrgament(mCategoryFragment);
			break;
		case R.id.shopcar_ll:
			replaceFrgament(mShopCarFragment);
			break;
		case R.id.mine_ll:
			replaceFrgament(mMineFragment);
			break;
		}
	}
	
}
