package com.ignited.webtoon.comp.lezhin.comic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ignited.webtoon.extract.comic.ComicInfo;
import com.ignited.webtoon.extract.comic.ComicSaver;
import com.ignited.webtoon.extract.comic.ListDownloader;
import com.ignited.webtoon.extract.comic.e.ComicAccessException;
import com.ignited.webtoon.extract.comic.e.ComicDownloadException;
import com.ignited.webtoon.extract.comic.e.ComicListInitException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * LezhinComicDownloader
 *
 * @author Ignited
 * @see com.ignited.webtoon.extract.comic.ListDownloader
 */
public class LezhinComicDownloader extends ListDownloader {

    private static final String list_url = "https://www.lezhin.com/ko/comic/";

    private static final String image_url = "https://cdn.lezhin.com/v2/comics/%s/episodes/%s/contents/scrolls/%d";

    /**
     * Instantiates a new Lezhin comic downloader.
     *
     * @param info the information about lezhin webtoon
     * @param path the location where the webtoon will be saved.
     * @throws ComicListInitException when it failed to get initial data.
     */
    public LezhinComicDownloader(ComicInfo info, String path) throws ComicListInitException {
        super(info, path);
    }

    /**
     * Instantiates a new Lezhin comic downloader.
     *
     * @param info the information about lezhin webtoon
     * @param path the location where the webtoon will be saved.
     * @param saver the comic saver
     * @throws ComicListInitException when it failed to get initial data.
     */
    public LezhinComicDownloader(ComicInfo info, String path, ComicSaver saver) throws ComicListInitException {
        super(info, path, saver);
    }

    /**
     * Instantiates a new Lezhin comic downloader.
     *
     * @param info the information about lezhin webtoon
     * @param path the location where the webtoon will be saved.
     * @param saver the comic saver
     * @param maxTry the max try to connect and get elements
     * @param wait   the wait time in millis after failure
     * @throws ComicListInitException when it failed to get initial data.
     */
    public LezhinComicDownloader(ComicInfo info, String path, ComicSaver saver, int maxTry, int wait) throws ComicListInitException {
        super(info, path, saver, maxTry, wait);
    }

    @Override
    protected void initItems() throws IOException {
        if(!"LEZHIN".equals(info.getType())) throw new IllegalArgumentException("Unmatching comic type. (expected=LEZHIN, type=" + info.getType() + ")");
        items = new ArrayList<>();
        Document doc = Jsoup.connect(list_url + ((LezhinComicInfo) info).getAlias())
                .get();

        String s = doc.toString();
        s = s.substring(s.indexOf("__LZ_PRODUCT__ = ") + 17);
        s = s.substring(0,s.indexOf("};") + 1);
        JsonObject product = new JsonParser().parse(s).getAsJsonObject();
        JsonArray all = product.get("all").getAsJsonArray();
       for (int i = 0;i<all.size();++i){
            JsonObject item = all.get(i).getAsJsonObject();
            String id = item.get("id").getAsString();
            String title = item.get("display").getAsJsonObject().get("title").getAsString();
            int seq = item.get("seq").getAsInt();
            items.add(new SeqItem(id,title,seq));
        }
        items.sort(Comparator.comparingInt(o -> ((SeqItem) o).seq));
    }

    @Override
    protected List<String> getImages(int index) throws ComicDownloadException {
        List<String> ret = new ArrayList<>();
        int i = 1;
        while (true){
            String u = String.format(image_url, info.getId(), items.get(index).getId(), i);
            try {
                HttpURLConnection con = (HttpURLConnection) new URL(u).openConnection();
                int res = con.getResponseCode();
                if(res == 200) {
                    ret.add(u);
                }else if(res == 404){
                    break;
                }else {
                    throw new ComicAccessException("Access failed. (index=" + i + ", url=" + u + ", res=" + res + ")" );
                }
                ++i;
            } catch (IOException e) {
                throw new ComicDownloadException("Connection open failed. (index=" + i + ", url=" + u + ")",e);
            }
        }

        return ret;
    }

    /**
     * The type Seq item.
     */
    class SeqItem extends Item {

        private int seq;

        /**
         * Instantiates a new Item.
         *
         * @param id    the id
         * @param title the title
         */
        public SeqItem(String id, String title, int seq) {
            super(id, title);
            this.seq = seq;
        }

        public int getSeq() {
            return seq;
        }

        @Override
        public String toString() {
            return "SeqItem{" +
                    "id='" + getId() + '\'' +
                    ", title='" + getTitle() + '\'' +
                    "seq=" + seq +
                    '}';
        }
    }
}
