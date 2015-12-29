package me.hapened.hapened;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;

public class LoadScreen extends Activity {
    private boolean loaded = false, passwordGood = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen);
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
                    if (pw.equals(input.getText().toString())) {
                        passwordGood = true;
                        if (loaded) {
                            startNext();
                        }
                    }
                }
            });
            AlertDialog a = alert.create();
            a.show();
        } else {
            passwordGood = true;
        }
        load();
    }

    private void load() {
        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    FileManager.setInstance(LoadScreen.this);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    if (passwordGood) {
                        startNext();
                    } else {
                        loaded = true;
                    }
                }
            }
        };
        welcomeThread.start();
    }

    private void startNext() {
        Intent i = new Intent(LoadScreen.this, ListAll.class);
        startActivity(i);
        finish();
    }
}
