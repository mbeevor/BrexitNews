package com.example.mbeev.brexitnews;

/**
 * Created by mbeev on 25/06/2017.
 */

public class Article {

    private String webPublicationDate;
    private String sectionName;
    private String webTitle;
    private String webUrl;
    private String trailText;

    public Article(String publicationDate, String articleSectionName, String articleWebTitle, String articleTrailText, String articleWebUrl) {
        webPublicationDate = publicationDate;
        webTitle = articleWebTitle;
        sectionName = articleSectionName;
        trailText = articleTrailText;
        webUrl = articleWebUrl;
    }

    public String getWebPublicationDate() { return webPublicationDate; }

    public String getWebTitle() {
        return webTitle;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getTrailText() { return trailText; }

    public String getWebUrl() {
        return webUrl;
    }

}
