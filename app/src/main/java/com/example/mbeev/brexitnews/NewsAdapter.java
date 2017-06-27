package com.example.mbeev.brexitnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mbeev on 25/06/2017.
 */

public class NewsAdapter extends ArrayAdapter<Article> {

    public NewsAdapter(Context context, ArrayList<Article> news) {

        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Article article = getItem(position);

        // get date, convert date to readable format and assign to ID
        TextView webPublicationDate = (TextView) listItemView.findViewById(R.id.textview_publication_date);
        SimpleDateFormat inputDate = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss'Z'");
        Date date = null;
        try {
            date = inputDate.parse(article.getWebPublicationDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outputDate = new SimpleDateFormat("dd MMMM yyyy");
        String finalDate = outputDate.format(date);
        webPublicationDate.setText(finalDate);

        // get web title and assign to ID
        TextView webTitle = listItemView.findViewById(R.id.textview_title);
        webTitle.setText(article.getWebTitle());

        // get section name and assign to ID
        TextView sectionName = listItemView.findViewById(R.id.textview_section_name);
        sectionName.setText(article.getSectionName());

        // get trailText and assign to ID
        TextView trailTtext = listItemView.findViewById(R.id.textview_trail_text);
        trailTtext.setText(article.getTrailText());

        return listItemView;
    }

}
