package com.example.hackerd.appweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main3Activity extends AppCompatActivity {
    TextView txttp1, txtnhietdo1, txtday1,txtdescription1, txtwind1, txtsunrise1, txtsunset1, txttimezone1;
    ImageView imgmay1;
    ImageView imgback1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Anhxa();
        imgback1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    public void GetCurrentWeatherData(String data){
        RequestQueue requestQueue = Volley.newRequestQueue(Main3Activity.this);
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+data+"&units=metric&appid=7ee57419b30bc7b44697cd10cbfba97b";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String day = jsonObject.getString("dt");
                            String timezone = jsonObject.getString( "timezone");
                            txttimezone1.setText("Múi Giờ:"+"timezone");
                            String name = jsonObject.getString("name");
                            txttp1.setText("Tên thành phố : "+name);

                            long l = Long.valueOf(day);
                            Date date = new Date(l*1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH-mm-ss");
                            String Day = simpleDateFormat.format(date);

                            txtday1.setText(Day);
                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                            String description= jsonObjectWeather.getString( "description");
                            txtdescription1.setText(description);
                            String icon = jsonObjectWeather.getString("icon");

                            Picasso.with(Main3Activity.this).load("http://openweathermap.org/img/w/"+icon+".png").into(imgmay1);

                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String nhietdo = jsonObjectMain.getString("temp");
                            Double a = Double.valueOf(nhietdo);
                            String Nhietdo = String.valueOf(a.intValue());

                            txtnhietdo1.setText(Nhietdo+"°C");


                            JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                            String gio = jsonObjectWind.getString("deg");
                            txtwind1.setText(gio);

                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                            String timesunrise = jsonObjectSys.getString("sunrise");
                            String timesunset = jsonObjectSys.getString( "sunset");

                            txtsunset1.setText(timesunset);
                            txtsunrise1.setText(timesunrise);

                        } catch ( JSONException e) {
                            e.printStackTrace();
                            Log.d("tag", e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }


    private void Anhxa() {
        imgback1=findViewById(R.id.imgback1);
        txttp1=(TextView) findViewById(R.id.txttp1);
        txtnhietdo1=(TextView) findViewById(R.id.txtnhietdo1);
        txtday1=(TextView) findViewById(R.id.txtday1);
        txtdescription1= findViewById(R.id.txtdescription1);
        txtwind1=(TextView) findViewById(R.id.txtwind1);
        txtsunrise1= findViewById(R.id.txtsunrise1);
        txtsunset1= findViewById(R.id.txtsunset1) ;
        txttimezone1= findViewById(R.id.txttimezone1);
        imgmay1=(ImageView) findViewById(R.id.imgmay1);

    }
}
