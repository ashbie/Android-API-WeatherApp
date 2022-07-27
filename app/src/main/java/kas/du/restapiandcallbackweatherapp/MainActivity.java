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
                        Toast.makeText(MainActivity.this,"Long. = "+ longi +"\nLat. = "+ lati,Toast.LENGTH_SHORT).show();
                    }
                });
                //Toast.makeText(MainActivity.this, coordinates, Toast.LENGTH_LONG).show();
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