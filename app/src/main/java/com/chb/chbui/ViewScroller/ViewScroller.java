package com.chb.chbui.ViewScroller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Scroller;

/**
 * create by chenhanbin at 2019/3/26
 * android开发艺术 demo 使用scroller做滑动
 **/
@SuppressLint("AppCompatCustomView")
public class ViewScroller extends ImageView {

    public Scroller mScrolller = new Scroller(getContext());

    public ViewScroller(Context context) {
        super(context);
    }

    public ViewScroller(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewScroller(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void smoothScrollTo(int x, int y) {
        int scrollX = getScrollX();
        int deltaX = x - scrollX;
        mScrolller.startScroll(getScrollX(), getScrollY(),300, 150, 1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScrolller.computeScrollOffset()) {
            scrollTo(mScrolller.getCurrX(), mScrolller.getCurrY());
            Log.d("chenhanbin", "computeScroll: x = " + mScrolller.getCurrX() + " , y = " + mScrolller.getCurrY());
            postInvalidate();
        }
    }
}
