package com.ignited.webtoon.view;

import com.ignited.webtoon.indexer.FileLoader;
import com.ignited.webtoon.indexer.order.Order;
import com.ignited.webtoon.indexer.order.Sortable;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * ImageCollector
 *
 * Collect Images in Chapter folders
 *
 * @author Ignited
 */
public class ImageCollector {

    private FileLoader loader;
    private File[] dir;
    private Sortable<File> order;

    /**
     * Instantiates a new Image collector.
     *
     * image file order is default, IMAGE_ORDER
     *
     * @param loader the reader
     */
    public ImageCollector(FileLoader loader) {
        this(loader, Order.IMAGE_ORDER);
    }

    /**
     * Instantiates a new Image collector.
     *
     * @param loader the loader
     * @param order  the sorting order of image files
     */
    public ImageCollector(FileLoader loader, Sortable<File> order) {
        this.loader = loader;
        this.order = order;
    }

    /**
     * Gets chapter name.
     *
     * @param index the number of chapter
     * @return the name of chapter
     */
    public String getName(int index) {
        return dir[index].getName();
    }

    /**
     * Collect image files of one specific chapter.
     *
     * @param index the number of chapter
     * @return the image files
     * @throws IOException when failed to get files
     */
    public File[] collect(int index) throws IOException {
        if(dir == null) {
            dir = loader.read();
        }
        File[] files = dir[index].listFiles();
        order.sort(files);
        return files;
    }

    /**
     * Collect image files of one specific chapter.
     *
     * @param filename the filename of chapter
     * @return the image files
     * @throws IOException when failed to get files
     */
    public File[] collect(String filename) throws IOException {
        if(dir == null) {
            dir = loader.read();
        }
        File f = null;
        for (File file: dir) {
            if(file.getName().equals(filename)){
                f = file;
                break;
            }
        }
        if(f == null) return null;
        File[] files = f.listFiles();
        order.sort(files);
        return files;
    }

    /**
     * Size int.
     *
     * @return the int
     */
    public int size(){
        return dir.length;
    }

}
