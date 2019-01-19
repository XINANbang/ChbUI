package com.chb.chbui.QQDragBall;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.chb.chbui.UIUtils;

public class QQDragBall extends View {

    public static final String TAG = "CHBUI_QQBALL";

    private static final int DRAG_MAX_DIS = 100;

    private float mStartRadius = UIUtils.dp2px(getContext(),15);
    private float mCurrentStartRadius = mStartRadius;

    private Paint mBallPaint;
    private Paint mTextPaint;

    private PointF mStartPoint;
    private PointF mEndPoint;

    private PointF mStartPointA;
    private PointF mStartPointB;
    private PointF mEndPointA;
    private PointF mEndPointB;
    private PointF mPointO;


    private boolean mCanMove;
    private boolean mDisappear;
    private int mMsgCount;


    public QQDragBall(Context context) {
        this(context, null);
    }

    public QQDragBall(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQDragBall(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void SetMsgCount(int count) {
        mMsgCount = count;
    }

    private void init() {
        initPaint();
        mStartPoint = new PointF();
        mEndPoint = new PointF();
        mStartPointA = new PointF();
        mStartPointB = new PointF();
        mEndPointA = new PointF();
        mEndPointB = new PointF();
        mPointO = new PointF();

    }

    private void initPaint() {
        mBallPaint = new Paint();
        mBallPaint.setColor(Color.RED);
        mBallPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mBallPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.RED);
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStartPoint.set(w/2f, h/2f);
        mEndPoint.set(w/2f, h/2f);
        Log.d(TAG, "onSizeChanged: mStartPoint = " + mStartPoint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (outOfDis()) {
            drawBall(canvas, mEndPoint, mStartRadius);
        } else {
            Log.d(TAG, "onDraw: start point = " + mStartPoint + ", r = " + mCurrentStartRadius);
            drawBall(canvas, mStartPoint, mCurrentStartRadius);
            Log.d(TAG, "onDraw: mEndPoint point = " + mEndPoint + ", r = " + mStartRadius);
            drawBall(canvas, mEndPoint, mStartRadius);
            drawBezier(canvas);
        }

        if (!mDisappear) {
            drawText(canvas, mEndPoint);
        }
    }

    private void drawBall(Canvas canvas, PointF point, float radius) {
        mBallPaint.setColor(Color.RED);
        canvas.drawCircle(point.x, point.y, radius, mBallPaint);
    }

    /**
     * 绘制文字
     *
     * @param canvas 画布
     */
    private void drawText(Canvas canvas, PointF point) {
        Rect textRect = new Rect();
        textRect.left = (int) (point.x - mStartRadius);
        textRect.top = (int) (point.y - mStartRadius);
        textRect.right = (int) (point.x + mStartRadius);
        textRect.bottom = (int) (point.y + mStartRadius);
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
        int baseline = (textRect.bottom + textRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        //文字绘制到整个布局的中心位置
        canvas.drawText(mMsgCount > 99 ? "99+" : mMsgCount + "", textRect.centerX(), baseline, mTextPaint);
    }

    /**
     * 设置贝塞尔曲线的相关点坐标  计算方式参照结算图即可看明白
     * （ps为了画个清楚这个图花了不少功夫哦）
     */
    private void setBezierPoint() {
        //控制点坐标
        mPointO.set((mStartPoint.x + mEndPoint.x) / 2.0f, (mStartPoint.y + mEndPoint.y) / 2.0f);

        float x = mEndPoint.x - mStartPoint.x;
        float y = mEndPoint.y - mStartPoint.y;

        //斜率 tanA=rate
        double rate;
        rate = x / y;
        //角度  根据反正切函数算角度
        float angle = (float) Math.atan(rate);

        mStartPointA.x = (float) (mStartPoint.x + Math.cos(angle) * mCurrentStartRadius);
        mStartPointA.y = (float) (mStartPoint.y - Math.sin(angle) * mCurrentStartRadius);

        mStartPointB.x = (float) (mStartPoint.x - Math.cos(angle) * mCurrentStartRadius);
        mStartPointB.y = (float) (mStartPoint.y + Math.sin(angle) * mCurrentStartRadius);

        mEndPointA.x = (float) (mEndPoint.x + Math.cos(angle) * mStartRadius);
        mEndPointA.y = (float) (mEndPoint.y - Math.sin(angle) * mStartRadius);

        mEndPointB.x = (float) (mEndPoint.x - Math.cos(angle) * mStartRadius);
        mEndPointB.y = (float) (mEndPoint.y + Math.sin(angle) * mStartRadius);

    }

    /**
     * 画贝塞尔曲线
     *
     * @param canvas 画布
     */
    private void drawBezier(Canvas canvas) {
        setBezierPoint();
        Path path = new Path();
        path.reset();
        path.moveTo(mStartPointA.x, mStartPointA.y);
        path.quadTo(mPointO.x, mPointO.y, mEndPointA.x, mEndPointA.y);
        path.lineTo(mEndPointB.x, mEndPointB.y);
        path.quadTo(mPointO.x, mPointO.y, mStartPointB.x, mStartPointB.y);
        path.lineTo(mStartPointA.x, mStartPointA.y);
        path.close();

        mBallPaint.setColor(Color.RED);
        canvas.drawPath(path, mBallPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: event = " + event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mCanMove = canMove(event);
                Log.d(TAG, "onTouchEvent: mCanMove = " + mCanMove);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mCanMove) {
                    mEndPoint.set(event.getX(), event.getY());
                    setCurrentStartRadius();
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (outOfDis()) {
                    //todo 爆炸效果
                    mDisappear = true;
                    mEndPoint.set(mStartPoint);
                } else {
                    final float a = (mEndPoint.y - mStartPoint.y) / (mEndPoint.x - mStartPoint.x);
                    ValueAnimator valueAnimator = ValueAnimator.ofFloat(mEndPoint.x, mStartPoint.x);
                    valueAnimator.setDuration(500);
                    valueAnimator.setInterpolator(new BounceInterpolator());
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float x = (float) animation.getAnimatedValue();
                            float y = mStartPoint.y + a * (x - mStartPoint.x);
                            mEndPoint.set(x, y);
                            setCurrentStartRadius();
                            invalidate();
                        }
                    });
                    valueAnimator.start();
                }
                break;
        }
        return true;
    }

    private boolean canMove(MotionEvent event) {
        Rect rect = new Rect();
        rect.left = (int)(mStartPoint.x - mStartRadius);
        rect.right = (int)(mStartPoint.x + mStartRadius);
        rect.top = (int)(mStartPoint.y - mStartRadius);
        rect.bottom = (int)(mStartPoint.y + mStartRadius);
        Log.d(TAG, "canMove: mStartPoint = " + mStartPoint + " , mStartRadius = " + mStartRadius + " , event = " + event.getX() + " , " + event.getY());
        return rect.contains((int) event.getX(), (int) event.getY());
    }

    private boolean outOfDis() {
        return Math.sqrt( Math.pow(mEndPoint.y - mStartPoint.y, 2)
                + Math.pow(mEndPoint.x - mStartPoint.x, 2)) > UIUtils.dp2px(getContext(),DRAG_MAX_DIS);
    }

    private void setCurrentStartRadius() {
        if (outOfDis()) {
            mCurrentStartRadius = 0f;
        } else {
            float distance = (float) Math.sqrt(Math.pow(mStartPoint.x - mEndPoint.x, 2)
                    + Math.pow(mStartPoint.y - mEndPoint.y, 2));
            float percent = distance / UIUtils.dp2px(getContext(),DRAG_MAX_DIS);
            Log.d(TAG, "setCurrentStartRadius: mStartPoint = " + mStartPoint);
            Log.d(TAG, "setCurrentStartRadius: mEndPoint = " + mEndPoint);
            Log.d(TAG, "setCurrentStartRadius: distance = " + distance);
            Log.d(TAG, "setCurrentStartRadius: percent = " + percent);
            mCurrentStartRadius = (1 - percent * 0.8f) * mStartRadius;
        }
    }
}