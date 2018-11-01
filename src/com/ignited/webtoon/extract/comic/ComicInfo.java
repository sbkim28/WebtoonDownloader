package com.ignited.webtoon.extract.comic;


/**
 * ComicInfo
 * <p>
 * The information about webtoon
 */
public class ComicInfo {
    private String id;
    private String title;
    private String type;

    /**
     * Instantiates a new Comic info.
     *
     * @param id    the id
     * @param title the title
     * @param type the comic type
     */
    public ComicInfo(String id, String title, String type) {
        this.id = id;
        this.title = title;
        this.type = type;
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

    @Override
    public String toString() {
        return "ComicInfo{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
