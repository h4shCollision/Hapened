package me.hapened.hapened;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Edit extends Activity {

    public static String TITLE_NAME = "titlename";
    private EditText editTitle,editContent;
    private ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_edit);
        Intent i = getIntent();
        String title = i.getStringExtra(TITLE_NAME);
        if (title == null) {
            title = (new SimpleDateFormat("yyyyMMdd")).format(new Date());
        }
        ab=getActionBar();
        ab.setTitle(title);
        ab.setDisplayHomeAsUpEnabled(true);

        editTitle= (EditText) findViewById(R.id.editTitle);
        editTitle.setText(title);
        editTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                titleChanged();
            }
        });
        editContent= (EditText) findViewById(R.id.editText);
        editContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                contentChanged();
            }
        });
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

    private void titleChanged(){
        ab.setTitle(editTitle.getText());
        //System.out.println(editTitle.getText());
    }

    private void contentChanged() {
    }

}
