package com.lniu.gridimagesearch.models;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lniu on 2/1/15.
 */
public class Settings {

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String sep = "";
        for(Setting s: settings) {
            if(!s.Empty()) {
                sb.append(sep).append(s);
                sep = "&";
            }
        }
        return sb.toString();
    }

    private class Setting {
        Setting(String name, String value) {
            this.name = name;
            this.value = value;
        }
    public boolean Empty() {
        return this.value.isEmpty();
    }
    @Override
    public String toString() {
        return String.format("%s&%s", name, value);
    }
        private String name;
        private String value;
    }

    List<Setting> settings;
    private Settings() {
        settings = Arrays.asList(new Setting("as_sitesearch", ""), // site.
                                 new Setting("imgsz", ""),  // size
                                 new Setting("imgcolor", ""), // color filter
                                 new Setting("imgtype", ""));  // type
    }

    private Settings instance_ = new Settings();
    public Settings Instance() {
        return instance_;
    }

    public Settings SetValue(String key, String value) {
        for(Setting s: settings) {
            if(s.name == key) {
                s.value = value;
                return instance_;
            }
        }
        return null;
    }



    public Settings SetSize(String size) {
        String key = "as_sitesearch";
        return SetValue(key, size);
    }

    public Settings SetSite(String site) {
        String key = "imgsz";
        return SetValue(key, site);
    }

    public Settings SetColor(String color) {
        String key = "imgcolor";
        return SetValue(key, color);
    }

    public Settings SetType(String type) {
        String key = "imgtype";
        return SetValue(key, type);
    }
}
