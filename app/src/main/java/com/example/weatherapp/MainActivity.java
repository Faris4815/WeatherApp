package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {

    Button getWeatherBTN;
    EditText cityNameET;
    String cityName;
    //Die Url des API Calls mit dem Namen der Stadt aus dem EditText!

    String weatherInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWeatherBTN = findViewById(R.id.getWeather_btn);
        cityNameET = findViewById(R.id.cityName_et);

        getWeatherBTN.setOnClickListener(v -> onClick());
    }


    public void onClick(){

        //Notwendig, damit die korrekte URL für den API Call erzeugt wird.
        cityName = cityNameET.getText().toString();
        //Hatte den url String estmal oben deklariert aber das geht offenbar nicht, weil dann der String cityName immer null ist und die url somit nicht mehr korrekt ist.
        //Ich vermute, es hat was damit zu tun das String immutable sind. Jedenfalls muss der String hier initialisiert werden.
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + "9846ed3dc28fa293652be575f76e8c0d";

        try {
            GetWeatherInfo_Task getWeatherInfo_task = new GetWeatherInfo_Task(url);

            ExecutorService exec = Executors.newSingleThreadExecutor();
            Future futureInfo = exec.submit(getWeatherInfo_task);
            weatherInfo = futureInfo.get().toString();

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
        }


    }





}