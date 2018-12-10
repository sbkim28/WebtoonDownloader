package com.ignited.webtoon.extract.comic;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.ignited.webtoon.extract.comic.e.ComicDownloadException;

import java.io.*;
import java.net.URLConnection;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

public class ComicJsonSaver extends ComicSaver {

    private static final Logger LOGGER = Logger.getLogger(ComicJsonSaver.class.getName());

    private JsonObject object;
    private JsonArray webtoons;



    public ComicJsonSaver(ComicInfo info) {
        super();
        object = new JsonObject();
        webtoons = new JsonArray();
        object.add("webtoons", webtoons);
        object.addProperty("title", info.getTitle());
        object.addProperty("type", info.getType());
        object.addProperty("id", info.getId());
    }

    public ComicJsonSaver(JsonObject object) {
        super();
        this.object = object;
        this.webtoons = object.get("webtoons").getAsJsonArray();
    }

    @Override
    public void save(List<String> src, String title) throws ComicDownloadException {
        int index = 0;
        boolean ex = false;

        JsonObject webtoon = new JsonObject();
        webtoons.add(webtoon);

        webtoon.addProperty("title", title);
        webtoon.addProperty("no", webtoons.size());
        JsonArray imgs = new JsonArray();
        webtoon.add("imgs", imgs);
        for (String s : src){
            JsonObject img = new JsonObject();
            img.addProperty("seq", ++index);
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
            img.addProperty("type", type);

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

            img.addProperty("data", new String(Base64.getEncoder().encode(baos.toByteArray())));

            imgs.add(img);

        }

        if(ex) throw new ComicDownloadException("Partially downloaded");
    }

    public void write() throws IOException {
        File file = new File(getPath());
        file.getParentFile().mkdirs();
        FileWriter fw = new FileWriter(file);
        new Gson().toJson(object, fw);
        fw.close();


    }
}
