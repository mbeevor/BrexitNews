package com.example.mbeev.brexitnews;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Article>> {

    private TextView emptyStateTextView;
    private ProgressBar progressBar;
    private ListView newsListView;
    private NewsAdapter adapter;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private static final String API_STRING = "https://content.guardianapis.com/search?q=brexit&page-size=50&show-fields=all&api-key=ecd2f5f3-61b9-4272-956d-ad37ea7e2359";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set Toolbar to replace the ActionBar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        emptyStateTextView = (TextView) findViewById(R.id.empty_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        newsListView = (ListView) findViewById(R.id.list);

        // set view to empty text view for no results found
        newsListView.setEmptyView(emptyStateTextView);

        // create a new ArrayAdapter of articles
        adapter = new NewsAdapter(this, new ArrayList<Article>());
        newsListView.setAdapter(adapter);

        // create onItemClickListener for each article
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Article currentArticle = adapter.getItem(position);

                // launch web browser if article in list is clicked
                if (currentArticle.getWebUrl() != null) {
                    Uri articleUri = Uri.parse(currentArticle.getWebUrl());
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, articleUri);
                    startActivity(webIntent);
                }
            }
        });

        // check for working network connection
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            getLoaderManager().initLoader(0, null, MainActivity.this);
        } else {
            progressBar.setVisibility(View.GONE);
            emptyStateTextView.setText(R.string.no_network);
        }
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {

        return new NewsLoader(this, API_STRING);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> articles) {

        // check for working network connection
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            // set empty text state
            emptyStateTextView.setText(R.string.empty_view);
        } else {
            // set no connection text state
            emptyStateTextView.setText(R.string.no_network);

        }

        // Hide progress bas
        progressBar.setVisibility(View.GONE);

        // clear any previous data
        adapter.clear();

        // return list of articles
        if (articles != null && !articles.isEmpty()) {
            adapter.addAll(articles);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        adapter.clear();
    }
}
