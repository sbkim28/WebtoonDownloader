package com.ignited.webtoon.extract.comic;

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


    /**
     * The comic items.
     */
    protected List<Item> items;

    /**
     * Instantiates a new downloader.
     *
     * @param info the information about webtoon
     * @param path the location where the webtoon will be saved.
     */
    public ListDownloader(ComicInfo info, String path) throws IOException{
        super(info, path);
        initItems();
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
