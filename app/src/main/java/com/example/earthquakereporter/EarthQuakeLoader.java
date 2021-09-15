package com.example.earthquakereporter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.net.URL;
import java.util.ArrayList;

public class EarthQuakeLoader extends AsyncTaskLoader<ArrayList<DataModel>> {
    private String url;
    private static final String LOG_TAG=EarthQuakeLoader.class.getName();


    public EarthQuakeLoader(@NonNull Context context,String url) {
        super(context);
        this.url=url;

    }

    @Nullable
    @Override
    public ArrayList<DataModel> loadInBackground() {
       if (url==null){
           return null;
       }

       ArrayList<DataModel> data;
       data=QueryUtils.fetchEarthQuakeData(url);
       return data;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
