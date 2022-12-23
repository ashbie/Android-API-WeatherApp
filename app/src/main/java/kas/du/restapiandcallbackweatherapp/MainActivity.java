package kas.du.restapiandcallbackweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btn_getCityId, btn_getWeatherById, btn_getWeatherByCityName;
    EditText et_cityNameOrId, et_cityNameSearch, et_longitude, et_latitude;
    ListView lv_weatherReports;
    ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // assign the widgets
        btn_getCityId = findViewById(R.id.bt_getCityId);
        btn_getWeatherById = findViewById(R.id.bt_getWeatherByCityId);
        btn_getWeatherByCityName = findViewById(R.id.bt_getWeatherByCityName);
        et_cityNameOrId = findViewById(R.id.et_enterData);
        et_cityNameSearch = findViewById(R.id.et_cityNameSearch);
        et_longitude = findViewById(R.id.et_longitude);
        et_latitude = findViewById(R.id.et_latitude);
        lv_weatherReports = findViewById(R.id.lv_weatherReports);


        final WeatherDataService weatherDataService = new WeatherDataService(MainActivity.this);

        // test if buttons work
        btn_getCityId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                weatherDataService.getCityCoordinates(et_cityNameOrId.getText().toString(), new WeatherDataService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(Double longi, Double lati) {
                        Toast.makeText(MainActivity.this,"Long. = "+ longi +"\nLat. = "+ lati+"\nYou can now search the weather.\nLatitude and Longitude have been automatically set.",Toast.LENGTH_SHORT).show();
                        et_latitude.setText(String.valueOf(lati));
                        et_longitude.setText(String.valueOf(longi));
                    }
                });
                //Toast.makeText(MainActivity.this, coordinates, Toast.LENGTH_LONG).show();
            }
        });

        // Get Weather By Long. & Lat.
        btn_getWeatherById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this,"You clicked button 2", Toast.LENGTH_SHORT).show();

                // Modify this to send 9999 for the latitude and longitude if the user didn't enter anything
                weatherDataService.getWeatherForecastByCoordinates(Double.parseDouble(et_longitude.getText().toString()), Double.parseDouble(et_latitude.getText().toString()), new WeatherDataService.ByLongAndLatResponseListener() {
                    @Override
                    public void onResponse(List<WeatherReportModel> jsonResponseInJavaObjectInList) {
                        arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, jsonResponseInJavaObjectInList);
                        lv_weatherReports.setAdapter(arrayAdapter);
                        Toast.makeText(MainActivity.this,jsonResponseInJavaObjectInList.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this,message, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btn_getWeatherByCityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weatherDataService.getWeatherForecastByCityName(et_cityNameSearch.getText().toString(), new WeatherDataService.WeatherByCityNameListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> listContainingAWeatherReportModel) {
                        arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, listContainingAWeatherReportModel);
                        lv_weatherReports.setAdapter(arrayAdapter);
                        Toast.makeText(MainActivity.this, listContainingAWeatherReportModel.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });




    }
}