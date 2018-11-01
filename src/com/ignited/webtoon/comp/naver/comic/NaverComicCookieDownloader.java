package com.ignited.webtoon.comp.naver.comic;

import com.ignited.webtoon.extract.ReadCookieDocument;
import com.ignited.webtoon.extract.comic.ComicInfo;
import com.ignited.webtoon.extract.comic.CookieSettable;

import java.util.Map;


/**
 * NaverComicCookieDownloader
 *
 * Dawnload Naver Webtoons with cookies
 *
 * @author Ignited
 * @see com.ignited.webtoon.comp.naver.comic.NaverComicDownloader
 * @see com.ignited.webtoon.extract.comic.CookieSettable
 *
 */
public class NaverComicCookieDownloader extends NaverComicDownloader implements CookieSettable {

    /**
     * Instantiates a new Naver comic cookie downloader.
     *
     * @param info the information about naver webtoon
     */
    public NaverComicCookieDownloader(ComicInfo info){
        this(info, null);
    }

    /**
     * Instantiates a new Naver comic cookie downloader.
     *
     * @param info the information about naver webtoon
     * @param path the location where the webtoon will be saved
     */
    public NaverComicCookieDownloader(ComicInfo info, String path) {
        super(info, path);
        doc = new ReadCookieDocument();
    }

    @Override
    public void setCookies(Map<String, String> cookies){
        ((ReadCookieDocument) doc).setCookies(cookies);
    }
}
