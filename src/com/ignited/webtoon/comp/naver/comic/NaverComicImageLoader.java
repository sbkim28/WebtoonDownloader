package com.ignited.webtoon.comp.naver.comic;

import com.ignited.webtoon.extract.ImageLoader;
import com.ignited.webtoon.extract.ReadDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NaverComicImageLoader implements ImageLoader {

    private final String detailUrl = "https://comic.naver.com/webtoon/detail.nhn";
    private final String qViewer = "wt_viewer";
    private final String qImg = "img";
    private final String srcAttr = "src";

    private ReadDocument rd;

    public NaverComicImageLoader(ReadDocument rd) {
        this.rd = rd;
    }

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
