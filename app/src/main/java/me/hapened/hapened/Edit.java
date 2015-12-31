package me.hapened.hapened;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Edit extends ActionBarActivity {

    public static String PREVIOUS_ACTIVITY = "pa";
    static final String INDEX = "I";
    private EditText editTitle, editContent;
    private ActionBar ab;
    private int index;
    private Entry entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent i = getIntent();
        index = i.getIntExtra(INDEX, 0);
        System.out.println("edit" + index);
        entry = FileManager.getInstance().getItem(this, index);
        String title = entry.getTitle();
        if (title == null) {
            title = (new SimpleDateFormat("yyyyMMdd")).format(new Date());
        }
        ab = getSupportActionBar();
        ab.setTitle(title);
        ab.setDisplayHomeAsUpEnabled(true);
        editTitle = (EditText) findViewById(R.id.editTitle);
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
        editContent = (EditText) findViewById(R.id.editText);
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
        editContent.setText(entry.getText());
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
            SA.startSettings(this);
            return true;
        }else if(id==16908332){
            f();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void titleChanged() {
        ab.setTitle(editTitle.getText().toString());
        entry.setTitle(editTitle.getText().toString());
        helper();
    }

    private void helper() {
        FileManager.getInstance().setItem(this, index, entry);
    }

    private void contentChanged() {
        entry.setText(editContent.getText().toString());
        helper();
    }

    @Override
    public void onBackPressed() {
        f();
    }

    private void f(){
        if (entry.getTitle() == null || entry.getTitle().equals("")&&entry.getText() != null && !entry.getText().equals("")) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Title cannot be empty");
            TextView tv = new TextView(this);
            tv.setText("Your entry will be deleted if the title is empty");
            alert.setView(tv);
            alert.setPositiveButton("Delete entry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Edit.super.onBackPressed();
                }
            });
            alert.setNegativeButton("Go Back", null);
            AlertDialog a = alert.create();
            a.show();
        }else{
            super.onBackPressed();
        }
    }
}
