package com.ignited.webtoon.view;

import com.ignited.webtoon.indexer.TextIndexReader;
import com.ignited.webtoon.translator.HTMLTranslator;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

/**
 * HTMLViewer
 *
 * Open the Html files and let them viewed
 * @author Ignited
 *
 */
public class HTMLViewer {

    private ImageCollector collector;
    private File workingDir;
    private String color;

    /**
     * Instantiates a new Html viewer.
     *
     * @param path the webtoon directory containing chapter folders
     */
    public HTMLViewer(String path) throws IOException {
        this(path, "D://");
    }

    /**
     * Instantiates a new Html viewer.
     *
     * @param path    the webtoon directory containing chapter folders
     * @param workDir the directory where the temporary files will be located
     */
    public HTMLViewer(String path, String workDir) throws IOException {
        File w = new File(workDir);
        if(!w.exists()){
            w.mkdirs();
            w.deleteOnExit();
        }
        if(!w.isDirectory()) throw new IllegalArgumentException("Not a Directory");
        workingDir = w;
        collector = new ImageCollector(new TextIndexReader(path));
    }

    /**
     * Set html file background color.
     *
     * @param color the color
     */
    public void setColor(String color){
        this.color = color;
    }

    /**
     * View.
     *
     * @param index the index
     * @throws IOException
     */
    public void view(int index) throws IOException {
        File _tmp = File.createTempFile("temp_", ".html", workingDir);
        _tmp.deleteOnExit();
        HTMLTranslator translator= new HTMLTranslator(collector.collect(index), _tmp, collector.getName(index));

        if(color != null) translator.setBackground(color);

        translator.translate();
        Desktop.getDesktop().browse(_tmp.toURI());
    }
}
