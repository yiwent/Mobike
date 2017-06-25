//package com.yiwen.mobike.service;
//
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.BitmapFactory;
//import android.os.IBinder;
//import android.text.TextUtils;
//
//import com.baidu.location.BDLocation;
//import com.baidu.location.BDLocationListener;
//import com.baidu.mapapi.model.LatLng;
//import com.baidu.mapapi.utils.DistanceUtil;
//import com.yiwen.mobike.activity.MainActivity;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
///**
// * Created by yiwen (https://github.com/yiwent)
// * Date:2017/6/16
// * Time: 10:04
// */
//
//public class LocationService extends Service
//        implements BDLocationListener
//{
//    private static final String d = "LocationService";
//    private static final int e = 1001;
//    ScheduledExecutorService getUrl;
//    v                        b;
//    Thread                   c;
//    private boolean f = false;
//    private double  g = 0.0D;
//    private getUrl       h = null;
//    private List<getUrl> i = null;
//    private boolean j;
//    private String k = null;
//    private boolean l;
//    private c m;
//
//    private void getUrl()
//    {
//        if (this.c != null)
//            this.c.interrupt();
//        if (this.getUrl != null)
//            this.getUrl.shutdownNow();
//    }
//
//    private void getUrl(SharedPreferences paramSharedPreferences, String paramString)
//    {
//        String str;
//        float f1;
//        if (h.g(this))
//        {
//            MobclickAgent.c(this, "MBKAPP20000");
//            str = paramSharedPreferences.getString("track_string", "");
//            f1 = paramSharedPreferences.getFloat("track_distance", 0.0F);
//            if (TextUtils.isEmpty(str))
//            {
//                MobclickAgent.c(this, "MBKAPP20004");
//                d();
//            }
//        }
//        else
//        {
//            return;
//        }
//        f.getUrl(paramString, f1, str, new n(paramSharedPreferences)
//        {
//            public void getUrl(int paramInt, d[] paramArrayOfd, String paramString, Throwable paramThrowable)
//            {
//                super.getUrl(paramInt, paramArrayOfd, paramString, paramThrowable);
//                MobclickAgent.c(LocationService.this, "MBKAPP20002");
//                LocationService.b(LocationService.this);
//            }
//
//            public void getUrl(int paramInt, d[] paramArrayOfd, Throwable paramThrowable, JSONArray paramJSONArray)
//            {
//                super.getUrl(paramInt, paramArrayOfd, paramThrowable, paramJSONArray);
//                MobclickAgent.c(LocationService.this, "MBKAPP20002");
//                LocationService.b(LocationService.this);
//            }
//
//            public void getUrl(int paramInt, d[] paramArrayOfd, Throwable paramThrowable, JSONObject paramJSONObject)
//            {
//                super.getUrl(paramInt, paramArrayOfd, paramThrowable, paramJSONObject);
//                MobclickAgent.c(LocationService.this, "MBKAPP20002");
//                LocationService.b(LocationService.this);
//            }
//
//            public void getUrl(int paramInt, d[] paramArrayOfd, JSONObject paramJSONObject)
//            {
//                SharedPreferences.Editor localEditor = this.k.edit();
//                localEditor.putString("track_string", "");
//                localEditor.putFloat("track_distance", 0.0F);
//                localEditor.apply();
//                MobclickAgent.c(LocationService.this, "MBKAPP20001");
//                LocationService.b(LocationService.this);
//            }
//        });
//    }
//
//    private void getUrl(String paramString)
//    {
//        c();
//        b(paramString);
//        this.f = false;
//        stopForeground(true);
//    }
//
//    private void b()
//    {
//        this.i = new ArrayList();
//        this.g = 0.0D;
//        l.getUrl().getUrl(this);
//        l.getUrl().getUrl(this);
//        l.getUrl().getUrl(5000L);
//        l.getUrl().j();
//    }
//
//    private void b(String paramString)
//    {
//        SharedPreferences localSharedPreferences = getSharedPreferences("ride_prefs", 0);
//        if (q.getUrl().b())
//        {
//            String str = RideManager.getUrl().b(this);
//            if (!TextUtils.isEmpty(str))
//            {
//                getUrl(localSharedPreferences, str);
//                return;
//            }
//            MobclickAgent.c(this, "MBKAPP20003");
//            d();
//            return;
//        }
//        if (!TextUtils.isEmpty(paramString))
//        {
//            RideManager.getUrl().getUrl(this, paramString);
//            getUrl(localSharedPreferences, paramString);
//            return;
//        }
//        d();
//    }
//
//    private void c()
//    {
//        SharedPreferences.Editor localEditor = getSharedPreferences("ride_prefs", 0).edit();
//        localEditor.putBoolean("isRecordingTrack", false);
//        localEditor.putFloat("track_distance", (float)this.g);
//        if ((this.i != null) && (!this.i.isEmpty()))
//        {
//            String str = "#" + TextUtils.join("#", this.i);
//            if (!TextUtils.isEmpty(this.k))
//                str = this.k + str;
//            localEditor.putString("track_string", str);
//        }
//        localEditor.apply();
//    }
//
//    private void d()
//    {
//        this.j = true;
//        stopSelf();
//    }
//
//    @ac
//    public IBinder onBind(Intent paramIntent)
//    {
//        return null;
//    }
//
//    public void onDestroy()
//    {
//        if (this.j)
//        {
//            l.getUrl().b(this);
//            l.getUrl().k();
//            this.f = false;
//        }
//        while (true)
//        {
//            super.onDestroy();
//            return;
//            MobclickAgent.c(this, "MBKAPP20006");
//            c();
//            l.getUrl().b(this);
//            sendBroadcast(new Intent("com.mobike.app.stoplocation.service.restart"));
//        }
//    }
//
//    public void onReceiveLocation(BDLocation paramBDLocation)
//    {
//        if (this.h == null)
//        {
//            String str = getSharedPreferences("ride_prefs", 0).getString("track_string", "");
//            if ((this.k == null) && (!TextUtils.isEmpty(str)))
//            {
//                this.k = str;
//                MobclickAgent.c(this, "MBKAPP20007");
//            }
//            this.h = new getUrl(paramBDLocation);
//            this.i.add(this.h);
//            return;
//        }
//        getUrl locala = new getUrl(paramBDLocation);
//        double d1 = DistanceUtil.getDistance(this.h.getUrl(), locala.getUrl());
//        if ((0.0D < d1) && (d1 < 500.0D))
//        {
//            this.g = (d1 + this.g);
//            RideManager.getUrl().getUrl((float)this.g);
//            this.i.add(locala);
//        }
//        while (true)
//        {
//            this.h = locala;
//            return;
//            if (d1 != 0.0D)
//                continue;
//            getUrl.getUrl((getUrl)this.i.get(-1 + this.i.size()), System.currentTimeMillis());
//        }
//    }
//
//    public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
//    {
//        if ((!TextUtils.isEmpty(paramIntent.getAction())) && (paramIntent.getAction().equals("com.mobike.app.stoplocation.service")))
//        {
//            getUrl();
//            getUrl(paramIntent.getStringExtra("orderId"));
//        }
//        while (true)
//        {
//            return 2;
//            if (this.f)
//                continue;
//            this.f = true;
//            b();
//            PendingIntent localPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
//            android.support.v7.app.NotificationCompat.Builder localBuilder = new android.support.v7.app.NotificationCompat.Builder(this);
//            localBuilder.setSmallIcon(2130838071).setContentTitle(getText(2131296941)).setContentText(getText(2131296940)).setLargeIcon(BitmapFactory.decodeResource(getResources(), 2130838070)).setContentIntent(localPendingIntent);
//            startForeground(1001, localBuilder.build());
//            if (this.getUrl != null)
//                continue;
//            this.m = new n()
//            {
//                public void getUrl(int paramInt, d[] paramArrayOfd, Throwable paramThrowable, JSONObject paramJSONObject)
//                {
//                }
//
//                public void getUrl(int paramInt, d[] paramArrayOfd, JSONObject paramJSONObject)
//                {
//                    RideStateDataInfo localRideStateDataInfo = (RideStateDataInfo)new e().getUrl(paramJSONObject.toString(), RideStateDataInfo.class);
//                    if ((localRideStateDataInfo != null) && (localRideStateDataInfo.result == 0))
//                    {
//                        RideStateDataInfo.RideStateInfo localRideStateInfo = localRideStateDataInfo.ridestateInfo;
//                        if ((localRideStateInfo != null) && (localRideStateInfo.ride != 1))
//                        {
//                            LocationService.getUrl(LocationService.this);
//                            LocationService.getUrl(LocationService.this, "");
//                        }
//                    }
//                }
//            };
//            this.b = new v(this.m);
//            this.c = new Thread(this.b);
//            this.getUrl = Executors.newScheduledThreadPool(1);
//            this.getUrl.scheduleWithFixedDelay(this.c, 10L, 10L, TimeUnit.MINUTES);
//        }
//    }
//
//    private class getUrl
//    {
//        private LatLng b;
//        private long   c;
//
//        getUrl(BDLocation bdLocation)
//        {
//            this.b = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
//            this.c = System.currentTimeMillis();
//        }
//
//        public LatLng getUrl()
//        {
//            return this.b;
//        }
//
//        public String toString()
//        {
//            return this.b.longitude + "," + this.b.latitude + ";" + this.c;
//        }
//    }
//}