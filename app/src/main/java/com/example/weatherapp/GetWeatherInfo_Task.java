package com.example.weatherapp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

public class GetWeatherInfo_Task implements Callable<String> {

     URL url;
     HttpURLConnection connection;
     InputStream is;
     InputStreamReader reader;
     BufferedReader bufferedReader;

     String weatherInfo = "";

    public GetWeatherInfo_Task(String url) throws MalformedURLException {
        this.url = new URL(url);
    }



    @Override
    public String call() throws Exception {

        connection = (HttpURLConnection) url.openConnection();
        is = connection.getInputStream();
        reader = new InputStreamReader(is);
        bufferedReader = new BufferedReader(reader);

        String currentLine = bufferedReader.readLine();

        while (currentLine != null){
            weatherInfo += currentLine;
            currentLine = bufferedReader.readLine();
        }

        return weatherInfo;
    }

}
