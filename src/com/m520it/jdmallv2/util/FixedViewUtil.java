package com.m520it.jdmallv2.util;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class FixedViewUtil {

	public static void setListViewHeightBasedOnChildren(GridView listView,int col) {
		// ��ȡlistview��adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		// �̶��п?�ж�����
		int totalHeight = 0;
		// iÿ�μ�4���൱��listAdapter.getCount()С�ڵ���4ʱ ѭ��һ�Σ�����һ��item�ĸ߶ȣ�
		// listAdapter.getCount()С�ڵ���8ʱ�������θ߶����
		for (int i = 0; i < listAdapter.getCount(); i += col) {
			// ��ȡlistview��ÿһ��item
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			// ��ȡitem�ĸ߶Ⱥ�
			totalHeight += listItem.getMeasuredHeight();
			totalHeight += listView.getVerticalSpacing();
			if (i==listAdapter.getCount()-1) {
				totalHeight += listView.getVerticalSpacing();
			}
		}
		// ��ȡlistview�Ĳ��ֲ���
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		// ���ø߶�
		params.height = totalHeight;
		// ����margin
		((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
		// ���ò���
		listView.setLayoutParams(params);
	}
	
	public static void setListViewHeightBasedOnChildren(ListView lv){  
		ListAdapter listAdapter = lv.getAdapter();
	    int listViewHeight = 0;  
	    int adaptCount = listAdapter.getCount();  
	    for(int i=0;i<adaptCount;i++){  
	        View temp = listAdapter.getView(i,null,lv);  
	        temp.measure(0,0);  
	        listViewHeight += temp.getMeasuredHeight();  
	    }  
	    LayoutParams layoutParams = lv.getLayoutParams();  
	    layoutParams.width = LayoutParams.MATCH_PARENT;  
	    layoutParams.height = listViewHeight;  
	    lv.setLayoutParams(layoutParams);  
	}

}
