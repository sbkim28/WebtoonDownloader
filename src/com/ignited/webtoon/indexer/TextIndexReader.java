package com.ignited.webtoon.indexer;

import org.mozilla.universalchardet.UniversalDetector;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class TextIndexReader implements FileIndexReader {

    protected File root;

    public TextIndexReader(String path){
        this(new File(path));
    }

    public TextIndexReader(File root) {
        if(!root.exists()||!root.isDirectory()) throw new IllegalArgumentException("Not a Directory");
        this.root = root;
    }

    @Override
    public File[] read() throws IOException {
        List<File> files = new LinkedList<>();
        File index = new File(root.getPath() + "/index.txt");
        if(!index.exists()) throw new FileNotFoundException();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(index), getEncoding(index)));
        String line;

        while ((line = reader.readLine()) != null) {
            File file = new File(root.getPath() + "/" + line);
            if (!file.exists()) throw new FileNotFoundException();

            files.add(file);
        }

        return files.toArray(new File[files.size()]);
    }

    private String getEncoding(File file) throws IOException{
        FileInputStream fis = new FileInputStream(file);

        UniversalDetector universalDetector = new UniversalDetector(null);
        int len;
        byte[] buf = new byte[4096];
        while ((len = fis.read(buf)) != -1){
            universalDetector.handleData(buf, 0, len);
        }
        universalDetector.dataEnd();
        String enc = universalDetector.getDetectedCharset();
        universalDetector.reset();
        return enc;
    }

}