package com.chb.chbui.Wave;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class WaveViewBezier extends View {

    //画笔
    private Paint mPaint;

    //绘制路径
    private Path mPath;

    //波长
    private int mWaveLength;

    //振幅
    private int mWaveHeight;

    //绘制的波的个数
    private int mWaveCount;

    //偏移
    private int mOffSet;

    //移动动画
    private ValueAnimator mAnimator;


    public WaveViewBezier(Context context) {
        this(context, null, 0);
    }

    public WaveViewBezier(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveViewBezier(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWaveCount = Math.round(w / mWaveLength + 1.5f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawWave(canvas);
    }

    public void init() {
        mWaveHeight = dpToPx(10);
        mWaveLength = dpToPx(100);
        mPath = new Path();
        initPaint();
        initAnimator();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    private void drawWave(Canvas canvas) {
        mPath.reset();
        mPath.moveTo(-mWaveLength + mOffSet, mWaveHeight);
        for (int i = 0; i < mWaveCount; i++) {
            //第一个控制点的坐标为(-mWaveLength * 3 / 4,-mWaveAmplitude)
            mPath.quadTo(-mWaveLength * 3 / 4 + mOffSet + i * mWaveLength,
                    -mWaveHeight,
                    -mWaveLength / 2 + mOffSet + i * mWaveLength,
                    mWaveHeight);

            //第二个控制点的坐标为(-mWaveLength / 4,3 * mWaveAmplitude)
            mPath.quadTo(-mWaveLength / 4 + mOffSet + i * mWaveLength,
                    3 * mWaveHeight,
                    mOffSet + i * mWaveLength,
                    mWaveHeight);
        }
        mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(0, getHeight());
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    private void initAnimator() {
        mAnimator = ValueAnimator.ofInt(0, mWaveLength);
        mAnimator.setDuration(2000);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mOffSet = (int) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mAnimator.start();
    }

    public void startAnimation() {
        if (mAnimator != null) {
            mAnimator.start();
        }
    }

    public void stopAnimation() {
        if (mAnimator != null) {
            mAnimator.cancel();
        }
    }

    public void pauseAnimation() {
        if (mAnimator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mAnimator.pause();
            }
        }
    }

    public void resumeAnimation() {
        if (mAnimator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mAnimator.resume();
            }
        }
    }

    private int dpToPx(int dpValue) {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }
}
