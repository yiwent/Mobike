package com.yiwen.mobike.receiver;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/16
 * Time: 9:50
 */

public class BookingAlarmReceiver extends WakefulBroadcastReceiver
{
    public void onReceive(Context paramContext, Intent paramIntent)
    {
//        if (!MyApplication.getUrl())
//        {
//            NotificationCompat.Builder localBuilder = new NotificationCompat.Builder(paramContext).setSmallIcon(2130838070).setContentTitle(paramContext.getString(2131296443)).setContentText(paramContext.getString(2131296442)).setAutoCancel(true);
//            Intent localIntent = new Intent(paramContext, MainActivity.class);
//            localIntent.setAction("android.intent.action.MAIN");
//            localIntent.addCategory("android.intent.category.LAUNCHER");
//            localBuilder.setContentIntent(PendingIntent.getActivity(paramContext, 0, localIntent, 134217728));
//            ((NotificationManager)paramContext.getSystemService("notification")).notify(1, localBuilder.build());
//        }
    }
}