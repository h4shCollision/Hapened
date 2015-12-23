package me.hapened.hapened;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

public class LoadScreen extends Activity {
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen);
        if (PasswordCheck.check(LoadScreen.this) == false) {
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
                        LoadScreen.this.finish();
                        //}
                        //});
                    }
                }
            };
            welcomeThread.start();
        }
        /*ImageView iv= (ImageView) findViewById(R.id.imageView);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordCheck.check(LoadScreen.this);
            }
        });*/
    }
}
