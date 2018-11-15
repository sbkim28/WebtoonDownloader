package com.ignited.webtoon.comp.lezhin.comic;

import com.ignited.webtoon.extract.comic.ComicInfo;

/**
 * The type Lezhin comic info.
 */
public class LezhinComicInfo extends ComicInfo {

    private String alias;

    /**
     * Instantiates a new Comic info.
     *
     * @param id    the id
     * @param title the title
     * @param alias the exposed id
     */
    public LezhinComicInfo(String id, String title, String alias) {
        super(id, title, "LEZHIN");
        this.alias = alias;
    }


    /**
     * Gets alias.
     *
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }

    @Override
    public String toString() {
        return "LezhinComicInfo{" +
                "id='" + getId() + '\'' +
                ", title='" + getTitle() + '\'' +
                "alias='" + alias + '\'' +
                '}';
    }
}
