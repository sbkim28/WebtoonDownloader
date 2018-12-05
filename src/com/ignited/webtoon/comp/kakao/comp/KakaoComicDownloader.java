package com.ignited.webtoon.comp.kakao.comp;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ignited.webtoon.comp.naver.comic.NaverComicImageLoader;
import com.ignited.webtoon.extract.comic.ComicInfo;
import com.ignited.webtoon.extract.comic.ComicSaver;
import com.ignited.webtoon.extract.comic.ListDownloader;
import com.ignited.webtoon.extract.comic.e.ComicDownloadException;
import com.ignited.webtoon.extract.comic.e.ComicListInitException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;

public class KakaoComicDownloader extends ListDownloader {

    private static final String url = "https://api2-page.kakao.com/api/v5/store/singles";
    private static final String view = "https://api2-page.kakao.com/api/v1/inven/get_download_data/web";

    public KakaoComicDownloader(ComicInfo info) throws ComicListInitException {
        this(info, null);
    }

    public KakaoComicDownloader(ComicInfo info, String path) throws ComicListInitException {
        this(info, path, DEFAULT_MAXTRY, DEFAULT_WAIT);
    }

    public KakaoComicDownloader(ComicInfo info, String path, int maxTry, int wait) throws ComicListInitException {
        super(info, path, maxTry, wait);
        this.saver = new ComicSaver(path);
        this.loader = new KakaoComicImageLoader();
    }

    @Override
    protected void initItems() throws IOException {
        if(! "KAKAO".equals(info.getType())) throw new IllegalArgumentException("Unmatching comic type");

        items = new ArrayList<>();

        Connection.Response res = Jsoup.connect(url)
                .ignoreContentType(true)
                .method(Connection.Method.POST)
                .data("seriesid", info.getId())
                .data("page","0")
                .data("direction", "asc")
                .data("page_size", "10000")
                .data("without_hidden", "true")
                .execute();
        JsonArray list = new JsonParser().parse(res.body()).getAsJsonObject().get("singles").getAsJsonArray();

        for (int i = 0;i<list.size();++i){
            JsonObject item = list.get(i).getAsJsonObject();
            items.add(new Item(item.get("id").getAsString(), item.get("title").getAsString()));
        }
    }

    @Override
    public void download(int index) throws ComicDownloadException {
        JsonObject obj;
        try {
            String s = Jsoup.connect(view)
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .data("productId",items.get(index).getId())
                    .execute().body();
            obj = new JsonParser().parse(s).getAsJsonObject();
        } catch (IOException e) {
            throw new ComicDownloadException(e);
        }
        ((KakaoComicImageLoader) loader).setSource(obj);
        super.download(index);
    }
}
