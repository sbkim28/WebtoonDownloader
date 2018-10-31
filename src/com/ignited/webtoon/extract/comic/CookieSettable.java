package com.ignited.webtoon.extract.comic;

import java.util.Map;

/**
 * CookieSettable
 * @author Ignited
 */
public interface CookieSettable {

    /**
     * Sets cookies.
     *
     * @param cookies the cookies
     */
    void setCookies(Map<String, String> cookies);
}
