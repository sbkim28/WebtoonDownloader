package com.ignited.webtoon.extract;


import java.io.IOException;

public interface ComicFactory {

    Finder finder() throws IOException;
    Downloader downloader(ComicInfo info);

}
