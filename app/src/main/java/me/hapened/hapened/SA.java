package me.hapened.hapened;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
//settings activity, messed up the naming
public class SA extends ActionBarActivity {

    static void startSettings(Context c){
        c.startActivity(new Intent(c, SA.class));
    }


    public static class PrefsFrag extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{

        Context c;

        public void setC(Context c) {
            this.c = c;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

        }

        @Override
        public void onPause() {
            getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
            super.onPause();
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if(key.equals("frequency")){
                BR.setAlarm(c,false,System.currentTimeMillis());
            }/*else if(key=="pswd"&&sharedPreferences.getBoolean(key,false)){

            }*/
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //LinearLayout ll= (LinearLayout) findViewById(R.id.settingA);
        FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
        PrefsFrag pf=new PrefsFrag();pf.setC(this);
        fragmentTransaction.add(R.id.settingA, pf);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_s, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
