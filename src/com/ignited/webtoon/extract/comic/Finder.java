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

    private int maxTry;
    private int wait;
    private boolean inited;

    /**
     * Instantiates a new Finder.
     */
    protected Finder() {
        this(2, 5000);
    }


    /**
     * Instantiates a new Finder.
     *
     * @param maxTry the max try to connect and get elements
     * @param wait   the wait time in millis after failure
     */
    protected Finder(int maxTry, int wait) {
        this.maxTry = maxTry;
        this.wait = wait;
        inited = false;
    }

    /**
     * initialize finder.
     *
     * @throws ComicFinderInitException when it failed to initialize
     */
    public final synchronized void init() throws ComicFinderInitException {
        if(inited) return;
        int i = 1;
        while (true){

            try {
                items = initialize();
                inited = true;
                break;
            }catch (IOException e){
                System.err.println(e.getClass().getName() + ":" + e.getMessage());
                System.err.println("Failed to initialize finder");
                System.err.println("Left attempt : " + (maxTry - i));
                if(++i > maxTry){
                    throw new ComicFinderInitException(e);
                }
                items = null;
                inited = false;
                try {
                    Thread.sleep(wait);
                } catch (InterruptedException e1) {
                    throw new ComicFinderInitException(e1);
                }
            }catch (Exception e){
                items = null;
                inited = false;
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

    public List<ComicInfo> getAll(){
        return new ArrayList<>(items);
    }

    /**
     * Initialize list.
     *
     * @return the list of ComicInfo
     * @throws IOException when it failed to initialize
     */
    protected abstract List<ComicInfo> initialize() throws IOException;

    public boolean isInited(){
        return inited;
    }
}
