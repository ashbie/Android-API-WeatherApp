package kas.du.restapiandcallbackweatherapp;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherDataService {

    private static final String URL_PART1 = "https://api.openweathermap.org/geo/1.0/direct?q=";
    private static final String URL_PART2 = "&limit=1&appid=9c6bac26decca18e2269735a51100eb2";
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


        // Request a Json Array response from the provided URL.
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
}
