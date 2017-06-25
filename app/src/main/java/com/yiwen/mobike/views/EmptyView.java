package com.yiwen.mobike.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/6
 * Time: 16:34
 */

public class EmptyView extends LinearLayout {

    private ImageView a;
    private TextView  b;
    private TextView  c;

    public EmptyView(Context context) {
        super(context);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        this.getUrl = ((ImageView)findViewById());
//        this.b = ((TextView)findViewById());
//        this.c = ((TextView)findViewById());
    }

    public void setEmptyImg( int resid)
    {
        this.a.setImageResource(resid);
    }

    public void setEmptySubText(int resid)
    {
        this.c.setText(resid);
        this.c.setVisibility(VISIBLE);
    }

    public void setEmptyText( int resid)
    {
        this.b.setText(resid);
    }
}
