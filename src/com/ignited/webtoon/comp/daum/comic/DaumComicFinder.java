package com.ignited.webtoon.comp.daum.comic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ignited.webtoon.extract.comic.ComicInfo;
import com.ignited.webtoon.extract.comic.Finder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * DaumComicFinder
 *
 * Find Daum Webtoons
 *
 * @author Ignited
 * @see com.ignited.webtoon.extract.comic.Finder
 */
public class DaumComicFinder extends Finder{

    private final String urlFinished = "http://webtoon.daum.net/data/pc/webtoon/list_finished/?genre_id=";
    private final String urlBase = "http://webtoon.daum.net/data/pc/webtoon/list_serialized/";

    private String[] day;

    /**
     * Instantiates a new Daum comic finder.
     *
     * @throws IOException when it failed to initialize.
     */
    public DaumComicFinder() throws IOException {
        super();
    }

    @Override
    protected List<ComicInfo> initialize() throws IOException {
        List<ComicInfo> ret = new ArrayList<>();
        day = new String[]{
                "mon", "tue","wed","thu", "fri", "sat", "sun"
        };
        for (int index = 0; index<day.length; ++index){


            JsonArray arr = new JsonParser().parse(new InputStreamReader(new URL(urlFactory(index)).openStream(), "UTF-8")).getAsJsonObject().get("data").getAsJsonArray();

            for(int i = 0;i<arr.size();++i){
                JsonObject item = arr.get(i).getAsJsonObject();
                ret.add(new ComicInfo(item.get("nickname").getAsString(), item.get("title").getAsString(), "DAUM"));
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
