package com.ignited.webtoon.comp.kakao.comp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ignited.webtoon.extract.comic.Cataloger;
import com.ignited.webtoon.extract.comic.ComicInfo;
import com.ignited.webtoon.util.ObjectMapperConfiguration;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Kakao comic cataloger.
 *
 * @author Ignited
 * @see com.ignited.webtoon.extract.comic.Cataloger
 */
public class KakaoComicCataloger extends Cataloger {

    private static final String list_url = "https://api2-page.kakao.com/api/v6/store/section_container/list?agent=web&category=10&subcategory=1000&day=0";
    private static final String thumb_url = "https://dn-img-page.kakao.com/download/resource?kid=";

    /**
     * Instantiates a new Kakao comic cataloger.
     */
    public KakaoComicCataloger() { }

    /**
     * Instantiates a new Kakao comic cataloger.
     *
     * @param maxTry the max try to connect and get elements
     * @param wait   the wait time in millis after failure
     */
    public KakaoComicCataloger(int maxTry, int wait) {
        super(maxTry, wait);
    }

    @Override
    protected List<ComicInfo> deliver() throws IOException {

        List<ComicInfo> infos = new ArrayList<>();

        ObjectMapper mapper = ObjectMapperConfiguration.getMapper();
        JsonNode arr = mapper.readTree(new URL(list_url)).get("section_containers")
                .get(0).get("section_series").get(0).get("list");

        for (JsonNode node : arr){
            infos.add(new ComicInfo(
                    node.get("series_id").asText(),
                    node.get("title").asText(),
                    "KAKAO",
                    thumb_url + node.get("image").asText()
            ));
        }
        return infos;

    }
}
