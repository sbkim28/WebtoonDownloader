package com.ignited.webtoon.extract.comic;


/**
 * ComicInfo
 *
 * The information about webtoon
 */
public class ComicInfo {
    private String id;
    private String title;

    /**
     * Instantiates a new Comic info.
     *
     * @param id    the id
     * @param title the title
     */
    public ComicInfo(String id, String title) {
        this.id = id;
        this.title = title;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "ComicInfo{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
