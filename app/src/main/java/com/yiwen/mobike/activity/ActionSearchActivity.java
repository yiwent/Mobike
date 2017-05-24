package com.yiwen.mobike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.baidu.location.BDLocation;
import com.yiwen.mobike.R;

public class ActionSearchActivity extends AppCompatActivity {
    private BDLocation mBDLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_search);
        intiView();

    }

    private void intiView() {
        Intent intent = getIntent();
        mBDLocation= intent.getParcelableExtra("mylotion");
    }
}
