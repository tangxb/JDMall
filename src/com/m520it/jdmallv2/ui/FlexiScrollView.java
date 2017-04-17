package com.m520it.jdmallv2.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ScrollView;

public class FlexiScrollView extends ScrollView {
	// ��ʼ������Y�᷽�����
	private static final int MAX_Y_OVERSCROLL_DISTANCE = 100;
	// �����Ļ���
	private Context mContext;
	// ʵ�ʿ���������Y���ϵľ���
	private int mMaxYOverscrollDistance;

	public FlexiScrollView(Context context) {
		super(context);
		mContext = context;
		initBounceListView();
	}

	public FlexiScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initBounceListView();
	}

	public FlexiScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		initBounceListView();
	}

	private void initBounceListView() {
		final DisplayMetrics metrics = mContext.getResources()
				.getDisplayMetrics();
		final float density = metrics.density;
		mMaxYOverscrollDistance = (int) (density * MAX_Y_OVERSCROLL_DISTANCE);
	}

	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
			int scrollY, int scrollRangeX, int scrollRangeY,
			int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
		// ʵ�ֵı��ʾ��������ﶯ̬�ı���maxOverScrollY��ֵ
		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY,
				scrollRangeX, scrollRangeY, maxOverScrollX,
				mMaxYOverscrollDistance, isTouchEvent);
	}

}
