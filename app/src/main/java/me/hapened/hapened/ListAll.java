package me.hapened.hapened;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

public class ListAll extends ActionBarActivity {

    ListView main;
    private final long[] INTERVALS = {0, AlarmManager.INTERVAL_DAY, AlarmManager.INTERVAL_DAY * 7, AlarmManager.INTERVAL_DAY * 30, AlarmManager.INTERVAL_DAY * 365};
    CustomAdapter ca;
    private int ind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all);
        ind = -1;
        main = (ListView) findViewById(R.id.mainlist);
        ca = new CustomAdapter(this, R.id.itemtv, FileManager.getInstance().getTitles());
        main.setAdapter(ca);
        main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("listclick");
                if (position == 0) {
                    ind = 0;
                    FileManager.getInstance().addItem();
                    Intent i = new Intent(ListAll.this, Edit.class);
                    i.putExtra(Edit.INDEX, position);
                    startActivity(i);
                } else {
                    ind = position - 1;
                    Intent i = new Intent(ListAll.this, Edit.class);
                    i.putExtra(Edit.INDEX, position - 1);
                    startActivity(i);
                }
            }
        });
        main.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if (position != 0) {
                    new AlertDialog.Builder(ListAll.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Delete File")
                            .setMessage("Are you sure you want to delete this entry")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FileManager.getInstance().deleteItem(position - 1);
                                    ca.notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
                return true;
            }
        });
        ((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE)).cancel(Notify.uniqueID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_all, menu);
        return true;
    }

    public void onResume() {
        if (ind >= 0) {
            FileManager.getInstance().checkEmpty(ind);
            ca.notifyDataSetChanged();
        }
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            SA.startSettings(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class CustomAdapter extends ArrayAdapter<String> {

        List<String> titles;
        private View addNew;

        public CustomAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            titles = objects;
            addNew = LayoutInflater.from(getContext()).inflate(R.layout.addnew, null);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (position == 0) {
                return addNew;
            }
            if (convertView == null || convertView == addNew) {
                LayoutInflater vi = LayoutInflater.from(getContext());
                convertView = vi.inflate(R.layout.listitem, null);
            }
            TextView t = (TextView) convertView.findViewById(R.id.itemtv);
            t.setText(titles.get(position - 1));
            return convertView;
        }

        @Override
        public int getCount() {
            return titles.size() + 1;
        }


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDestroy() {
        int prefIdx = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(this).getString("frequency", "1"));
        //=0;
        System.out.println("freq"+PreferenceManager.getDefaultSharedPreferences(this).getString("frequency", "1"));

        Intent intent1 = new Intent(this, Notify.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
        am.cancel(pendingIntent);

        if (prefIdx != 0) {
            System.out.println("alarm");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            //calendar.set(Calendar.HOUR_OF_DAY, 13);
            //calendar.set(Calendar.MINUTE, 40);
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
        }

        super.onDestroy();
    }
}
