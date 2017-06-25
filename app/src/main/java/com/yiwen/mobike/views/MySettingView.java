package com.yiwen.mobike.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiwen.mobike.R;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.yiwen.mobike.R.id.iv_ringht;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/5/23
 * Time: 17:01
 */

public class MySettingView extends RelativeLayout {
    private LayoutInflater  mInflater;
    private TextView        mLeftTextView;
    private TextView        mRinghtTextView;
    private CircleImageView mRinghtImagView;
    private ImageView       mIndicatorImagView;
    private ImageView       mDotImagView;

    private Drawable right_ImagViewDrawble;

    private Boolean showRightImage = false;
    private Boolean showDotImage   = false;
    private Boolean showRightTv    = false;
    private Boolean showIndicate   = true;

    public MySettingView(Context context) {
        this(context, null);
    }

    public MySettingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySettingView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MySettingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mInflater = LayoutInflater.from(context);
        initView();
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MySettingView, defStyleAttr, defStyleRes);
            showRightImage = a.getBoolean(R.styleable.MySettingView_showRightImage, false);
            showDotImage = a.getBoolean(R.styleable.MySettingView_showDotImage, false);
            showRightTv = a.getBoolean(R.styleable.MySettingView_showRightTv, false);
            showIndicate = a.getBoolean(R.styleable.MySettingView_showIndicate, true);

            checkVisible();

            int rightTvcolor = a.getColor(R.styleable.MySettingView_ringhtTvTextColor, getResources()
                    .getColor(R.color.smssdk_gray));

            setRigtTvColor(rightTvcolor);

            right_ImagViewDrawble = a.getDrawable(R.styleable.MySettingView_rightImageIcon);
            if (right_ImagViewDrawble == null)
                right_ImagViewDrawble = getResources().getDrawable(R.drawable.avatar_default_login, null);
            setRigtImageDrawable(right_ImagViewDrawble);

            String leftTvText = a.getString(R.styleable.MySettingView_leftTvText);
            String rightTvText = a.getString(R.styleable.MySettingView_rightTvText);

            setLeftTvText(leftTvText);

            setRigtTvText(rightTvText);

            a.recycle();
        }
    }
    public CircleImageView getRigtImageView() {
        if (mRinghtImagView != null)
            return mRinghtImagView;
        return null;
    }
    public void setRigtTvText(String s) {
        if (mRinghtTextView != null)
            mRinghtTextView.setText(s);
    }

    public CharSequence getLeftTvText(String s) {
        if (mLeftTextView != null)
            mLeftTextView.getText();
        return null;
    }

    public CharSequence getRigtTvText() {
        if (mRinghtTextView != null)
            return mRinghtTextView.getText();
        return null;
    }

    public void setLeftTvText(String s) {
        if (mLeftTextView != null)
            mLeftTextView.setText(s);
    }

    public void setRigtTvColor(int rightTvcolor) {
        if (mRinghtTextView != null)
            mRinghtTextView.setTextColor(rightTvcolor);

    }

    public void setRigtImageDrawable(Drawable iv_ringht) {
        if (mRinghtImagView != null)
            mRinghtImagView.setImageDrawable(iv_ringht);
    }

    public void setDotImagViewVisiable(int visiable) {
        if (mDotImagView != null)
            mDotImagView.setVisibility(visiable);

    }

    public void setShowIndicator(boolean isShow){
        if (mIndicatorImagView!=null){
            mIndicatorImagView.setVisibility(true==isShow?View.VISIBLE : View.GONE);
        }
    }

    private void checkVisible() {
        mRinghtImagView.setVisibility(true == showRightImage ? View.VISIBLE : View.GONE);
        mDotImagView.setVisibility(true == showDotImage ? View.VISIBLE : View.GONE);
        mRinghtTextView.setVisibility(true == showRightTv ? View.VISIBLE : View.GONE);
        mIndicatorImagView.setVisibility(true == showIndicate ? View.VISIBLE : View.GONE);
    }

    private void initView() {
        View view = mInflater.inflate(R.layout.setting_view, this, true);
        mLeftTextView = (TextView) view.findViewById(R.id.tv_left);
        mRinghtTextView = (TextView) view.findViewById(R.id.tv_ringht);
        mRinghtImagView = (CircleImageView) findViewById(iv_ringht);
        mIndicatorImagView = (ImageView) findViewById(R.id.indicate);
        mDotImagView = (ImageView) findViewById(R.id.iv_dot);
    }


}
