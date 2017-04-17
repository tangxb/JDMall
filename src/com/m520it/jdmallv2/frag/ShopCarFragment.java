package com.m520it.jdmallv2.frag;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.activity.SettleActivity;
import com.m520it.jdmallv2.adapter.ShopCarAdapter;
import com.m520it.jdmallv2.bean.RResult;
import com.m520it.jdmallv2.bean.ShopCarListBean;
import com.m520it.jdmallv2.cons.IDiyMessage;
import com.m520it.jdmallv2.cons.IntentValues;
import com.m520it.jdmallv2.controller.ShopcarController;
import com.m520it.jdmallv2.protocol.IModleChangeListener;
import com.m520it.jdmallv2.protocol.IShopcarDeleteListener;

public class ShopCarFragment extends BaseFragment 
		implements IModleChangeListener, OnItemClickListener, OnCheckedChangeListener
		,IShopcarDeleteListener, OnClickListener{
	
	private ListView mShopcarLv;
	private ShopCarAdapter mShopcarAdapter;
	private CheckBox mAllCheckBox;
	private TextView mAllPriceTv;
	private TextView mSettleTv;
	private ShopcarController mController;
	private Handler mHandler=new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case IDiyMessage.ACTION_SHOW_SHOPCAR_LIST_RESULT:
				handleShowShopcarList((List<ShopCarListBean>)msg.obj);
				break;
			case IDiyMessage.ACTION_DELETE_SHOPCAR_ITEM_RESULT:
				handleDeleteShopcarItem((RResult)msg.obj);
				break;
			}
		}
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_shopcar, null);
	}
	
	protected void handleDeleteShopcarItem(RResult obj) {
		Toast.makeText(getActivity(), obj.isSuccess()?"删除成功":"删除失败", 0).show();
		if (obj.isSuccess()) {
//			删除成功后 刷新界面 TODO Adapter提供一种方法（删除mDatas和mChecked里面对应的那一样）
			mController.sendAyncMessage(IDiyMessage.ACTION_SHOW_SHOPCAR_LIST, 0);
		}
	}

	protected void handleShowShopcarList(List<ShopCarListBean> beans) {
		mShopcarAdapter.setDatas(beans);
		mShopcarAdapter.notifyDataSetChanged();
//		删除成功后 修改总价 总数量
		mAllPriceTv.setText("总额: ￥ "+mShopcarAdapter.getCheckedPrice());
		mSettleTv.setText("去结算("+mShopcarAdapter.getCheckedCount()+")");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		initController();
		initUI();
		
		mController.sendAyncMessage(IDiyMessage.ACTION_SHOW_SHOPCAR_LIST, 0);
	}

	private void initController() {
		mController=new ShopcarController(getActivity());
		mController.setIModelChangeListener(this);
	}

	private void initUI() {
		mShopcarLv=(ListView) getActivity().findViewById(R.id.shopcar_lv);
		mShopcarAdapter=new ShopCarAdapter();
		mShopcarAdapter.setListener(this);
		mShopcarLv.setAdapter(mShopcarAdapter);
		mShopcarLv.setOnItemClickListener(this);
		
		mAllCheckBox=(CheckBox)getActivity().findViewById(R.id.all_cbx);
		mAllCheckBox.setOnCheckedChangeListener(this);
		
		mAllPriceTv=(TextView)getActivity().findViewById(R.id.all_money_tv);
		mSettleTv=(TextView)getActivity().findViewById(R.id.settle_tv);
		mSettleTv.setOnClickListener(this);
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action,values[0]).sendToTarget();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		mShopcarAdapter.setCheckPosition(position);
		mShopcarAdapter.notifyDataSetChanged();
		mAllPriceTv.setText("总额: ￥ "+mShopcarAdapter.getCheckedPrice());
		mSettleTv.setText("去结算("+mShopcarAdapter.getCheckedCount()+")");
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		mShopcarAdapter.checkeAll(isChecked);
		mShopcarAdapter.notifyDataSetChanged();
		mAllPriceTv.setText("总额: ￥ "+mShopcarAdapter.getCheckedPrice());
		mSettleTv.setText("去结算("+mShopcarAdapter.getCheckedCount()+")");
	}

	@Override
	public void onDetele(long id) {
//		做一个网络请求 
		mController.sendAyncMessage(IDiyMessage.ACTION_DELETE_SHOPCAR_ITEM, id);
	}

	@Override
	public void onClick(View v) {
//		启动一个新的SettleActivity
		Intent intent=new Intent(getActivity(),SettleActivity.class);
		intent.putExtra(IntentValues.TO_SETTLE_PRODUCTS, mShopcarAdapter.getCheckedProduct());
		intent.putExtra(IntentValues.TO_SETTLE_TOTALPRICE, mShopcarAdapter.getCheckedPrice());
		startActivity(intent);
	}
	
}
