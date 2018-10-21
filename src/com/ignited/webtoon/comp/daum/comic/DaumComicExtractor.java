package com.ignited.webtoon.comp.daum.comic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ignited.tools.connect.Connector;
import com.ignited.tools.connect.Linker;
import com.ignited.tools.connect.SimpleIndexedLinker;
import com.ignited.tools.connect.SimpleLinker;
import com.ignited.tools.connect.adapter.DefaultLinkerAdapter;
import com.ignited.webtoon.extract.ListedExtractor;
import com.ignited.webtoon.util.ErrorHandler;
import com.ignited.webtoon.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.util.*;

public class DaumComicExtractor extends ListedExtractor {

    private final String listUrl = "http://webtoon.daum.net/data/pc/webtoon/view/";
    private final String viewUrl = "http://webtoon.daum.net/data/pc/webtoon/viewer_images/";

    protected List<String> titles;
    private String name = "img";

    private int imageIndex;

    public DaumComicExtractor(String id) {
        this(id,0);
    }

    public DaumComicExtractor(String id, int start) {
        super(start);
        imageIndex = 1;
        init(id);
    }

    public void setName(String name) {
        this.name = name;
    }

    protected void init(String id){
        items = new ArrayList<>();
        titles = new ArrayList<>();
        Connector linker = Linker.getInstance(new DefaultLinkerAdapter(listUrl + id), this);
        String s = linker.read("utf-8");
        JsonArray array = new JsonParser().parse(s).getAsJsonObject().get("data").getAsJsonObject().get("webtoon").getAsJsonObject().get("webtoonEpisodes").getAsJsonArray();
        List<JsonObject> list = new ArrayList<>();
        for(int i = 0;i<array.size();++i){
            list.add(array.get(i).getAsJsonObject());
        }
        list.sort(Comparator.comparingInt(o -> o.get("episode").getAsInt()));
        for(JsonObject object : list) {
            titles.add(StringUtils.factor(object.get("title").getAsString()));
            items.add(object.get("id").getAsString());
        }
    }

    @Override
    protected void save(List<String> srcs) {
        SimpleIndexedLinker linker = new SimpleIndexedLinker(srcs);
        while (linker.hasNext()){
            try {
                linker.connect();
                BufferedImage image = ImageIO.read(linker.getInputstream());
                if(path == null) return;
                StringBuilder builder = new StringBuilder().append(path).append("/").append(titles.get(index)).append("/");
                File file = new File(builder.toString());
                if(!file.exists()) file.mkdirs();
                builder.append(name).append(imageIndex).append(".jpg");
                ImageIO.write(image, "jpg", new File(builder.toString()));
            } catch (IOException e) {
                ErrorHandler.writeFail(e);
            }
            ++imageIndex;
        }
        imageIndex = 1;
    }

    @Override
    protected List<String> getImageSrcs() {
        List<String> ret = new ArrayList<>();

        Connector linker = new SimpleLinker(viewUrl + items.get(index));
        JsonArray array = new JsonParser().parse(linker.read("utf-8")).getAsJsonObject().get("data").getAsJsonArray();
        for(int i = 0;i<array.size();++i){
            ret.add(array.get(i).getAsJsonObject().get("url").getAsString());
        }
        return ret;
    }
}
