package com.ignited.webtoon.extract.comic;

import com.ignited.webtoon.extract.comic.e.ComicCatalogException;
import com.ignited.webtoon.extract.comic.e.ComicFinderInitException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Cataloger
 *
 * Make a list of webtoons
 *
 * @author Ignited
 */
public abstract class Cataloger {

    private List<ComicInfo> items;

    private int maxTry;
    private int wait;


    /**
     * Instantiates a new Cataloger.
     */
    public Cataloger() {
        this(2, 5000);
    }


    /**
     * Instantiates a new Cataloger.
     *
     * @param maxTry the max try to connect and get elements
     * @param wait   the wait time in millis after failure
     */
    public Cataloger(int maxTry, int wait) {
        this.maxTry = maxTry;
        this.wait = wait;
        items = new ArrayList<>();
    }


    /**
     * Make a list of comic provided that item list is empty.
     *
     * @throws ComicCatalogException the comic catalog init exception
     * @return this
     */
    public Cataloger catalog() throws ComicCatalogException {
        return catalog(false);
    }

    /**
     * Make a list of comic.
     *
     * @param recreate decide whether to renew list if item list is already made,
     * @throws ComicCatalogException the comic catalog init exception
     * @return this
     */
    public Cataloger catalog(boolean recreate) throws ComicCatalogException {
        synchronized (this){
            if(!recreate && !items.isEmpty()) return this;
            int i = 1;
            while (true){
                try {
                    items = deliver();
                    break;
                }catch (IOException e){
                    System.err.println(e.getClass().getName() + ":" + e.getMessage());
                    System.err.println("Failed to initialize finder");
                    System.err.println("Left attempt : " + (maxTry - i));
                    if(++i > maxTry){
                        throw new ComicCatalogException(e);
                    }
                    items = null;
                    try {
                        Thread.sleep(wait);
                    } catch (InterruptedException e1) {
                        throw new ComicCatalogException(e1);
                    }
                }catch (Exception e){
                    items = null;
                    throw new ComicCatalogException(e);
                }
            }
        }
        return this;
    }


    /**
     * Deliver list.
     *
     * @return the list
     * @throws IOException when it failed to make list
     */
    protected abstract List<ComicInfo> deliver() throws IOException;


    /**
     * Get comic item list.
     *
     * @return the list
     */
    public List<ComicInfo> getList(){
        return new ArrayList<>(items);
    }

    /**
     * Check if the list of comic is already made.
     *
     * @return the boolean
     */
    public boolean isCatalogMade(){
        return items.isEmpty();
    }

    /**
     * Sets the max try to connect and get elements
     *
     * @param maxTry the max try
     */
    public void setMaxTry(int maxTry) {
        this.maxTry = maxTry;
    }

    /**
     * Sets the wait time in millis after failure
     *
     * @param wait the wait time
     */
    public void setWait(int wait) {
        this.wait = wait;
    }
}
