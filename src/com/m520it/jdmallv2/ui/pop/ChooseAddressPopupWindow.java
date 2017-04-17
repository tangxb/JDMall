package com.m520it.jdmallv2.ui.pop;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.bean.AddressBean;
import com.m520it.jdmallv2.cons.IDiyMessage;
import com.m520it.jdmallv2.controller.ShopcarController;
import com.m520it.jdmallv2.protocol.IModleChangeListener;
import com.m520it.jdmallv2.protocol.IaddressChooseListener;

public class ChooseAddressPopupWindow 
		implements IPopupWindown, OnClickListener, IModleChangeListener {
	
	private PopupWindow mPopWindow;
	private Context mContext;
	private ListView mDistLv;
	private ListView mCityLv;
	private ListView mProvinceLv;
	private List<AddressBean> mProvinces;
	private List<AddressBean> mCities;
	private List<AddressBean> mAreas;
//	创建三个对象将当前选择的值保存起来 以便Activity在显示或者点击保存按钮的时候将当前省市区code发送给后台
	public AddressBean mProvince;
	public AddressBean mCity;
	public AddressBean mArea;
	private IaddressChooseListener mListener;
	
	public void setListener(IaddressChooseListener mListener) {
		this.mListener = mListener;
	}

	private ShopcarController mController;
	private Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case IDiyMessage.ACTION_PROVINCE_RESULT:
				handleShowProvinces((List<AddressBean>)msg.obj);
				break;
			case IDiyMessage.ACTION_CITY_RESULT:
				handleShowCities((List<AddressBean>)msg.obj);
				break;
			case IDiyMessage.ACTION_DIST_RESULT:
				handleShowAreas((List<AddressBean>)msg.obj);
				break;
			}
		}
	};
	

	public ChooseAddressPopupWindow(Context c) {
		mContext=c;
		initController();
		initView();
	}

	protected void handleShowAreas(List<AddressBean> obj) {
//		创建一个Adapter  绑定到mProvinceLv里面
		mAreas=obj;
		ArrayList<String> datas=new ArrayList<String>();
		if (obj.size()!=0) {
			for (int i = 0; i < obj.size(); i++) {
				datas.add(obj.get(i).getName());
			}
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,
					android.R.id.text1, datas);
			mDistLv.setAdapter(arrayAdapter);
		}
	}

	protected void handleShowCities(List<AddressBean> obj) {
//		创建一个Adapter  绑定到mProvinceLv里面
		mCities=obj;
		ArrayList<String> datas=new ArrayList<String>();
		if (obj.size()!=0) {
			for (int i = 0; i < obj.size(); i++) {
				datas.add(obj.get(i).getName());
			}
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,
					android.R.id.text1, datas);
			mCityLv.setAdapter(arrayAdapter);
		}
	}

	protected void handleShowProvinces(List<AddressBean> obj) {
//		创建一个Adapter  绑定到mProvinceLv里面
		mProvinces=obj;
		ArrayList<String> datas=new ArrayList<String>();
		if (obj.size()!=0) {
			for (int i = 0; i < obj.size(); i++) {
				datas.add(obj.get(i).getName());
			}
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,
					android.R.id.text1, datas);
			mProvinceLv.setAdapter(arrayAdapter);
		}
	}

	private void initController() {
		mController=new ShopcarController(mContext);
		mController.setIModelChangeListener(this);
	}

	@Override
	public void initView() {
//		1.初始化布局   获取PopWindow内部所有控件
		LayoutInflater inflater=LayoutInflater.from(mContext);
		View contentView = inflater.inflate(R.layout.address_pop_view, null);
		TextView mSureTv=(TextView)contentView.findViewById(R.id.submit_tv);
		mSureTv.setOnClickListener(this);
		mProvinceLv=(ListView)contentView.findViewById(R.id.province_lv);
		mProvinceLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String provinceCode = mProvinces.get(position).getCode();
				mProvince=mProvinces.get(position);
//				当再次选择省的时候 需要将原来的市区的值置为空
				mCity=null;
				mArea=null;
//				发送一个异步请求
				mController.sendAyncMessage(IDiyMessage.ACTION_CITY, provinceCode);
			}
		});
		mCityLv=(ListView)contentView.findViewById(R.id.city_lv);
		mCityLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String cityCode = mCities.get(position).getCode();
				mCity=mCities.get(position);
				mArea=null;
//				发送一个异步请求
				mController.sendAyncMessage(IDiyMessage.ACTION_DIST, cityCode);
			}
		});
		mDistLv=(ListView)contentView.findViewById(R.id.dist_lv);
		mDistLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mArea=mAreas.get(position);
			}
		});
		
//		希望在PopupWindow创建的时候 就初始化省的列表
//		发送一个网络请求获取省的数据
		mController.sendAyncMessage(IDiyMessage.ACTION_PROVINCE, 0);
		
//		2.初始化PopWindow
		mPopWindow=new PopupWindow(contentView, -1, -1);
//		内部默认不可点击
		mPopWindow.setFocusable(true);
//		外部又不可以点击了
		mPopWindow.setOutsideTouchable(true);
		mPopWindow.setBackgroundDrawable(new ColorDrawable());
//		刷新界面
		mPopWindow.update();
	}

	@Override
	public void show(View anchor) {
		if (mPopWindow!=null) {
			mPopWindow.showAtLocation(anchor, Gravity.CENTER, 0, 0);
		}
	}

	@Override
	public void dismiss() {
		if (mPopWindow!=null&&mPopWindow.isShowing()) {
			mPopWindow.dismiss();
		}
	}

	@Override
	public void onClick(View v) {
		if (mListener!=null) {
			mListener.onAddressChoose();
		}
		dismiss();
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action,values[0]).sendToTarget();
	}


}
