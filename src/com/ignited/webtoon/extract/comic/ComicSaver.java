package com.ignited.webtoon.extract.comic;

import com.ignited.webtoon.extract.comic.e.ComicDownloadException;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.logging.Logger;

/**
 * ComicSaver
 *
 * Save Webtoons
 *
 * @author Ignited
 */
public class ComicSaver {

    private static final Logger LOGGER = Logger.getLogger(ComicSaver.class.getName());

    /**
     * The constant DEFAULT_IMG_NAME.
     */
    public static final String DEFAULT_IMG_NAME = "img";

    private String path;
    private String name;

    private ConnectionBuildStrategy ubs;


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
     * Sets ubs.
     *
     * @param ubs the URL Build Strategy
     */
    public void setUbs(ConnectionBuildStrategy ubs) {
        this.ubs = ubs;
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
            StringBuilder builder = new StringBuilder().append(path).append("/").append(title).append("/");

            while (builder.charAt(builder.length() - 2) == '.') {
                builder.deleteCharAt(builder.length() - 2);
            }

            File file = new File(builder.toString());
            boolean dir = false;
            if (!file.exists()){
                dir = file.mkdirs();
            }
            builder.append(name).append(++index);
            URLConnection con;
            try {
                con = build(s);
            }catch (IOException e) {
                e.printStackTrace();
                LOGGER.warning("Connection build failed. (src=" + s + ", ubs=" + (ubs == null) + ")");
                ex = true;
                continue;
            }

            String type = con.getContentType();
            if(type.contains("jpeg")){
                builder.append(".jpg");
            }else if(type.contains("gif")){
                builder.append(".gif");
            }else if(type.contains("png")){
                builder.append(".png");
            }else {
                LOGGER.warning("Unsupported image format. (type=" + type+ ", src=" +s+ ")");
                ex = true;
            }

            try {
                BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(builder.toString()));

                int len;
                byte[] buf = new byte[1024];
                while ((len = bis.read(buf)) != -1) {
                    bos.write(buf, 0, len);
                    bos.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                ex = true;
                LOGGER.warning("Reading and writing failed. (type=" + type + ", src= " + s + ", location=" + builder.toString() + ", createdir=" + dir + ")");
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
    private URLConnection build(String url) throws IOException {
        if(ubs == null) {
            return new URL(url).openConnection();
        }else {
            return ubs.build(url);
        }
    }


    public interface ConnectionBuildStrategy {
        URLConnection build(String url) throws IOException;
    }
}
