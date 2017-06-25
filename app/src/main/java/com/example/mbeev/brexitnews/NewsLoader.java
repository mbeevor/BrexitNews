package com.example.mbeev.brexitnews;

import android.app.DownloadManager;
import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by mbeev on 25/06/2017.
 */

public class NewsLoader extends AsyncTaskLoader<List<Article>> {


    private String url;

    public NewsLoader(Context context, String apiUrl) {
        super(context);
        url = apiUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Article> loadInBackground() {
        if (url == null) {
            return null;
        }
        List<Article> articles = QueryUtils.fetchBookData(url);
        return articles;
    }

}
