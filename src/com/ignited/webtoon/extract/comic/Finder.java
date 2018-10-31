package com.ignited.webtoon.extract.comic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Finder
 *
 * Find Webtoons
 *
 * @author Ignited
 */
public abstract class Finder{

    private List<ComicInfo> items;

    /**
     * Instantiates a new Finder.
     *
     * @throws IOException when it failed to initiate
     */
    public Finder() throws IOException {
        items = this.initialize();
    }

    /**
     * Find one webtoon whose name exactly matches the param title
     *
     * @param title the title
     * @return ComicInfo, if nothing matches, return null
     */
    public ComicInfo findMatch(String title){
        for(ComicInfo item : items){
            if(item.getTitle().equals(title)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Find one webtoon whose name contains the param title
     *
     * @param title the title
     * @return ComicInfo first found, if nothing matches, return null.
     */
    public ComicInfo findContains(String title){
        for(ComicInfo item : items){
            if(item.getTitle().contains(title)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Find all webtoons whose name contains the param title
     *
     * @param title the title
     * @return List of ComicInfo
     */
    public List<ComicInfo> findAllContains(String title){
        List<ComicInfo> ret = new ArrayList<>();
        for(ComicInfo item : items){
            if(item.getTitle().contains(title)) {
                ret.add(item);
            }
        }
        return ret;
    }

    /**
     * Initialize list.
     *
     * @return the list of ComicInfo
     * @throws IOException when it failed to initialize
     */
    protected abstract List<ComicInfo> initialize() throws IOException;
}
