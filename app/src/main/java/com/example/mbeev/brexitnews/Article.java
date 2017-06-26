package com.example.mbeev.brexitnews;

/**
 * Created by mbeev on 25/06/2017.
 */

public class Article {

    private String sectionName;
    private String webTitle;
    private String webUrl;

    public Article(String articleSectionName, String articleWebTitle, String articleWebUrl) {
        webTitle = articleWebTitle;
        sectionName = articleSectionName;
        webUrl = articleWebUrl;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getWebUrl() {
        return webUrl;
    }

}
