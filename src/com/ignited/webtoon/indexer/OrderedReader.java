package com.ignited.webtoon.indexer;

import com.ignited.webtoon.util.Order;
import com.ignited.webtoon.util.Sortable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class OrderedReader implements FileIndexReader {

    protected File root;
    protected Sortable<File> order;

    public OrderedReader(String path) {
        this(new File(path), Order.CREATED_ASCENDING);
    }

    public OrderedReader(File root) {
        this(root, Order.CREATED_ASCENDING);
    }

    public OrderedReader(String path, Sortable<File> order) {
        this(new File(path), order);
    }

    public OrderedReader(File root, Sortable<File> order) {
        if(!root.exists()||!root.isDirectory()) throw new IllegalArgumentException("Not a Directory");
        this.root = root;
        this.order = order;
    }

    @Override
    public File[] read() throws IOException {
        File[] files = root.listFiles();
        if (files == null) {
            throw new FileNotFoundException("No files under root");
        }
        return order.sort(files);
    }
}
