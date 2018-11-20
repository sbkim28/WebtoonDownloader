package com.ignited.webtoon.comp.naver.comic;

import com.ignited.webtoon.extract.comic.Cataloger;
import com.ignited.webtoon.extract.comic.ComicInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NaverComicCataloger extends Cataloger {

    private final String url = "https://comic.naver.com/webtoon/creation.nhn";

    private final String titleId = "titleId=";

    /**
     * Instantiates a new Naver comic cataloger.
     *
     */
    public NaverComicCataloger() {
        super();
    }

    /**
     * Instantiates a new Naver comic cataloger.
     *
     * @param maxTry the max try to connect and get elements
     * @param wait   the wait time in millis after failure
     */
    public NaverComicCataloger(int maxTry, int wait)  {
        super(maxTry, wait);
    }

    @Override
    protected List<ComicInfo> deliver() throws IOException {
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
