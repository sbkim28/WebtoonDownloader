package com.ignited.webtoon.control;

import com.ignited.webtoon.extract.comic.ComicInfo;
import com.ignited.webtoon.extract.comic.e.ComicNotFoundException;

import java.util.Collection;

public interface ComicFindStrategy {
    boolean find(ComicInfo info);
    ComicInfo find(Collection<ComicInfo> infos) throws ComicNotFoundException;
}
