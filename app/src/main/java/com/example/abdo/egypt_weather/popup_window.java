package com.example.abdo.egypt_weather;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.gcm.Task;

/**
 * Created by Abdo on 11/10/2017.
 */
public class popup_window extends Activity {
    TextView name;
    TextView currenttemp;
    TextView temp_min;
    TextView temp_max;
    TextView Pressure;

    TextView speed;
    TextView humidty;

    PopupWindow popup;
    TextView description;
//    LayoutParams params;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup);

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int hight=dm.heightPixels;
        getWindow().setLayout((int)width/2,(int)hight/2);

        popup=new PopupWindow(this);
        layout=new LinearLayout(this);
        layout.setBackgroundColor(Color.TRANSPARENT);
        popup.showAtLocation(layout, Gravity.CENTER|Gravity.CENTER,0,0);
        popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));




        temp_min=(TextView)findViewById(R.id.temp_min);
        temp_max=(TextView)findViewById(R.id.temp_max);

        speed=(TextView)findViewById(R.id.wind_speed);
        humidty=(TextView)findViewById(R.id.humidity);
        name=(TextView)findViewById(R.id.name);
        Pressure=(TextView)findViewById(R.id.Pressure);
        currenttemp=(TextView)findViewById(R.id.currenttemp);
        description=(TextView)findViewById(R.id.description);

        String city_name=getIntent().getExtras().getString("name").toString();
        name.setText("city_name / "+city_name);

        String currtemp=getIntent().getExtras().getString("currenttemp").toString();
        currenttemp.setText("Current temp / "+currtemp+" celuis");


        String des=getIntent().getExtras().getString("description").toString();
        description.setText("Description / "+ des);

        String temp_mi=getIntent().getExtras().getString("temp_min").toString();
        temp_min.setText("temp_min / "+temp_mi+" celuis");

        String temp_ma=getIntent().getExtras().getString("temp_max").toString();
        temp_max.setText("temp_max / "+temp_ma+" celuis");

        String Presure=getIntent().getExtras().getString("pressure").toString();
        Pressure.setText("Pressure / "+Presure+"  Pascal");

        String humidityIn=getIntent().getExtras().getString("humidityIn").toString();
        humidty.setText("humidty / "+humidityIn+"  %");


        String sped=getIntent().getExtras().getString("speed").toString();
        speed.setText("speed / "+sped+" mph");




        popup.setContentView(layout);




    }
}
