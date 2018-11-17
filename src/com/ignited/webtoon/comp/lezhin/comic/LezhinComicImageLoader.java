package com.ignited.webtoon.comp.lezhin.comic;

import com.ignited.webtoon.extract.comic.ImageLoader;
import com.ignited.webtoon.extract.comic.e.ComicAccessException;
import com.ignited.webtoon.extract.comic.e.ComicDownloadException;

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
    public List<String> load() throws ComicDownloadException{
        List<String> ret = new ArrayList<>();
        int i = 1;
        while (true){
            try {
                String u = String.format(url, comicId, episodeId, i);
                HttpURLConnection con = (HttpURLConnection) new URL(u).openConnection();
                int res = con.getResponseCode();
                System.out.println(res);
                if(res == 200) {
                    ret.add(u);
                }else if(res == 404){
                    break;
                }else {
                    throw new ComicAccessException(u, res);
                }
                ++i;
            } catch (IOException e) {
                throw new ComicDownloadException(e);
            }
        }

        return ret;
    }
}
