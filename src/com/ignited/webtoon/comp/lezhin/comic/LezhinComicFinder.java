package com.ignited.webtoon.comp.lezhin.comic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ignited.webtoon.extract.comic.ComicInfo;
import com.ignited.webtoon.extract.comic.Finder;
import com.ignited.webtoon.extract.comic.e.ComicFinderInitException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * LezhinComicFinder
 *
 * Find Lezhin Webtoons
 *
 * @author Ignited
 * @see com.ignited.webtoon.extract.comic.Finder
 */
public class LezhinComicFinder extends Finder{

    private final String url = "https://www.lezhin.com/api/v2/comics?adult_kind=all&store=web&offset=";
    private final String limit = "&limit=";
    private final String thumb = "https://cdn.lezhin.com/v2/comics/%s/images/thumbnail";

    private final int lim = 2000;

    /**
     * Instantiates a new LezhinComicFinder.
     */
    public LezhinComicFinder() {
        super();
    }

    /**
     * Instantiates a new Finder.
     *
     * @param maxTry the max try to connect and get elements
     * @param wait   the wait time in millis after failure
     */
    public LezhinComicFinder(int maxTry, int wait) {
        super(maxTry, wait);
    }

    @Override
    protected List<ComicInfo> initialize() throws IOException {
        List<ComicInfo> infos = new ArrayList<>();
        boolean hasNext;
        int offset = 0;
        do{
            HttpURLConnection connection = (HttpURLConnection) new URL(url + offset + limit + lim).openConnection();
            connection.setRequestProperty("x-lz-locale", "ko_KR");
            JsonObject object =  new JsonParser().parse(new InputStreamReader(connection.getInputStream(), "UTF-8")).getAsJsonObject();
            hasNext = object.get("hasNext").getAsBoolean();
            JsonArray arr = object.get("data").getAsJsonArray();
            for(int i = 0;i<arr.size();++i){
                JsonObject item = arr.get(i).getAsJsonObject();
                String id = item.get("id").getAsString();
                String alias = item.get("alias").getAsString();
                String title = item.get("title").getAsString();
                infos.add(new LezhinComicInfo(id, title, alias, String.format(thumb, id)));
            }
            offset += lim;
        }while (hasNext);
        return infos;
    }
}
