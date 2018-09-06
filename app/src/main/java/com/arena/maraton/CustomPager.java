package com.arena.maraton;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class CustomPager extends ViewPager {
	private int mCurrentPagePosition = 0;
	public CustomPager(Context context) {
		super(context);
	}
	public CustomPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		try {
			View child = getChildAt(mCurrentPagePosition);
			if (child != null) {
				child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
				int h = child.getMeasuredHeight();
				heightMeasureSpec = MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);
	}

	public void reMeasureCurrentPage(int position) {
		mCurrentPagePosition = position;
		requestLayout();
	}
}