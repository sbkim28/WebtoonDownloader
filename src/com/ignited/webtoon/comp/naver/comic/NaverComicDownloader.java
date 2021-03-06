package com.ignited.webtoon.comp.naver.comic;

import com.ignited.webtoon.extract.*;
import com.ignited.webtoon.extract.comic.ComicInfo;
import com.ignited.webtoon.extract.comic.ComicSaver;
import com.ignited.webtoon.extract.comic.Downloader;
import com.ignited.webtoon.extract.comic.e.ComicAccessException;
import com.ignited.webtoon.extract.comic.e.ComicDownloadException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * NaverComicDownloader
 * @author Ignited
 * @see com.ignited.webtoon.extract.comic.Downloader
 */
public class NaverComicDownloader extends Downloader implements CookieSettable {

    private static final Logger LOGGER = Logger.getLogger(NaverComicCataloger.class.getName());

    private final String detailUrl = "https://comic.naver.com/webtoon/detail.nhn";
    private final String listUrl = "https://comic.naver.com/webtoon/list.nhn";
    private final String titleId = "?titleId=";

    private final String noStart = "&no=";
    private final String noEnd = "&weekday=";

    private Document document;

    private Map<String, String> cookies;

    private int size;

    /**
     * Instantiates a new Naver comic downloader.
     *
     * @param info the information about naver webtoon
     * @param path the location where the webtoon will be saved
     */
    public NaverComicDownloader(ComicInfo info, String path){
        this(info, path, new ComicSaver());

    }

    /**
     * Instantiates a new Naver comic downloader.
     *
     * @param info the information about naver webtoon
     * @param path the location where the webtoon will be saved
     * @param saver the saver
     */
    public NaverComicDownloader(ComicInfo info, String path, ComicSaver saver) {
        super(info, path, saver);
        if(!"NAVER".equals(info.getType())) throw new IllegalArgumentException("Unmatching comic type. (expected=NAVER, type=" + info.getType() + ")");
        saver.setUbs(url -> {
            URLConnection conn = new URL(url).openConnection();
            conn.setRequestProperty("referer", "http://m.naver.com");
            return conn;
        });
        initSize();
    }

    public void initSize() {
        String href;
        try {
            href = Jsoup.connect(listUrl +  titleId + info.getId()).get().getElementsByClass("viewList").first().selectFirst("tbody td.title")
                .selectFirst("a").attr("href");
        }catch (IOException e){
            LOGGER.warning("Size initialize failed. (url=" + listUrl +  titleId + info.getId() + ")");
            e.printStackTrace();
            size = 0;
            return;
        }
        size = Integer.parseInt(href.substring(href.indexOf(noStart) + 4, href.indexOf(noEnd)));
    }

    @Override
    protected String getTitle(int index) {
        return document.selectFirst("h3").text();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    @Override
    protected List<String> getImages(int index) throws ComicDownloadException {
        String url = detailUrl + titleId + info.getId() + "&no=" + (index + 1);
        Connection c = Jsoup.connect(url);
        if(cookies != null){
            c.cookies(cookies);
        }
        try {
            document = c.get();
        } catch (IOException e) {
            throw new ComicDownloadException("Connection failed. (url=" + url + ", cookies=" + cookies + ")",e);
        }
        if(!document.baseUri().contains(detailUrl)){
            LOGGER.info(document.baseUri());
            throw new ComicAccessException("Access failed. (url=" + url + ", cookies="+cookies+")");
        }

        List<String> ret = new ArrayList<>();
        Elements imgs = document.getElementsByClass("wt_viewer").first().select("img");
        for(Element e : imgs){
            ret.add(e.attr("src"));
        }
        return ret;
    }
}
