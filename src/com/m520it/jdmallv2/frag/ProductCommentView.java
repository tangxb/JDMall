package com.m520it.jdmallv2.frag;

import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.activity.ProductDetailsActivity;
import com.m520it.jdmallv2.adapter.CommentAdapter;
import com.m520it.jdmallv2.bean.CommentBean;
import com.m520it.jdmallv2.bean.CommentCount;
import com.m520it.jdmallv2.cons.IDiyMessage;
import com.m520it.jdmallv2.controller.ProductController;
import com.m520it.jdmallv2.protocol.IModleChangeListener;

public class ProductCommentView extends Fragment implements IModleChangeListener, OnClickListener {
	
	private ProductController mController;
	private TextView mAllCommentTv;
	private TextView mPositiveCommentTv;
	private TextView mNagetiveCommentTv;
	private TextView mCenterCommentTv;
	private TextView mHasImageCommentTv;
	private TextView mAllCommentTip;
	private TextView mPositiveCommentTip;
	private TextView mCenterCommentTip;
	private TextView mNagetiveCommentTip;
	private TextView mHasImageCommentTip;
	
	private ListView mCommentLv;
	private CommentAdapter mCommentAdapter;
	private long mPid;
	private Handler mHanlder=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case IDiyMessage.ACTION_COMMENT_COUNT_RESULT:
				handleCommentCount(msg.obj);
				break;
			case IDiyMessage.ACTION_GET_COMMENT_BY_TYPE_RESULT:
				handleCommentWithType((List<CommentBean>)msg.obj);
				break;
			}
		}
	};
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_product_comment, null);
	}
	
	protected void handleCommentWithType(List<CommentBean> beans) {
		mCommentAdapter.setDatas(beans);
		mCommentAdapter.notifyDataSetChanged();
	}

	protected void handleCommentCount(Object obj) {
		if (obj!=null) {
			CommentCount bean=(CommentCount) obj;
			mAllCommentTv.setText(bean.getAllComment()+"");
			mPositiveCommentTv.setText(bean.getPositiveCom()+"");
			mCenterCommentTv.setText(bean.getModerateCom()+"");
			mNagetiveCommentTv.setText(bean.getNegativeCom()+"");
			mHasImageCommentTv.setText(bean.getHasImgCom()+"");
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mPid = ((ProductDetailsActivity)getActivity()).mPid;
		initController();
		initUI();
		mController.sendAyncMessage(IDiyMessage.ACTION_COMMENT_COUNT, mPid);
		defaultShowComment();
	}

	private void defaultShowComment() {
		mAllCommentTv.setSelected(true);
		mAllCommentTip.setSelected(true);
		//默认界面需要展示的内容请求
		mController.sendAyncMessage(IDiyMessage.ACTION_GET_COMMENT_BY_TYPE, mPid,ProductController.TYPE_ALL);
	}

	private void initController() {
		mController=new ProductController(getActivity());
		mController.setIModelChangeListener(this);
	}

	private void initUI() {
		mAllCommentTv=(TextView)getActivity().findViewById(R.id.all_comment_tv);
		mPositiveCommentTv=(TextView)getActivity().findViewById(R.id.positive_comment_tv);
		mCenterCommentTv=(TextView)getActivity().findViewById(R.id.center_comment_tv);
		mNagetiveCommentTv=(TextView)getActivity().findViewById(R.id.nagetive_comment_tv);
		mHasImageCommentTv=(TextView)getActivity().findViewById(R.id.has_image_comment_tv);

		mAllCommentTip=(TextView)getActivity().findViewById(R.id.all_comment_tip);
		mPositiveCommentTip=(TextView)getActivity().findViewById(R.id.positive_comment_tip);
		mCenterCommentTip=(TextView)getActivity().findViewById(R.id.center_comment_tip);
		mNagetiveCommentTip=(TextView)getActivity().findViewById(R.id.nagetive_comment_tip);
		mHasImageCommentTip=(TextView)getActivity().findViewById(R.id.has_image_comment_tip);
		
		getActivity().findViewById(R.id.all_comment_ll).setOnClickListener(this);
		getActivity().findViewById(R.id.positive_comment_ll).setOnClickListener(this);
		getActivity().findViewById(R.id.center_comment_ll).setOnClickListener(this);
		getActivity().findViewById(R.id.nagetive_comment_ll).setOnClickListener(this);
		getActivity().findViewById(R.id.has_image_comment_ll).setOnClickListener(this);
		
		mCommentLv=(ListView) getActivity().findViewById(R.id.commentlv);
		mCommentAdapter=new CommentAdapter();
		mCommentLv.setAdapter(mCommentAdapter);
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHanlder.obtainMessage(action,values[0]).sendToTarget();
	}

	@Override
	public void onClick(View v) {
		defaultIndicator();
		switch (v.getId()) {
			case R.id.all_comment_ll:
				mAllCommentTv.setSelected(true);
				mAllCommentTip.setSelected(true);
				mController.sendAyncMessage(IDiyMessage.ACTION_GET_COMMENT_BY_TYPE, mPid,ProductController.TYPE_ALL);
				break;
			case R.id.positive_comment_ll:
				mPositiveCommentTv.setSelected(true);
				mPositiveCommentTip.setSelected(true);
				mController.sendAyncMessage(IDiyMessage.ACTION_GET_COMMENT_BY_TYPE, mPid,ProductController.TYPE_GOOD);
				break;
			case R.id.center_comment_ll:
				mCenterCommentTv.setSelected(true);
				mCenterCommentTip.setSelected(true);
				mController.sendAyncMessage(IDiyMessage.ACTION_GET_COMMENT_BY_TYPE, mPid,ProductController.TYPE_CENTER);
				break;
			case R.id.nagetive_comment_ll:
				mNagetiveCommentTv.setSelected(true);
				mNagetiveCommentTip.setSelected(true);
				mController.sendAyncMessage(IDiyMessage.ACTION_GET_COMMENT_BY_TYPE, mPid,ProductController.TYPE_BAD);
				break;
			case R.id.has_image_comment_ll:
				mHasImageCommentTv.setSelected(true);
				mHasImageCommentTip.setSelected(true);
				mController.sendAyncMessage(IDiyMessage.ACTION_GET_COMMENT_BY_TYPE, mPid,ProductController.TYPE_HASIMG);
				break;
		}
	}
	
	private void defaultIndicator(){
		mAllCommentTv.setSelected(false);
		mPositiveCommentTv.setSelected(false);
		mCenterCommentTv.setSelected(false);
		mNagetiveCommentTv.setSelected(false);
		mHasImageCommentTv.setSelected(false);

		mAllCommentTip.setSelected(false);
		mPositiveCommentTip.setSelected(false);
		mCenterCommentTip.setSelected(false);
		mNagetiveCommentTip.setSelected(false);
		mHasImageCommentTip.setSelected(false);
	}
	
}
