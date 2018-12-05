package com.ignited.webtoon.extract.comic;

import com.ignited.webtoon.extract.comic.e.ComicDownloadException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
     * @throws ComicDownloadException when chapter was donwloaded partially.
     */
    public void save(List<String> src, String title) throws ComicDownloadException {
        int index = 0;
        boolean ex = false;
        for(String s : src) {
            try {
                //BufferedImage image = ImageIO.read(build(s));
                StringBuilder builder = new StringBuilder().append(path).append("/").append(title).append("/");

                while (builder.charAt(builder.length() - 2) == '.') {
                    builder.deleteCharAt(builder.length() - 2);
                }

                File file = new File(builder.toString());
                if (!file.exists()) file.mkdirs();

                builder.append(name).append(++index);
                URLConnection con = build(s);
                String type = con.getContentType();
                if(type.contains("jpeg")){
                    builder.append(".jpg");
                }else if(type.contains("gif")){
                    builder.append(".gif");
                }else if(type.contains("png")){
                    builder.append(".png");
                }else {
                    throw new IOException("Unsupported Image Format : " + type);
                }

                BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(builder.toString()));

                int len;
                byte[] buf = new byte[1024];
                while ((len = bis.read(buf)) != -1){
                    bos.write(buf, 0, len);
                    bos.flush();
                }

            } catch (IOException e) {
                e.printStackTrace();
                ex = true;
            }
        }

        if(ex) throw new ComicDownloadException("Partially downloaded");
    }

    /**
     * Build url connection
     *
     * @param url the url of the image
     * @return the url connection
     * @throws IOException when it failed to open connection
     */
    protected URLConnection build(String url) throws IOException {
        return new URL(url).openConnection();
    }

}
