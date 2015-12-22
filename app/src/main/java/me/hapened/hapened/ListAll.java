package me.hapened.hapened;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class ListAll extends ActionBarActivity {

    ListView main;
    private final long[] INTERVALS = {0, AlarmManager.INTERVAL_DAY, AlarmManager.INTERVAL_DAY * 7, AlarmManager.INTERVAL_DAY * 30, AlarmManager.INTERVAL_DAY * 365};
    ArrayList<String> al;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all);
        main = (ListView) findViewById(R.id.mainlist);
        al = new ArrayList<>();
        al.add("ahj");
        main.setAdapter(new CustomAdapter(this, R.id.itemtv, al));
        main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent i = new Intent(ListAll.this, Edit.class);
                    FileManager.getInstance(ListAll.this).addItem(ListAll.this, 0, new Entry());
                    i.putExtra(Edit.INDEX, position);
                    startActivity(i);
                } else {
                    Intent i = new Intent(ListAll.this, Edit.class);
                    i.putExtra(Edit.TITLE_NAME, ((TextView) view.findViewById(R.id.itemtv)).getText());
                    i.putExtra(Edit.INDEX, position);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_all, menu);
        return true;
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

        ArrayList<String> titles;

        public CustomAdapter(Context context, int resource, ArrayList<String> objects) {
            super(context, resource, objects);
            titles = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                convertView = vi.inflate(R.layout.listitem, null);
            }
            if (position != 0) {
                convertView.setOnLongClickListener(new OLCL(position));
            } else {
                convertView.setOnLongClickListener(null);
            }
            TextView t = (TextView) convertView.findViewById(R.id.itemtv);
            if (position == 0) {
                t.setText("addnew");
            } else
                t.setText(Integer.toString(position));
            return t;
        }

        class OLCL implements View.OnLongClickListener {
            int position;
            OLCL(int pos){//pos is position in list
                position=pos-1;
            }

            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(ListAll.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete File")
                        .setMessage("Are you sure you want to delete this entry")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FileManager.getInstance(ListAll.this).deleteItem(ListAll.this,position);
                                al.remove(position+1);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        }
    }

    @Override
    protected void onDestroy() {
        Toast.makeText(this, "leaving", Toast.LENGTH_SHORT).show();
        super.onDestroy();
        int prefIdx = PreferenceManager.getDefaultSharedPreferences(this).getInt("frequency", 1);
        if (prefIdx != 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 18);
            calendar.set(Calendar.MINUTE, 30);
            calendar.set(Calendar.SECOND, 0);
            Intent intent1 = new Intent(this, Notify.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager am = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), INTERVALS[prefIdx], pendingIntent);
        }
    }
}
