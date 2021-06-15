package com.example.android.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    ArrayList<Earthquake> earthquakes;
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.geojson";
    private TextView emptyStateView;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        emptyStateView=(TextView)findViewById(R.id.empty_view);
        progressBar=(ProgressBar)findViewById(R.id.progress_bar);

        QuakeAsyncTask task = new QuakeAsyncTask();
        task.execute();

        // Create a fake list of earthquake locations.
    }

    private class QuakeAsyncTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            // Create URL object
            URL url = createUrl(USGS_REQUEST_URL);

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                // TODO Handle the IOException
            }

            // Extract relevant fields from the JSON response and create an {@link Event} object

            // Return the {@link Event} object as the result fo the {@link TsunamiAsyncTask}
            return jsonResponse;
        }


        @Override
        protected void onPostExecute(String jsonResponse) {
            if (jsonResponse == null) {
                return;
            }

            progressBar.setVisibility(View.GONE);
            QueryUtils.setSampleJsonResponse(jsonResponse);
            earthquakes = QueryUtils.extractEarthquakes();
            ListView earthquakeListView = (ListView) findViewById(R.id.list);
            earthquakeListView.setEmptyView(emptyStateView);
            emptyStateView.setText("EMPTY VIEW");


            final EarthquakeAdapter adapter=new EarthquakeAdapter(EarthquakeActivity.this,earthquakes);

            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            earthquakeListView.setAdapter(adapter);

            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Earthquake currentEarthquake = (Earthquake) adapter.getItem(i);
                    Uri uri=Uri.parse(currentEarthquake.getmUrl());
                    Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(intent);
                }
            });

        }

        /**
         * Returns new URL object from the given string URL.
         */
        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                Log.e(LOG_TAG, "Error with creating URL", exception);
                return null;
            }
            return url;
        }

        /**
         * Make an HTTP request to the given URL and return a String as the response.
         */
        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.connect();

                if(urlConnection.getResponseCode()==200) {
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);
                }
            } catch (IOException e) {
                // TODO: Handle the exception
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // function must handle java.io.IOException here
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        /**
         * Convert the {@link InputStream} into a String which contains the
         * whole JSON response from the server.
         */
        private String readFromStream(InputStream inputStream) throws IOException {
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
    }
}
