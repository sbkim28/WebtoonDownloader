package com.ignited.webtoon.comp.daum.comic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ignited.webtoon.extract.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class DaumComicImageLoader implements ImageLoader {

    private final String viewUrl = "http://webtoon.daum.net/data/pc/webtoon/viewer_images/";

    private JsonObject json;

    public DaumComicImageLoader(JsonObject json) {
        this.json = json;
    }

    public void setSource(JsonObject json) {
        this.json = json;
    }

    @Override
    public List<String> load() {
        List<String> ret = new ArrayList<>();
        JsonArray array = json.get("data").getAsJsonArray();
        for(int i = 0;i<array.size();++i){
            ret.add(array.get(i).getAsJsonObject().get("url").getAsString());
        }
        return ret;
    }
}
