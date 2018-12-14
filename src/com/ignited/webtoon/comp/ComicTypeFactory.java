package com.ignited.webtoon.comp;

import com.ignited.webtoon.comp.daum.comic.DaumComicCataloger;
import com.ignited.webtoon.comp.daum.comic.DaumComicDownloader;
import com.ignited.webtoon.comp.kakao.comp.KakaoComicCataloger;
import com.ignited.webtoon.comp.kakao.comp.KakaoComicDownloader;
import com.ignited.webtoon.comp.lezhin.comic.LezhinComicCataloger;
import com.ignited.webtoon.comp.lezhin.comic.LezhinComicDownloader;
import com.ignited.webtoon.comp.naver.comic.NaverComicCataloger;
import com.ignited.webtoon.comp.naver.comic.NaverComicDownloader;
import com.ignited.webtoon.extract.comic.*;
import com.ignited.webtoon.extract.comic.e.ComicListInitException;


/**
 * ComicTypeFactory
 *
 * Comic Factory creating Cataloger and Downloader of Naver, Daum, Lezhin and Kakao webtoon
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
        public Downloader downloader(ComicInfo info, String path) {
            return new NaverComicDownloader(info, path);
        }

        @Override
        public Downloader downloader(ComicInfo info, String path, ComicSaver saver) {
            return new NaverComicDownloader(info, path, saver);
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
        public Downloader downloader(ComicInfo info, String path) throws ComicListInitException {
            return new DaumComicDownloader(info, path);
        }

        @Override
        public Downloader downloader(ComicInfo info, String path, ComicSaver saver) throws ComicListInitException {
            return new DaumComicDownloader(info, path, saver);
        }

        @Override
        public Cataloger cataloger() {
            return new DaumComicCataloger();
        }
    },

    /**
     * The LezhinComic.
     */
    LEZHIN {

        @Override
        public Downloader downloader(ComicInfo info, String path) throws ComicListInitException {
            return new LezhinComicDownloader(info, path);
        }

        @Override
        public Downloader downloader(ComicInfo info, String path, ComicSaver saver) throws ComicListInitException {
            return new LezhinComicDownloader(info, path, saver);
        }

        @Override
        public Cataloger cataloger() {
            return new LezhinComicCataloger();
        }
    },

    KAKAO{

        @Override
        public Downloader downloader(ComicInfo info, String path) throws ComicListInitException {
            return new KakaoComicDownloader(info, path);
        }

        @Override
        public Downloader downloader(ComicInfo info, String path, ComicSaver saver) throws ComicListInitException {
            return new KakaoComicDownloader(info, path, saver);
        }

        @Override
        public Cataloger cataloger() {
            return new KakaoComicCataloger();
        }
    };

}

