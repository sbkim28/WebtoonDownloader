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

    private File[] dir;
    private Sortable<File> order;

    /**
     * Instantiates a new Image collector.
     *
     * image file order is default, IMAGE_ORDER
     *
     * @param reader the reader
     * @throws IOException the io exception
     */
    public ImageCollector(FileLoader reader) throws IOException {
        this(reader, Order.IMAGE_ORDER);
    }

    /**
     * Instantiates a new Image collector.
     *
     * @param reader the reader
     * @param order  the sorting order of image files
     * @throws IOException the io exception
     */
    public ImageCollector(FileLoader reader, Sortable<File> order) throws IOException {
        dir = reader.read();
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
     */
    public File[] collect(int index){
        File[] files = dir[index].listFiles();
        order.sort(files);
        return files;
    }

    public File[] collect(String filename){
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
