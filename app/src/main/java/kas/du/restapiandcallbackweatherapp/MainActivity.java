package kas.du.restapiandcallbackweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Button btn_getCityId, btn_getWeatherById, btn_getWeatherByCityName;
    EditText et_cityNameOrId;
    ListView lv_weatherReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // assign the widgets
        btn_getCityId = findViewById(R.id.bt_getCityId);
        btn_getWeatherById = findViewById(R.id.bt_getWeatherByCityId);
        btn_getWeatherByCityName = findViewById(R.id.bt_getWeatherByCityName);
        et_cityNameOrId = findViewById(R.id.et_enterData);
        lv_weatherReports = findViewById(R.id.lv_weatherReports);

        // test if buttons work
        btn_getCityId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url = "https://api.openweathermap.org/geo/1.0/direct?q="+et_cityNameOrId.getText().toString()+"&limit=1&appid=9c6bac26decca18e2269735a51100eb2";

                // Request a Json Array response from the provided URL.
                JsonArrayRequest jaRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                // show the json array using toast
                                double longitude=0;
                                double latitude=0;
                                try {
                                    JSONObject cityInfo = response.getJSONObject(0);
                                    longitude = cityInfo.getDouble("lon");
                                    latitude = cityInfo.getDouble("lat");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Toast.makeText(MainActivity.this,"Long. = "+longitude +"\n::Lat. = "+ latitude,Toast.LENGTH_SHORT).show();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"There was an error!!!",Toast.LENGTH_SHORT).show();
                    }
                });

                // Add the request to the RequestQueue.
                queue.add(jaRequest);
                //Toast.makeText(MainActivity.this,"You clicked button 1", Toast.LENGTH_LONG).show();
            }
        });

        btn_getWeatherById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"You clicked button 2", Toast.LENGTH_SHORT).show();
            }
        });

        btn_getWeatherByCityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"(LENGTH_LONG)You clicked button 3 and typed: "+et_cityNameOrId.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });




    }
}