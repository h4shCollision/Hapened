package me.hapened.hapened;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;

public class LoadScreen extends Activity {
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen);
        PasswordCheck.check(this);
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("pswd", false)) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            input.setTransformationMethod(PasswordTransformationMethod.getInstance());
            alert.setView(input);    //edit text added to alert
            alert.setTitle("Password Required");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String pw = PreferenceManager.getDefaultSharedPreferences(LoadScreen.this).getString("password", "");
                    //System.out.println("result" + result);
                    if (pw.equals(input.getText().toString())) {
                        load();
                    }
                }
            });
            AlertDialog a = alert.create();
            a.show();//a.dismiss();
        } else {
            load();
        }
    }

    private void load() {
        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    FileManager.getInstance(LoadScreen.this);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    //mHandler.post(new Runnable() {
                    //@Override
                    //public void run() {
                    Intent i = new Intent(LoadScreen.this, ListAll.class);
                    startActivity(i);
                    finish();
                    //}
                    //});
                }
            }
        };
        welcomeThread.start();
    }
}
