package com.ignited.webtoon.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StringUtils {

    public static String toString(InputStream in){
        byte[] buffer = new byte[1024];
        int len;
        StringBuilder builder = new StringBuilder();
        try {
            while ((len = in.read(buffer)) != -1) {
                builder.append(new String(buffer, 0, len));
            }
            in.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static String toString(InputStream in, String charset){
        InputStreamReader reader;
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[512];
        int len;
        try {
            reader = new InputStreamReader(in, charset);
            while((len = reader.read(buffer)) != -1){
                builder.append(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

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
