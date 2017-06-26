package com.yiwen.mobike.activity.riding;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.yiwen.mobike.MyApplication;
import com.yiwen.mobike.R;
import com.yiwen.mobike.activity.pay.PayActivity;
import com.yiwen.mobike.bean.Credit;
import com.yiwen.mobike.bean.MyTripsData;
import com.yiwen.mobike.bean.MyUser;
import com.yiwen.mobike.bean.RideSummary;
import com.yiwen.mobike.bean.RoutePoint;
import com.yiwen.mobike.utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import dmax.dialog.SpotsDialog;


/**
 * Created by gaolei on 16/12/29.
 */

public class RouteDetailActivity extends AppCompatActivity {

    private MapView route_detail_mapview;
    BaiduMap routeBaiduMap;
    private BitmapDescriptor startBmp, endBmp;
    private MylocationListener mlistener;
    LocationClient mlocationClient;
    TextView       total_time, total_distance, total_price;
    public ArrayList<RoutePoint> routePoints;
    public static boolean completeRoute = false;
    String time, distance, price, routePointsStr;
    SpotsDialog mDialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_detail);
        route_detail_mapview = (MapView) findViewById(R.id.route_detail_mapview);
        total_time = (TextView) findViewById(R.id.total_time);
        total_distance = (TextView) findViewById(R.id.total_distance);
        total_price = (TextView) findViewById(R.id.total_pricce);
        routeBaiduMap = route_detail_mapview.getMap();
        route_detail_mapview.showZoomControls(false);
        startBmp = BitmapDescriptorFactory.fromResource(R.mipmap.route_start);
        endBmp = BitmapDescriptorFactory.fromResource(R.mipmap.route_end);
        initMap();

        Intent intent = getIntent();
        String time = intent.getStringExtra("totalTime");
        String distance = intent.getStringExtra("totalDistance");
        String price = intent.getStringExtra("totalPrice");
        routePointsStr = intent.getStringExtra("routePoints");
        routePoints = new Gson().fromJson(routePointsStr, new TypeToken<List<RoutePoint>>() {
        }.getType());

        savaDatas(time,distance,price);//yiwen add

        List<LatLng> points = new ArrayList<LatLng>();

        for (int i = 0; i < routePoints.size(); i++) {
            RoutePoint point = routePoints.get(i);
            LatLng latLng = new LatLng(point.getRouteLat(), point.getRouteLng());
            Log.d("gaolei", "point.getRouteLat()----show-----" + point.getRouteLat());
            Log.d("gaolei", "point.getRouteLng()----show-----" + point.getRouteLng());
            points.add(latLng);
        }
        if (points.size() > 2) {
            OverlayOptions ooPolyline = new PolylineOptions().width(10)
                    .color(0xFF36D19D).points(points);
            routeBaiduMap.addOverlay(ooPolyline);
            RoutePoint startPoint = routePoints.get(0);
            LatLng startPosition = new LatLng(startPoint.getRouteLat(), startPoint.getRouteLng());

            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(startPosition).zoom(18.0f);
            routeBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

            RoutePoint endPoint = routePoints.get(routePoints.size() - 1);
            LatLng endPosition = new LatLng(endPoint.getRouteLat(), endPoint.getRouteLng());
            addOverLayout(startPosition, endPosition);
        }

        total_time.setText("骑行时长：" + time + "分钟");
        total_distance.setText("骑行距离：" + distance + "米");
        total_price.setText("余额支付：" + price + "元");


    }

    private void savaDatas(String time, String distance, String price) {
        mDialog=new SpotsDialog(this);
        mDialog.show();
        MyTripsData myTripsData=new MyTripsData();
        myTripsData.setMyUser(MyApplication.getInstance().getUser());
        myTripsData.setMoney(price);
        myTripsData.setRideTime(time);
        SimpleDateFormat formatter   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate =  new Date(System.currentTimeMillis());
        myTripsData.setTime( formatter.format(curDate));
        myTripsData.setCarNub("88888888");
        myTripsData.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){

                }else {
                    ToastUtils.show(RouteDetailActivity.this,"骑行记录保存失败");
                    Logger.d(e);
                    mDialog.dismiss();
                }

            }
        });
        BmobQuery<Credit> credit = new BmobQuery<Credit>();
        credit.addWhereEqualTo("mMyUser", MyApplication.getInstance().getUser());
        credit.setLimit(1);
        credit.findObjects(new FindListener<Credit>() {
            @Override
            public void done(List<Credit> list, BmobException e) {
                if (e == null&&list!=null) {
                    Credit cr=list.get(0);
                    cr.setCreditNub(cr.getCreditNub()+1);
                  add(cr);
                } else {
                    mDialog.dismiss();
                }
            }
        });
        BmobQuery<RideSummary> query = new BmobQuery<RideSummary>();
        query.addWhereEqualTo("mMyUser", MyApplication.getInstance().getUser());
        query.setLimit(1);
        query.findObjects(new FindListener<RideSummary>() {
            @Override
            public void done(List<RideSummary> list, BmobException e) {
                if (e == null&&list!=null) {
                    RideSummary rideSummary=list.get(0);
                    rideSummary.setSave((Float.parseFloat(rideSummary.getSave())+10)+"");
                    rideSummary.setRide((Float.parseFloat(rideSummary.getRide())+10)+"");
                    rideSummary.setKaluli((Float.parseFloat(rideSummary.getKaluli())+10)+"");
                    savaRideSummary(rideSummary);
                } else {
                    ToastUtils.show(RouteDetailActivity.this,"骑行记录保存失败");
                    Logger.d(e);
                    mDialog.dismiss();
                }
            }
        });

        MyUser myUser=  MyApplication.getInstance().getUser();
        if (myUser.getMoney()<Float.parseFloat(price)){
            ToastUtils.show(RouteDetailActivity.this,"余额不足，请充值");
            startActivity(new Intent(RouteDetailActivity.this, PayActivity.class));
        }else {
            myUser.setMoney(myUser.getMoney()-
                    Float.parseFloat(price));
            MyUser newUse=new MyUser();
            newUse.setMoney(myUser.getMoney()-
                    Float.parseFloat(price));
            MyApplication.getInstance().upDataUser(myUser,newUse);
        }


    }

    private void savaRideSummary(RideSummary summary) {
        summary.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){

                }else {
                    ToastUtils.show(RouteDetailActivity.this,"骑行记录保存失败");
                    Logger.d(e);
                }
                mDialog.dismiss();
            }
        });
    }

    private void add(Credit cr) {

        cr.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){

                }else {
                    ToastUtils.show(RouteDetailActivity.this,"骑行记录保存失败");
                    Logger.d(e);
                    mDialog.dismiss();
                }

            }
        });
    }

    private void initMap() {
        mlocationClient = new LocationClient(this);
        //        mlistener = new MylocationListener();
        //        mlocationClient.registerLocationListener(mlistener);

        LocationClientOption mOption = new LocationClientOption();
        //设置坐标类型
        mOption.setCoorType("bd09ll");
        //设置是否需要地址信息，默认为无地址
        mOption.setIsNeedAddress(true);
        //设置是否打开gps进行定位
        mOption.setOpenGps(true);
        //设置扫描间隔，单位是毫秒 当<1000(1s)时，定时定位无效
        int span = 10000;
        mOption.setScanSpan(span);
        //设置 LocationClientOption
        mlocationClient.setLocOption(mOption);
        if (!mlocationClient.isStarted()) {
            mlocationClient.start();
        }
        UiSettings settings = routeBaiduMap.getUiSettings();
        settings.setScrollGesturesEnabled(true);
    }

    public class MylocationListener implements BDLocationListener {
        //定位请求回调接口
        private boolean isFirstIn = true;

        //定位请求回调函数,这里面会得到定位信息
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //判断是否为第一次定位,是的话需要定位到用户当前位置
            if (isFirstIn) {
                Log.d("gaolei", "onReceiveLocation----------RouteDetail-----" + bdLocation.getAddrStr());
                //                LatLng currentLL = new LatLng(bdLocation.getLatitude(),
                //                        bdLocation.getLongitude());
                ////                startNodeStr = PlanNode.withLocation(currentLL);
                //                MapStatus.Builder builder = new MapStatus.Builder();
                //                builder.target(currentLL).zoom(18.0f);
                //                routeBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                isFirstIn = false;

            }
        }
    }

    private void addOverLayout(LatLng startPosition, LatLng endPosition) {
        //先清除图层
        //        mBaiduMap.clear();
        // 定义Maker坐标点
        // 构建MarkerOption，用于在地图上添加Marker
        MarkerOptions options = new MarkerOptions().position(startPosition)
                .icon(startBmp);
        // 在地图上添加Marker，并显示
        routeBaiduMap.addOverlay(options);
        MarkerOptions options2 = new MarkerOptions().position(endPosition)
                .icon(endBmp);
        // 在地图上添加Marker，并显示
        routeBaiduMap.addOverlay(options2);

    }

    public void onDestroy() {
        super.onDestroy();
        routeBaiduMap.setMyLocationEnabled(false);
        mlocationClient.stop();
        completeRoute = false;
    }

    public void finishActivity(View view) {
        completeRoute = true;
        finish();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            completeRoute = true;
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
