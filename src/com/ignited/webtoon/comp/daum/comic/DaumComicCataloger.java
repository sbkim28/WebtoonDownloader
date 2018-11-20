package com.ignited.webtoon.comp.daum.comic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ignited.webtoon.extract.comic.Cataloger;
import com.ignited.webtoon.extract.comic.ComicInfo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DaumComicCataloger extends Cataloger {
    private final String urlFinished = "http://webtoon.daum.net/data/pc/webtoon/list_finished/?genre_id=";
    private final String urlBase = "http://webtoon.daum.net/data/pc/webtoon/list_serialized/";

    private String[] day;

    /**
     * Instantiates a new Daum comic cataloger.
     *
     */
    public DaumComicCataloger() {
        super();
    }

    /**
     * Instantiates a new Daum comic cataloger.
     *
     * @param maxTry the max try to connect and get elements
     * @param wait   the wait time in millis after failure
     */
    public DaumComicCataloger(int maxTry, int wait) {
        super(maxTry, wait);
    }

    @Override
    protected List<ComicInfo> deliver() throws IOException {
        List<ComicInfo> ret = new ArrayList<>();
        day = new String[]{
                "mon", "tue","wed","thu", "fri", "sat", "sun"
        };
        for (int index = 0; index<day.length; ++index){


            JsonArray arr = new JsonParser().parse(new InputStreamReader(new URL(urlFactory(index)).openStream(), "UTF-8")).getAsJsonObject().get("data").getAsJsonArray();


            for(int i = 0;i<arr.size();++i){
                JsonObject item = arr.get(i).getAsJsonObject();
                ret.add(new ComicInfo(item.get("nickname").getAsString(), item.get("title").getAsString(), "DAUM",
                        item.get("pcThumbnailImage").getAsJsonObject().get("url").getAsString()));
            }

        }
        return ret;
    }

    private String urlFactory(int i){
        if(i == 0){
            return urlFinished;
        }else {
            return urlBase + day[i-1];
        }

    }
}
