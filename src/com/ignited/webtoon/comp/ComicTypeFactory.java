package com.ignited.webtoon.comp;

import com.ignited.webtoon.comp.daum.comic.DaumComicDownloader;
import com.ignited.webtoon.comp.daum.comic.DaumComicFinder;
import com.ignited.webtoon.comp.lezhin.comic.LezhinComicDownloader;
import com.ignited.webtoon.comp.lezhin.comic.LezhinComicFinder;
import com.ignited.webtoon.comp.naver.comic.NaverComicCookieDownloader;
import com.ignited.webtoon.comp.naver.comic.NaverComicDownloader;
import com.ignited.webtoon.comp.naver.comic.NaverComicFinder;
import com.ignited.webtoon.extract.comic.ComicFactory;
import com.ignited.webtoon.extract.comic.ComicInfo;
import com.ignited.webtoon.extract.comic.Downloader;
import com.ignited.webtoon.extract.comic.Finder;
import com.ignited.webtoon.extract.comic.e.ComicFinderInitException;
import com.ignited.webtoon.extract.comic.e.ComicListInitException;

import java.io.IOException;


/**
 * ComicTypeFactory
 * <p>
 * Comic Factory creating Finder and Downloader of Naver and Daum webtoon
 *
 * @author Ignited
 * @see com.ignited.webtoon.extract.comic.ComicFactory
 */
public enum ComicTypeFactory implements ComicFactory {

    /**
     * The NaverComic
     */
    NAVER{
        @Override
        public Finder finder() throws ComicFinderInitException {
            if(!NAVER_COMIC_FINDER.isInited()){
                NAVER_COMIC_FINDER.init();
            }
            return NAVER_COMIC_FINDER;
        }

        @Override
        public Downloader downloader(ComicInfo info) {
            return new NaverComicDownloader(info);
        }

    },

    /**
     * The DaumComic
     */
    DAUM{
        @Override
        public Finder finder() throws ComicFinderInitException {
            if(!DAUM_COMIC_FINDER.isInited()){
                DAUM_COMIC_FINDER.init();
            }
            return DAUM_COMIC_FINDER;
        }

        @Override
        public Downloader downloader(ComicInfo info) {
            try {
                return new DaumComicDownloader(info);
            } catch (ComicListInitException e) {
                e.printStackTrace();
                return null;
            }
        }
    },

    /**
     * The NaverComic with cookie
     */
    NAVER_COOKIE {
        @Override
        public Finder finder() throws ComicFinderInitException {
            if(!NAVER_COMIC_FINDER.isInited()){
                NAVER_COMIC_FINDER.init();
            }
            return NAVER_COMIC_FINDER;
        }

        @Override
        public Downloader downloader(ComicInfo info) {
            return new NaverComicCookieDownloader(info);
        }
    },

    /**
     * The LezhinComic.
     */
    LEZHIN {
        @Override
        public Finder finder() throws ComicFinderInitException {
            if(!LEZHIN_COMIC_FINDER.isInited()){
                LEZHIN_COMIC_FINDER.init();
            }
            return LEZHIN_COMIC_FINDER;
        }

        @Override
        public Downloader downloader(ComicInfo info) {
            try {
                return new LezhinComicDownloader(info);
            } catch (ComicListInitException e) {
                e.printStackTrace();
                return null;
            }
        }

    };

    private static final NaverComicFinder NAVER_COMIC_FINDER = new NaverComicFinder();
    private static final DaumComicFinder DAUM_COMIC_FINDER = new DaumComicFinder();
    private static final LezhinComicFinder LEZHIN_COMIC_FINDER = new LezhinComicFinder();

}
