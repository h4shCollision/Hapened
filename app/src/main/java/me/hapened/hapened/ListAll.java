package me.hapened.hapened;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAll extends ActionBarActivity {

    ListView main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all);
        main=(ListView)findViewById(R.id.mainlist);
        ArrayList<String> al=new ArrayList<>(10);
        al.add("ahj");
        al.add("ahj");
        al.add("ahj");
        al.add("ahj");
        al.add("ahj");
        al.add("ahj");
        al.add("ahj");
        al.add("ahj");
        al.add("ahj");
        al.add("ahj");
        al.add("ahj");
        al.add("ahj");
        main.setAdapter(new CustomAdapter(this,R.id.itemtv,al));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_all, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class CustomAdapter extends ArrayAdapter<String>{

        ArrayList<String> titles;
        public CustomAdapter(Context context, int resource, ArrayList<String> objects) {
            super(context, resource, objects);
            titles=objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                convertView = vi.inflate(R.layout.listitem, null);
            }
            TextView t= (TextView) convertView.findViewById(R.id.itemtv);
            t.setText(Integer.toString(position));
            return t;
        }
    }
}
