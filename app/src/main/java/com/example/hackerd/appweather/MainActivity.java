package com.example.hackerd.appweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class MainActivity extends AppCompatActivity {
    EditText edtname;
    Button btnnext, buttontimkiem;
    TextView txttp,txtqg,txtnhietdo,txttrangthai,txtdrop,txtcloud,txtwind,txtday;
    ImageView imgmay;
    String City = "";
    ImageButton btntimkiem;
    Button btnnext2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        GetCurrentWeatherData("Hanoi");
        buttontimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = edtname.getText().toString();
                if (city.equals("")){
                    City = "Hanoi";
                    GetCurrentWeatherData(City);
                }else {
                    City = city;
                }
                GetCurrentWeatherData(city);
            }
        });
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = edtname.getText().toString();
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("name", city);
                startActivity(intent);
            }
        });
        btnnext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = btnnext2.getText().toString();
                Intent intent = new Intent(MainActivity.this,Main3Activity.class);
                intent.putExtra("name", city);
                startActivity(intent);
            }
        });

    }

    public void GetCurrentWeatherData(String data){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+data+"&units=metric&appid=7ee57419b30bc7b44697cd10cbfba97b";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String day = jsonObject.getString("dt");
                            String name = jsonObject.getString("name");
                            txttp.setText("Tên thành phố : "+name);

                            long l = Long.valueOf(day);
                            Date date = new Date(l*1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH-mm-ss");
                            String Day = simpleDateFormat.format(date);

                            txtday.setText(Day);
                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                            String status = jsonObjectWeather.getString("main");
                            String icon = jsonObjectWeather.getString("icon");

                            Picasso.with(MainActivity.this).load("http://openweathermap.org/img/w/"+icon+".png").into(imgmay);
                            txttrangthai.setText(status);

                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String nhietdo = jsonObjectMain.getString("temp");
                            String doam = jsonObjectMain.getString("humidity");
                            Double a = Double.valueOf(nhietdo);
                            String Nhietdo = String.valueOf(a.intValue());

                            txtnhietdo.setText(Nhietdo+"°C");
                            txtdrop.setText(doam+"%");

                            JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                            String gio = jsonObjectWind.getString("speed");
                            txtwind.setText(gio+"m/s");

                            JSONObject jsonObjectClouds = jsonObject.getJSONObject("clouds");
                            String may = jsonObjectClouds.getString("all");
                            txtcloud.setText(may+"%");

                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                            String country = jsonObjectSys.getString("country");
                            txtqg.setText("Tên quốc gia :"+country);
                        } catch (JSONException e) {
                            e.printStackTrace();
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
        edtname= (EditText) findViewById(R.id.edtname);
        btnnext=(Button) findViewById(R.id.btnnext);
        buttontimkiem=(Button) findViewById(R.id.buttontimkiem);
        txttp=(TextView) findViewById(R.id.txttp);
        txtqg=(TextView) findViewById(R.id.txtqg);
        txtnhietdo=(TextView) findViewById(R.id.txtnhietdo);
        txttrangthai=(TextView) findViewById(R.id.txttrangthai);
        txtdrop=(TextView) findViewById(R.id.txtdrop);
        txtcloud=(TextView) findViewById(R.id.txtcloud);
        txtwind=(TextView) findViewById(R.id.txtwind);
        txtday=(TextView) findViewById(R.id.txtday);
        imgmay=(ImageView) findViewById(R.id.imgmay);
        btnnext2=(Button) findViewById(R.id.btnnext2);
    }
}
