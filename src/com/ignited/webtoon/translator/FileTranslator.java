package com.ignited.webtoon.translator;

import java.io.*;


/**
 * FileTranslator
 *
 * Translate Image Files to an Easily Accessible File
 * @author Ignited
 * @see com.ignited.webtoon.translator.Translator
 */
public abstract class FileTranslator implements Translator, Runnable{

    /**
     * The Image Files.
     */

    protected File[] files;
    /**
     * The directory translated Files will be located at.
     */
    protected File writeOn;

    /**
     * Instantiates a new File translator.
     *
     * @param files   the image files
     * @param writeOn the directory translated files will be located at
     */
    public FileTranslator(File[] files, File writeOn) {
        this.files = files;
        this.writeOn = writeOn;
    }

    /**
     * return the length of images.
     *
     * @return the length of image files
     */
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

    /**
     * Write.
     *
     * @param data the data
     * @throws IOException the io exception
     */
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
