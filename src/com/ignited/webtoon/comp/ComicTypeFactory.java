package com.ignited.webtoon.comp;

import com.ignited.webtoon.comp.daum.comic.DaumComicExtractor;
import com.ignited.webtoon.comp.daum.comic.DaumComicFinder;
//import com.ignited.webtoon.comp.daum.comic.DaumTextExtractor;
import com.ignited.webtoon.comp.naver.comic.NaverComicExtractor;
import com.ignited.webtoon.comp.naver.comic.NaverComicFinder;
import com.ignited.webtoon.extract.ComicFactory;
import com.ignited.webtoon.extract.Extractor;
import com.ignited.webtoon.extract.Finder;

public enum ComicTypeFactory implements ComicFactory {
    NAVER{
        private NaverComicFinder finder;
        @Override
        public Finder finder() {
            if (finder == null) {
                finder = new NaverComicFinder();
            }
            return finder;
        }

        @Override
        public Extractor extractor(String id) {
            return new NaverComicExtractor(id);
        }
    },
    DAUM{
        private DaumComicFinder finder;
        @Override
        public Finder finder(){
            if (finder == null) {
                finder = new DaumComicFinder();
            }
            return finder;
        }

        @Override
        public Extractor extractor(String id) {
            return new DaumComicExtractor(id);
        }
    },

    /*
    DAUM_TEXT{
        @Override
        public Finder finder() {
            return null;
        }

        @Override
        public Extractor extractor(String id) {
            return new DaumTextExtractor(id);
        }
    }
    */

}
