package com.ignited.webtoon.comp.naver.comic;

import com.ignited.webtoon.extract.comic.ComicInfo;
import com.ignited.webtoon.extract.comic.Finder;
import com.ignited.webtoon.extract.comic.e.ComicFinderInitException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private final String titleId = "titleId=";

    /**
     * Instantiates a new Naver comic finder.
     *
     */
    public NaverComicFinder() {
        super();
    }

    /**
     * Instantiates a new Finder.
     *
     * @param maxTry the max try to connect and get elements
     * @param wait   the wait time in millis after failure
     */
    public NaverComicFinder(int maxTry, int wait)  {
        super(maxTry, wait);
    }

    @Override
    protected List<ComicInfo> initialize() throws IOException {
        Document doc = Jsoup.connect(url).get();
        String qAllList = "all_list";
        String qli = "li";
        Elements imageList = doc.getElementsByClass(qAllList).first().select(qli);

        List<ComicInfo> info = new ArrayList<>();
        for(Element webtoon : imageList){
            Element a = webtoon.selectFirst("a");
            String id = a.attr("href");
            id = id.substring(id.indexOf(titleId) + titleId.length());

            String thumb = webtoon.selectFirst(".thumb")
                    .selectFirst("img").attr("src");

            info.add(new ComicInfo(
                    id, a.attr("title"),
                    "NAVER",
                    thumb
            ));
        }

        return info;
    }
}

