package com.ignited.webtoon.comp.kakao.comp;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ignited.webtoon.extract.comic.ImageLoader;
import com.ignited.webtoon.extract.comic.e.ComicDownloadException;

import java.util.ArrayList;
import java.util.List;

/**
 * KakaoComicImageLoader
 *
 * Prepare Images of Kakao Webtoons
 *
 * @author Ignited
 * @see com.ignited.webtoon.extract.comic.ImageLoader
 */
public class KakaoComicImageLoader implements ImageLoader {

    private JsonObject json;

    /**
     * Instantiates a new Kakao comic image loader.
     */
    public KakaoComicImageLoader() { }

    /**
     * Instantiates a new Kakao comic image loader.
     *
     * @param json the jsonObject of kako comic images
     */
    public KakaoComicImageLoader(JsonObject json) {
        this.json = json;
    }

    /**
     * Sets source.
     *
     * @param json the jsonObject of kakao comic images
     */
    public void setSource(JsonObject json) {
        this.json = json;
    }

    @Override
    public List<String> load() {

        List<String> ret = new ArrayList<>();
        JsonObject data = json.get("downloadData").getAsJsonObject().get("members").getAsJsonObject();
        String drmURL = data.get("sAtsServerUrl").getAsString();
        JsonArray files = data.get("files").getAsJsonArray();

        for(int i = 0; i<files.size(); ++i){
            ret.add(drmURL + files.get(i).getAsJsonObject().get("secureUrl").getAsString());
        }

        return ret;
    }
}
