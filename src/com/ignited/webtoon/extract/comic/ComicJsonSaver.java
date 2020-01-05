package com.ignited.webtoon.extract.comic;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ignited.webtoon.extract.comic.e.ComicDownloadException;
import com.ignited.webtoon.util.ObjectMapperConfiguration;

import java.io.*;
import java.net.URLConnection;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

public class ComicJsonSaver extends ComicSaver {

    private static final Logger LOGGER = Logger.getLogger(ComicJsonSaver.class.getName());

    private ObjectNode object;
    private ArrayNode webtoons;



    public ComicJsonSaver(ComicInfo info) {
        super();
        ObjectMapper mapper = ObjectMapperConfiguration.getMapper();
        object = mapper.createObjectNode();
        webtoons = object.putArray("webtoons");
        object.put("title", info.getTitle());
        object.put("type", info.getType());
        object.put("id", info.getId());
    }

    public ComicJsonSaver(ObjectNode object) {
        super();
        this.object = object;
        this.webtoons = (ArrayNode) object.get("webtoons");
    }

    @Override
    public void save(List<String> src, String title) throws ComicDownloadException {
        int index = 0;
        boolean ex = false;

        ObjectNode webtoon = webtoons.addObject();

        webtoon.put("title", title);
        webtoon.put("no", webtoons.size());
        ArrayNode imgs = webtoon.putArray("imgs");
        for (String s : src){
            ObjectNode img = imgs.addObject();
            img.put("seq", ++index);
            URLConnection con;
            try {
                con = build(s);
            }catch (IOException e) {
                e.printStackTrace();
                LOGGER.warning("Connection build failed. (src=" + s + ", ubs=" + (getUbs() == null) + ")");
                ex = true;
                continue;
            }
            String type = con.getContentType();
            img.put("type", type);

            ByteArrayOutputStream baos;
            try {

                BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
                baos = new ByteArrayOutputStream();
                int len;
                byte[] buf = new byte[1024];
                while ((len = bis.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
                ex = true;
                LOGGER.warning("Reading failed. (type=" + type + ", src= " + s + ")");
                continue;
            }

            img.put("data", new String(Base64.getEncoder().encode(baos.toByteArray())));
        }

        if(ex) throw new ComicDownloadException("Partially downloaded");
    }

    public void write() throws IOException {
        File file = new File(getPath());
        file.getParentFile().mkdirs();
        ObjectMapper mapper = ObjectMapperConfiguration.getMapper();
        mapper.writeTree(mapper.getFactory().createGenerator(file, JsonEncoding.UTF8), object);
    }
}
