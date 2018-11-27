package com.ignited.webtoon.comp;

import com.ignited.webtoon.comp.daum.comic.DaumComicCataloger;
import com.ignited.webtoon.comp.daum.comic.DaumComicDownloader;
import com.ignited.webtoon.comp.lezhin.comic.LezhinComicCataloger;
import com.ignited.webtoon.comp.lezhin.comic.LezhinComicDownloader;
import com.ignited.webtoon.comp.naver.comic.NaverComicCataloger;
import com.ignited.webtoon.comp.naver.comic.NaverComicCookieDownloader;
import com.ignited.webtoon.comp.naver.comic.NaverComicDownloader;
import com.ignited.webtoon.extract.comic.*;
import com.ignited.webtoon.extract.comic.e.ComicListInitException;


/**
 * ComicTypeFactory
 *
 * Comic Factory creating Cataloger and Downloader of Naver, Daum, and Lezhin webtoon
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
        public Downloader downloader(ComicInfo info) {
            return new NaverComicDownloader(info);
        }

        @Override
        public Cataloger cataloger() {
            return new NaverComicCataloger();
        }

    },

    /**
     * The DaumComic
     */
    DAUM{
        @Override
        public Downloader downloader(ComicInfo info) {
            try {
                return new DaumComicDownloader(info);
            } catch (ComicListInitException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public Cataloger cataloger() {
            return new DaumComicCataloger();
        }
    },

    /**
     * The NaverComic with cookie
     */
    NAVER_COOKIE {

        @Override
        public Downloader downloader(ComicInfo info) {
            return new NaverComicCookieDownloader(info);
        }

        @Override
        public Cataloger cataloger() {
            return new NaverComicCataloger();
        }
    },

    /**
     * The LezhinComic.
     */
    LEZHIN {

        @Override
        public Downloader downloader(ComicInfo info) {
            try {
                return new LezhinComicDownloader(info);
            } catch (ComicListInitException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public Cataloger cataloger() {
            return new LezhinComicCataloger();
        }
    };

}
