package com.example.abdo.egypt_weather;

import android.os.AsyncTask;
import android.os.StrictMode;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Abdo on 11/9/2017.
 */
public class DownloadTask extends AsyncTask<String,Void ,String> {
    HashMap<String,Integer> weather_Data=new HashMap<String,Integer>();

    String result="";
    HttpURLConnection con=null;
    InputStream stream=null;
     String description="";


    @Override
    protected String doInBackground(String... urls) {

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {

            URL url = new URL(urls[0]);
            con=(HttpURLConnection)  url.openConnection();
            stream=con.getInputStream();
            InputStreamReader reader=new InputStreamReader(stream);
            int data=reader.read();//as it's read it's return +1

            while (data!=-1)
            {
                char current=(char) data;
                result=result+current;

                data=reader.read();
            }

            return result;

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(String result) {


        double current_temp=-1;
        double pressure=-1;
        double temp_min=-1;
        double temp_max=-1;

        double humidityIn=-1;
        double speed=-1;
        double deg=-1;
        try {
            JSONObject jsonObject=new JSONObject(result);
            JSONObject weatherdata=new JSONObject(jsonObject.getString("main"));


             humidityIn=Double.parseDouble(weatherdata.getString("humidity"));
             pressure=Double.parseDouble(weatherdata.getString("pressure"));
             temp_min=Double.parseDouble(weatherdata.getString("temp_min"));
             temp_max=Double.parseDouble(weatherdata.getString("temp_max"));
             current_temp=Double.parseDouble(weatherdata.getString("temp"));



            JSONArray weatherdesc=new JSONArray(jsonObject.getString("weather"));
            JSONObject des=weatherdesc.getJSONObject(0);
            description=des.getString("description");

            JSONObject winddata=new JSONObject(jsonObject.getString("wind"));

             speed=Double.parseDouble(winddata.getString("speed"));
             deg=Double.parseDouble(winddata.getString("deg"));
            weather_Data.clear();
            weather_Data.put("current_temp",(int)(current_temp-273.15));

            weather_Data.put("pressure",(int)pressure);

            weather_Data.put("temp_min",(int)(temp_min-273.15));

            weather_Data.put("temp_max",(int)(temp_max-273.15));




            weather_Data.put("humidityIn",(int)humidityIn);

            weather_Data.put("speed",(int)speed);


            weather_Data.put("deg",(int)deg);


        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
