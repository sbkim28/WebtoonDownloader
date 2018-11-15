package com.ignited.webtoon.extract.comic;

import java.util.List;


/**
 * Imageloader
 *
 * Prepare Images of Webtoon
 *
 * @author Ignited
 */
public interface ImageLoader {


    /**
     * Analyze given image data.
     *
     * @return the list of url of the images.
     */
    List<String> load();


}