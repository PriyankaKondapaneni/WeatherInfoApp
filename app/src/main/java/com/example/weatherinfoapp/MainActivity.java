package com.example.weatherinfoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.media.Image;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private TextView cityName;
    private Button button;
    private RequestQueue mQueue;
    private TextView description;
    private TextView date;
    private TextView temp;
    private TextView humidity;
    private TextView pressure;
    private TextView max_temp;
    private TextView min_temp;
    private TextView humidity2;
    private TextView pressure2;
    private TextView max_temp2;
    private TextView min_temp2;
    private EditText enter_name;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityName =  findViewById(R.id.city_name);
        button = findViewById(R.id.button1);
        description = findViewById(R.id.description);
        date = findViewById(R.id.date);
        enter_name =findViewById(R.id.enter_name);
        temp =  findViewById(R.id.temperature);
        humidity = findViewById(R.id.humidity);
        pressure = findViewById(R.id.pressure);
        max_temp = findViewById(R.id.max_temp);
        min_temp = findViewById(R.id.min_temp);
        humidity2= findViewById(R.id.humidity_textView);
        pressure2 = findViewById(R.id.pressure_textView);
        max_temp2 = findViewById(R.id.max_temp_textView);
        min_temp2= findViewById(R.id.min_temp_textView);
        imageView = findViewById(R.id.image);

        mQueue = Volley.newRequestQueue(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWeatherInfo();
            }
        });

    }


    public void getWeatherInfo() {

        String location =  enter_name.getText().toString();
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=9d00850eb3791837ad55befa3342577b";
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                JSONObject main = response.getJSONObject("main");
                JSONArray array = response.getJSONArray("weather");
                JSONObject object = array.getJSONObject(0);
                String temp1 = String.valueOf(main.getDouble("temp"));
                String hum = String.valueOf(main.getDouble("humidity"));
                String pre = String.valueOf(main.getDouble("pressure"));
                String maxtemp = String.valueOf(main.getDouble("temp_max"));
                String mintemp = String.valueOf(main.getDouble("temp_min"));
                String description1 = object.getString("description");
                String img = object.getString("icon");
                double tempe = Double.parseDouble(temp1);
                int temp2 = (int)Math.round((tempe - 273.15));
                String temperature= String.valueOf(temp2);
                double maxtempe = Double.parseDouble(maxtemp);
                int temp3 = (int)Math.round((maxtempe - 273.15));
                String max_temp1 = String.valueOf(temp3);
                double mintempe = Double.parseDouble(mintemp);
                int temp4= (int)Math.round((mintempe - 273.15));
                String min_temp1 = String.valueOf(temp4);

                String humidity3 = "Humidity :";
                String pressure3 = "Pressure :";
                String maxtemp3 = "Max Temp :";
                String mintemp3 = "Min Temp :";
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format_date = new SimpleDateFormat("EEEE-MMMM-dd");
                String date1 = format_date.format(calendar.getTime());
                cityName.setText(location);
                temp.setText(Html.fromHtml(temperature + "\u2103"));
                humidity.setText(hum);
                pressure.setText(pre);
                max_temp.setText(Html.fromHtml(max_temp1 + "\u2103"));
                min_temp.setText(Html.fromHtml(min_temp1 + "\u2103"));
                date.setText(date1);
                description.setText(description1);
                humidity2.setText(humidity3);
                pressure2.setText(pressure3);
                max_temp2.setText(maxtemp3);
                min_temp2.setText(mintemp3);
                String iconUrl = "http://openweathermap.org/img/w/" + img + ".png";
                Picasso.get().load(iconUrl).into(imageView);
            } catch (JSONException e) {
                e.printStackTrace();
            }
                progressDialog.dismiss();
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            progressDialog.dismiss();
            error.printStackTrace();
        }
    });

        mQueue.add(request);
}

}