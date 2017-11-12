package com.example.abdo.egypt_weather;

import android.content.Intent;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    HashMap<LatLng,String> cities=new HashMap<LatLng,String>();
    AutoCompleteTextView City_name;
    Button search;
    Button button_cities;
    DownloadTask Task=new DownloadTask() ;
    HashMap<String,Integer> weather_Data=new HashMap<String,Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        search=(Button)findViewById(R.id.button);
        City_name=(AutoCompleteTextView)findViewById(R.id.city);
        button_cities=(Button)findViewById(R.id.button_cities);



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        LatLng Cairo = new LatLng(30.0444,31.2357);
        mMap.addMarker(new MarkerOptions().position(Cairo).title("Marker in cairo"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Cairo));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Cairo,7));
        Egyption_cities_LatLng();


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                LatLng postion=marker.getPosition();
                String city_name="";
                try{
                    city_name=cities.get(postion);
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),"Not Egyption City",Toast.LENGTH_LONG).show();
                }


                  String quary_string="http://api.openweathermap.org/data/2.5/weather?q="+city_name+"&appid=7311bc2f4c371dd11c6206b7d364e16c";




                String result=Task.doInBackground(quary_string);
                Task.onPostExecute(result);
                weather_Data=Task.weather_Data;


                String pressure="-1";
                String temp_min="-1";
                String temp_max="-1";
                String tempIn="-1";
                String humidityIn="-1";
                String speed="-1";
                String deg="-1";
                String currenttemp="-1";

                humidityIn=weather_Data.get("humidityIn").toString();
                speed=weather_Data.get("speed").toString();
                deg=weather_Data.get("deg").toString();
                pressure=weather_Data.get("pressure").toString();
                temp_min=weather_Data.get("temp_min").toString();
                temp_max=weather_Data.get("temp_max").toString();
                currenttemp=weather_Data.get("current_temp").toString();

                Task.weather_Data.clear();
                weather_Data.clear();

                Intent go_to_pop=new Intent(MapsActivity.this,popup_window.class);

                go_to_pop.putExtra("tempIn",tempIn);
                go_to_pop.putExtra("humidityIn",humidityIn);
                go_to_pop.putExtra("speed",speed);
                go_to_pop.putExtra("deg",deg);
                go_to_pop.putExtra("name",city_name);
                go_to_pop.putExtra("pressure",pressure);
                go_to_pop.putExtra("temp_min",temp_min);
                go_to_pop.putExtra("temp_max",temp_max);
                go_to_pop.putExtra("description",Task.description);
                go_to_pop.putExtra("currenttemp",currenttemp);


                startActivity(go_to_pop);




                Toast.makeText(getApplicationContext(),
                        city_name+"  "+deg+"   "+tempIn +"   "+humidityIn+"   "+speed+": lat:"+marker.getPosition().latitude+"long:"+marker.getPosition().longitude,Toast.LENGTH_LONG)
                        .show();


                return true;
            }


        });


        button_cities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go_to_search_Egyption_Cities=new Intent(MapsActivity.this,search_Egyption_Cities.class);
                startActivity(go_to_search_Egyption_Cities);

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location=City_name.getText().toString();
                On_Map_Search(location);
            }
        });
    }


    public void Egyption_cities_LatLng()
    {



        LatLng Alexandria = new LatLng(31.2001,29.9187);
        mMap.addMarker(new MarkerOptions().position(Alexandria).title("Marker in Alex"));
        cities.put(Alexandria,"Alexandria");

        LatLng Aswan = new LatLng(24.0889,32.8998);
        mMap.addMarker(new MarkerOptions().position(Aswan).title("Marker in Aswan"));
        cities.put(Aswan,"Aswan");

        LatLng Sharqiyah = new LatLng(27.29315,30.31329);
        mMap.addMarker(new MarkerOptions().position(Sharqiyah).title("Marker in Sharqiyah"));
        cities.put(Sharqiyah,"Sharqiyah");

        LatLng Faiyum = new LatLng(29.3084,30.8428);
        mMap.addMarker(new MarkerOptions().position(Faiyum).title("Marker in Fayom"));
        cities.put(Faiyum,"Faiyum");

        LatLng Tanta = new LatLng(30.7856,31.0004);
        mMap.addMarker(new MarkerOptions().position(Tanta).title("Marker in Tanta"));
        cities.put(Tanta,"Tanta");

        LatLng Minya = new LatLng(28.0871,30.7618);
        mMap.addMarker(new MarkerOptions().position(Minya).title("Marker in Minya"));
        cities.put(Minya,"Minya");

      //  LatLng Befi_suef = new LatLng(29.0661,31.0994);
      //  mMap.addMarker(new MarkerOptions().position(Befi_suef).title("Marker in Befi_suef"));
      //  cities.put(Befi_suef,"Beni Suef");

        LatLng Ismailia = new LatLng(30.60427,32.27225);
        mMap.addMarker(new MarkerOptions().position(Ismailia).title("Marker in Ismailia"));
        cities.put(Ismailia,"Ismailia");

        LatLng Ewift = new LatLng(30.0444,31.2357);
        mMap.addMarker(new MarkerOptions().position(Ewift).title("Marker in Ewift"));
        cities.put(Ewift,"Ewift");

        LatLng Menoufia = new LatLng(30.5972,30.9876);
        mMap.addMarker(new MarkerOptions().position(Menoufia).title("Marker in Menoufia"));
        cities.put(Menoufia,"Menoufia");

        LatLng Port_said = new LatLng(31.152354,32.17280);
        mMap.addMarker(new MarkerOptions().position(Port_said).title("Marker in Port_said"));
        cities.put(Port_said,"Port Said");

        LatLng Mersa_matruh = new LatLng(31.3543,27.2373);
        mMap.addMarker(new MarkerOptions().position(Mersa_matruh).title("Marker in Mersa_matruh"));
        cities.put(Mersa_matruh,"Marsa Matruh");

        LatLng Sohag = new LatLng(26.5591,31.6957);
        mMap.addMarker(new MarkerOptions().position(Sohag).title("Marker in Sohag"));
        cities.put(Sohag,"Sohag");

        LatLng Luxor = new LatLng(25.6872,32.6396);
        mMap.addMarker(new MarkerOptions().position(Luxor).title("Marker in Luxor"));
        cities.put(Luxor,"Luxor");

        LatLng North_sinai = new LatLng(30.6085,33.6176);
        mMap.addMarker(new MarkerOptions().position(North_sinai).title("Marker in North_sinai"));
        cities.put(North_sinai,"North Sinai");

        LatLng South_sinai = new LatLng(29.3102,34.1532);
        mMap.addMarker(new MarkerOptions().position(South_sinai).title("Marker in South_sinai"));
        cities.put(South_sinai,"South Sinai");

        LatLng Qena = new LatLng(26.155061,32.716012);
        mMap.addMarker(new MarkerOptions().position(Qena).title("Marker in Qena"));
        cities.put(Qena,"Qena");

        LatLng New_valley = new LatLng(24.5456,27.1735);
        mMap.addMarker(new MarkerOptions().position(New_valley).title("Marker in New_valley"));
        cities.put(New_valley,"New Valley");

        LatLng Giza = new LatLng(30.0131,31.2089);
        mMap.addMarker(new MarkerOptions().position(Giza).title("Marker in Giza"));
        cities.put(Giza,"Giza");

        LatLng Suze = new LatLng(29.9668,32.5498);
        mMap.addMarker(new MarkerOptions().position(Suze).title("Marker in Suze"));
        cities.put(Suze,"Suez");

        LatLng Asyut = new LatLng(27.1783,31.1859);
        mMap.addMarker(new MarkerOptions().position(Asyut).title("Marker in Asyut"));
        cities.put(Asyut,"Asyut");

        LatLng Kafr_sheikh = new LatLng(31.3085,30.8039);
        mMap.addMarker(new MarkerOptions().position(Kafr_sheikh).title("Marker in Kafr_sheikh"));
        cities.put(Kafr_sheikh,"Kafr Sheikh");




    }

    public void On_Map_Search(String location)
    {
        List<android.location.Address> AddressList=null;
        if (location !=null || !location.equals(""))
        {
            Geocoder geocoder=new Geocoder(this);
            try {
                AddressList=geocoder.getFromLocationName(location,1);

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }


        }
        android.location.Address address=AddressList.get(0);
        LatLng latLng=new LatLng(address.getLatitude(),address.getLongitude());
        mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in "+location));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,7));

    }


}
