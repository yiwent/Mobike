package com.yiwen.mobike.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yiwen.mobike.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Hint_viewActivity extends Activity {

    @BindView(R.id.viepager_hintview)
    ViewPager mViepagerHintview;
    private List<View> mViewList   = new ArrayList<View>(5);
    private int[]      imageViewID = {R.drawable.hint_card_1, R.drawable.hint_card_2,
            R.drawable.hint_card_3, R.drawable.hint_card_4, R.drawable.hint_card_5};
    private String[] title;
    private String[] contnet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint_view);
        ButterKnife.bind(this);
        initView();
        intData();
        initEvent();
    }

    private void initView() {
        title = getApplicationContext().getResources().getStringArray(R.array.hint_title);
        contnet = getApplicationContext().getResources().getStringArray(R.array.hint_content);
        LayoutInflater inflater = getLayoutInflater();

        for (int i = 0; i < 5; i++) {
            View v = inflater.inflate(R.layout.hint_card_layout, null);
            ImageView imageView = (ImageView) v.findViewById(R.id.car_view);
            TextView tvTitle = (TextView) v.findViewById(R.id.tv_hint_card_title);
            TextView tvContent = (TextView) v.findViewById(R.id.tv_hint_card_content);
            TextView tvPargeSize = (TextView) v.findViewById(R.id.tv_hint_card_pagesize);
            TextView tvTNext = (TextView) v.findViewById(R.id.tv_hint_card_next);
            Glide.with(this).load(imageViewID[i]).into(imageView);
            //  imageView.setImageResource(imageViewID[i]);
            tvTitle.setText(title[i]);
            tvContent.setText(contnet[i]);
            tvPargeSize.setText(i + 1 + "/5");
            final int temp = i + 1;
            if (4 == i) {
                tvTNext.setText("知道了");
                tvTNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Hint_viewActivity.this.finish();
                    }
                });
            } else {
                tvTNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViepagerHintview.setCurrentItem(temp);
                    }
                });
            }
            mViewList.add(v);
            v=null;
        }
        PagerAdapter mPagerAdapter = new Hint_card_PangerAdater();
        mViepagerHintview.setAdapter(mPagerAdapter);
        mViepagerHintview.setPageTransformer(true, new DepthPageTransformer());

    }

    private void intData() {
    }

    private void initEvent() {
    }

    private class Hint_card_PangerAdater extends PagerAdapter {

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView(mViewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }
    }

    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            Log.d("DepthPageTransformer", view.getTag() + " , " + position + "");
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
}
