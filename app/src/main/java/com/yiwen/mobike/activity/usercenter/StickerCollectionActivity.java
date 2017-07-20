package com.yiwen.mobike.activity.usercenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.yiwen.mobike.R;
import com.yiwen.mobike.views.TabTitleView;
import com.yiwent.physicslayoutlib.PhysicsFrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StickerCollectionActivity extends AppCompatActivity {

    @BindView(R.id.title_sticker)
    TabTitleView       mTitleSticker;
    @BindView(R.id.physics_layout)
    PhysicsFrameLayout mPhysicsLayout;
    @BindView(R.id.id_recyclerview)
    RecyclerView       mRecyclerview;
    int catIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_collection);
        ButterKnife.bind(this);
        initPhysicsLayout();
    }

    private void initPhysicsLayout() {
        for (int i=0; i<10; i++) {
            ImageView imageView = new ImageView(StickerCollectionActivity.this);
            imageView.setImageResource(R.drawable.mobike_logo);
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
                    getResources().getDimensionPixelSize(R.dimen.square_size),
                    getResources().getDimensionPixelSize(R.dimen.square_size));
            imageView.setLayoutParams(llp);
            imageView.setId(catIndex);
            catIndex++;
            mPhysicsLayout.addView(imageView);
            Glide.with(StickerCollectionActivity.this)
                    .load(R.drawable.mobike_logo)
                    .placeholder(R.drawable.mobike_logo)
                    .into(imageView);
        }


    }
}
