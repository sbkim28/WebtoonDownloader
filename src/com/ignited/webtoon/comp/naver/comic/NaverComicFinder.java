package com.ignited.webtoon.comp.naver.comic;

import com.ignited.tools.connect.Connector;
import com.ignited.tools.connect.SimpleLinker;
import com.ignited.webtoon.extract.ComicInfo;
import com.ignited.webtoon.extract.Finder;

import java.util.*;

public class NaverComicFinder extends Finder {

    private final String url = "https://comic.naver.com/webtoon/creation.nhn";

    @Override
    protected List<ComicInfo> initialize() {
        List<ComicInfo> ret = new ArrayList<>();
        Connector linker = new SimpleLinker(url);
        String s = linker.read("utf-8");
        String[] items = s.substring(s.indexOf("class=\"all_list all_image\""),s.indexOf("class=\"goto_area\"")).split("class=\"thumb\"");
        for(String item : items){
            int idIndex;
            if((idIndex = item.indexOf("?titleId=")+ 9)  == 8) {
                continue;
            }
            int titleIndex = item.indexOf("title=");
            String id = item.substring(idIndex,titleIndex-2);
            String title = item.substring(titleIndex+7, item.indexOf("img onerror") - 3);
            String image = item.substring(item.indexOf("src=\"") + 5, item.indexOf("\" width="));
            ret.add(new NaverComicInfo(id, title,image));
        }
        return ret;
    }
}

