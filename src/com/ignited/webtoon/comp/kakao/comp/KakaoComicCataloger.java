package com.ignited.webtoon.comp.kakao.comp;

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

/**
 * Kakao comic cataloger.
 *
 * @author Ignited
 * @see com.ignited.webtoon.extract.comic.Cataloger
 */
public class KakaoComicCataloger extends Cataloger {

    private static final String list_url = "https://api2-page.kakao.com/api/v6/store/section_container/list?agent=web&category=10&subcategory=1000&day=0";
    private static final String thumb_url = "https://dn-img-page.kakao.com/download/resource?kid=";

    /**
     * Instantiates a new Kakao comic cataloger.
     */
    public KakaoComicCataloger() { }

    /**
     * Instantiates a new Kakao comic cataloger.
     *
     * @param maxTry the max try to connect and get elements
     * @param wait   the wait time in millis after failure
     */
    public KakaoComicCataloger(int maxTry, int wait) {
        super(maxTry, wait);
    }

    @Override
    protected List<ComicInfo> deliver() throws IOException {

        List<ComicInfo> infos = new ArrayList<>();


        JsonArray series = new JsonParser().parse(new InputStreamReader(new URL(list_url).openStream(), "utf-8")).getAsJsonObject().get("section_containers").getAsJsonArray()
                .get(0).getAsJsonObject().get("section_series").getAsJsonArray().get(0).getAsJsonObject().get("list").getAsJsonArray();

        for(int i = 0;i<series.size();++i){
            JsonObject item = series.get(i).getAsJsonObject();
            ComicInfo info = new ComicInfo(item.get("series_id").getAsString(), item.get("title").getAsString(),
                    "KAKAO" ,thumb_url + item.get("image").getAsString());

            infos.add(info);
        }
        return infos;

    }
}
