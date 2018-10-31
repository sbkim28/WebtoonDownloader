package com.ignited.webtoon.comp.naver.comic;

import com.ignited.webtoon.extract.comic.ComicInfo;

/**
 * NaverComicInfo
 *
 * The Information about Naver Webtoon
 *
 * @author Ignited
 * @see com.ignited.webtoon.extract.comic.ComicInfo
 */
public class NaverComicInfo extends ComicInfo {

    private String image;

    /**
     * Instantiates a new Naver comic info.
     *
     * @param id    the id
     * @param title the title
     */
    public NaverComicInfo(String id, String title) {
        this(id, title, null);
    }

    /**
     * Instantiates a new Naver comic info.
     *
     * @param id    the id
     * @param title the title
     * @param image the image
     */
    public NaverComicInfo(String id, String title, String image) {
        super(id, title);
        this.image = image;
    }

    /**
     * Gets image.
     *
     * @return the image
     */
    public String getImage() {
        return image;
    }


    @Override
    public String toString() {
        return "NaverComicInfo{" + "id=" + getId() + '\'' + "title=" + getTitle() + '\'' +
                "image='" + image + '\'' +
                '}';
    }
}
