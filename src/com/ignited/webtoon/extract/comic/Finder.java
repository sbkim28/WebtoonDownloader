package com.ignited.webtoon.extract.comic;

import com.ignited.webtoon.extract.comic.e.ComicFinderInitException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Finder
 * <p>
 * Find Webtoons
 *
 * @author Ignited
 */
public abstract class Finder{

    private List<ComicInfo> items;

    /**
     * Instantiates a new Finder.
     *
     * @throws ComicFinderInitException when it failed to initialize
     */
    public Finder() throws ComicFinderInitException {
        this(2, 5000);
    }


    /**
     * Instantiates a new Finder.
     *
     * @param maxTry the max try to connect and get elements
     * @param wait   the wait time in millis after failure
     * @throws ComicFinderInitException when it failed to initialize
     */
    public Finder(int maxTry, int wait) throws ComicFinderInitException {
        int i = 1;
        while (true){

            try {
                items = initialize();
                break;
            }catch (IOException e){
                System.err.println(e.getClass().getName() + ":" + e.getMessage());
                System.err.println("Failed to initialize finder");
                System.err.println("Left attempt : " + (maxTry - i));
                if(++i > maxTry){
                    throw new ComicFinderInitException(e);
                }
                items = null;
                try {
                    Thread.sleep(wait);
                } catch (InterruptedException e1) {
                    throw new ComicFinderInitException(e1);
                }
            }catch (Exception e){
                throw new ComicFinderInitException(e);
            }
        }
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
