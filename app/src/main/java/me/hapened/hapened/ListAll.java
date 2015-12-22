package me.hapened.hapened;

import android.content.Context;
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

import java.util.ArrayList;

public class ListAll extends ActionBarActivity {

    ListView main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all);
        main=(ListView)findViewById(R.id.mainlist);
        ArrayList<String> al=new ArrayList<>();
        al.add("ahj");
        main.setAdapter(new CustomAdapter(this, R.id.itemtv, al));
        main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    Intent i=new Intent(ListAll.this,Edit.class);
                    //i.putExtra(Edit.TITLE_NAME,((TextView) view.findViewById(R.id.itemtv)).getText());
                    startActivity(i);
                }else{
                    Intent i=new Intent(ListAll.this,Edit.class);
                    i.putExtra(Edit.TITLE_NAME,((TextView) view.findViewById(R.id.itemtv)).getText());
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
            if(position==0){
                t.setText("addnew");
            }else
                t.setText(Integer.toString(position));
            return t;
        }
    }
}
