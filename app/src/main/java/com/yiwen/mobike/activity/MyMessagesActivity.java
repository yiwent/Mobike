package com.yiwen.mobike.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.yiwen.mobike.R;
import com.yiwen.mobike.views.MyToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyMessagesActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_massage)
    MyToolBar    mToolbarMassage;
    @BindView(R.id.recycler_massage)
    RecyclerView mRecyclerMassage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_messages);
        ButterKnife.bind(this);
        initEvent();
    }

    private void initEvent() {
        mToolbarMassage.setOnLeftButtonClickListener(new MyToolBar.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                MyMessagesActivity.this.finish();
            }
        });
    }
}
