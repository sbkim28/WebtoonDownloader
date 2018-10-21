package com.ignited.webtoon.extract;

public interface ComicFactory {

    Finder finder();
    Extractor extractor(String id);

}
