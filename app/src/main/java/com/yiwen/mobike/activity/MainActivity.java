package com.yiwen.mobike.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.yiwen.mobike.R;
import com.yiwen.mobike.utils.MyConstains;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yiwen.mobike.R.id.refresh;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.ic_menu)
    ImageView      mIcMenu;
    @BindView(R.id.ic_search)
    ImageView      mIcSearch;
    @BindView(R.id.ic_message)
    ImageView      mIcMessage;
    @BindView(R.id.tv_allmobike)
    TextView       mTvAllmobike;
    @BindView(R.id.tv_mobike)
    TextView       mTvMobike;
    @BindView(R.id.tv_mobikelite)
    TextView       mTvMobikelite;
    @BindView(R.id.layout_selectmobike)
    LinearLayout   mLayoutSelectmobike;
    @BindView(R.id.bmapview)
    TextureMapView mapView;
    @BindView(R.id.main_login)
    RelativeLayout mMainLogin;
    @BindView(R.id.dingwei)
    ImageView      mDingwei;
    @BindView(R.id.kefu)
    ImageView      mKefu;
    @BindView(R.id.hongbao)
    ImageView      mHongbao;
    @BindView(R.id.refreshAll)
    RelativeLayout mRefreshAll;
    @BindView(R.id.scan_qrcode)
    LinearLayout   mScan_qrcode;
    @BindView(R.id.tv_location_info)
    TextView       mTvLocationInfo;
    @BindView(R.id.layout_location_info)
    LinearLayout   mLayoutLocationInfo;
    @BindView(R.id.tv_prices)
    TextView       mTvPrices;
    @BindView(R.id.tv_distance)
    TextView       mTvDistance;
    @BindView(R.id.minute)
    TextView       mMinute;
    @BindView(R.id.bike_info_board)
    LinearLayout   mBikeInfoBoard;
    @BindView(R.id.refresh)
    ImageView      mRefresh;
    private boolean isNeedLogin = true;
    private long    firstTime   = 0;
    public  LocationClient mLocationClient;
    private BaiduMap       baiduMap;
    private boolean isFirstLocation = true;

    private BDLocation mBDLocation;
    private static int MOBIKETYPE_ALL_MOBIKE = 0;//全部单车
    private static int MOBIKETYPE_MOBIKE     = 1;//MOBIKE
    private static int MOBIKETYPE_MOBIKELITE = 2;//MOBIKE_LITE
    private        int CURRENT_MOBIKETYPE    = MOBIKETYPE_ALL_MOBIKE;//当前mobike类型

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MylocationListener());

        ButterKnife.bind(this);

        //  mapView.showScaleControl(false);// 隐藏比例尺控件
        mapView.showZoomControls(false);//隐藏缩放按钮
        baiduMap = mapView.getMap();

        baiduMap.setMyLocationEnabled(true);

        requesMisson();

        initViews();

        initData();

        initEvent();

    }

    private void intiAnimation() {
        Animation ra;//动画
        if (mRefreshAll.getVisibility() == View.GONE) {
            mRefreshAll.setVisibility(View.VISIBLE);
            mRefreshAll.setClickable(true);
        }
        ra = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setFillAfter(true);
        ra.setDuration(500);
        ra.setRepeatCount(2);

        mRefresh.startAnimation(ra);

        ra.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mRefreshAll.setVisibility(View.GONE);
                mRefreshAll.setClickable(false);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void requesMisson() {
        List<String> permissionlist = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionlist.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionlist.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionlist.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (!permissionlist.isEmpty()) {
            String[] perssions = permissionlist.toArray(new String[permissionlist.size()]);
            ActivityCompat.requestPermissions(MainActivity.this, perssions, 1);
        } else {
            requestionLotion();
        }
    }

    private void initEvent() {

        initGPS();//检测GPS开启
    }

    private void initData() {
        isNeedLogin = getSharedPreferences(MyConstains.SP_MOBIKE, MODE_PRIVATE).
                getBoolean(MyConstains.IS_NEED_LOGIN, true);

    }

    private void initViews() {
        setMyClickable(mTvAllmobike);
    }

    private void requestionLotion() {
        initLotion();
        mLocationClient.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result == PackageManager.PERMISSION_DENIED) {
                            Toast.makeText(MainActivity.this, "请同意所申请权限", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestionLotion();
                } else {
                    Toast.makeText(MainActivity.this, "somthing hanppened", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {  //如果两次按键时间间隔大于2秒，则不退出
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;//更新firstTime
                    return true;
                } else {     //两次按键小于2秒时，退出应用
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    @OnClick({R.id.ic_menu, R.id.ic_search, R.id.ic_message, R.id.tv_allmobike, R.id.tv_mobike,
            R.id.tv_mobikelite, R.id.layout_selectmobike, R.id.main_login, R.id.dingwei, refresh,
            R.id.kefu, R.id.hongbao, R.id.scan_qrcode, R.id.layout_location_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_menu:
                Go2UserActivity();
                break;
            case R.id.ic_search:
                Go2Seach();
                break;
            case R.id.ic_message:
                Go2Login();
                break;
            case R.id.tv_allmobike:
                selectAllMObike();
                break;
            case R.id.tv_mobike:
                selectMObike();
                break;
            case R.id.tv_mobikelite:
                selectMObikeLite();
                break;
            case R.id.main_login:
                Go2Login();
                break;
            case R.id.dingwei:
                Go2myLotionAndRefresh();
                break;
            case refresh:
                refreshData();
                break;
            case R.id.kefu:
                Go2Kefu();
                break;
            case R.id.hongbao:
                Go2Login();
                break;
            case R.id.scan_qrcode:
                mRefresh.setVisibility(View.VISIBLE);
                //    Go2Login();
                break;
            case R.id.layout_location_info:
                break;

        }
    }

    private void setMyClickable(TextView tv) {
        mTvAllmobike.setClickable(true);
        mTvMobike.setClickable(true);
        mTvMobikelite.setClickable(true);
        mTvMobikelite.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTvAllmobike.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTvMobike.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        tv.setClickable(false);
        tv.setBackground(getResources().getDrawable(R.drawable.top_tab_select));
        CURRENT_MOBIKETYPE = Integer.parseInt((String) tv.getTag());
    }

    private void selectMObikeLite() {
        setMyClickable(mTvMobikelite);

    }

    private void selectMObike() {
        setMyClickable(mTvMobike);
    }

    private void selectAllMObike() {
        setMyClickable(mTvAllmobike);
    }

    private void Go2myLotionAndRefresh() {
        navigateTo(mBDLocation);
        refreshData();

    }

    private void Go2Seach() {
        Intent intent = new Intent(MainActivity.this, ActionSearchActivity.class);
        intent.putExtra("mylotion",mBDLocation);
        startActivityForResult(intent,1);
    }

    private void Go2UserActivity() {
        if (isNeedLogin) {
            Go2Login();
        } else {
            Intent intent = new Intent(MainActivity.this, UserActivity.class);
            startActivity(intent);
        }
    }

    private void refreshData() {
        intiAnimation();

    }

    private void Go2Login() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        // MainActivity.this.finish();
    }

    private void Go2Kefu() {
        if (isNeedLogin) {
            Intent intent = new Intent(MainActivity.this, Hint_viewActivity.class);
            startActivity(intent);
        }
    }

    private void initLotion() {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        mLocationClient.setLocOption(option);
    }

    /**
     * bd地图监听，接收当前位置
     */
    public class MylocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            mBDLocation = location;
            StringBuilder currentPosion = new StringBuilder();
            currentPosion.append("纬度：").append(location.getLatitude()).append("\n");
            currentPosion.append("经度：").append(location.getLongitude()).append("\n");
            currentPosion.append("国家：").append(location.getCountry()).append("\n");
            currentPosion.append("省：").append(location.getProvince()).append("\n");
            currentPosion.append("市：").append(location.getCity()).append("\n");
            currentPosion.append("区：").append(location.getDirection()).append("\n");
            currentPosion.append("街道：").append(location.getStreet()).append("\n");
//            currentPosion.append("定位方式：");
            // Log.d("currentPosion", "onReceiveLocation: " + currentPosion);
            //            if (location.getLocType() == BDLocation.TypeGpsLocation) {
            //                currentPosion.append("GPS");
            //            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
            //                currentPosion.append("network");
            //            }
            //            if (location.getLocType() == BDLocation.TypeGpsLocation ||
            //                    location.getLocType() == BDLocation.TypeNetWorkLocation) {
            //                navigateTo(location);
            //            }
            // mTextView.setText(currentPosion);
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    /**
     * des:地图跳到指定位置
     *
     * @param location
     */
    private void navigateTo(BDLocation location) {
        if (isFirstLocation) {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(18f);
            baiduMap.animateMapStatus(update);
            isFirstLocation = false;
        }
        MyLocationData.Builder builder = new MyLocationData.Builder();
        builder.latitude(location.getLatitude());
        builder.longitude(location.getLongitude());
        MyLocationData data = builder.build();
        baiduMap.setMyLocationData(data);
    }

    /**
     * des:提示开启GPS
     */
    private void initGPS() {
        LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        // 判断GPS模块是否开启，如果没有则跳转至设置开启界面，设置完毕后返回到首页
        if (!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("GPS提示：");
            builder.setMessage("请打开GPS开关，以便您更准确的找到自行车");
            builder.setCancelable(true);

            builder.setPositiveButton("确定",
                    new android.content.DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                            // 转到手机设置界面，用户设置GPS
                            Intent intent = new Intent(
                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(intent, 2); // 设置完成后返回到原来的界面

                        }
                    });
            builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        baiduMap.setMyLocationEnabled(false);
    }
}
