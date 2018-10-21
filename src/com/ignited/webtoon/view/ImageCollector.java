package com.ignited.webtoon.view;

import com.ignited.webtoon.indexer.FileIndexReader;
import com.ignited.webtoon.util.Order;
import com.ignited.webtoon.util.Sortable;

import java.io.File;
import java.io.IOException;

public class ImageCollector {

    private File[] dir;
    private Sortable<File> order;

    public ImageCollector(FileIndexReader reader) throws IOException {
        this(reader, Order.CREATED_ASCENDING);
    }
    public ImageCollector(FileIndexReader reader, Sortable<File> order) throws IOException {
        dir = reader.read();
        this.order = order;
    }

    public String getName(int index) {
        return dir[index].getName();
    }

    public File[] collect(int index){
        return order.sort(dir[index].listFiles());
    }

    public int size(){
        return dir.length;
    }

}
