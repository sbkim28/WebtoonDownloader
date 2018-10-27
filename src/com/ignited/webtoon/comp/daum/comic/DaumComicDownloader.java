package com.ignited.webtoon.comp.daum.comic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ignited.webtoon.extract.ComicSaver;
import com.ignited.webtoon.extract.Downloader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DaumComicDownloader extends Downloader {


    private final String listUrl = "http://webtoon.daum.net/data/pc/webtoon/view/";
    private final String viewUrl = "http://webtoon.daum.net/data/pc/webtoon/viewer_images/";

    private final List<String> items ;
    private final List<String> titles;

    public DaumComicDownloader(DaumComicInfo info, String path) throws IOException {
        super(info, path);
        this.saver = new ComicSaver(path);
        this.loader = new DaumComicImageLoader(null);
        items = new ArrayList<>();
        titles = new ArrayList<>();
        init();
    }

    private void init() throws IOException {
        JsonArray array = new JsonParser().parse(new InputStreamReader(new URL(listUrl + info.getId()).openStream(), "UTF-8")).getAsJsonObject().get("data")
                .getAsJsonObject().get("webtoon").getAsJsonObject().get("webtoonEpisodes").getAsJsonArray();
        List<JsonObject> list = new ArrayList<>();
        for(int i = 0;i<array.size();++i){
            list.add(array.get(i).getAsJsonObject());
        }
        list.sort(Comparator.comparingInt(o -> o.get("episode").getAsInt()));
        for(JsonObject object : list) {
            titles.add(object.get("title").getAsString());
            items.add(object.get("id").getAsString());
        }
    }

    @Override
    public void download(int index) throws IOException {
        JsonObject obj = new JsonParser().parse(new InputStreamReader(new URL(viewUrl + items.get(index)).openStream(), "UTF-8")).getAsJsonObject();
        ((DaumComicImageLoader) loader).setSource(obj);
        super.download(index);
    }



    @Override
    protected String getTitle(int index) {
        return titles.get(index);
    }

    @Override
    public int size() {
        return items.size();
    }
}
