package com.ignited.webtoon.comp.lezhin.comic;

import com.ignited.webtoon.extract.comic.ImageLoader;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LezhinComicImageLoader implements ImageLoader {

    private final String url = "https://cdn.lezhin.com/v2/comics/%s/episodes/%s/contents/scrolls/%d";

    private String comicId;
    private String episodeId;

    public void setComicId(String comicId) {
        this.comicId = comicId;
    }

    public void setEpisodeId(String episodeId) {
        this.episodeId = episodeId;
    }

    @Override
    public List<String> load() {
        List<String> ret = new ArrayList<>();
        int i = 0;
        while (true){
            try {
                String u = String.format(url, comicId, episodeId, ++i);
                HttpURLConnection con = (HttpURLConnection) new URL(u).openConnection();
                if(con.getResponseCode() == 200) {
                    ret.add(u);
                }else if(con.getResponseCode() == 404){
                    break;
                }else {
                    throw new IOException();
                }
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        return ret;
    }
}
