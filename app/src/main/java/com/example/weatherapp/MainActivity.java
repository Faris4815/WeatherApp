package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {

    Button getWeatherBTN;
    EditText cityNameET;
    String cityName;
    TextView temperatureTW;
    String weatherInfo;
    private static final DecimalFormat df = new DecimalFormat("0.00");      //Needed to round up the temperature before displaying it in the app

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWeatherBTN = findViewById(R.id.getWeather_btn);
        cityNameET = findViewById(R.id.cityName_et);
        temperatureTW = findViewById(R.id.temperature_tw);

        getWeatherBTN.setOnClickListener(v -> onClick());
    }


    public void onClick(){

        //Necessary to create the correct URL for the API call
        cityName = cityNameET.getText().toString();

        //Earlier i declared the Url String above, where the other ariables are as well, but thats not working because the String cityName is null then and therefor the Url is not correct anymore.
        //I assume it has to do with the fact that Strings are immutable. Anyway, the Strinf has to be initialized here.
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + "9846ed3dc28fa293652be575f76e8c0d";

        try {
            GetWeatherInfo_Task getWeatherInfo_task = new GetWeatherInfo_Task(url);

            ExecutorService exec = Executors.newSingleThreadExecutor();
            Future futureInfo = exec.submit(getWeatherInfo_task);
            weatherInfo = futureInfo.get().toString();

            //Convert the String into a JSON Object to make it easier to read the Infos out of it!
            JSONObject weatherInfo_JSON = new JSONObject(weatherInfo);          //weatherInfo_JSON is the whole JSON-file

            //Get the temperature from the JSON-Object as a String
            String temperature_kelvin = (String) weatherInfo_JSON.getJSONObject("main").getString("temp");

            float temperatur_Celsius = (float) ((Float.parseFloat(temperature_kelvin) - 273.15));

            //Set the temperature-TextView in in App as the temperature_Ceslius value. temperature_celsius is round to 2 decimal places.
            temperatureTW.setText(df.format(temperatur_Celsius) + "°C");
            temperatureTW.setVisibility(View.VISIBLE);


            //Some Debugging info
            Log.i("City", cityName);
            Log.i("MYInfo", weatherInfo);
            Log.i("MYURL", url);



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }





}