package me.hapened.hapened;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Edit extends Activity {

    public static String TITLE_NAME = "titlename";
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_edit);
        Intent i = getIntent();
        title = i.getStringExtra(TITLE_NAME);
        if (title == null) {
            title = (new SimpleDateFormat("yyyyMMdd")).format(new Date());
        }
        ActionBar ab = getActionBar();
        ab.setTitle(title);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
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
