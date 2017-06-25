package com.yiwen.mobike.views;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import java.util.ArrayList;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/24
 * Time: 15:51
 */

public class NonFocusingScrollView extends ScrollView {
    public NonFocusingScrollView(Context context) {
        super(context);
    }

    public NonFocusingScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NonFocusingScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NonFocusingScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public ArrayList<View> getFocusables(int paramInt)
    {
        return new ArrayList();
    }

    protected boolean onRequestFocusInDescendants(int paramInt, Rect paramRect)
    {
        return true;
    }
}
