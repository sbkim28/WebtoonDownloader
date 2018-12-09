package com.ignited.webtoon.comp.kakao.comp;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ignited.webtoon.extract.comic.ComicInfo;
import com.ignited.webtoon.extract.comic.ComicSaver;
import com.ignited.webtoon.extract.comic.ListDownloader;
import com.ignited.webtoon.extract.comic.e.ComicDownloadException;
import com.ignited.webtoon.extract.comic.e.ComicListInitException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Kakao comic downloader.
 *
 * @author Ignited
 * @see com.ignited.webtoon.extract.comic.ListDownloader
 * @see com.ignited.webtoon.extract.comic.Downloader
 */
public class KakaoComicDownloader extends ListDownloader {

    private static final String url = "https://api2-page.kakao.com/api/v5/store/singles";
    private static final String view = "https://api2-page.kakao.com/api/v1/inven/get_download_data/web";


    /**
     * Instantiates a new Kakao comic downloader.
     *
     * @param info the information about kakao webtoon
     * @param path the location where the webtoon will be saved
     * @throws ComicListInitException the comic list init exception
     */
    public KakaoComicDownloader(ComicInfo info, String path) throws ComicListInitException {
        super(info, path);
    }

    /**
     * Instantiates a new Kakao comic downloader.
     *
     * @param info the information about kakao webtoon
     * @param path the location where the webtoon will be saved
     * @param saver the comic saver
     * @throws ComicListInitException when it failed to get initial data.
     */
    public KakaoComicDownloader(ComicInfo info, String path, ComicSaver saver) throws ComicListInitException {
        super(info, path, saver);
    }

    /**
     * Instantiates a new Kakao comic downloader.
     *
     * @param info the information about kakao webtoon
     * @param path the location where the webtoon will be saved
     * @param saver the comic saver
     * @param maxTry the max try to connect and get elements
     * @param wait   the wait time in millis after failure
     * @throws ComicListInitException when it failed to get initial data.
     */
    public KakaoComicDownloader(ComicInfo info, String path, ComicSaver saver, int maxTry, int wait) throws ComicListInitException {
        super(info, path, saver, maxTry, wait);
    }

    @Override
    protected void initItems() throws IOException {
        if (!"KAKAO".equals(info.getType()))
            throw new IllegalArgumentException("Unmatching comic type. (expected=KAKAO, type=" + info.getType() + ")");

        items = new ArrayList<>();
        Connection.Response res = Jsoup.connect(url)
                .ignoreContentType(true)
                .method(Connection.Method.POST)
                .data("seriesid", info.getId())
                .data("page", "0")
                .data("direction", "asc")
                .data("page_size", "10000")
                .data("without_hidden", "true")
                .execute();

        JsonArray list = new JsonParser().parse(res.body()).getAsJsonObject().get("singles").getAsJsonArray();

        for (int i = 0; i < list.size(); ++i) {
            JsonObject item = list.get(i).getAsJsonObject();
            items.add(new Item(item.get("id").getAsString(), item.get("title").getAsString()));
        }
    }

    @Override
    protected List<String> getImages(int index) throws ComicDownloadException {

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

        List<String> ret = new ArrayList<>();
        JsonObject data = obj.get("downloadData").getAsJsonObject().get("members").getAsJsonObject();
        String drmURL = data.get("sAtsServerUrl").getAsString();
        JsonArray files = data.get("files").getAsJsonArray();

        for(int i = 0; i<files.size(); ++i){
            ret.add(drmURL + files.get(i).getAsJsonObject().get("secureUrl").getAsString());
        }

        return ret;
    }
}
