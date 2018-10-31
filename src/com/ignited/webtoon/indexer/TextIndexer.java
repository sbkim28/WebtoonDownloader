package com.ignited.webtoon.indexer;

import com.ignited.webtoon.indexer.order.Sortable;

import java.io.*;
import java.nio.file.Files;
import java.util.List;


/**
 * TextIndexer
 *
 * Create Text Index File
 *
 * @author Ignited
 * @see com.ignited.webtoon.indexer.FileIndexer
 */

public class TextIndexer extends FileIndexer{

    /**
     * Instantiates a new Text indexer.
     *
     * @param path the directory
     * @throws IOException if file is not directory
     */
    public TextIndexer(String path) throws IOException {
        super(path);
    }

    /**
     * Instantiates a new Text indexer.
     *
     * @param path  the directory
     * @param order the sorting order
     * @throws IOException if file is not directory
     */
    public TextIndexer(String path, Sortable<File> order) throws IOException {
        super(path, order);
    }

    /**
     * Instantiates a new Text indexer.
     *
     * @param root the directory
     * @throws IOException if file is not directory
     */
    public TextIndexer(File root) throws IOException {
        super(root);
    }

    /**
     * Instantiates a new Text indexer.
     *
     * @param root  the directory
     * @param order the sorting order
     * @throws IOException if file is not directory
     */
    public TextIndexer(File root, Sortable<File> order) throws IOException {
        super(root, order);
    }

    @Override
    protected void writeIndex(List<String> names) throws IOException {
        File file = new File(root.getPath() + "/index.txt");
        if (!file.exists()) file.createNewFile();
        else file.delete();
        Writer writer = new FileWriter(file);

        for (String name : names) {
            writer.write(name);
            writer.write(System.getProperty("line.separator"));
        }
        writer.close();

        Files.setAttribute(file.toPath(), "dos:hidden", true);
        file.setReadOnly();
    }
}
