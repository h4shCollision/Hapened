package me.hapened.hapened;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LoadScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen);

        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    FileManager.getInstance(LoadScreen.this);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    Intent i = new Intent(LoadScreen.this, ListAll.class);
                    startActivity(i);
                    LoadScreen.this.finish();
                }
            }
        };
        welcomeThread.start();
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_load_screen, menu);
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
    }*/
}
