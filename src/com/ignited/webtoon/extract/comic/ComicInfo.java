package com.ignited.webtoon.extract.comic;


/**
 * ComicInfo
 *
 * The information about webtoon
 * @author Ignited
 */
public class ComicInfo {
    private String id;
    private String title;
    private String type;
    private String thumbnail;

    /**
     * Instantiates a new Comic info.
     *
     * @param id        the comic id
     * @param title     the comic title
     * @param type      the comic type
     * @param thumbnail the thumbnail
     */
    public ComicInfo(String id, String title, String type, String thumbnail) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.thumbnail = thumbnail;
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


    /**
     * Gets comic type.
     *
     * @return the comic type
     */
    public String getType() {
        return type;
    }


    /**
     * Gets thumbnail.
     *
     * @return the thumbnail
     */
    public String getThumbnail() {
        return thumbnail;
    }


    @Override
    public String toString() {
        return "ComicInfo{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
