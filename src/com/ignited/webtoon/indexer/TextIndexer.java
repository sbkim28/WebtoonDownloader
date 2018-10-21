package com.ignited.webtoon.indexer;

import com.ignited.webtoon.util.ErrorHandler;
import com.ignited.webtoon.util.Sortable;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

public class TextIndexer extends FileIndexer{
    public TextIndexer(String path) throws IOException {
        super(path);
    }

    public TextIndexer(String path, Sortable<File> order) throws IOException {
        super(path, order);
    }

    public TextIndexer(File root) throws IOException {
        super(root);
    }

    public TextIndexer(File root, Sortable<File> order) throws IOException {
        super(root, order);
    }

    @Override
    protected void writeIndex(List<String> names) {
        File file = new File(root.getPath() + "/index.txt");
        try {
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
        } catch (IOException e) {
            ErrorHandler.writeFail(e);
        }
    }
}
