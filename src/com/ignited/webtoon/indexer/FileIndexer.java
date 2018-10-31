package com.ignited.webtoon.indexer;

import com.ignited.webtoon.indexer.order.Order;
import com.ignited.webtoon.indexer.order.Sortable;

import java.io.File;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.List;


/**
 * FileIndexer
 *
 * Create Index File
 *
 * @author Ignited
 * @see com.ignited.webtoon.indexer.Indexer
 */
public abstract class FileIndexer implements Indexer{

    /**
     * The sorting order of file.
     */
    protected Sortable<File> order;

    /**
     * The directory containing files to index.
     */
    protected File root;

    /**
     * Instantiates a new File indexer.
     *
     * @param path the directory
     * @throws IOException if the file is not a folder
     */
    public FileIndexer(String path) throws IOException {
        this(new File(path), Order.NAME_ASCENDING);
    }

    /**
     * Instantiates a new File indexer.
     *
     * @param path  the directory
     * @param order the sorting order
     * @throws IOException if the file is not a folder
     */
    public FileIndexer(String path, Sortable<File> order) throws IOException {
        this(new File(path), order);
    }

    /**
     * Instantiates a new File indexer.
     *
     * @param root the directory
     * @throws IOException if the file is not a folder
     */
    public FileIndexer(File root) throws IOException {
        this(root, Order.NAME_ASCENDING);
    }

    /**
     * Instantiates a new File indexer.
     *
     * @param root  the directory
     * @param order the sorting order
     * @throws IOException if the file is not a folder
     */
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

    /**
     * Write index file.
     *
     * @param names the ordered file names
     * @throws IOException when it failed to write index file
     */
    abstract protected void writeIndex(List<String> names) throws IOException;
}
