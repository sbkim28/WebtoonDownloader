package com.ignited.webtoon.comp.naver.comic;

import com.ignited.tools.connect.Connector;
import com.ignited.tools.connect.adapter.DefaultLinkerAdapter;
import com.ignited.tools.connect.adapter.IndexedLinkerAdapter;
import com.ignited.tools.connect.Linker;
import com.ignited.tools.connect.SimpleLinker;
import com.ignited.webtoon.extract.Extractor;
import com.ignited.webtoon.util.ErrorHandler;
import com.ignited.webtoon.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class NaverComicExtractor extends Extractor{

    private final String listUrl = "https://comic.naver.com/webtoon/list.nhn";
    private final String detailUrl = "https://comic.naver.com/webtoon/detail.nhn";
    protected String id;
    private int maxIndex;

    private int imageIndex;

    protected String title;
    private String name = "img";

    public NaverComicExtractor(String id) {
        this(id, 0);
    }

    public NaverComicExtractor(String id, int start) {
        super(start);
        this.id = id;
        imageIndex = 1;
        setMaxIndex();
    }

    public void setName(String name) {
        this.name = name;
    }

    private void setMaxIndex(){
        Connector linker = new SimpleLinker(listUrl + "?titleId=" + id);

        String s =linker.read("utf-8");
        s = s.substring(s.indexOf("class=\"viewList\""), s.indexOf("class=\"paginate\""));
        s = s.substring(s.indexOf("</thead>"));
        s = s.substring(s.indexOf("<tr>"));
        maxIndex = Integer.parseInt(s.substring(s.indexOf("&no=") + 4, s.indexOf("&weekday=")));
    }

    @Override
    public boolean hasNext() {
        return index + 1 <= maxIndex;
    }

    @Override
    public int size() {
        return maxIndex;
    }

    @Override
    protected void save(List<String> srcs) {
        IndexedLinkerAdapter adapter = new IndexedLinkerAdapter(srcs){
            @Override
            protected void initialize(URLConnection connection) {
                connection.setRequestProperty("referer", "http://m.naver.com");
            }
        };
        Connector linker = Linker.getInstance(adapter, this);
        while (adapter.hasNext()){
            try {
                linker.connect();
                BufferedImage image = ImageIO.read(linker.getInputstream());
                if(path == null) return;
                StringBuilder builder = new StringBuilder().append(path).append("/").append(title).append("/");

                while (builder.charAt(builder.length() - 2) == '.'){
                    builder.deleteCharAt(builder.length() - 2);
                }

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
        Connector linker = Linker.getInstance(new DefaultLinkerAdapter(detailUrl + "?titleId=" + id + "&no=" + (index + 1)), this);

        String s = linker.read("utf-8");

        String[] items = s.substring(s.indexOf("class=\"wt_viewer\""), s.indexOf("id=\"ppl_image_section\"")).split("src=\"");
        s = s.substring(s.indexOf("class=\"tit_area\""), s.indexOf("class=\"btn_area\""));
        title = StringUtils.factor(s.substring(s.indexOf("<h3>") +4, s.indexOf("</h3>")));
        for (String item : items){
            int i;
            if((i = item.indexOf("title=\"")) == -1){
                continue;
            }
            ret.add(item.substring(0, i-2));
        }
        return ret;
    }
}
