package com.ignited.webtoon.comp;

import com.ignited.webtoon.comp.daum.comic.DaumComicDownloader;
import com.ignited.webtoon.comp.daum.comic.DaumComicFinder;
//import com.ignited.webtoon.comp.daum.comic.DaumTextExtractor;
import com.ignited.webtoon.comp.daum.comic.DaumComicInfo;
import com.ignited.webtoon.comp.naver.comic.NaverComicDownloader;
import com.ignited.webtoon.comp.naver.comic.NaverComicFinder;
import com.ignited.webtoon.comp.naver.comic.NaverComicInfo;
import com.ignited.webtoon.extract.*;

import java.io.IOException;

public enum ComicTypeFactory implements ComicFactory {
    NAVER{
        private NaverComicFinder finder;
        @Override
        public Finder finder() throws IOException {
            if (finder == null) {
                finder = new NaverComicFinder();
            }
            return finder;
        }

        @Override
        public Downloader downloader(ComicInfo info) {
            return new NaverComicDownloader((NaverComicInfo) info, null);
        }
    },
    DAUM{
        private DaumComicFinder finder;
        @Override
        public Finder finder() throws IOException {
            if (finder == null) {
                finder = new DaumComicFinder();
            }
            return finder;
        }

        @Override
        public Downloader downloader(ComicInfo info) {
            try {
                return new DaumComicDownloader((DaumComicInfo) info, null);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}
