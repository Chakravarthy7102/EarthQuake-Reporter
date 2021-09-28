package com.example.earthquakereporter;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

public final class QueryUtils {


    private static final String LOG_INFO=QueryUtils.class.getName();

    private QueryUtils() {
    }

    //CREATE A METHOD TO CONVERT A STRING OBJECT INTO A URL OBJECT
    private static URL convertToUrl(String String_Url)  {
        URL url=null;
        try{
            url=new URL(String_Url);
        }
        catch (MalformedURLException e){
            Log.e(LOG_INFO,"Error in convertToUrl method",e);
        }

        return url;
    }

//https request url method

  private static String doHTTPRequest(URL url) throws IOException {
        String jsonResponseResult="";
        InputStream inputStream=null;
        HttpsURLConnection urlConnection = null;
      if (url == null) {
          return jsonResponseResult;
      }

        try{
              urlConnection=(HttpsURLConnection) url.openConnection();
             // urlConnection.setConnectTimeout(10000);//this piece of code ca be dodged
             // urlConnection.setReadTimeout(15000);
              urlConnection.setRequestMethod("GET");
              urlConnection.connect();

              if (urlConnection.getResponseCode()== HttpURLConnection.HTTP_OK){
                  inputStream=urlConnection.getInputStream();
                  jsonResponseResult=getInputStream(inputStream);

              }
        }
        catch (IOException e)
        {
            Log.e(LOG_INFO,"Error in HTTPS request",e);
        }
        finally {
            //after making requests close the connection and close the inputStream
            if (urlConnection!=null){
                urlConnection.disconnect();
            }
            if (inputStream!=null){
                inputStream.close();
            }
        }


        return jsonResponseResult;
  }


  //parsing input Stream into a String using StringBuilder and Buffered reader Classes
  private static String getInputStream(InputStream inputStream) throws IOException {

      StringBuilder output = new StringBuilder();
      if (inputStream != null) {
          InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
          BufferedReader reader = new BufferedReader(inputStreamReader);
          String line = reader.readLine();
          while (line != null) {
              output.append(line);
              line = reader.readLine();
          }
      }
      return output.toString();
  }


    private static ArrayList<DataModel> extractEarthquakes(String stringJson) {

        if (TextUtils.isEmpty(stringJson)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<DataModel> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject root=new JSONObject(stringJson);
            JSONArray features=root.getJSONArray("features");
            for (int i=0;i<features.length();i++){

                JSONObject properties=features.getJSONObject(i);
                String type=properties.getString("type");
                String empty=properties.getString("properties");

                JSONObject properties1=properties.getJSONObject("properties");

                String place=properties1.getString("place");
                String time=properties1.getString("time");
                String felt=properties1.getString("felt");
                String url=properties1.getString("url");
                //now splitting the given place string into two different categories ie., into main place and a radius location

                //formatting the unix epoch timeStamp to Standard time and date
                long timeInMillieSec=Long.parseLong(time);//parsing each unix timeStamp string into a long variable

                Date dateObject=new Date(timeInMillieSec);//changing unix time in millieSeconds to a date format
                SimpleDateFormat dateFormat=new SimpleDateFormat("LLL dd, yyyy");
                String formattedDate=dateFormat.format(dateObject);
                //formatting into appropriate time

                String mag=properties1.getString("mag");
                earthquakes.add(new DataModel(place,formattedDate,mag,time,felt));

            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

    //fetching earthQuake data from the url using this specific method with the
    //help of above sub methods that help to get data and other parsing activities

    public static ArrayList<DataModel> fetchEarthQuakeData(String requestingURL) {
         URL url=convertToUrl(requestingURL);
        String jsonResponse="";
        try{
            jsonResponse=doHTTPRequest(url);
        }
        catch (IOException e)
        {
            Log.e(LOG_INFO,"Error in fetchEarthQuakeData method that throws",e);
        }
        ArrayList<DataModel> dataModels=extractEarthquakes(jsonResponse);

        return dataModels;
    }

}
