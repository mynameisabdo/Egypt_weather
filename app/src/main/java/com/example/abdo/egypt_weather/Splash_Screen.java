package com.example.abdo.egypt_weather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);
        Thread thread=new Thread(){
            @Override
            public void run() {

                try {
                    sleep(1000);
                    Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();


    }
}
