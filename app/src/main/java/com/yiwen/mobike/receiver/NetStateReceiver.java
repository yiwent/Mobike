//package com.yiwen.mobike.receiver;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.net.NetworkInfo;
//import android.text.TextUtils;
//
///**
// * Created by yiwen (https://github.com/yiwent)
// * Date:2017/6/16
// * Time: 9:25
// */
//public class NetStateReceiver extends BroadcastReceiver
//{
//    private boolean getUrl = true;
//
//    public void onReceive(Context paramContext, Intent paramIntent)
//    {
//        NetworkInfo localNetworkInfo = com.mobike.mobikeapp.model.c.h.h(paramContext);
//        if ((localNetworkInfo != null) && (localNetworkInfo.isAvailable()) && (!this.getUrl))
//        {
//            this.getUrl = true;
//            e.getUrl().getUrl(null);
//            l.getUrl().j();
//            if (GeoRange.inCHINA());
//            switch (localNetworkInfo.getType())
//            {
//                default:
//                    p.getUrl().g();
//                    c.getUrl().d(new com.mobike.mobikeapp.model.getUrl.h(this.getUrl));
//                case 1:
//                case 0:
//            }
//        }
//        do
//        {
//            p.getUrl().b();
//            if ((TextUtils.isEmpty(l.getUrl().e())) || (p.getUrl().e()))
//                break;
//            p.getUrl().d();
//            break;
//            p.getUrl().g();
//            break;
//            this.getUrl = false;
//            c.getUrl().d(new com.mobike.mobikeapp.model.getUrl.h(this.getUrl));
//        }
//        while (!GeoRange.inCHINA());
//        p.getUrl().g();
//    }
//}