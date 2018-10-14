package io.github.mattfedd.testapp;

import org.json.JSONException;
import org.json.JSONObject;

public class Article_NhkNewsEasy {

    private String id;
    private String title;
    private String publication_time;
    private Boolean has_nhk_image;
    private Boolean has_nhk_video;
    private Boolean has_easy_image;
    private Boolean has_easy_video;
    private Boolean has_easy_audio;
    private String nhk_image_uri;
    private String nhk_video_uri;
    private String easy_image_uri;
    private String easy_video_uri;
    private String easy_audio_uri;
    private String url;
    private Boolean isDisplayed;

    public Article_NhkNewsEasy(JSONObject data) {
        try {
            this.id = data.getString("news_id");
            this.title = data.getString("title");
            this.publication_time = data.getString("news_publication_time");
            this.has_nhk_image = data.getBoolean("has_news_web_image");
            this.has_nhk_video = data.getBoolean("has_news_web_movie");
            this.has_easy_image = data.getBoolean("has_news_easy_image");
            this.has_easy_video = data.getBoolean("has_news_easy_movie");
            this.has_easy_audio = data.getBoolean("has_news_easy_voice");
            String temp = data.getString("news_web_image_uri").replace("\\", "");
            if(temp.length() > 0 ) {
                this.nhk_image_uri = temp.substring(0, temp.indexOf("..")) + temp.substring(temp.indexOf("..") + 12);
            } else {
                this.nhk_image_uri = "";
            }
            this.nhk_video_uri = data.getString("news_web_movie_uri").replace("\\", "");
            this.easy_image_uri = data.getString("news_easy_image_uri").replace("\\", "");
            this.easy_video_uri = data.getString("news_easy_movie_uri").replace("\\", "");
            this.easy_audio_uri = data.getString("news_easy_voice_uri").replace("\\", "");
            this.url = data.getString("news_web_url").replace("\\", "");
            this.isDisplayed = data.getBoolean("news_display_flag");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String id() {
        return this.id;
    }
    public String title() {
        return this.title;
    }
    public String publication_time() {
        return this.publication_time;
    }
    public Boolean has_nhk_image() {
        return this.has_nhk_image;
    }
    public Boolean has_nhk_video() {
        return this.has_nhk_video;
    }
    public Boolean has_easy_image() {
        return this.has_easy_image;
    }
    public Boolean has_easy_video() {
        return this.has_easy_video;
    }
    public Boolean has_easy_audio() {
        return this.has_easy_audio;
    }
    public String nhk_image() {
        return this.nhk_image_uri;
    }
    public String nhk_video() {
        return this.nhk_video_uri;
    }
    public String easy_image() {
        return this.easy_image_uri;
    }
    public String easy_video() {
        return this.easy_video_uri;
    }
    public String easy_audio() {
        return this.easy_audio_uri;
    }
    public String url() {
        return this.url;
    }
    public Boolean isDisplayed() {
        return this.isDisplayed;
    }
}
