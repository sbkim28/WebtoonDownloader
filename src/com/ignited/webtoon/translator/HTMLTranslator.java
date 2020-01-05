package com.ignited.webtoon.translator;

import com.ignited.webtoon.util.Compatamizer;

import java.io.*;


/**
 * HTMLTranslator
 *
 * Translate Image Files to an Easily Accessible File
 * @author Ignited
 * @see com.ignited.webtoon.translator.Translator
 */
public abstract class HTMLTranslator implements Translator{


    /**
     * The directory translated Files will be located at.
     */
    protected File writeOn;

    protected String title;
    protected String background;

    /**
     * Instantiates a new File translator.
     *
     * @param writeOn the directory translated files will be located at
     * @param title      the html title
     * @param background the html background
     */
    public HTMLTranslator(File writeOn, String title, String background) {
        this.writeOn = writeOn;
        this.title = title;
        this.background = background;
    }

    /**
     * Sets background color.
     *
     * @param background the background color
     */
    public void setBackground(String background) {
        this.background = background;
    }

    @Override
    public String translate() {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html><head><meta charset=\"UTF-8\"><title>")
                .append(Compatamizer.toHTML(title)) // position:absolute;left:50%; transform:translate(-50%, 0%)
                .append("</title>")
                //.append("<style>.layer{margin: auto;\nwidth: 50%;}</style>")
                .append("</head><body style=\"margin: 0px; background: #")
                .append(background)
                .append(";\"><div class=\"layer\">")
                .append("<center>");
        appendImage(htmlBuilder);
        htmlBuilder
                .append("</center>")
                .append("</div></body></html>");
        return htmlBuilder.toString();
    }

    protected abstract void appendImage(StringBuilder htmlBuilder);

    /**
     * Write.
     *
     * @throws IOException the io exception
     */
    public void write() throws IOException {
        File file = writeOn;
        if (!file.exists()) {
            file.createNewFile();
        }
        if (file.isDirectory()) {
            throw new IllegalArgumentException("File exists but Directory");
        }
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        writer.write(translate());
        writer.close();

    }
}
