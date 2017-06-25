package com.example.mbeev.brexitnews;

import android.text.TextUtils;
import android.util.Log;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbeev on 25/06/2017.
 */

public class QueryUtils {

    // Tag for log messages to identify errors
    private static final String LOG_TAG = QueryUtils.class.getName();

    // required empty constructor
    private QueryUtils() {}

    // public method that brings everything together to get a list of articles

    public static List<Article> fetchBookData(String requestUrl) {

        // create the URL
        URL url = createUrl(requestUrl);

        // send HTTP request to server
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // process the response from the server
        List<Article> articles = extractArticles(jsonResponse);
        return articles;

    }

    // private method for creating URL
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
            // generic catch method for URLs
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL.", e);
        }
        // return URL for public method
        return url;
    }

    // private method for making HTTP Request and returning a jsonResponse String
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // if the URL is null, return early
        if (url == null) {
            return jsonResponse;
        }

        // otherwise process with rest of method
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect(); //at this point, the URL connection is made

            // if URL connection is successful (result 200) proceed; else throw error
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Uh, oh - error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the data.", e);
        } finally {
            // if URL connection is made and data retrieved, disconnect and close input stream
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        // return jsonResponse for public method
        return jsonResponse;
    }

    // use StringBuilder and Buffer reader to read and compute the String, so as to minimise use of resources
    private static String readFromStream(InputStream inputStream) throws IOException {
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

    // method to use data required from API
    private static List<Article> extractArticles(String articleJSON) {

        // return early if the JSON string is empty
        if (TextUtils.isEmpty(articleJSON)) {
            return null;
        }

        // create an empty ArrayList that articles can be added to
        List<Article> articles = new ArrayList<>();

        // Parse the response from the URL; throw exception if there is a problem and report to logs
        try {
            JSONObject jsonObject = new JSONObject(articleJSON);
            // extract 'results' from JSONArray
            JSONArray articleArray = jsonObject.getJSONArray("results");
            // Loop through each item in the items array
            for (int i = 0; i < articleArray.length(); i++) {
                JSONObject currentArticle = articleArray.getJSONObject(i);

                // TODO: fix based on using Array only, and not an object

                // get section name for each item in array list
                JSONObject results = currentArticle.getJSONObject("results");

                String sectionName = results.getString("sectionName");
                String webTitle = results.getString("webTitle");
                String webUrl = results.getString("webUrl");

                // create new book
                Article article = new Article(sectionName, webTitle, webUrl);
                articles.add(article);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the News API results.", e);
        }

        // return the list of books
        return articles;
    }

}


