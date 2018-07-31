package me.hapened.hapened;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;

public class Notify extends WakefulBroadcastReceiver {
    public static final int uniqueID=987988;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder n=new NotificationCompat.Builder(context);
        n.setAutoCancel(true);
        n.setSmallIcon(R.mipmap.ic_launcher);
        n.setTicker("Don't forget to write your entry!");
        n.setWhen(System.currentTimeMillis());
        n.setContentTitle("Hapened");
        n.setContentText("Don't forget to write your entry!");
        Intent it=new Intent(context,ListAll.class);
        PendingIntent pi=PendingIntent.getBroadcast(context,0, it,0);
        n.setContentIntent(pi);
        ((NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(uniqueID, n.build());
        BR.setAlarm(context,false,context.getSharedPreferences(BR.PREF_KEY_FILE, Context.MODE_PRIVATE).getLong(BR.NXT_TRIGGER_TIME_KEY, System.currentTimeMillis()));
    }
}
