package com.ignited.webtoon.indexer;

import com.ignited.webtoon.indexer.order.Order;
import com.ignited.webtoon.indexer.order.Sortable;

import java.io.File;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.List;

public abstract class FileIndexer implements Indexer{

    protected Sortable<File> order;
    protected File root;

    public FileIndexer(String path) throws IOException {
        this(new File(path), Order.NAME_ASCENDING);
    }

    public FileIndexer(String path, Sortable<File> order) throws IOException {
        this(new File(path), order);
    }

    public FileIndexer(File root) throws IOException {
        this(root, Order.NAME_ASCENDING);
    }
    public FileIndexer(File root, Sortable<File> order) throws IOException{
        if(order == null) throw new IllegalArgumentException("Order cannot be null");
        if(!root.exists()||!root.isDirectory()) throw new NotDirectoryException(root.getPath());
        this.order = order;
        this.root = root;
    }

    @Override
    public void setIndex() throws IOException {
        File[] files = root.listFiles();
        if (files != null) {
            files = order.sort(files);
            List<String> names = new ArrayList<>(files.length);
            for (File f : files) {
                if (!f.isDirectory()) continue;
                names.add(f.getName());
            }
            writeIndex(names);
        }
    }

    abstract protected void writeIndex(List<String> names) throws IOException;
}
