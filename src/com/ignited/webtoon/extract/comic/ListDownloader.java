package com.ignited.webtoon.extract.comic;

import com.ignited.webtoon.extract.comic.e.ComicListInitException;

import java.io.IOException;
import java.util.List;

/**
 * ListDonwloader
 *
 * Dawnload Webtoons using list
 *
 * @author Ignited
 * @see com.ignited.webtoon.extract.comic.Downloader
 */
public abstract class ListDownloader extends Downloader{


    public static final int DEFAULT_MAXTRY = 2;
    public static final int DEFAULT_WAIT = 5000;
    /**
     * The comic items.
     */
    protected List<Item> items;

    /**
     * Instantiates a new downloader.
     *
     * @param info the information about webtoon
     * @param path the location where the webtoon will be saved.
     * @throws ComicListInitException when it failed to get inital data.
     */
    public ListDownloader(ComicInfo info, String path) throws ComicListInitException {
        this(info, path, DEFAULT_MAXTRY, DEFAULT_WAIT);
    }

    /**
     * Instantiates a new List downloader.
     *
     * @param info   the information about webtoon
     * @param path   the location where the webtoon will be saved
     * @param maxTry the max try to connect and get elements
     * @param wait   the wait time in millis after failure
     * @throws ComicListInitException when it failed to get inital data.
     */
    public ListDownloader(ComicInfo info, String path, int maxTry, int wait) throws ComicListInitException {
        super(info, path);
        int i = 1;
        while (true) {
            try {
                initItems();
                break;
            } catch (IOException e) {
                System.err.println(e.getClass().getName() + ":" + e.getMessage());
                System.err.println("Failed to init item list");
                System.err.println("Left attempt : " + (maxTry - i));

                if(++i > maxTry){
                    throw new ComicListInitException(e);
                }
                items = null;
                try {
                    Thread.sleep(wait);
                } catch (InterruptedException e1) {
                    throw new ComicListInitException(e1);
                }
            } catch (Exception e){
                throw new ComicListInitException(e);
            }
        }
    }

    /**
     * Init comic items.
     */
    protected abstract void initItems() throws IOException;

    @Override
    protected String getTitle(int index) {
        return items.get(index).title;
    }

    @Override
    public int size() {
        return items.size();
    }

    protected static class Item{
        private String id;
        private String title;

        /**
         * Instantiates a new Item.
         *
         * @param id    the id
         * @param title the title
         */
        public Item(String id, String title) {
            this.id = id;
            this.title = title;
        }

        /**
         * Gets id.
         *
         * @return the id
         */
        public String getId() {
            return id;
        }

        /**
         * Gets title.
         *
         * @return the title
         */
        public String getTitle() {
            return title;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }
}
