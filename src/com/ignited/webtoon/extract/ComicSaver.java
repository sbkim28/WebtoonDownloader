package com.ignited.webtoon.extract;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ComicSaver {

    public static final String DEFAULT_IMG_NAME = "img";

    private String path;
    private String name;
    public ComicSaver(String path) {
        this(path, DEFAULT_IMG_NAME);
    }

    public ComicSaver(String path, String name) {
        this.path = path;
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void save(List<String> src, String title){
        int index = 0;
        for(String s : src) {
            try {
                BufferedImage image = ImageIO.read(build(s));
                StringBuilder builder = new StringBuilder().append(path).append("/").append(title).append("/");

                while (builder.charAt(builder.length() - 2) == '.') {
                    builder.deleteCharAt(builder.length() - 2);
                }

                File file = new File(builder.toString());
                if (!file.exists()) file.mkdirs();

                builder.append(name).append(++index).append(".jpg");
                ImageIO.write(image, "jpg", new File(builder.toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected InputStream build(String url) throws IOException {
        return new URL(url).openStream();
    }

}
