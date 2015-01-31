package com.lniu.gridimagesearch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by lniu on 1/29/15.
 */
public class ImageResult implements Serializable {

    public ImageResult(JSONObject json) {
        try {
            this.thumbUrl = json.getString("tbUrl");
            this.title = json.getString("title");
            this.fullUrl = json.getString("url");
            int width = json.getInt("width");
            int height = json.getInt("height");
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ImageResult> fromJsonArray(JSONArray array) throws JSONException {
        ArrayList<ImageResult> results = new ArrayList<>();
        for(int i = 0; i < array.length(); ++i) {
            results.add(new ImageResult(array.getJSONObject(i)));
        }
        return results;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public String getTitle() {
        return title;
    }

    private String fullUrl;
    private String thumbUrl;
    private String title;

}
