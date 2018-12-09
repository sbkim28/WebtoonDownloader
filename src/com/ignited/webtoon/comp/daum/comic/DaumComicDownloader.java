package com.ignited.webtoon.comp.daum.comic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ignited.webtoon.extract.comic.ComicInfo;
import com.ignited.webtoon.extract.comic.ComicSaver;
import com.ignited.webtoon.extract.comic.ListDownloader;
import com.ignited.webtoon.extract.comic.e.ComicAccessException;
import com.ignited.webtoon.extract.comic.e.ComicDownloadException;
import com.ignited.webtoon.extract.comic.e.ComicListInitException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * DaumComicDonwloader
 *
 * Dawnload Daum Webtoons
 *
 * @author Ignited
 * @see com.ignited.webtoon.extract.comic.ListDownloader
 */
public class DaumComicDownloader extends ListDownloader {


    private final String listUrl = "http://webtoon.daum.net/data/pc/webtoon/view/";
    private final String viewUrl = "http://webtoon.daum.net/data/pc/webtoon/viewer_images/";


    /**
     * Instantiates a new Daum comic downloader.
     *
     * @param info the information about daum webtoon
     * @throws ComicListInitException when it failed to get inital data.
     */
    public DaumComicDownloader(ComicInfo info) throws ComicListInitException {
        this(info, null);

    }

    /**
     * Instantiates a new Daum comic downloader.
     *
     * @param info the information about daum webtoon
     * @param path the location where the webtoon will be saved
     * @throws ComicListInitException when it failed to get initial data.
     */
    public DaumComicDownloader(ComicInfo info, String path) throws ComicListInitException {
        this(info, path, DEFAULT_MAXTRY, DEFAULT_WAIT);
    }

    /**
     * Instantiates a new Daum comic downloader.
     *
     * @param info the information about daum webtoon
     * @param path the location where the webtoon will be saved
     * @param maxTry the max try to connect and get elements
     * @param wait   the wait time in millis after failure
     * @throws ComicListInitException when it failed to get initial data.
     */
    public DaumComicDownloader(ComicInfo info, String path, int maxTry, int wait) throws ComicListInitException {
        super(info, path, maxTry, wait);
        this.loader = new DaumComicImageLoader(null);
    }

    @Override
    protected void initItems() throws IOException {
        if(!"DAUM".equals(info.getType())) throw new IllegalArgumentException("Unmatching comic type");
        items = new ArrayList<>();

        JsonArray array = new JsonParser().parse(new InputStreamReader(new URL(listUrl + info.getId()).openStream(), "UTF-8")).getAsJsonObject().get("data")
                .getAsJsonObject().get("webtoon").getAsJsonObject().get("webtoonEpisodes").getAsJsonArray();
        List<JsonObject> list = new ArrayList<>();
        for(int i = 0;i<array.size();++i){
            list.add(array.get(i).getAsJsonObject());
        }
        list.sort(Comparator.comparingInt(o -> o.get("episode").getAsInt()));
        for(JsonObject object : list) {
            items.add(new Item(object.get("id").getAsString(), object.get("title").getAsString()));
        }

    }

    @Override
    public void download(int index) throws ComicDownloadException {
        String url = viewUrl + items.get(index).getId();
        JsonObject obj;
        try {
            obj = new JsonParser().parse(new InputStreamReader(new URL(url).openStream(), "UTF-8")).getAsJsonObject();
        } catch (IOException e) {
            throw new ComicDownloadException(e);
        }
        String status = obj.get("result").getAsJsonObject().get("status").getAsString();
        if(!status.equals("200")){
            throw new ComicAccessException(url, status);
        }
        ((DaumComicImageLoader) loader).setSource(obj);
        super.download(index);
    }


}
