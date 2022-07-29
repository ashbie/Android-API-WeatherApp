package kas.du.restapiandcallbackweatherapp;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherDataService {

    private static final String URL_PART1 = "https://api.openweathermap.org/geo/1.0/direct?q=";
    private static final String URL_PART2 = "&limit=1&appid=9c6bac26decca18e2269735a51100eb2";
    public static final String URL_LAT_IS = "https://api.openweathermap.org/data/2.5/weather?units=metric&appid=9c6bac26decca18e2269735a51100eb2&lat=";
    private static final String LON_PART_OF_URL = "&lon=";
    private Context contextMainActivity;
    double longitude=9999999;
    double latitude=999999;

    public WeatherDataService(Context contextSentFromMainActivity){
        contextMainActivity = contextSentFromMainActivity;
    }

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(Double longi, Double lati);
    }

    public void getCityCoordinates(String cityName, VolleyResponseListener volleyResponseListener){
        String url = URL_PART1 +cityName+ URL_PART2;


        // Request a Json Array response from the provided URL. +35+ +139
        JsonArrayRequest jaRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // show the json array using toast

                        try {
                            JSONObject cityInfo = response.getJSONObject(0);
                            longitude = cityInfo.getDouble("lon");
                            latitude = cityInfo.getDouble("lat");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Toast.makeText(contextMainActivity,"Long. = "+ longitude +"\nLat. = "+ latitude,Toast.LENGTH_SHORT).show();
                        volleyResponseListener.onResponse(longitude, latitude);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(contextMainActivity,,Toast.LENGTH_SHORT).show();
                volleyResponseListener.onError("There was an error!!!\nUnable to retrieve longitude and latitude");
            }
        });

        // Add the request to the RequestQueue.
        MySingleton.getInstance(contextMainActivity).addToRequestQueue(jaRequest);


        //return "longitude="+longitude +" \nlatitude="+ latitude;
    }

    public interface ByLongAndLatResponseListener {
        void onResponse(List<WeatherReportModel> jsonResponseInJavaObjectInList);

        void onError(String message);
    }

    public void getWeatherForecastByCoordinates(double longitude, double latitude, ByLongAndLatResponseListener byLongAndLatResponseListener){
        List<WeatherReportModel> weatherReportModelList = new ArrayList<>();
        WeatherReportModel weatherReportModel = new WeatherReportModel();
        String url = URL_LAT_IS +latitude+ LON_PART_OF_URL+longitude;
        // Request a Json Array response from the provided URL.
        JsonObjectRequest joRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(contextMainActivity,response.toString(), Toast.LENGTH_LONG).show();
                try {
                    JSONArray weather = response.getJSONArray("weather");
                    JSONObject weather_0 = (JSONObject) weather.get(0);
                    JSONObject main = response.getJSONObject("main");
                    JSONObject wind = response.getJSONObject("wind");
                    JSONObject sys = response.getJSONObject("sys");

                    weatherReportModel.setWeather__id(weather_0.getInt("id"));
                    weatherReportModel.setWeather__main(weather_0.getString("main"));
                    weatherReportModel.setWeather__description(weather_0.getString("description"));
                    /* Try to incorporate the icon later. So that I can show it in the app */

                    weatherReportModel.setMain__temp((float) main.getDouble("temp"));
                    weatherReportModel.setMain__feels_like((float) main.getDouble("feels_like"));
                    weatherReportModel.setMain__temp_min((float) main.getDouble("temp_min"));
                    weatherReportModel.setMain__temp_max((float) main.getDouble("temp_max"));
                    weatherReportModel.setMain__pressure(main.getInt("pressure"));
                    weatherReportModel.setMain__humidity(main.getInt("humidity"));

                    weatherReportModel.setWind__speed((float) wind.getDouble("speed"));

                    weatherReportModel.setSys__country(sys.getString("country"));

                    weatherReportModel.setName(response.getString("name"));

                    weatherReportModelList.add(weatherReportModel);

                    byLongAndLatResponseListener.onResponse(weatherReportModelList);

                    //using JSONObject & JSONArray is a bit tricky. I can't directy get something that a nested (e.g. 5 levels deep in a json object)
                    //I will have to define variables for each level in order to get the other nested levels
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(contextMainActivity,"ERROR getting weather by latitude and longitude coordinates", Toast.LENGTH_LONG).show();
                byLongAndLatResponseListener.onError("ERROR getting weather by latitude and longitude coordinates");
            }
        });

        MySingleton.getInstance(contextMainActivity).addToRequestQueue(joRequest);
        // At the moment, I'm returning this thing below of type: List<WeatherReportModel> even though I am not using itreturn weatherReportModel;
    }

    public interface WeatherByCityNameListener{
        void onError(String message);
        void onResponse(List<WeatherReportModel> listContainingAWeatherReportModel);
    }

    public void getWeatherForecastByCityName(String cityName, WeatherByCityNameListener weatherByCityNameListener){
        // Get it's coordinates(long. & lat.)
            getCityCoordinates(cityName, new VolleyResponseListener() {
                @Override
                public void onError(String message) {
                    Toast.makeText(contextMainActivity, message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Double longi, Double lati) {
                    // Get it's weather forecast
                    getWeatherForecastByCoordinates(longi, lati, new ByLongAndLatResponseListener() {
                        @Override
                        public void onResponse(List<WeatherReportModel> jsonResponseInJavaObjectInList) {
                            weatherByCityNameListener.onResponse(jsonResponseInJavaObjectInList);
                        }

                        @Override
                        public void onError(String message) {
                            weatherByCityNameListener.onError("An error was encountered while trying to process your request");
                        }
                    });
                }
            });

    }


}
