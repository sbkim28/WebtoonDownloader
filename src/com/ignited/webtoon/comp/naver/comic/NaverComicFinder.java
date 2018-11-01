package com.ignited.webtoon.comp.naver.comic;

import com.ignited.webtoon.extract.comic.ComicInfo;
import com.ignited.webtoon.extract.comic.Finder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * NaverComicFinder
 *
 * Find Naver Webtoons
 *
 * @author Ignited
 * @see com.ignited.webtoon.extract.comic.Finder
 */
public class NaverComicFinder extends Finder {

    private final String url = "https://comic.naver.com/webtoon/creation.nhn";

    private final String qAllList = "all_list";
    private final String qli = "li";
    private final String qa = "a";
    private final String qimg = "img";
    private final String titleAttr = "title";
    private final String hrefAttr= "href";
    private final String titleId = "titleId=";
    private final String src  = "src";

    /**
     * Instantiates a new Naver comic finder.
     *
     * @throws IOException the io exception
     */
    public NaverComicFinder() throws IOException {
        super();
    }

    @Override
    protected List<ComicInfo> initialize() throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements imageList = doc.getElementsByClass(qAllList).first().select(qli);

        List<ComicInfo> info = new ArrayList<>();
        for(Element webtoon : imageList){
            Element a = webtoon.selectFirst(qa);
            String id = a.attr(hrefAttr);
            id = id.substring(id.indexOf(titleId) + titleId.length());
            info.add(new ComicInfo(
                    id, a.attr(titleAttr),
                    "NAVER"
            ));
        }

        return info;
    }
}

