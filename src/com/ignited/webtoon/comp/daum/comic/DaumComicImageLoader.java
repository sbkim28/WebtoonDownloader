package com.ignited.webtoon.comp.daum.comic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ignited.webtoon.extract.comic.ImageLoader;

import java.util.ArrayList;
import java.util.List;


/**
 * DaumComicImageLoader
 *
 * Prepare Images of Daum Webtoons
 *
 * @author Ignited
 * @see com.ignited.webtoon.extract.comic.ImageLoader
 */
public class DaumComicImageLoader implements ImageLoader {

    private JsonObject json;

    /**
     * Instantiates a new Daum comic image loader.
     *
     * @param json the jsonObject of daum comic images
     */
    public DaumComicImageLoader(JsonObject json) {
        this.json = json;
    }

    /**
     * Sets source.
     *
     * @param json the jsonObject of daum comic images
     */
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
