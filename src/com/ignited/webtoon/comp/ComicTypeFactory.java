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
        private NaverComicFinder finder;
        @Override
        public Finder finder() throws ComicFinderInitException {
            if (finder == null) {
                finder = new NaverComicFinder();
            }
            return finder;
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
        private DaumComicFinder finder;
        @Override
        public Finder finder() throws ComicFinderInitException {
            if (finder == null) {
                finder = new DaumComicFinder();
            }
            return finder;
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
    NAVER_COOKIE{
        private NaverComicFinder finder;
        @Override
        public Finder finder() throws ComicFinderInitException {
            if (finder == null) {
                finder = new NaverComicFinder();
            }
            return finder;
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

        private LezhinComicFinder finder;
        @Override
        public Finder finder() throws ComicFinderInitException {
            if (finder == null) {
                finder = new LezhinComicFinder();
            }
            return finder;
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

    }
}
