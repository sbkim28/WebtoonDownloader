package com.ignited.webtoon.comp.naver.comic;

import com.ignited.webtoon.extract.*;
import com.ignited.webtoon.extract.comic.ComicInfo;
import com.ignited.webtoon.extract.comic.CookieSettable;
import com.ignited.webtoon.extract.comic.Downloader;
import com.ignited.webtoon.extract.comic.e.ComicAccessException;
import com.ignited.webtoon.extract.comic.e.ComicDownloadException;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Map;

/**
 * NaverComicDownloader
 * @author Ignited
 * @see com.ignited.webtoon.extract.comic.Downloader
 */
public class NaverComicDownloader extends Downloader implements CookieSettable {

    private final String detailUrl = "https://comic.naver.com/webtoon/detail.nhn";
    private final String listUrl = "https://comic.naver.com/webtoon/list.nhn";
    private final String titleId = "?titleId=";

    private final String noStart = "&no=";
    private final String noEnd = "&weekday=";


    private int size;
    /**
     * The Html Document
     */
    private ReadDocument doc;

    /**
     * Instantiates a new Naver comic downloader.
     *
     * @param info the information about naver webtoon
     */
    public NaverComicDownloader(ComicInfo info) {
        this(info, null);
    }

    /**
     * Instantiates a new Naver comic downloader.
     *
     * @param info the information about naver webtoon
     * @param path the location where the webtoon will be saved
     */
    public NaverComicDownloader(ComicInfo info, String path){
        super(info, path);
        if(!"NAVER".equals(info.getType())) throw new IllegalArgumentException("Unmatching comic type");
        this.saver = new NaverComicSaver(path);
        this.loader = new NaverComicImageLoader();
        doc = new ReadDocument();
        setSize();
    }

    @Override
    public void download(int index) throws ComicDownloadException {
        String url = detailUrl + titleId + info.getId() + "&no=" + (index + 1);
        try {
            doc.read(url);
        } catch (IOException e) {
            throw new ComicDownloadException(e);
        }

        if(!doc.getDoc().baseUri().contains(detailUrl)){
            throw new ComicAccessException("Unable to access " + url);
        }
        ((NaverComicImageLoader) loader).setSource(doc);
        super.download(index);
    }

    private void setSize() {
        String href;
        try {
            href = Jsoup.connect(listUrl +  titleId + info.getId()).get().getElementsByClass("viewList").first().selectFirst("tbody td.title")
                .selectFirst("a").attr("href");
        }catch (IOException e){
            e.printStackTrace();
            size = 0;
            return;
        }
        size = Integer.parseInt(href.substring(href.indexOf(noStart) + 4, href.indexOf(noEnd)));
    }

    @Override
    protected String getTitle(int index) {
        return doc.getDoc().selectFirst("h3").text();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void setCookies(Map<String, String> cookies) {
        doc.setCookies(cookies);
    }
}
