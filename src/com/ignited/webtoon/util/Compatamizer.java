package com.ignited.webtoon.util;

/**
 * The type Compatamizer.
 *
 * Make String compatamize.
 *
 * @author Ignited
 *
 */
public class Compatamizer {


    /**
     * Make invalid String eliminated in filename
     *
     * @param html the html
     * @return the string
     */
    public static String factor(String html){
        return html.replace("&nbsp", " ")
                .replace("&lt;", "")
                .replace("&gt;", "")
                .replace("&amp;", "&")
                .replace("?", "")
                .replace(":", "")
                .replace("\\", "")
                .replace("*", "")
                .replace("/", "")
                .replace("\"", "")
                .replace("<", "")
                .replace(">", "").trim();
    }

    /**
     * Make invalid String replaced in html file.
     *
     * @param string the html contents
     * @return the string
     */
    public static String toHTML(String string){
        return string.replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("&", "&amp;");
    }
}
