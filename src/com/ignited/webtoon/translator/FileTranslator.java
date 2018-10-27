package com.ignited.webtoon.translator;

import java.io.*;

public abstract class FileTranslator implements Translator{

    protected File[] files;
    protected File writeOn;

    public FileTranslator(File[] files, File writeOn) {
        this.files = files;
        this.writeOn = writeOn;
    }

    public int length(){
        return files.length;
    }

    @Override
    public void run() {
        try {
            write(translate());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String data) throws IOException {
        File file = writeOn;
        if (!file.exists()) {
            file.createNewFile();
        }
        if (file.isDirectory()) {
            throw new IllegalArgumentException("File exists but Directory");
        }
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        writer.write(data);
        writer.close();

    }
}
