package com.ignited.webtoon.extract;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Finder{

    private List<ComicInfo> items;

    public Finder() throws IOException {
        items = this.initialize();
    }

    public ComicInfo findMatch(String title){
        for(ComicInfo item : items){
            if(item.getTitle().equals(title)) {
                return item;
            }
        }
        return null;
    }

    public ComicInfo findContains(String title){
        for(ComicInfo item : items){
            if(item.getTitle().contains(title)) {
                return item;
            }
        }
        return null;
    }

    public List<ComicInfo> findAllContains(String title){
        List<ComicInfo> ret = new ArrayList<>();
        for(ComicInfo item : items){
            if(item.getTitle().contains(title)) {
                ret.add(item);
            }
        }
        return ret;
    }

    protected abstract List<ComicInfo> initialize() throws IOException;
}
