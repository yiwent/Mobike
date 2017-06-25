package com.yiwen.mobike.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yiwen.mobike.R;

import net.frakbot.jumpingbeans.JumpingBeans;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/13
 * Time: 23:40
 */

public class LoadingPageView extends LinearLayout {
    private ImageView    mImageView;
    private Animation    mAnimation;
    private TextView     mTextView;
    private JumpingBeans mJumpingBeans;

    private LayoutInflater mInflater;

    public LoadingPageView(Context context) {
        this(context, null);
    }

    public LoadingPageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);
        initView();
    }

    private void initView() {
        //        View view = mInflater.inflate(R.layout.loading_page, this, true);
        //        mImageView = ((ImageView) view.findViewById(R.id.loading_page_img));
        //        mTextView = ((TextView) view.findViewById(R.id.loading_text));
        //        mAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.loading);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //   mInflater = LayoutInflater.from(context);
        mImageView = ((ImageView) findViewById(R.id.loading_page_img));
        mTextView = ((TextView) findViewById(R.id.loading_text));
        mAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.loading);
    }

    public void show() {
        if (this.mImageView != null) {
            this.mImageView.startAnimation(mAnimation);
            mJumpingBeans = JumpingBeans.with(mTextView)
                    .appendJumpingDots()
                    .build();
        }
        this.setVisibility(VISIBLE);
    }

    public void hide() {
        if (this.mImageView != null) {
            //  this.mAnimation.cancel();
            this.mImageView.clearAnimation();
        }
        if (mJumpingBeans != null)
            mJumpingBeans.stopJumping();
        this.setVisibility(GONE);
    }


    public void setLoadingText(String text) {
        if (this.mTextView != null) {
            this.mTextView.setText(text);
            mJumpingBeans = JumpingBeans.with(mTextView)
                    .appendJumpingDots()
                    .build();
        }
    }

    public void setLoadingText(int resId) {
        if (this.mTextView != null) {
            this.mTextView.setText(resId);
            mJumpingBeans = JumpingBeans.with(mTextView)
                    .appendJumpingDots()
                    .build();
        }

    }
}
