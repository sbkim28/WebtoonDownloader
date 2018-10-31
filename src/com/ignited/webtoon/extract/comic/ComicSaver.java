package com.ignited.webtoon.extract.comic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * ComicSaver
 *
 * Save Webtoons
 *
 * @author Ignited
 */
public class ComicSaver {

    /**
     * The constant DEFAULT_IMG_NAME.
     */
    public static final String DEFAULT_IMG_NAME = "img";

    private String path;
    private String name;

    /**
     * Instantiates a new Comic saver.
     * as Default Image Name
     *
     * @param path the location where the webtoon will be saved
     */
    public ComicSaver(String path) {
        this(path, DEFAULT_IMG_NAME);
    }

    /**
     * Instantiates a new Comic saver.
     *
     * @param path the location where naver webtoon will be saved
     * @param name the specific image name
     */
    public ComicSaver(String path, String name) {
        this.path = path;
        this.name = name;
    }

    /**
     * Sets path.
     *
     * @param path the location where naver webtoon will be saved
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Sets name.
     *
     * @param name the specific image name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Save a chapter of the webtoon.
     *
     * @param src   the src of the image of one chapter
     * @param title the title of the chapter
     */
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

    /**
     * Build Image inputStream
     *
     * @param url the url of the image
     * @return the inputstream of the image
     * @throws IOException when it failed to open stream.
     */
    protected InputStream build(String url) throws IOException {
        return new URL(url).openStream();
    }

}
