package com.m520it.jdmallv2.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.loopj.android.image.SmartImageView;
import com.m520it.jdmallv2.JDApplication;
import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.bean.RAddOrderResult;
import com.m520it.jdmallv2.bean.RResult;
import com.m520it.jdmallv2.bean.ReceiverAddress;
import com.m520it.jdmallv2.bean.SAddOrderParams;
import com.m520it.jdmallv2.bean.SOerderParamsWithProduct;
import com.m520it.jdmallv2.bean.ShopCarListBean;
import com.m520it.jdmallv2.cons.IDiyMessage;
import com.m520it.jdmallv2.cons.IntentValues;
import com.m520it.jdmallv2.cons.NetworkConst;
import com.m520it.jdmallv2.controller.ShopcarController;
import com.m520it.jdmallv2.protocol.IAddOrderListener;
import com.m520it.jdmallv2.protocol.IModleChangeListener;
import com.m520it.jdmallv2.ui.pop.AddOrderPopupWindow;

/**
 * @author Administrator
 * 
 */
public class SettleActivity extends BaseActivity implements
		IModleChangeListener, OnClickListener,IAddOrderListener {

	private TextView mReceiverNameTv;
	private TextView mReceiverPhoneTv;
	private TextView mReceiverAddressTv;
	private List<ShopCarListBean> mShopCarListBeans;
	private double mTotalPrice;
	private TextView mAllPriceValTv;
	private TextView mPayMoneyTv;
	private TextView mTotalSizeTv;
	private LinearLayout mProductContainerLl;
	private ShopcarController mController;
	private TextView mPayOnlineTv;
	private TextView mPayWhenGetTv;
	private ReceiverAddress mAddress;
	private int mPayType=-1;
	public static final int TO_CHOOSE_ADDRESS_REQ=0x001;
	public static final int TO_CHOOSE_ADDRESS_RES=0x002;
	public static final int TO_ADD_ADDRESS_REQ=0x003;
	public static final int TO_ADD_ADDRESS_RES=0x004;
	
	
	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case IDiyMessage.ACTION_GET_DEFAULT_RECEIVER_ADDRESS_RESULT:
				handleShowDefaultReceiverAddress((List<ReceiverAddress>) msg.obj);
				break;
			case IDiyMessage.ACTION_ADD_ORDER_RESULT:
				handleAddOrderPayWhenGet((RResult) msg.obj);
				break;
			case IDiyMessage.ACTION_ADD_ORDER_ONLINE_RESULT:
				handlAddOrderOnline((RAddOrderResult)msg.obj);
				break;
			}
		}
	};
	private AddOrderPopupWindow mAddOrderPopupWindow;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settle);
		// 1.获取前一个页面传递过来的数据
		mShopCarListBeans = (List<ShopCarListBean>) getIntent()
				.getSerializableExtra(IntentValues.TO_SETTLE_PRODUCTS);
		mTotalPrice = getIntent().getDoubleExtra(
				IntentValues.TO_SETTLE_TOTALPRICE, 0);
		if (mShopCarListBeans == null || mTotalPrice == 0) {
			Toast.makeText(this, "很抱歉 系统出错了...", 0).show();
			finish();
			return;
		}
		initController();
		initUI();
		mController.sendAyncMessage(
				IDiyMessage.ACTION_GET_DEFAULT_RECEIVER_ADDRESS, 0);
	}

//	如果成功的 弹出一个POP  失败 提示创建订单失败 网络异常 TODO
	protected void handlAddOrderOnline(RAddOrderResult obj) {
		if (obj==null) {
			Toast.makeText(this, "服务器异常", 0).show();
			return ;
		}
		mAddOrderPopupWindow=new AddOrderPopupWindow(this);
		mAddOrderPopupWindow.setListener(this);
//		填充数据到POP
		mAddOrderPopupWindow.setDatas(obj);
		mAddOrderPopupWindow.show(findViewById(R.id.content_view));
	}

	protected void handleAddOrderPayWhenGet(RResult obj) {
		Toast.makeText(this, obj.isSuccess()?"下单成功 请到订单列表里面查看订单":"下单失败", 0).show();
		if (obj.isSuccess()) {
//			1 关闭AC
			finish();
//			2 删除购物车商品 找到传进来的商品信息 逐一删除   删除的是购物车列表里的商品 所以 应该传入的是商品在购物车表里面的id而不是商品的id
			for (int i = 0; i < mShopCarListBeans.size(); i++) {
				mController.sendAyncMessage(IDiyMessage.ACTION_DELETE_SHOPCAR_ITEM,mShopCarListBeans.get(i).getId());
			}
		}
	}

	protected void handleShowDefaultReceiverAddress(List<ReceiverAddress> params) {
		// 判断到底有没数据 如果有数据 显示上面的LL 设置数据 如果没数据 显示下面的Ll
		findViewById(R.id.has_receiver_rl).setVisibility(
				params.size() == 0 ? View.GONE : View.VISIBLE);
		findViewById(R.id.no_receiver_rl).setVisibility(
				params.size() == 0 ? View.VISIBLE : View.GONE);
		if (params.size() != 0) {
			mAddress = params.get(0);
			mReceiverNameTv.setText(mAddress.getReceiverName());
			mReceiverPhoneTv.setText(mAddress.getReceiverPhone());
			mReceiverAddressTv.setText(mAddress.getReceiverAddress());
		}
	}

	private void initController() {
		mController = new ShopcarController(this);
		mController.setIModelChangeListener(this);
	}

	private void initUI() {
		mReceiverNameTv = (TextView) findViewById(R.id.name_tv);
		mReceiverPhoneTv = (TextView) findViewById(R.id.phone_tv);
		mReceiverAddressTv = (TextView) findViewById(R.id.address_tv);

		mAllPriceValTv = (TextView) findViewById(R.id.all_price_val_tv);
		mPayMoneyTv = (TextView) findViewById(R.id.pay_money_tv);
		mTotalSizeTv = (TextView) findViewById(R.id.total_psize_tv);
		mAllPriceValTv.setText("￥" + mTotalPrice);
		mPayMoneyTv.setText("实付款: ￥" + mTotalPrice);
		mTotalSizeTv.setText("共" + mShopCarListBeans.size() + "件");
		mProductContainerLl = (LinearLayout) findViewById(R.id.product_container_ll);
		initNeededBuyProductList();
		
		mPayOnlineTv = (TextView) findViewById(R.id.pay_online_tv);
		mPayWhenGetTv = (TextView) findViewById(R.id.pay_whenget_tv);
		mPayOnlineTv.setOnClickListener(this);
		mPayWhenGetTv.setOnClickListener(this);
	}

	private void initNeededBuyProductList() {
		// 判断传过来的数据不能超过mProductContainerLl子控件的长度
		// 1.知道到底为多少个子控件添加图片内容
		int count = mShopCarListBeans.size() > mProductContainerLl
				.getChildCount() ? mProductContainerLl.getChildCount()
				: mShopCarListBeans.size();
		for (int i = 0; i < count; i++) {
			LinearLayout childLl = (LinearLayout) mProductContainerLl
					.getChildAt(i);
			// 修改商品的图片控件
			SmartImageView smiv = (SmartImageView) childLl.getChildAt(0);
			smiv.setImageUrl(NetworkConst.BASE_URL
					+ mShopCarListBeans.get(i).getPimageUrl());
			// 修改商品的购买数量
			TextView buyCountTv = (TextView) childLl.getChildAt(1);
			buyCountTv.setText("x " + mShopCarListBeans.get(i).getBuyCount());
		}
	}

	/**
	 * 选择地址
	 */
	public void chooseAddress(View v) {
		Intent intent=new Intent(this,ChooseAddressActivity.class);
		startActivityForResult(intent, TO_CHOOSE_ADDRESS_REQ);
	}

	/**
	 * 添加收货人地址
	 */
	public void addAddress(View v) {
		Intent intent=new Intent(this,AddAddressActivity.class);
		startActivityForResult(intent, TO_ADD_ADDRESS_REQ);
	}
	
	/**
	 * 提交订单 
	 */
	public void submitClick(View v){
//		判断一下收货人有没选择
		int visibility = findViewById(R.id.has_receiver_rl).getVisibility();
		if (visibility!=View.VISIBLE) {
			Toast.makeText(this, "请选择收货人地址", 0).show();
			return;
		}
//		再判断一下 是否选择了支付方式
		if (mPayType==-1) {
			Toast.makeText(this, "请选择支付方式", 0).show();
			return;
//			订单的状态：提交订单(待支付)--》付款(等待发货)--》商家发货/物流信息（等待收货）---》确认收货---》评价（完成订单）--》退货 只退款/退货退款
		}else if (mPayType==0) {
//			在线支付   发送网络请求生成一笔订单
			String jsonString = JSON.toJSONString(initAddOrderParams(0));
			mController.sendAyncMessage(IDiyMessage.ACTION_ADD_ORDER_ONLINE, jsonString);
		}else if (mPayType==1) {
//			货到付款  创建一笔订单--》如果后台返回创建订单成功---》直接显示订单详情
			String jsonString = JSON.toJSONString(initAddOrderParams(1));
			mController.sendAyncMessage(IDiyMessage.ACTION_ADD_ORDER, jsonString);
		}
	}

	/**
	 *	初始化添加订单参数
	 */
	private SAddOrderParams initAddOrderParams(int payway) {
		ArrayList<SOerderParamsWithProduct> beans=new ArrayList<SOerderParamsWithProduct>();
		for (int i = 0; i < mShopCarListBeans.size(); i++) {
			ShopCarListBean bean = mShopCarListBeans.get(i);
			beans.add(new SOerderParamsWithProduct(bean.getBuyCount(), bean.getPversion(), bean.getPid()));
		}
		SAddOrderParams result=new SAddOrderParams();
		result.setProducts(beans);
		result.setAddrId(mAddress.getId());
		result.setPayWay(payway);
		long userId = Long.parseLong(((JDApplication)getApplication()).mUserInfo.getId());
		result.setUserId(userId);
		return result;
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action, values[0]).sendToTarget();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pay_online_tv:
			mPayOnlineTv.setSelected(true);
			mPayWhenGetTv.setSelected(false);
			mPayType=0;
			break;
		case R.id.pay_whenget_tv:
			mPayOnlineTv.setSelected(false);
			mPayWhenGetTv.setSelected(true);
			mPayType=1;
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int req, int res, Intent arg2) {
		super.onActivityResult(req, res, arg2);
		if (req==TO_CHOOSE_ADDRESS_REQ&&res==TO_CHOOSE_ADDRESS_RES) {
//			将数据返回
			mAddress=(ReceiverAddress) arg2.getSerializableExtra(IntentValues.RETURN_CHOOSE_ADDRESS);
//			回显
			handleShowDefaultReceiverAddress(mAddress);
		}else if (req==TO_ADD_ADDRESS_REQ&&res==TO_ADD_ADDRESS_RES) {
//			将数据返回
			mAddress=(ReceiverAddress) arg2.getSerializableExtra(IntentValues.RETURN_CHOOSE_ADDRESS);
//			回显
			handleShowDefaultReceiverAddress(mAddress);
		}
	}
	
	protected void handleShowDefaultReceiverAddress(ReceiverAddress params) {
		// 判断到底有没数据 如果有数据 显示上面的LL 设置数据 如果没数据 显示下面的Ll
		findViewById(R.id.has_receiver_rl).setVisibility(
				params==null ? View.GONE : View.VISIBLE);
		findViewById(R.id.no_receiver_rl).setVisibility(
				params==null ? View.VISIBLE : View.GONE);
		if (params!=null) {
			mReceiverNameTv.setText(params.getReceiverName());
			mReceiverPhoneTv.setText(params.getReceiverPhone());
			mReceiverAddressTv.setText(params.getReceiverAddress());
		}
	}

	@Override
	public void onSure(String tn) {
		mAddOrderPopupWindow.dismiss();
//		打开一个AC AC自己去访问后台携带TN
		Intent intent=new Intent(this,AlipayActivity.class);
		intent.putExtra(IntentValues.TN, tn);
		startActivity(intent);
		finish();
	}

	@Override
	public void onCancle() {
		Toast.makeText(this, "请到订单列表页面继续支付", 0).show();
		mAddOrderPopupWindow.dismiss();
		finish();
	}

}
