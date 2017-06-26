package com.yiwen.mobike.activity.usercenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiwen.mobike.R;
import com.yiwen.mobike.activity.ActionSearchActivity;
import com.yiwen.mobike.provider.PoiObject;
import com.yiwen.mobike.utils.JSONUtil;
import com.yiwen.mobike.utils.PreferencesUtils;
import com.yiwen.mobike.views.TabTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingAddressActivity extends AppCompatActivity {
    private static final int REQUEST_FIRSTADDRESS=1;
    private static final int REQUEST_SECONDADDRESS=2;
    private static final String FIRST_ADDRESS="address";
    private static final String SENCOND_ADDRESS="address1";
    @BindView(R.id.title_address)
    TabTitleView   mTitleAddress;
    @BindView(R.id.id_iv_star)
    ImageView      mStar;
    @BindView(R.id.lo_first_address)
    RelativeLayout mLoFirstAddress;
    @BindView(R.id.id_iv_star1)
    ImageView      mStar1;
    @BindView(R.id.lo_second_address)
    RelativeLayout mLoSecondAddress;
    @BindView(R.id.address)
    TextView       mAddress;
    @BindView(R.id.address1)
    TextView       mAddress1;
    @BindView(R.id.district)
    TextView       mDistrict;
    @BindView(R.id.district1)
    TextView       mDistrict1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_address);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    private void initEvent() {
       mTitleAddress.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
           @Override
           public void onClick() {
               finish();
           }
       });
    }

    private void initView() {
        if (PreferencesUtils.getString(this,FIRST_ADDRESS,null)!=null){
            PoiObject poiObject= JSONUtil.fromJson(PreferencesUtils.
                    getString(this, FIRST_ADDRESS, null),PoiObject.class);
            mStar.setImageResource(R.drawable.address_star_selected);
            mAddress.setText(poiObject.address);
            mDistrict.setText(poiObject.district);
        }else {
            mStar.setImageResource(R.drawable.address_star_normal);
            mAddress.setText("地址1");
            mDistrict.setText("请点击搜索地址保存");
        }
        if (PreferencesUtils.getString(this,SENCOND_ADDRESS,null)!=null){
            PoiObject poiObject= JSONUtil.fromJson(PreferencesUtils.
                    getString(this, SENCOND_ADDRESS, null),PoiObject.class);// new TypeToken<PoiObject>() {}.getType()
            mStar1.setImageResource(R.drawable.address_star_selected);
            mAddress1.setText(poiObject.address);
            mDistrict1.setText(poiObject.district);
        }else {
            mStar1.setImageResource(R.drawable.address_star_normal);
            mAddress1.setText("地址1");
            mDistrict1.setText("请点击搜索地址保存");
        }

    }

    public static Intent getMyIntent(Context context) {
        Intent intent = new Intent(context, SettingAddressActivity.class);
        return intent;
    }

    @OnClick({R.id.lo_first_address, R.id.lo_second_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lo_first_address:
               startActivityForResult(new Intent
                       (SettingAddressActivity.this, ActionSearchActivity.class),REQUEST_FIRSTADDRESS);
                break;
            case R.id.lo_second_address:
                startActivityForResult(new Intent
                        (SettingAddressActivity.this, ActionSearchActivity.class),REQUEST_SECONDADDRESS);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK)
        switch (requestCode){
            case REQUEST_FIRSTADDRESS :
                updataAddress(data);
                break;
            case REQUEST_SECONDADDRESS:
                updataAddress1(data);
                break;
        }

    }

    private void updataAddress1(Intent data) {
        PoiObject poiObject= (PoiObject) data.getSerializableExtra("PoiObject");
        mStar1.setImageResource(R.drawable.address_star_selected);
        mAddress1.setText(poiObject.address);
        mDistrict1.setText(poiObject.district);
        PreferencesUtils.putString(SettingAddressActivity.this,FIRST_ADDRESS,JSONUtil.toJSON(poiObject));
    }

    private void updataAddress(Intent data) {
        PoiObject poiObject= (PoiObject) data.getSerializableExtra("PoiObject");
        mStar.setImageResource(R.drawable.address_star_selected);
        mAddress.setText(poiObject.address);
        mDistrict.setText(poiObject.district);
        PreferencesUtils.putString(SettingAddressActivity.this,SENCOND_ADDRESS,JSONUtil.toJSON(poiObject));
    }


}
