package com.ignited.webtoon.comp.naver.comic;

import com.ignited.webtoon.extract.*;
import com.ignited.webtoon.extract.comic.Downloader;
import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * NaverComicDownloader
 * @author Ignited
 * @see com.ignited.webtoon.extract.comic.Downloader
 */
public class NaverComicDownloader extends Downloader {

    private final String detailUrl = "https://comic.naver.com/webtoon/detail.nhn";
    private final String listUrl = "https://comic.naver.com/webtoon/list.nhn";
    private final String qh3 = "h3";
    private final String titleId = "?titleId=";

    private final String qViewList = "viewList";
    private final String qtr = "tbody td.title";
    private final String qa = "a";
    private final String hrefAttr= "href";

    private final String noStart = "&no=";
    private final String noEnd = "&weekday=";


    private int size;
    /**
     * The Html Document
     */
    protected ReadDocument doc;

    /**
     * Instantiates a new Naver comic downloader.
     *
     * @param info the information about naver webtoon
     */
    public NaverComicDownloader(NaverComicInfo info) {
        this(info, null);
    }

    /**
     * Instantiates a new Naver comic downloader.
     *
     * @param info the information about naver webtoon
     * @param path the location where the webtoon will be saved
     */
    public NaverComicDownloader(NaverComicInfo info, String path){
        super(info, path);
        this.saver = new NaverComicSaver(path);
        this.loader = new NaverComicImageLoader(null);
        this.doc = new ReadDocument();
        getSize();
    }

    @Override
    public void download(int index) throws IOException {
        doc.read(detailUrl + titleId + info.getId() + "&no=" + (index + 1));
        ((NaverComicImageLoader) loader).setSource(doc);
        super.download(index);
    }

    private void getSize() {
        String href;
        try {
            href = Jsoup.connect(listUrl +  titleId + info.getId()).get().getElementsByClass(qViewList).first().selectFirst(qtr)
                .selectFirst(qa).attr(hrefAttr);
        }catch (IOException e){
            e.printStackTrace();
            size = 0;
            return;
        }
        size = Integer.parseInt(href.substring(href.indexOf(noStart) + 4, href.indexOf(noEnd)));
    }

    @Override
    protected String getTitle(int index) {
        return doc.getDoc().selectFirst(qh3).text();
    }

    @Override
    public int size() {
        return size;
    }
}
