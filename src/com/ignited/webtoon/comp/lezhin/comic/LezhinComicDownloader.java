package com.ignited.webtoon.comp.lezhin.comic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ignited.webtoon.extract.comic.ComicInfo;
import com.ignited.webtoon.extract.comic.ComicSaver;
import com.ignited.webtoon.extract.comic.ListDownloader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * LezhinComicDownloader
 * @author Ignited
 * @see com.ignited.webtoon.extract.comic.ListDownloader
 */
public class LezhinComicDownloader extends ListDownloader {

    private final String list_url = "https://www.lezhin.com/ko/comic/";

    public LezhinComicDownloader(ComicInfo info) throws IOException {
        this(info, null);
    }

    /**
     * Instantiates a new Lezhin comic downloader.
     *
     * @param info the information about webtoon
     * @param path the location where the webtoon will be saved.
     */
    public LezhinComicDownloader(ComicInfo info, String path) throws IOException {
        super(info, path);
        this.saver = new ComicSaver(path);
        this.loader = new LezhinComicImageLoader();
    }

    @Override
    protected void initItems() throws IOException {
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
    public void download(int index) throws IOException {
        ((LezhinComicImageLoader) loader).setComicId(info.getId());
        ((LezhinComicImageLoader) loader).setEpisodeId(items.get(index).getId());
        super.download(index);
    }

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
