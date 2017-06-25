package com.example.mbeev.brexitnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

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

        TextView webTitle = listItemView.findViewById(R.id.web_title);
        webTitle.setText(article.getWebTitle());

        TextView sectionName = listItemView.findViewById(R.id.section_name);
        sectionName.setText(article.getSectionName());

        return listItemView;
    }
}
