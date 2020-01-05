package com.ignited.webtoon.comp.daum.comic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ignited.webtoon.extract.comic.Cataloger;
import com.ignited.webtoon.extract.comic.ComicInfo;
import com.ignited.webtoon.util.ObjectMapperConfiguration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Daum comic cataloger.
 *
 * @author Ignited
 * @see com.ignited.webtoon.extract.comic.Cataloger
 */
public class DaumComicCataloger extends Cataloger {
    private final String urlFinished = "http://webtoon.daum.net/data/pc/webtoon/list_finished/?genre_id=";
    private final String urlBase = "http://webtoon.daum.net/data/pc/webtoon/list_serialized/";

    private String[] day;

    /**
     * Instantiates a new Daum comic cataloger.
     *
     */
    public DaumComicCataloger() {
        super();
    }

    /**
     * Instantiates a new Daum comic cataloger.
     *
     * @param maxTry the max try to connect and get elements
     * @param wait   the wait time in millis after failure
     */
    public DaumComicCataloger(int maxTry, int wait) {
        super(maxTry, wait);
    }

    @Override
    protected List<ComicInfo> deliver() throws IOException {
        List<ComicInfo> ret = new ArrayList<>();
        day = new String[]{
                "mon", "tue","wed","thu", "fri", "sat", "sun"
        };
        for (int index = 0; index<=day.length; ++index){

            ObjectMapper mapper = ObjectMapperConfiguration.getMapper();

            JsonNode node = mapper.readTree(new URL(urlFactory(index)))
                    .get("data");

            for(JsonNode item : node){
                ret.add(new ComicInfo(
                        item.get("nickname").asText(),
                        item.get("title").asText(),
                        "DAUM",
                        item.get("pcThumbnailImage").get("url").asText()
                ));
            }

        }
        return ret;
    }

    private String urlFactory(int i){
        if(i == 0){
            return urlFinished;
        }else {
            return urlBase + day[i-1];
        }

    }
}
