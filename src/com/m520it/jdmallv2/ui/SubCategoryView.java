package com.m520it.jdmallv2.ui;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.activity.ProductListActivity;
import com.m520it.jdmallv2.bean.RBaseCategory;
import com.m520it.jdmallv2.bean.RSubCategory;
import com.m520it.jdmallv2.cons.IDiyMessage;
import com.m520it.jdmallv2.cons.IntentValues;
import com.m520it.jdmallv2.cons.NetworkConst;
import com.m520it.jdmallv2.controller.CategoryController;
import com.m520it.jdmallv2.protocol.IModleChangeListener;
import com.m520it.jdmallv2.protocol.IViewContainer;

public class SubCategoryView extends FlexiScrollView implements IViewContainer, IModleChangeListener, OnClickListener{
	
	private LinearLayout mContainerLl;
	private RBaseCategory mTopCategoryBean;//传过来的1级分类对象
	private CategoryController mController;
	private Handler mHanlder=new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case IDiyMessage.ACTION_SUB_CATEGORY_RESULT:
					handleLoadSubCategory((List<RSubCategory>)msg.obj);
					break;
			}
		}
	};
	
	
	public SubCategoryView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	/**
	 * 加载2级分类  3级分类
	 */
	protected void handleLoadSubCategory(List<RSubCategory> subCategorys) {
//		清空容器先
		mContainerLl.removeAllViews();
//		创建视图 为每个视图添加数据
//		1. 添加1级分类的图片控件
		SmartImageView iv=new SmartImageView(getContext());
		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(-1, -2);
		iv.setLayoutParams(params);
		iv.setScaleType(ScaleType.FIT_XY);
		iv.setImageUrl(NetworkConst.BASE_URL+mTopCategoryBean.getBannerUrl());
		mContainerLl.addView(iv);
//		2. 添加2级分类列表		
		for (int i = 0; i < subCategorys.size(); i++) {
//			创建一个简单的TextView
			TextView tv=new TextView(getContext());
			LinearLayout.LayoutParams tvParams=new LinearLayout.LayoutParams(-2, -2);
			tvParams.setMargins(5, 10, 0, 0);
			tv.setLayoutParams(tvParams);
			RSubCategory subCategory = subCategorys.get(i);
			tv.setText(subCategory.getName());
			mContainerLl.addView(tv);
			
//			3.获取3级分类的数据  遍历循环 9宫格 
			List<RBaseCategory> thirdCategory = subCategory.getThirdCategory();
//			4. 根据列计算有多少行
			int columnCount=3;
			int lineNum=thirdCategory.size()/columnCount;
			lineNum+=(thirdCategory.size()%columnCount==0?0:1);
//			5. 在每一行里面 添加列 
			for (int j = 0; j < lineNum; j++) {
//				有了行号之后 创建一个行的容器 而且这个容器是LinearLayout-->水平
				LinearLayout lineLl=new LinearLayout(getContext());
				LinearLayout.LayoutParams lineLlParams=new LinearLayout.LayoutParams(-1, -2);
				lineLl.setLayoutParams(lineLlParams);
				lineLl.setOrientation(LinearLayout.HORIZONTAL);
//				添加第一列
//				现在是有了列的索引  有了总数据 可以从总数据里面获取这一项的数据     列的索引  j*columnCount
				addColumn(thirdCategory,j*columnCount,lineLl);
//				判断如果这个行有2列 就应该添加第2列   列的索引  j*columnCount+1
				if (j*columnCount+1<thirdCategory.size()-1) {
					addColumn(thirdCategory,j*columnCount+1,lineLl);
				}
//				判断如果这个行有3列 就应该添加第3列   列的索引  j*columnCount+2
				if (j*columnCount+2<thirdCategory.size()-1) {
					addColumn(thirdCategory,j*columnCount+2,lineLl);
				}
//				将LinearLayout添加到mContainerLl
				mContainerLl.addView(lineLl);
			}
		}
	}
	
	public void addColumn(List<RBaseCategory> thirdCategory, int index, LinearLayout containerLl){
		RBaseCategory bean=thirdCategory.get(index);
//		创建内部视图
		LinearLayout lineLl=new LinearLayout(getContext());
		LinearLayout.LayoutParams lineLlParams=new LinearLayout.LayoutParams(getWidth()/3,-2);
		lineLlParams.setMargins(0,8, 0, 0);
		lineLl.setLayoutParams(lineLlParams);
		lineLl.setOrientation(LinearLayout.VERTICAL);
		lineLl.setGravity(Gravity.CENTER_HORIZONTAL);//设置所有子控件水平居中
		lineLl.setOnClickListener(this);
		lineLl.setTag(bean.getId());
//		创建图片控件 自动为其填充数据
		SmartImageView smiv=new SmartImageView(getContext());
		LinearLayout.LayoutParams smivParams=new LinearLayout.LayoutParams(-2,-2);
		smiv.setLayoutParams(smivParams);
		smiv.setImageUrl(NetworkConst.BASE_URL+bean.getBannerUrl());
		lineLl.addView(smiv);
//		创建文本控件 自动为其填充数据
		TextView tv=new TextView(getContext());
		LinearLayout.LayoutParams tvParams=new LinearLayout.LayoutParams(-2,-2);
		tvParams.setMargins(0, 5, 0, 0);
		tv.setLayoutParams(tvParams);
		tv.setText(bean.getName());
		lineLl.addView(tv);
		
		containerLl.addView(lineLl);
	}

	@Override
//	将xml转换成View完毕的时候调用
	protected void onFinishInflate() {
		super.onFinishInflate();
		mController=new CategoryController(getContext());
		mController.setIModelChangeListener(this);
		mContainerLl=(LinearLayout) findViewById(R.id.child_container_ll);
	}

	@Override
	public void show(Object... values) {
//		传递过来的就是这个对象 RBaseCategory
		mTopCategoryBean=(RBaseCategory) values[0];
 
		mController.sendAyncMessage(IDiyMessage.ACTION_SUB_CATEGORY, mTopCategoryBean.getId());
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHanlder.obtainMessage(action, values[0]).sendToTarget();
	}

	@Override
	public void onClick(View v) {
//		商品列表页 需要传递两个id 一个1级的cid(获取品牌信息传给商品列表页)  一个是3级的cid(显示列表)
		Intent intent=new Intent(getContext(),ProductListActivity.class);
		intent.putExtra(IntentValues.TOPCID, mTopCategoryBean.getId());
		intent.putExtra(IntentValues.THIRDCID, (Long) v.getTag());
		getContext().startActivity(intent);
	}

}
