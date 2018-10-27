package com.ignited.webtoon.util;

public class Compatamizer {

    public static String factor(String html){
        return html.replace("&nbsp", " ")
                .replace("&lt;", "")
                .replace("&gt;", "")
                .replace("&amp;", "&")
                .replace("?", "")
                .replace(":", "")
                .replace("\\", "")
                .replace("*", "")
                .replace("\"", "")
                .replace("<", "")
                .replace(">", "").trim();
    }

    public static String toHTML(String string){
        return string.replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("&", "&amp;");
    }
}
