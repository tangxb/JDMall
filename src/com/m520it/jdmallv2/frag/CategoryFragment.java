package com.m520it.jdmallv2.frag;

import java.util.List;

import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.adapter.TopCategoryAdapter;
import com.m520it.jdmallv2.bean.RBaseCategory;
import com.m520it.jdmallv2.cons.IDiyMessage;
import com.m520it.jdmallv2.controller.CategoryController;
import com.m520it.jdmallv2.protocol.IModleChangeListener;
import com.m520it.jdmallv2.ui.SubCategoryView;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CategoryFragment extends BaseFragment implements IModleChangeListener, OnItemClickListener{
	
	private ListView mTopCategoryLv;
	private TopCategoryAdapter mTopCategoryAdapter;
	private SubCategoryView mSubCategoryView;
	private CategoryController mController;
	private Handler mHandler=new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case IDiyMessage.ACTION_TOP_CATEGORY_RESULT:
				handleShowTopCategory(msg);
				break;
			}
		}
		
	};
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_category, null);
	}
	
	@Override
//	实现获取HomeFragment里面控件
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		initController();
		initUI();
		mController.sendAyncMessage(IDiyMessage.ACTION_TOP_CATEGORY, 0);
	}

	private void initUI() {
		mTopCategoryLv=(ListView) getActivity().findViewById(R.id.top_lv);
		mTopCategoryAdapter=new TopCategoryAdapter();
		mTopCategoryLv.setAdapter(mTopCategoryAdapter);
		mTopCategoryLv.setOnItemClickListener(this);
		
		mSubCategoryView=(SubCategoryView) getActivity().findViewById(R.id.subcategory);
	}

	private void initController() {
		mController=new CategoryController(getActivity());
		mController.setIModelChangeListener(this);
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action,values[0]).sendToTarget();
	}

	private void handleShowTopCategory(android.os.Message msg) {
		mTopCategoryAdapter.setDatas((List<RBaseCategory>) msg.obj);
		mTopCategoryAdapter.notifyDataSetChanged();
//		默认选中第一个  模拟用户点击了某个按钮
		mTopCategoryLv.performItemClick(null, 0, 0);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
//		记录当前选中的位置 刷新
		mTopCategoryAdapter.mPosition=position;
		mTopCategoryAdapter.notifyDataSetChanged();
//		将当前1级分类的ID传递给右边的视图
		mSubCategoryView.show(mTopCategoryAdapter.getItem(position));
	}
}
