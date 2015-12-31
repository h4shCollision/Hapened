package me.hapened.hapened;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;

/**
 * BootReceiver
 */
public class BR extends BroadcastReceiver {
    public static final long[] INTERVALS = {0, AlarmManager.INTERVAL_DAY, AlarmManager.INTERVAL_DAY * 7, AlarmManager.INTERVAL_DAY * 30, AlarmManager.INTERVAL_DAY * 365};
    public static final String PREF_KEY_FILE = "key", NXT_TRIGGER_TIME_KEY = "time";
    //public static final long[] INTERVALS = {0, 1000, 10000, 50000, 100000};
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("adf");
        setAlarm(context, true, context.getSharedPreferences(PREF_KEY_FILE, Context.MODE_PRIVATE).getLong(NXT_TRIGGER_TIME_KEY, System.currentTimeMillis()));
    }

    public static void setAlarm(Context c, boolean booting, long time) {
        int prefIdx = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(c).getString("frequency", "1"));
        //=0;
        System.out.println("freq" + prefIdx);

        Intent intent1 = new Intent(c, Notify.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(c, 0, intent1, 0);
        AlarmManager am = (AlarmManager) c.getSystemService(c.ALARM_SERVICE);
        System.out.println("pref"+prefIdx);
        if (prefIdx != 0) {
            System.out.println("alarm");
            time=Math.max(time,System.currentTimeMillis());
            if (!booting) {
                time += INTERVALS[prefIdx];
            }
            am.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
            c.getSharedPreferences(PREF_KEY_FILE, Context.MODE_PRIVATE).edit().putLong(NXT_TRIGGER_TIME_KEY, time);
            ComponentName receiver = new ComponentName(c, BR.class);
            PackageManager pm = c.getPackageManager();
            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);
        } else {
            am.cancel(pendingIntent);
            ComponentName receiver = new ComponentName(c, BR.class);
            PackageManager pm = c.getPackageManager();
            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);
        }
    }
}
