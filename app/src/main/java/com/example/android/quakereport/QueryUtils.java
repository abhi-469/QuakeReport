package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public final class QueryUtils {
    private static  String SAMPLE_JSON_RESPONSE;
    private QueryUtils() {
    }

    public static void setSampleJsonResponse(String sampleJsonResponse) {
        SAMPLE_JSON_RESPONSE = sampleJsonResponse;
    }

    public static ArrayList<Earthquake> extractEarthquakes() {


        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        try {

            JSONObject baseJsonResponse=new JSONObject(SAMPLE_JSON_RESPONSE);
            JSONArray earthquakeArray=baseJsonResponse.getJSONArray("features");
            for(int i=0;i<earthquakeArray.length();i++)
            {
                JSONObject currentEarthquake=earthquakeArray.getJSONObject(i);
                JSONObject properties=currentEarthquake.getJSONObject("properties");
                double magD=properties.getDouble("mag");
                String url=properties.getString("url");

                DecimalFormat decimalFormatter=new DecimalFormat("0.0");
                String mag=decimalFormatter.format(magD);

                String location=properties.getString("place");
                String locationOffset;
                String primaryLocation;

                if(location.contains("of"))
                {
                    String[] part=location.split("of");
                    locationOffset=part[0]+"of";
                    primaryLocation=part[1];
                }
                else
                {
                    locationOffset="";
                    primaryLocation=location;
                }

                long dateL=properties.getLong("time");

                Date dateObject=new Date(dateL);
                SimpleDateFormat dateFormatter=new SimpleDateFormat("MMM-DD,YYYY");
                String date=dateFormatter.format(dateObject);

                SimpleDateFormat timeFormatter=new SimpleDateFormat("hh:mm a");
                String time=timeFormatter.format(dateObject);

                Earthquake earthquake=new Earthquake(mag,locationOffset,primaryLocation,date,time,url);
                earthquakes.add(earthquake);
            }

        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        return earthquakes;
    }

}