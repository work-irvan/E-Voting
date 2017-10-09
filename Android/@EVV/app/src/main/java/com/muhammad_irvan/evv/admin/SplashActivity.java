package com.muhammad_irvan.evv.admin;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static java.lang.Thread.sleep;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Loading().execute();
    }

    private class Loading extends AsyncTask<Void, Void, String> {
        int i = 0;
        Boolean lanjut = true;
        @Override
        protected String doInBackground(Void... params) {
            while (lanjut) {
                try {
                    if(i >= 5){
                        lanjut = false;
                    }
                    i++;
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "";
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            startActivity(new Intent(SplashActivity.this, MasukActivity.class));
            finish();
        }
    }
}
