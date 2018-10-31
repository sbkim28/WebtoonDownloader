package com.ignited.webtoon.comp.naver.comic;

import com.ignited.webtoon.extract.comic.ImageLoader;
import com.ignited.webtoon.extract.ReadDocument;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * NaverComicImageLoader
 *
 * Prepare Images of Daum Webtoons
 *
 * @author Ignited
 * @see com.ignited.webtoon.extract.comic.ImageLoader
 */
public class NaverComicImageLoader implements ImageLoader {

    private final String detailUrl = "https://comic.naver.com/webtoon/detail.nhn";
    private final String qViewer = "wt_viewer";
    private final String qImg = "img";
    private final String srcAttr = "src";

    private ReadDocument rd;

    /**
     * Instantiates a new Naver comic image loader.
     *
     * @param rd the Html Document
     */
    public NaverComicImageLoader(ReadDocument rd) {
        this.rd = rd;
    }

    /**
     * Sets source.
     *
     * @param document the Html document
     */
    public void setSource(ReadDocument document) {
        rd = document;
    }

    @Override
    public List<String> load() {
        List<String> ret = new ArrayList<>();
        Elements imgs = rd.getDoc().getElementsByClass(qViewer).first().select(qImg);

        for(Element e : imgs){
            ret.add(e.attr(srcAttr));
        }
        return ret;
    }
}
