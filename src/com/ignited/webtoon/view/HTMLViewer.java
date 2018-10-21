package com.ignited.webtoon.view;

import com.ignited.webtoon.indexer.TextIndexReader;
import com.ignited.webtoon.translator.HTMLTranslator;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class HTMLViewer {

    private ImageCollector collector;
    private File workingDir;
    private String color;

    public HTMLViewer(String path) throws IOException {
        this(path, "D://");
    }

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

    public void setColor(String color){
        this.color = color;
    }

    public void view(int index) throws IOException {
        File _tmp = File.createTempFile("temp_", ".html", workingDir);
        _tmp.deleteOnExit();
        HTMLTranslator translator= new HTMLTranslator(collector.collect(index), _tmp, collector.getName(index));

        if(color != null) translator.setBackground(color);

        translator.translate();
        Desktop.getDesktop().browse(_tmp.toURI());
    }
}
