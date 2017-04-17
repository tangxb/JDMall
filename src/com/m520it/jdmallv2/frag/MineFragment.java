package com.m520it.jdmallv2.frag;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.m520it.jdmallv2.JDApplication;
import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.activity.OrderListActivity;
import com.m520it.jdmallv2.bean.RLogin;
import com.m520it.jdmallv2.cons.IDiyMessage;
import com.m520it.jdmallv2.controller.MineController;
import com.m520it.jdmallv2.protocol.IModleChangeListener;
import com.m520it.jdmallv2.util.ActivityUtil;

public class MineFragment extends BaseFragment implements OnClickListener, IModleChangeListener{
	
	private TextView mUserNameTv;
	private TextView mUserLevelTv;
	private TextView mWaitPayTv;
	private TextView mWaitReceiveTv;
	private MineController mController;
	private Handler mHandler=new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case IDiyMessage.ACTION_DELETE_USER_RESULT:
				handleLogout();
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_mine, null);
	}
	
	/**
	 * 删除完数据库了 现在实现退出登录
	 */
	protected void handleLogout() {
//		getActivity()  MainActivity
		getActivity().finish();
	}

	@Override
//	实现获取HomeFragment里面控件  在Activity  onCreate()之后
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		initController();
		initUI();
	}

	private void initController() {
		mController=new MineController(getActivity());
		mController.setIModelChangeListener(this);
	}

	private void initUI() {
		mUserNameTv=(TextView)getActivity().findViewById(R.id.user_name_tv);
		mUserLevelTv=(TextView)getActivity().findViewById(R.id.user_level_tv);
		mWaitPayTv=(TextView)getActivity().findViewById(R.id.wait_pay_tv);
		mWaitReceiveTv=(TextView)getActivity().findViewById(R.id.wait_receive_tv);
		
		RLogin loginBean = ((JDApplication)(getActivity().getApplication())).mUserInfo;
		mUserNameTv.setText(loginBean.getUserName());
		initUserLevel(loginBean.getUserLevel());
		mWaitPayTv.setText(loginBean.getWaitPayCount()+"");
		mWaitReceiveTv.setText(loginBean.getWaitReceiveCount()+"");
		
		Button logoutBtn=(Button) getActivity().findViewById(R.id.logout_btn);
		logoutBtn.setOnClickListener(this);
		
		getActivity().findViewById(R.id.mime_order).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ActivityUtil.start(getActivity(), OrderListActivity.class,false);
			}
		});
	}

//	1注册会员2铜牌会员3银牌会员4金牌会员5钻石会员
	private void initUserLevel(int level) {
		switch (level) {
			case 2:
				mUserLevelTv.setText("铜牌会员");
				break;
			case 3:
				mUserLevelTv.setText("银牌会员");
				break;
			case 4:
				mUserLevelTv.setText("金牌会员");
				break;
			case 5:
				mUserLevelTv.setText("钻石会员");
				break;
			case 1:
			default:
				mUserLevelTv.setText("注册会员");
				break;
		}
	}

	@Override
	public void onClick(View v) {
//		退出登录   1.删除数据库内容  2.退出MainActivity
		mController.sendAyncMessage(IDiyMessage.ACTION_DELETE_USER, 0);
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action, values[0]).sendToTarget();
	}
	
	
}
