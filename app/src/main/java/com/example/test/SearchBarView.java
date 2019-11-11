package com.example.test;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.rxjavatest.R;


/**
 * @author qirong
 */

public class SearchBarView extends View {

    private static final String TAG = "SearchBarView";
    private static final int DEFAULT_ANIMATION_DURATION = 500;
    private static final int STATUS_OPEN = 1;
    private static final int STATUS_CLOSE = 1 << 2;
    private static final int STATUS_PROCESS = 1 << 3;
    private static final int DEFAULT_SEARCH_BAR_COLOR = Color.WHITE;
    private static final int DEFAULT_RIGHT_POSITION = 1;
    private static final int DEFAULT_LEFT_POSITION = 1 << 2;
    private static final int DEFAULT_SEARCH_TEXT_COLOR = Color.GRAY;
    private static final int DEFAULT_HINT_TEXT_SIZE = 14;
    private static final int DEFAULT_HEIGHT = 40;

    private int mWidth;
    private int mHeight;
    private int mRadius;
    private int mStatus;
    private Paint mPaint;
    private int mPosition;
    private int mOffsetX;
    private RectF mRectFOpen;
    private RectF mRectFClose;
    private int open_close_padding;
    private RectF mDstRectFOpen;
    private RectF mDstRectFClose;
    private CharSequence mSearchText;
    private int searchBarColor;
    private int searchTextColor;
    private float defaultHeight;
    private ValueAnimator openAnimator = new ValueAnimator();
    private ValueAnimator closeAnimator = new ValueAnimator();
    private Bitmap bitmapOpen;
    private Bitmap bitmapClose;

    public SearchBarView(Context context) {
        this(context, null);
    }

    public SearchBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SearchBarView);
        searchBarColor = array.getColor(R.styleable.SearchBarView_search_bar_color, DEFAULT_SEARCH_BAR_COLOR);
        mPosition = array.getInteger(R.styleable.SearchBarView_search_bar_position, DEFAULT_RIGHT_POSITION);
        mStatus = array.getInteger(R.styleable.SearchBarView_search_bar_status, STATUS_CLOSE);
        int mDuration = array.getInteger(R.styleable.SearchBarView_search_bar_duration, DEFAULT_ANIMATION_DURATION);
        int searchBarIconOpen = array.getResourceId(R.styleable.SearchBarView_search_bar_icon_open, android.R.drawable.ic_search_category_default);
        int searchBarIconClose = array.getResourceId(R.styleable.SearchBarView_search_bar_icon_close, android.R.drawable.ic_search_category_default);
        mSearchText = array.getText(R.styleable.SearchBarView_search_bar_hint_text);
        open_close_padding=array.getDimensionPixelOffset(R.styleable.SearchBarView_search_bar_icon_open_close_padding,-1);
        searchTextColor = array.getColor(R.styleable.SearchBarView_search_bar_hint_text_color, DEFAULT_SEARCH_TEXT_COLOR);
        float defaultTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, DEFAULT_HINT_TEXT_SIZE, getResources().getDisplayMetrics());
        float searchTextSize = array.getDimension(R.styleable.SearchBarView_search_bar_hint_text_size, defaultTextSize);
        defaultHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_HEIGHT, getResources().getDisplayMetrics());
        array.recycle();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(searchBarColor);
        mPaint.setTextSize(searchTextSize);
        mRectFOpen = new RectF();
        mRectFClose=new RectF();
        mDstRectFOpen = new RectF();
        mDstRectFClose=new RectF();
        bitmapOpen = BitmapFactory.decodeResource(getResources(), searchBarIconOpen);
        bitmapClose = BitmapFactory.decodeResource(getResources(), searchBarIconClose);
        initAnimator(mDuration);
    }

    private void initAnimator(long duration) {
        //AccelerateInterpolator accelerateInterpolator = new AccelerateInterpolator();
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffsetX = (int) animation.getAnimatedValue();
                invalidate();
            }
        };
        // init open animator
        openAnimator = new ValueAnimator();
        //openAnimator.setInterpolator(accelerateInterpolator);
        openAnimator.setDuration(duration);
        openAnimator.addUpdateListener(animatorUpdateListener);
        openAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                mStatus = STATUS_PROCESS;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mStatus = STATUS_OPEN;
                invalidate();
            }
        });
        // init close animator
        closeAnimator = new ValueAnimator();
        //closeAnimator.setInterpolator(accelerateInterpolator);
        closeAnimator.setDuration(duration);
        closeAnimator.addUpdateListener(animatorUpdateListener);
        closeAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                mStatus = STATUS_PROCESS;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mStatus = STATUS_CLOSE;
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else {
            mWidth = widthSize;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {
            mHeight = (int) defaultHeight;
            if (heightMode == MeasureSpec.AT_MOST) {
                mHeight = (int) (Math.min(heightSize, defaultHeight));
            }
        }
        mRadius = Math.min(mWidth - getPaddingLeft() - getPaddingRight(),mHeight - getPaddingTop() - getPaddingBottom()) / 2;
        if (mStatus == STATUS_OPEN) {
            mOffsetX = mWidth/2 - mRadius * 2 - getPaddingRight() - getPaddingLeft();
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // draw search bar
        mPaint.setColor(searchBarColor);
        int leftOpen = mPosition == DEFAULT_RIGHT_POSITION ? mWidth/2 - getPaddingRight() - 2 * mRadius - mOffsetX : getPaddingLeft();
        int leftClose= mPosition == DEFAULT_RIGHT_POSITION ? mWidth-getPaddingRight()-2*mRadius-mOffsetX:getPaddingLeft();
        int rightOpen = mPosition == DEFAULT_RIGHT_POSITION ? mWidth/2 - getPaddingRight() : 2 * mRadius + mOffsetX + getPaddingLeft();
        int rightClose=mPosition  == DEFAULT_RIGHT_POSITION ?leftOpen+mOffsetX+2*mRadius:2*mRadius+mOffsetX+getPaddingLeft()+getPaddingLeft();
        int top = getPaddingTop();
        int bottom = mHeight - getPaddingBottom();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(leftOpen, top, rightOpen, bottom, mRadius, mRadius, mPaint);
            canvas.drawRoundRect(leftClose,top,rightClose,bottom,mRadius,mRadius,mPaint);
        } else {
            mRectFOpen.set(leftOpen, top, rightOpen, bottom);
            canvas.drawRoundRect(mRectFOpen, mRadius, mRadius, mPaint);

            mRectFClose.set(leftClose,top,rightOpen,bottom);
            canvas.drawRoundRect(mRectFOpen,mRadius,mRadius,mPaint);
        }
        // draw search bar icon
        mDstRectFOpen.set(leftOpen + (int) ((1 - Math.sqrt(2) / 2) * mRadius),     top + (int) ((1 - Math.sqrt(2) / 2) * mRadius), leftOpen + (int) ((1 + Math.sqrt(2) / 2) * mRadius), top + (int) ((1 + Math.sqrt(2) / 2) * mRadius));
        canvas.drawBitmap(bitmapOpen, null, mDstRectFOpen, mPaint);


        mDstRectFClose.set(leftClose + (int) ((1 - Math.sqrt(2) / 2) * mRadius),     top + (int) ((1 - Math.sqrt(2) / 2) * mRadius), leftClose + (int) ((1 + Math.sqrt(2) / 2) * mRadius), top + (int) ((1 + Math.sqrt(2) / 2) * mRadius));
        canvas.drawBitmap(bitmapClose,null,mDstRectFClose,mPaint);
        //canvas.drawBitmap(bitmapClose,null,null,mPaint);
        // draw search bar text
        if (mStatus == STATUS_OPEN && !TextUtils.isEmpty(mSearchText)) {
            mPaint.setColor(searchTextColor);
            Paint.FontMetrics fm = mPaint.getFontMetrics();
            double textHeight = Math.ceil(fm.descent - fm.ascent);
            canvas.drawText(mSearchText.toString(), leftOpen + 2 * mRadius, top + (float) (mRadius + textHeight / 2 - fm.descent), mPaint);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // 如果事件不是在search bar区域内，那么不响应
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float actionX = event.getX();
            // 计算search bar的左右边界
            int left = mPosition == DEFAULT_RIGHT_POSITION ? mWidth - 2 * mRadius - mOffsetX : 0;
            int right = mPosition == DEFAULT_RIGHT_POSITION ? mWidth : 2 * mRadius + mOffsetX;
            if (actionX < left || actionX > right) {
                return false;
            }
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 判断搜索栏是否为打开状态
     *
     * @return
     */
    public boolean isOpen() {
        return mStatus == STATUS_OPEN;
    }

    /**
     * 判断搜索栏是否为关闭状态
     *
     * @return
     */
    public boolean isClose() {
        return mStatus == STATUS_CLOSE;
    }

    /**
     * 打开搜索栏
     */
    public void startOpen() {
        if (isOpen()) {
            return;
        } else if (openAnimator.isStarted()) {
            return;
        } else if (closeAnimator.isStarted()) {
            closeAnimator.cancel();
        }

        openAnimator.setIntValues(mOffsetX, mWidth - mRadius * 2 - getPaddingLeft() - getPaddingRight());
        openAnimator.start();
    }

    /**
     * 关闭搜索栏
     */
    public void startClose() {
        if (isClose()) {
            return;
        } else if (closeAnimator.isStarted()) {
            return;
        } else if (openAnimator.isStarted()) {
            openAnimator.cancel();
        }
        closeAnimator.setIntValues(mOffsetX, 0);
        closeAnimator.start();
    }

}
