package com.ignited.webtoon.comp.daum.comic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ignited.tools.connect.SimpleIndexedLinker;
import com.ignited.webtoon.extract.ComicInfo;
import com.ignited.webtoon.extract.Finder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DaumComicFinder extends Finder{

    private final String urlFinished = "http://webtoon.daum.net/data/pc/webtoon/list_finished/?genre_id=";
    private final String urlBase = "http://webtoon.daum.net/data/pc/webtoon/list_serialized/";

    @Override
    protected List<ComicInfo> initialize() {
        List<ComicInfo> ret = new ArrayList<>();
        SimpleIndexedLinker linker = new SimpleIndexedLinker(Arrays.asList(urlFinished,"mon", "tue","wed","thu", "fri", "sat", "sun")){
            @Override
            public String next() {
                return (urls.get(index).equals(urlFinished) ? "" : urlBase) + urls.get(index++);
            }
        };
        while (linker.hasNext()){
            linker.connect();
            JsonArray arr = new JsonParser().parse(linker.read("utf-8")).getAsJsonObject().get("data").getAsJsonArray();
            for(int i = 0;i<arr.size();++i){
                JsonObject item = arr.get(i).getAsJsonObject();
                ret.add(new DaumComicInfo(item.get("nickname").getAsString(), item.get("title").getAsString()));
            }
        }
        return ret;
    }
}
