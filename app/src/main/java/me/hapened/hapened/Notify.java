package me.hapened.hapened;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Lucy on 2015-12-22.
 */
public class Notify extends BroadcastReceiver {
    private int uniqueID=3657554;
    @Override
    public void onReceive(Context context, Intent intent) {
        //System.out.println("asdfasdfasdfasdf");
        //Toast.makeText(context,"worked",Toast.LENGTH_LONG).show();
        NotificationCompat.Builder n=new NotificationCompat.Builder(context);
        n.setAutoCancel(true);
        n.setSmallIcon(R.mipmap.ic_launcher);
        n.setTicker("ticker");
        n.setWhen(System.currentTimeMillis());
        n.setContentTitle("title");
        n.setContentText("text");
        Intent it=new Intent(context,ListAll.class);
        PendingIntent pi=PendingIntent.getBroadcast(context,0, it,0);
        n.setContentIntent(pi);
        ((NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(uniqueID,n.build());
    }
}
