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
import android.support.v7.view.ActionMode;
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
import java.util.List;

public class ListAll extends ActionBarActivity {

    ListView main;
    private final long[] INTERVALS = {0, AlarmManager.INTERVAL_DAY, AlarmManager.INTERVAL_DAY * 7, AlarmManager.INTERVAL_DAY * 30,AlarmManager.INTERVAL_DAY * 365};
    List<String> al;CustomAdapter ca;
    private int ind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all);
        main = (ListView) findViewById(R.id.mainlist);
        al=new ArrayList<>(FileManager.getInstance(this).getTitles(this));
        /*if (al==null){
            al=new ArrayList<>();
        }*/
        al.add(0, "ahs");
        ind=-1;
        ca=new CustomAdapter(this, R.id.itemtv, al);
        main.setAdapter(ca);
        main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    System.out.println("0asdfa");
                    FileManager.getInstance(ListAll.this).addItem(ListAll.this, 0);
                    ListAll.this.al.add(1, "");
                    ca.notifyDataSetChanged();
                    Intent i = new Intent(ListAll.this, Edit.class);
                    i.putExtra(Edit.INDEX, position);
                    startActivity(i);
                } else {
                    System.out.println("pos"+position);
                    ind=position;
                    Intent i = new Intent(ListAll.this, Edit.class);
                    i.putExtra(Edit.INDEX, position-1);
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

    public void onResume(){
        if(ind>=0){
            System.out.println("asg");
            al.set(ind,FileManager.getInstance(this).getItem(this,ind-1).getTitle());
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

        public CustomAdapter(Context context, int resource, List<String> objects) {
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
                //convertView.setOnLongClickListener(new OLCL(position));
            } else {
                //convertView.setOnLongClickListener(null);
            }
            TextView t = (TextView) convertView.findViewById(R.id.itemtv);
            if (position == 0) {
                t.setText("addnew");
            } else
                t.setText(titles.get(position));
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
                                al.remove(position + 1);
                                ca.notifyDataSetChanged();
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
        super.onDestroy();
        int prefIdx //= PreferenceManager.getDefaultSharedPreferences(this).getInt("frequency", 1);
        =0;
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
