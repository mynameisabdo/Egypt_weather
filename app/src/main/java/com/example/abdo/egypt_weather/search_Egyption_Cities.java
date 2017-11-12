package com.example.abdo.egypt_weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class search_Egyption_Cities extends AppCompatActivity {


    ArrayList<String> Listitems=new ArrayList<>();
    ArrayAdapter<String>adapter;
    ListView listView;
    EditText editText;
    DownloadTask Task=new DownloadTask() ;
    HashMap<String,Integer> weather_Data=new HashMap<String,Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__egyption__cities);

        listView=(ListView)findViewById(R.id.listview);
        editText=(EditText)findViewById(R.id.searchText);
        init_list();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String s=charSequence.toString();
                if (s.equals(""))
                {
                    init_list();
                }
                else
                {
                    searchitem(s);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    void init_list()
    {
        Listitems.clear();
        ArrayList<String> citis=new ArrayList<String>();
        citis.add("Cairo");citis.add("Sohag");citis.add("Luxor");//citis.add("Tanta");
        citis.add("Port Said");citis.add("Marsa Matruh");
        citis.add("Alexandria");citis.add("Aswan");//citis.add("Sharqiyah");
       // citis.add("Faiyum");
        citis.add("Ismailia");
       // citis.add("Qena");//citis.add("Giza");
        //citis.add("Kafr Sheikh");
        citis.add("Asyut");
        for (String city : citis)
        {
            String quary_string="http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=7311bc2f4c371dd11c6206b7d364e16c";
            String result=Task.doInBackground(quary_string);
            Task.onPostExecute(result);
            weather_Data=Task.weather_Data;
            String pressure=weather_Data.get("pressure").toString();
            String temp_min=weather_Data.get("temp_min").toString();
            String temp_max=weather_Data.get("temp_max").toString();
            String currenttemp=weather_Data.get("current_temp").toString();
            Listitems.add(city +", currenttemp "+currenttemp + ", temp_max "+temp_max+
            ", temp_min "+temp_min + ", pressure "+pressure);

            Task.weather_Data.clear();
        }



       // items=new String[]{"","","",""};
        adapter=new ArrayAdapter<String>(this,R.layout.list_item,R.id.txtitem,Listitems);
        listView.setAdapter(adapter);





    }

    void  searchitem(String search)
    {
        ArrayList<String> resit=new ArrayList<>();
        for (String item:Listitems)
        {

            if (!item.contains(search))
            {
                resit.add(item);
            }


        }

        for (String item:resit)
        {
            Listitems.remove(item);
        }

        adapter.notifyDataSetChanged();
    }



}
