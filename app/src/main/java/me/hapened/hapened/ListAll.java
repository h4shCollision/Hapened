package me.hapened.hapened;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.List;

public class ListAll extends ActionBarActivity {

    ListView main;
    CustomAdapter ca;
    private static int ind=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all);
        main = (ListView) findViewById(R.id.mainlist);
        ca = new CustomAdapter(this, R.id.itemtv, FileManager.getInstance().getTitles());
        main.setAdapter(ca);
        main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("listclick");
                if (position == 0) {
                    ind = 0;
                    FileManager.getInstance().addItem(ListAll.this);
                    Intent i = new Intent(ListAll.this, Edit.class);
                    i.putExtra(Edit.INDEX, position);
                    startActivity(i);
                } else {
                    ind = position - 1;
                    Intent i = new Intent(ListAll.this, Edit.class);
                    i.putExtra(Edit.INDEX, ind);
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
                                    FileManager.getInstance().deleteItem(ListAll.this, position - 1);
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

    @Override
    public void onResume() {
        //System.out.println("ind"+ind);
        if (ind >= 0) {
            FileManager.getInstance().checkEmpty(this,ind);
            ca.notifyDataSetChanged();
        }
        ind=-1;
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
}
