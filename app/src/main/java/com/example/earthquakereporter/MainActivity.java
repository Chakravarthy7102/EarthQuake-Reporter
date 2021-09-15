package com.example.earthquakereporter;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String INTERNET_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=5&limit=20";

    private static final int LOADER_ID = 1;
    private ListView earthquakeListView;
    private ListAdapter myAdapter;
    private ProgressBar progressBar;
    private TextView notFoundText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ListView  initialization
        earthquakeListView = findViewById(R.id.list);
        myAdapter = new ListAdapter(MainActivity.this, new ArrayList<>());
        progressBar = findViewById(R.id.progress_circular);
        progressBar.setVisibility(View.GONE);
        //setting adapter to the ListView
        //earthquakeListView.setAdapter(new ListAdapter(MainActivity.this,earthquakes));
        earthquakeListView.setAdapter(myAdapter);
        notFoundText = findViewById(R.id.notFoundMsg);
        //set a msg on the main screen which shows the EarthQuake not Found message if
        //there are no results to show on the main activity
        earthquakeListView.setEmptyView(notFoundText);
        //initializing the async task.
        EarthQuakeAsync task=new EarthQuakeAsync();
        task.execute(INTERNET_URL);

        //item click listener to open the url into the requested site
        earthquakeListView.setOnItemClickListener((parent, view, position, id) -> {
            String url = "https://earthquake.usgs.gov/earthquakes/eventpage/us20004vvx/executive";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });
    }


    //creating a ASYNC class to do the network calls in the background
    //this class will execute the https request code in the background the thread

    private class EarthQuakeAsync extends AsyncTask<String, Void, ArrayList<DataModel>> {

        private ArrayList<DataModel> data = new ArrayList<>();


        @Override
        protected ArrayList<DataModel> doInBackground(String... urls) {
            progressBar.setVisibility(View.VISIBLE);
            if (urls.length < 1 || urls[0] == null) {

                return null;
            }

            data = QueryUtils.fetchEarthQuakeData(urls[0]);

            return data;
        }


        @Override
        protected void onPostExecute(ArrayList<DataModel> dataModelArrayList) {

            progressBar.setVisibility(View.GONE);
            myAdapter.clear();
            if (data != null && !data.isEmpty()) {

                myAdapter.addAll(data);

            }
        }


    }
}