package com.yiwen.mobike.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.orhanobut.logger.Logger;
import com.yiwen.mobike.MyApplication;
import com.yiwen.mobike.R;
import com.yiwen.mobike.activity.customer.BikeDamageReportActivity;
import com.yiwen.mobike.activity.customer.NormalCustomerServiceActivity;
import com.yiwen.mobike.activity.customer.ReportUnlockFailActivity;
import com.yiwen.mobike.activity.customer.ReportViolationsActivity;
import com.yiwen.mobike.activity.login.LoginActivity;
import com.yiwen.mobike.activity.redpacket.MyRedPocketActivity;
import com.yiwen.mobike.activity.usercenter.MyMessagesActivity;
import com.yiwen.mobike.activity.usercenter.UserActivity;
import com.yiwen.mobike.bean.BikeInfo;
import com.yiwen.mobike.bean.MyUser;
import com.yiwen.mobike.map.MyOrientationListener;
import com.yiwen.mobike.map.RouteLineAdapter;
import com.yiwen.mobike.overlayutil.OverlayManager;
import com.yiwen.mobike.overlayutil.WalkingRouteOverlay;
import com.yiwen.mobike.provider.PoiObject;
import com.yiwen.mobike.qrcode.CaptureActivity;
import com.yiwen.mobike.service.RouteService;
import com.yiwen.mobike.utils.MyLocationManager;
import com.yiwen.mobike.utils.ToastUtils;
import com.yiwen.mobike.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yiwen.mobike.R.id.refresh;
import static com.yiwen.mobike.bean.BikeInfo.infos;

public class MainActivity extends AppCompatActivity implements OnGetRoutePlanResultListener {
    private static final String TAG           = "MainActivity";
    private static final int    REQS_LOCATION = 1;
    private static final int    REQS_UNLOCK   = 2;
    @BindView(R.id.ic_menu)
    ImageView      mIcMenu;//菜单按钮
    @BindView(R.id.ic_search)
    ImageView      mIcSearch;//搜索按钮
    @BindView(R.id.ic_message)
    ImageView      mIcMessage;//消息按钮
    @BindView(R.id.tv_allmobike)
    TextView       mTvAllmobike;//全部单车
    @BindView(R.id.tv_mobike)
    TextView       mTvMobike;//Mobike单车
    @BindView(R.id.tv_mobikelite)
    TextView       mTvMobikelite;//Mobikelite单车
    @BindView(R.id.layout_selectmobike)
    LinearLayout   mLayoutSelectmobike;//单车类型布局
    @BindView(R.id.bmapview)
    MapView        mBaiduMap;//mBaiduMap
    @BindView(R.id.id_bt_login)
    Button         mLogin;//login
    @BindView(R.id.dingwei)
    ImageView      mDingwei;//定位按钮
    @BindView(R.id.kefu)
    ImageView      mKefu;//客服按钮
    @BindView(R.id.hongbao)
    ImageView      mHongbao;//红包按钮
    @BindView(R.id.refreshAll)
    RelativeLayout mRefreshAll;//刷新按钮
    @BindView(R.id.scan_qrcode)
    LinearLayout   mScan_qrcode;//扫描按钮
    @BindView(R.id.tv_location_info)
    TextView       mTvLocationInfo;//当前定位地址
    @BindView(R.id.bike_info_board)
    LinearLayout   mBikeInfoBoard;//骑行信息布局
    @BindView(R.id.bike_order_layout)
    LinearLayout   mBikeOrderBoard;//骑行信息布局
    @BindView(R.id.refresh)
    ImageView      mRefresh;//刷新
    @BindView(R.id.bt_loginOrorder)
    Button         mBtLoginOrorder;//登陆或者预定按钮
    @BindView(R.id.book_countdown)
    TextView       book_countdown;//倒计时
    @BindView(R.id.bike_code)
    TextView       bike_code;//倒计时
    @BindView(R.id.cancel_book)
    TextView       mTvCancleBook;//
    @BindView(R.id.bike_sound)
    TextView       mTvBikeSound;//
    @BindView(R.id.id_lo_bike_distance)
    LinearLayout   mLoBikeInfo;
    @BindView(R.id.confirm_cancel_layout)
    LinearLayout   mConfirm_cancle;
    @BindView(R.id.tv_prices1)
    TextView       mTvPrices1;//价格
    @BindView(R.id.tv_distance1)
    TextView       mTvDistance1;//距离
    @BindView(R.id.minute1)
    TextView       mMinute1;//时间
    //   @BindView(R.id.tv_prices)
    public static TextView mTvPrices;//价格
    //    @BindView(R.id.tv_distance)
    public static TextView mTvDistance;//距离
    //   @BindView(R.id.minute)
    public static TextView mMinute;//时间
    private boolean isNeedLogin = true;//是否已登录
    private long    firstTime   = 0;//首次进入时间
    public  LocationClient mlocationClient;
    private BaiduMap       baiduMap;
    private boolean isFirstLocation       = true;//首次定位
    private boolean isNeedCurrentlocation = false;

    private BDLocation mBDLocation;
    private static int MOBIKETYPE_ALL_MOBIKE = 0;//全部单车
    private static int MOBIKETYPE_MOBIKE     = 1;//MOBIKE
    private static int MOBIKETYPE_MOBIKELITE = 2;//MOBIKE_LITE
    private        int CURRENT_MOBIKETYPE    = MOBIKETYPE_ALL_MOBIKE;//当前mobike类型

    private MyLocationConfiguration.LocationMode mCurrentMode;
    private MyLocationConfiguration.LocationMode locationMode;
    private MyOrientationListener                myOrientationListener;
    private float                                mCurrentX;
    private BikeInfo                             bInfo;
    private boolean hasPlanRoute = false, isServiceLive = false;
    PlanNode startNodeStr, endNodeStr;
    OverlayManager routeOverlay = null;
    LatLng currentLL;
    private double currentLatitude, currentLongitude, changeLatitude, changeLongitude;
    //自定义图标
    private BitmapDescriptor mIconLocation, dragLocationIcon, bikeIcon, nearestIcon;
    private long exitTime = 0;
    int nodeIndex = -1, distance;
    WalkingRouteResult nowResultwalk = null;
    RouteLine          routeLine     = null;
    RoutePlanSearch    mSearch       = null;    // 搜索模块，也可去掉地图模块独立使用
    private MyUser      myUser;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());//必须放在这里
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        myUser = MyApplication.getInstance().getUser();
        if (myUser != null)
            isNeedLogin = false;
        else {
            isNeedLogin = true;
        }
        Log.d(TAG, "initData: " + isNeedLogin);

        initMap();
        requesPemission();
        initViews();
        isServiceLive = Utils.isServiceWork(this, "com.yiwen.mobike.service.RouteService");
        if (isServiceLive)
            beginService();

        initData();

        initEvent();

    }

    private void beginService() {
        if (!Utils.isGpsOPen(this)) {
            Utils.showDialog(this);
            return;
        }
        isNeedCurrentlocation = true;
        mScan_qrcode.setVisibility(View.GONE);
        mTvLocationInfo.setVisibility(View.GONE);
        mBikeInfoBoard.setVisibility(View.VISIBLE);
        mLoBikeInfo.setVisibility(View.VISIBLE);
        mBikeOrderBoard.setVisibility(View.GONE);
        mBtLoginOrorder.setText("结束骑行");
        mBtLoginOrorder.setVisibility(View.VISIBLE);
        mMinute1.setText("骑行时长");
        mTvDistance1.setText("骑行距离");
        mTvPrices1.setText("费用计算");
        mRefreshAll.setVisibility(View.GONE);
        countDownTimer.cancel();

        if (routeOverlay != null)
            routeOverlay.removeFromMap();
        mBaiduMap.showZoomControls(false);
        baiduMap.clear();
        if (isServiceLive)
            mlocationClient.requestLocation();
        Intent intent = new Intent(this, RouteService.class);
        startService(intent);
        MyLocationConfiguration configuration
                = new MyLocationConfiguration(locationMode, true, mIconLocation);
        //设置定位图层配置信息，只有先允许定位图层后设置定位图层配置信息才会生
    }

    private void initMap() {
        baiduMap = mBaiduMap.getMap();

        baiduMap.setMyLocationEnabled(true);

        mlocationClient = new LocationClient(getApplicationContext());
        mlocationClient.registerLocationListener(new MylocationListener());
        //  mBaiduMap.showScaleControl(false);// 隐藏比例尺控件
        mBaiduMap.showZoomControls(false);//隐藏缩放按钮

        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(5000);//设置onReceiveLocation()获取位置的频率
        option.setIsNeedAddress(true);//如想获得具体位置就需要设置为true
        mlocationClient.setLocOption(option);
        mlocationClient.start();
        mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
        baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                mCurrentMode, true, null));
        myOrientationListener = new MyOrientationListener(this);
        //通过接口回调来实现实时方向的改变
        myOrientationListener.setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
            @Override
            public void onOrientationChanged(float x) {
                mCurrentX = x;
            }
        });
        myOrientationListener.start();
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);
        initMarkerClickEvent();
    }

    private void initMarkerClickEvent() {
        // 对Marker的点击
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                // 获得marker中的数据
                if (marker != null && marker.getExtraInfo() != null) {
                    BikeInfo bikeInfo = (BikeInfo) marker.getExtraInfo().get("info");
                    if (bikeInfo != null)
                        updateBikeInfo(bikeInfo);
                }
                return true;
            }
        });
    }

    private void updateBikeInfo(BikeInfo bikeInfo) {
        if (!hasPlanRoute) {
            if (isNeedLogin) {
                mBtLoginOrorder.setText("请登录后骑车");
            } else {
                mBtLoginOrorder.setText("预约骑车");
            }
            mLogin.setVisibility(View.GONE);
            mTvLocationInfo.setVisibility(View.VISIBLE);
            mBtLoginOrorder.setVisibility(View.VISIBLE);
            mBikeInfoBoard.setVisibility(View.VISIBLE);
            mLoBikeInfo.setVisibility(View.VISIBLE);
            mMinute.setText(bikeInfo.getTime());
            mTvDistance.setText(bikeInfo.getDistance());
            bInfo = bikeInfo;
            endNodeStr = PlanNode.withLocation(new LatLng(bikeInfo.getLatitude(), bikeInfo.getLongitude()));
            drawPlanRoute(endNodeStr);
        }
    }

    private void drawPlanRoute(PlanNode endNodeStr) {
        if (routeOverlay != null)
            routeOverlay.removeFromMap();
        if (endNodeStr != null) {
            Log.d(TAG, "changeLatitude-----startNode--------" + startNodeStr.getLocation().latitude);
            Log.d(TAG, "changeLongitude-----startNode--------" + startNodeStr.getLocation().longitude);
            mSearch.walkingSearch((new WalkingRoutePlanOption())
                    .from(startNodeStr).to(endNodeStr));
        }
    }

    private BaiduMap.OnMapStatusChangeListener changeListener = new BaiduMap.OnMapStatusChangeListener() {
        public void onMapStatusChangeStart(MapStatus mapStatus) {
        }

        public void onMapStatusChangeFinish(MapStatus mapStatus) {
            String _str = mapStatus.toString();
            String _regex = "target lat: (.*)\ntarget lng";
            String _regex2 = "target lng: (.*)\ntarget screen x";
            changeLatitude = Double.parseDouble(latlng(_regex, _str));
            changeLongitude = Double.parseDouble(latlng(_regex2, _str));
            LatLng changeLL = new LatLng(changeLatitude, changeLongitude);
            startNodeStr = PlanNode.withLocation(changeLL);
            Log.d(TAG, "changeLatitude-----change--------" + changeLatitude);
            Log.d(TAG, "changeLongitude-----change--------" + changeLongitude);
            if (!isNeedCurrentlocation) {
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(changeLL);
                baiduMap.setMapStatus(u);
                if (Math.hypot((changeLatitude - currentLatitude),
                        (changeLongitude - currentLongitude)) > 0.00001) {
                    Logger.d(Math.hypot((changeLatitude - currentLatitude),
                            (changeLongitude - currentLongitude)));
                    if (routeOverlay != null)
                        routeOverlay.removeFromMap();
                    addOverLayout(changeLatitude, changeLongitude);
                }

            }
        }

        public void onMapStatusChange(MapStatus mapStatus) {
        }
    };

    private String latlng(String regexStr, String str) {
        Pattern pattern = Pattern.compile(regexStr);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            str = matcher.group(1);
        }
        return str;
    }

    /**
     * 刷新动画
     */
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
                mRefreshAll.setClickable(false);
                mRefreshAll.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public static Intent getMyIntent(Context context, boolean unlockSuccess) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("unlockSuccess", unlockSuccess);
        return intent;
    }

    private void requesPemission() {
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
            //   requestionLotion();
        }
    }

    private void initEvent() {

        initGPS();//检测GPS开启
    }

    private void initData() {

    }

    private void initViews() {
        initKefuPopu();
        mTvPrices = (TextView) findViewById(R.id.tv_prices);
        mTvDistance = (TextView) findViewById(R.id.tv_distance);
        mMinute = (TextView) findViewById(R.id.minute);
        if (isNeedLogin) {
            mLogin.setVisibility(View.VISIBLE);
        } else {
            mLogin.setVisibility(View.GONE);
        }
        mScan_qrcode.setVisibility(View.VISIBLE);
        mBikeInfoBoard.setVisibility(View.GONE);
        mConfirm_cancle.setVisibility(View.GONE);
        mBikeOrderBoard.setVisibility(View.GONE);
        mBtLoginOrorder.setVisibility(View.GONE);
        mBikeInfoBoard.setVisibility(View.GONE);
        dragLocationIcon = BitmapDescriptorFactory.fromResource(R.mipmap.drag_location);
        bikeIcon = BitmapDescriptorFactory.fromResource(R.mipmap.bike_icon);
        setMyClickable(mTvAllmobike);
        baiduMap = mBaiduMap.getMap();
        baiduMap.setOnMapStatusChangeListener(changeListener);
    }

    /**
     * 客服菜单
     */
    private void initKefuPopu() {
        View popupView = getLayoutInflater().inflate(R.layout.kufu_popupwindow, null);
        mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mPopupWindow.getContentView().setFocusableInTouchMode(true);
        mPopupWindow.getContentView().setFocusable(true);
        mPopupWindow.setAnimationStyle(R.style.anim_kefu);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        mPopupWindow.getContentView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (mPopupWindow != null && mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }
                    return true;
                }
                return false;
            }
        });
        LinearLayout lo_unlockfail = (LinearLayout) popupView.findViewById(R.id.lo_unlockfail);
        LinearLayout lo_bike_broken = (LinearLayout) popupView.findViewById(R.id.lo_bike_broken);
        LinearLayout lo_report_illegal = (LinearLayout) popupView.findViewById(R.id.lo_report_illegal);
        LinearLayout lo_others_question = (LinearLayout) popupView.findViewById(R.id.lo_others_question);
        lo_unlockfail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                startActivity(new Intent(MainActivity.this, ReportUnlockFailActivity.class));
            }
        });
        lo_bike_broken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                startActivity(new Intent(MainActivity.this, BikeDamageReportActivity.class));
            }
        });
        lo_report_illegal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                startActivity(new Intent(MainActivity.this, ReportViolationsActivity.class));
            }
        });
        lo_others_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                startActivity(new Intent(MainActivity.this, NormalCustomerServiceActivity.class));
            }
        });
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha： 0~1
     */
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    public void getMyLocation() {
        isNeedCurrentlocation = true;
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        baiduMap.setMapStatus(msu);
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
                    //      requestionLotion();
                } else {
                    Toast.makeText(MainActivity.this, "somthing hanppened", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (mBikeInfoBoard.getVisibility() == View.VISIBLE) {
                if (!Utils.isServiceWork(this, "com.yiwen.mobike.service.RouteService"))
                    cancelBook();
                return true;
            }
            if (mPopupWindow != null && mPopupWindow.isShowing()) {
                // mPopupWindow.showAtLocation(findViewById(R.id.activity_main), Gravity.BOTTOM, 0, 0);
                mPopupWindow.dismiss();
                return true;
            }

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                //                finish();
                //                System.exit(0);
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void cancelBook() {
        if (isNeedLogin) {
            mLogin.setVisibility(View.VISIBLE);
        } else {
            mLogin.setVisibility(View.GONE);
        }
        mBikeInfoBoard.setVisibility(View.GONE);
        countDownTimer.cancel();
        mBikeOrderBoard.setVisibility(View.GONE);
        //        confirm_cancel_layout.setVisibility(View.GONE);
        //        prompt.setVisibility(View.GONE);
        //        bike_distance_layout.setVisibility(View.VISIBLE);
        //        bike_distance_layout.setVisibility(View.VISIBLE);
        mBtLoginOrorder.setVisibility(View.VISIBLE);
        if (routeOverlay != null)
            routeOverlay.removeFromMap();
        MapStatus.Builder builder = new MapStatus.Builder();
        //地图缩放比设置为18
        builder.target(currentLL).zoom(18.0f);
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    private CountDownTimer countDownTimer = new CountDownTimer(10 * 60 * 1000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            book_countdown.setText(millisUntilFinished / 60000 + "分" + ((millisUntilFinished / 1000) % 60) + "秒");
        }

        @Override
        public void onFinish() {
            book_countdown.setText("预约结束");
            Toast.makeText(MainActivity.this, getString(R.string.cancel_book_toast), Toast.LENGTH_SHORT).show();
        }
    };

    @OnClick({R.id.ic_menu, R.id.ic_search, R.id.ic_message, R.id.tv_allmobike, R.id.tv_mobike,
            R.id.tv_mobikelite, R.id.id_bt_login, R.id.dingwei, refresh,
            R.id.kefu, R.id.hongbao, R.id.scan_qrcode, R.id.bt_loginOrorder, R.id.cancel_book, R.id.bike_sound})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_menu:
                Go2otherActivityAndChackLogin(UserActivity.class);
                break;
            case R.id.ic_search:
                Go2Seach();
                break;
            case R.id.ic_message:
                startActivity(new Intent(MainActivity.this, MyMessagesActivity.class));
                break;
            case R.id.tv_allmobike:
                selectAllMobike();
                break;
            case R.id.tv_mobike:
                selectMobike();
                break;
            case R.id.tv_mobikelite:
                selectMobikeLite();
                break;
            case R.id.id_bt_login:
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
                Go2hongbao();
                break;
            case R.id.scan_qrcode:
                Go2LoginOrScan();
                break;
            case R.id.bt_loginOrorder:
                Go2LoginOrorder();
                break;
            case R.id.cancel_book:
                cancelBook();
                break;
            case R.id.bike_sound:
                beginService();
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

    private void selectMobikeLite() {
        setMyClickable(mTvMobikelite);
        refreshData();

    }

    private void selectMobike() {
        setMyClickable(mTvMobike);
        refreshData();
    }

    private void selectAllMobike() {
        setMyClickable(mTvAllmobike);
        refreshData();
    }

    private void Go2myLotionAndRefresh() {
        getMyLocation();
        refreshData();
    }

    private void Go2otherActivityAndChackLogin(Class c) {
        if (isNeedLogin) {
            Go2Login();
        } else {
            Intent intent = new Intent(MainActivity.this, c);
            startActivity(intent);
        }
    }

    private void Go2Seach() {
        Intent intent = new Intent(MainActivity.this, ActionSearchActivity.class);
        intent.putExtra("mylotion", mBDLocation);
        startActivityForResult(intent, REQS_LOCATION);
    }

    private void Go2LoginOrorder() {
        if (isNeedLogin) {
            Go2Login();
        } else {
            isServiceLive = Utils.isServiceWork(this, "com.yiwen.mobike.service.RouteService");
            if (isServiceLive) {
                toastDialog();
            } else {
                GoforOrorder();
            }

        }
    }

    private void Go2hongbao() {
        if (isNeedLogin) {
            Go2Login();
        } else {
            Intent intent = new Intent(MainActivity.this, MyRedPocketActivity.class);
            startActivity(intent);
        }
    }


    private void GoforOrorder() {
        mBikeInfoBoard.setVisibility(View.VISIBLE);
        mBikeOrderBoard.setVisibility(View.VISIBLE);
        mConfirm_cancle.setVisibility(View.VISIBLE);
        mLoBikeInfo.setVisibility(View.GONE);
        mBtLoginOrorder.setVisibility(View.GONE);
        bike_code.setText(bInfo.getName());
        countDownTimer.start();
    }

    private void Go2LoginOrScan() {
        if (isNeedLogin) {
            Go2Login();
        } else {
            Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
            startActivityForResult(intent, REQS_UNLOCK);
        }
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
        if (routeOverlay != null)
            routeOverlay.removeFromMap();
        Log.d(TAG, "changeLatitude-----btn_refresh--------" + changeLatitude);
        Log.d(TAG, "changeLongitude-----btn_refresh--------" + changeLongitude);
        addOverLayout(changeLatitude, changeLongitude);
    }

    private void Go2Login() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void Go2Kefu() {
        if (isNeedLogin) {
            Intent intent = new Intent(MainActivity.this, HelpCardActivity.class);
            startActivity(intent);
        } else {
            mPopupWindow.showAtLocation(findViewById(R.id.layout_main), Gravity.BOTTOM, 0, 0);
            backgroundAlpha(0.4f);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQS_LOCATION:
                if (RESULT_OK == resultCode) {
                    PoiObject info = (PoiObject) data.getSerializableExtra("PoiObject");
                    Logger.d(info);
                    LatLng latLng = new LatLng(Double.parseDouble(info.lattitude),
                            Double.parseDouble(info.longitude));
                    isNeedCurrentlocation = false;
                    MyLocationData.Builder builder = new MyLocationData.Builder();
                    builder.latitude(latLng.latitude);
                    builder.longitude(latLng.longitude);
                    MyLocationData build = builder.build();
                    baiduMap.setMyLocationData(build);
                    //                    MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                    //                    baiduMap.setMapStatus(msu);
                    if (routeOverlay != null)
                        routeOverlay.removeFromMap();
                    addOverLayout(latLng.latitude, latLng.longitude);
                }
                break;
            case REQS_UNLOCK:
                if (RESULT_OK == resultCode) {
                    ToastUtils.show(MainActivity.this, data.getStringExtra("result"));
                    beginService();
                }

                break;
        }
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(MainActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            nodeIndex = -1;

            if (result.getRouteLines().size() > 1) {
                nowResultwalk = result;

                MyTransitDlg myTransitDlg = new MyTransitDlg(MainActivity.this,
                        result.getRouteLines(),
                        RouteLineAdapter.Type.WALKING_ROUTE);
                myTransitDlg.setOnItemInDlgClickLinster(new OnItemInDlgClickListener() {
                    public void onItemClick(int position) {
                        routeLine = nowResultwalk.getRouteLines().get(position);
                        WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(baiduMap);


                        routeOverlay = overlay;
                        //路线查询成功
                        try {
                            overlay.setData(nowResultwalk.getRouteLines().get(position));
                            overlay.addToMap();
                            overlay.zoomToSpan();
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.show(MainActivity.this, "路径规划异常");
                        }
                    }

                });
                myTransitDlg.show();

            } else if (result.getRouteLines().size() == 1) {
                // 直接显示
                routeLine = result.getRouteLines().get(0);
                int totalDistance = routeLine.getDistance();
                int totalTime = routeLine.getDuration() / 60;
                mTvDistance.setText(Utils.distanceFormatter(totalDistance));
                mMinute.setText(Utils.timeFormatter(totalTime));
                String distanceStr = Utils.distanceFormatter(totalDistance);
                String timeStr = Utils.timeFormatter(totalTime);
                //                setSpannableStr(bike_time, timeStr, 0, timeStr.length() - 2);
                //                setSpannableStr(bike_distance, distanceStr, 0, distanceStr.length() - 1);
                Log.d("gaolei", "totalDistance------------------" + totalDistance);

                WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(baiduMap);
                //                    mBaiduMap.setOnMarkerClickListener(overlay);
                routeOverlay = overlay;
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
            } else {
                Log.d("route result", "结果数<0");
                return;
            }
        }
    }

    protected void onRestart() {
        super.onRestart();
        myUser = MyApplication.getInstance().getUser();
        if (myUser != null)
            isNeedLogin = false;
        else {
            isNeedLogin = true;
        }
        baiduMap.setMyLocationEnabled(true);
        mlocationClient.start();
        myOrientationListener.start();
        mlocationClient.requestLocation();
        isServiceLive = Utils.isServiceWork(this, "com.yiwen.mobike.service.RouteService");
        Log.d(TAG, "MainActivity------------onRestart------------------");
        if (QRCodeInputActivity.unlockSuccess || isServiceLive) {
            beginService();
            return;
        }
        // if (RouteDetailActivity.completeRoute)
        backFromRouteDetail();
    }

    private void backFromRouteDetail() {
        isFirstLocation = true;
        mBikeInfoBoard.setVisibility(View.GONE);
        mConfirm_cancle.setVisibility(View.GONE);
        mBikeOrderBoard.setVisibility(View.GONE);
        mBtLoginOrorder.setVisibility(View.GONE);
        mBikeInfoBoard.setVisibility(View.GONE);
        mScan_qrcode.setVisibility(View.VISIBLE);
        if (isNeedLogin) {
            mLogin.setVisibility(View.VISIBLE);
            mBtLoginOrorder.setText("请登录后骑车");
        } else {
            mBtLoginOrorder.setText("预约骑车");
            mLogin.setVisibility(View.GONE);
        }
        mBaiduMap.showZoomControls(false);
        getMyLocation();
        if (routeOverlay != null)
            routeOverlay.removeFromMap();
        addOverLayout(currentLatitude, currentLongitude);
    }

    private class MyWalkingRouteOverlay extends WalkingRouteOverlay {

        public MyWalkingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            //            if (useDefaultIcon) {
            return BitmapDescriptorFactory.fromResource(R.mipmap.transparent_icon);
            //            }
            //            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            //            if (useDefaultIcon) {
            return BitmapDescriptorFactory.fromResource(R.mipmap.transparent_icon);
            //            }
            //            return null;
        }
    }

    // 供路线选择的Dialog
    class MyTransitDlg extends Dialog {

        private List<? extends RouteLine> mtransitRouteLines;
        private ListView                  transitRouteList;
        private RouteLineAdapter          mTransitAdapter;

        OnItemInDlgClickListener onItemInDlgClickListener;

        public MyTransitDlg(Context context, int theme) {
            super(context, theme);
        }

        public MyTransitDlg(Context context, List<? extends RouteLine> transitRouteLines, RouteLineAdapter.Type
                type) {
            this(context, 0);
            mtransitRouteLines = transitRouteLines;
            mTransitAdapter = new RouteLineAdapter(context, mtransitRouteLines, type);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        public void setOnItemInDlgClickLinster(OnItemInDlgClickListener itemListener) {
            onItemInDlgClickListener = itemListener;
        }
    }

    // 响应DLg中的List item 点击
    interface OnItemInDlgClickListener {
        public void onItemClick(int position);
    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }

    /**
     * bd地图监听，接收当前位置
     */
    public class MylocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null || baiduMap == null) {
                return;
            }
            mBDLocation = bdLocation;
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    .direction(mCurrentX)//设定图标方向     // 此处设置开发者获取到的方向信息，顺时针0-360
                    .latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude()).build();
            if (isNeedCurrentlocation)
                baiduMap.setMyLocationData(locData);
            currentLatitude = bdLocation.getLatitude();
            currentLongitude = bdLocation.getLongitude();
            mTvLocationInfo.setText(bdLocation.getAddrStr());
            currentLL = new LatLng(bdLocation.getLatitude(),
                    bdLocation.getLongitude());
            MyLocationManager.getInstance().setCurrentLL(currentLL);
            MyLocationManager.getInstance().setAddress(bdLocation.getAddrStr());
            startNodeStr = PlanNode.withLocation(currentLL);
            //option.setScanSpan(5000)，每隔5000ms这个方法就会调用一次，而有些我们只想调用一次，所以要判断一下isFirstLoc
            if (isFirstLocation) {
                isFirstLocation = false;
                LatLng ll = new LatLng(bdLocation.getLatitude(),
                        bdLocation.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                //地图缩放比设置为18
                builder.target(ll).zoom(18.0f);
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                changeLatitude = bdLocation.getLatitude();
                changeLongitude = bdLocation.getLongitude();
                if (!isServiceLive) {
                    addOverLayout(currentLatitude, currentLongitude);
                }
            }
        }
    }

    private void addOverLayout(double _latitude, double _longitude) {
        //先清除图层
        baiduMap.clear();
        mlocationClient.requestLocation();
        // 定义Maker坐标点
        LatLng point = new LatLng(_latitude, _longitude);
        // 构建MarkerOption，用于在地图上添加Marker
        MarkerOptions options = new MarkerOptions().position(point)
                .icon(dragLocationIcon);
        // 在地图上添加Marker，并显示
        baiduMap.addOverlay(options);
        infos.clear();
        infos.add(new BikeInfo(_latitude - new Random().nextInt(5) * 0.0005, _longitude - new Random().nextInt(5) * 0.0005, R.mipmap.bike_mobai, "001",
                "100米", "1分钟"));
        infos.add(new BikeInfo(_latitude - new Random().nextInt(5) * 0.0005, _longitude - new Random().nextInt(5) * 0.0005, R.mipmap.bike_youbai, "002",
                "200米", "2分钟"));
        infos.add(new BikeInfo(_latitude - new Random().nextInt(5) * 0.0005, _longitude - new Random().nextInt(5) * 0.0005, R.mipmap.bike_ofo, "003",
                "300米", "3分钟"));
        infos.add(new BikeInfo(_latitude - new Random().nextInt(5) * 0.0005, _longitude - new Random().nextInt(5) * 0.0005, R.mipmap.bike_xiaolan, "004",
                "400米", "4分钟"));
        BikeInfo bikeInfo = new BikeInfo(_latitude - 0.0005, _longitude - 0.0005, R.mipmap.bike_xiaolan, "005",
                "50米", "0.5分钟");
        infos.add(bikeInfo);
        addInfosOverlay(infos);
        initNearestBike(bikeInfo, new LatLng(_latitude - 0.0005, _longitude - 0.0005));
    }

    private void initNearestBike(final BikeInfo bikeInfo, LatLng latLng) {
        ImageView nearestIcon = new ImageView(getApplicationContext());
        nearestIcon.setImageResource(R.mipmap.nearest_icon);
        InfoWindow.OnInfoWindowClickListener listener = null;
        listener = new InfoWindow.OnInfoWindowClickListener() {
            public void onInfoWindowClick() {
                updateBikeInfo(bikeInfo);
                baiduMap.hideInfoWindow();
            }
        };
        InfoWindow mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(nearestIcon), latLng, -108, listener);
        baiduMap.showInfoWindow(mInfoWindow);
    }

    private void addInfosOverlay(List<BikeInfo> infos) {
        LatLng latLng = null;
        OverlayOptions overlayOptions = null;
        Marker marker = null;
        for (BikeInfo info : infos) {
            // 位置
            latLng = new LatLng(info.getLatitude(), info.getLongitude());
            // 图标
            overlayOptions = new MarkerOptions().position(latLng)
                    .icon(bikeIcon).zIndex(5);
            marker = (Marker) (baiduMap.addOverlay(overlayOptions));
            Bundle bundle = new Bundle();
            bundle.putSerializable("info", info);
            marker.setExtraInfo(bundle);
        }
        // 将地图移到到最后一个经纬度位置
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
        baiduMap.setMapStatus(u);
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

    public static class LocationReceiver extends BroadcastReceiver {
        public LocationReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Utils.isTopActivity(context)) {
                String time = intent.getStringExtra("totalTime");
                String distance = intent.getStringExtra("totalDistance");
                String price = intent.getStringExtra("totalPrice");
                mMinute.setText(time);
                mTvDistance.setText(distance);
                mTvPrices.setText(price);
                Log.d(TAG, "MainActivity-------TopActivity---------true");
                Log.d(TAG, "MainActivity-------time:" + time);
                Log.d(TAG, "MainActivity-------distance:" + distance);
                Log.d(TAG, "MainActivity-------price:" + price);
            } else {
                Log.d(TAG, "MainActivity-------TopActivity---------false");
            }
        }
    }

    /**
     * 结束骑行
     */
    private void toastDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("确认要结束进程吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(MainActivity.this, RouteService.class);
                stopService(intent);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
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
        mBaiduMap.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBaiduMap.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBaiduMap.onDestroy();
        baiduMap.setMyLocationEnabled(false);
        // 退出时销毁定位
        mlocationClient.stop();
        // 关闭定位图层
        mBaiduMap = null;
        countDownTimer.cancel();
        isFirstLocation = true;
    }
}
