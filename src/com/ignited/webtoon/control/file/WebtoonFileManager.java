package com.ignited.webtoon.control.file;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ignited.webtoon.comp.ComicTypeFactory;
import com.ignited.webtoon.control.ComicFindStrategy;
import com.ignited.webtoon.extract.CookieSettable;
import com.ignited.webtoon.extract.comic.*;
import com.ignited.webtoon.extract.comic.e.*;
import com.ignited.webtoon.indexer.FileLoader;
import com.ignited.webtoon.indexer.TextIndexedLoader;
import com.ignited.webtoon.translator.HTMLJsonMultiTranslator;
import com.ignited.webtoon.translator.HTMLJsonTranslator;
import com.ignited.webtoon.translator.HTMLTranslator;
import com.ignited.webtoon.util.ObjectMapperConfiguration;
import com.ignited.webtoon.view.ImageCollector;

import java.awt.Desktop;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class WebtoonFileManager {

    private static final Logger LOGGER = Logger.getLogger(WebtoonFileManager.class.getName());

    private String path;

    private File file;

    private List<ComicInfo> webtoons;
    private Map<String, String> cookies;


    public WebtoonFileManager(String path) {
        this(new File(path));
    }

    public WebtoonFileManager(File file) {
        if(file.isFile()){
            throw new IllegalArgumentException("Not a directory");
        }
        this.file = file;
        this.path = file.getPath();
        webtoons = new ArrayList<>();
    }

    public void updateWebtoon(){
        updateWebtoon(ComicTypeFactory.values());
    }

    public void updateWebtoon(ComicFactory... cfs){
        for (ComicFactory cf : cfs){
            Cataloger c = cf.cataloger();
            String name = c.getClass().getSimpleName();
            try {
                c.catalog(true);
                webtoons.addAll(c.getList());
                LOGGER.info(name);
            } catch (ComicCatalogException e) {
                e.printStackTrace();
                LOGGER.warning("Update failed.");
            }
        }
    }

    public void create(String name) throws ComicException{
        ComicInfo info = findOne(name);
        LOGGER.info(info.toString());
        download(info,  name.replace(' ', '_') + ".json", Integer.MAX_VALUE);
    }

    public void create(String name, int size) throws ComicException {
        ComicInfo info = findOne(name);
        LOGGER.info(info.toString());
        download(info,  name.replace(' ', '_') + ".json", size);
    }

    public void create(String name, int size, String path) throws ComicException {
        ComicInfo info = findOne(name);
        LOGGER.info(info.toString());
        download(info, path, size);
    }

    public void create(String name, int size, String path, ComicFindStrategy cfs) throws ComicException {
        ComicInfo info = cfs.find(webtoons);
        LOGGER.info(info.toString());
        download(info, path, size);
    }

    private void download(ComicInfo info, String path, int size) throws ComicListInitException, ComicDownloadException {

        ComicJsonSaver cjs = new ComicJsonSaver(info);
        Downloader d = ComicTypeFactory.valueOf(info.getType()).downloader(info, this.path + "/" + path, cjs);
        if(d instanceof CookieSettable && cookies != null){
            ((CookieSettable) d).setCookies(cookies);
        }

        int s = Math.min(size, d.size());

        for (int i = 0; i<s; ++i){
            try {
                d.download(i);
                LOGGER.info("Download done : " + i);
            }catch (ComicDownloadException e){
                e.printStackTrace();
            }
        }

        try {
            cjs.write();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ComicDownloadException("Writing failed.", e);
        }

    }

    public void reform(File file) throws IOException {
        reform(file, file.getName());
    }

    public void reform(File file, String name) throws IOException {
        FileLoader fl = new TextIndexedLoader(file);
        File[] fs = fl.read();

        ObjectMapper mapper = ObjectMapperConfiguration.getMapper();

        ObjectNode node = mapper.createObjectNode();
        node.put("title", name);
        ArrayNode webtoons = node.putArray("webtoons");

//        JsonObject object = new JsonObject();
//        JsonArray webtoons = new JsonArray();
//        object.add("webtoons", webtoons);
//        object.addProperty("title", name);

        try {
            ComicInfo info = findOne(name);
            node.put("type", info.getType());
            node.put("id", info.getId());

            //object.addProperty("type", info.getType());
            //object.addProperty("id", info.getId());

        }catch (ComicNotFoundException e){
            e.printStackTrace();
            LOGGER.info("Cannot find webtoon");

            node.put("type", file.getParentFile().getName().toUpperCase());
            node.put("id", "");

            //object.addProperty("type", file.getParentFile().getName().toUpperCase());
            //object.addProperty("id", "");

        }
        ImageCollector ic = new ImageCollector(fl);

        for (int i = 0; i<fs.length;++i){

            ObjectNode webtoon = webtoons.addObject();
            webtoon.put("title", fs[i].getName());
            webtoon.put("no", i+1);
            ArrayNode imgs = webtoon.putArray("imgs");

//            JsonObject webtoon = new JsonObject();
//            webtoons.add(webtoon);
//            webtoon.addProperty("title", fs[i].getName());
//            webtoon.addProperty("no", i + 1);
//            JsonArray imgs = new JsonArray();
//            webtoon.add("imgs", imgs);

            int seq = 0;
            for (File f : ic.collect(i)){
                ObjectNode img = imgs.addObject();
                img.put("seq", ++seq);

                //JsonObject img = new JsonObject();
                //img.addProperty("seq", ++seq);

                String n = f.getName();
                n = n.substring(n.lastIndexOf('.'));

                img.put("type", "image/"+n);
                //img.addProperty("type", "image/" + n);

                ByteArrayOutputStream baos;
                baos = new ByteArrayOutputStream();
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
                int len;
                byte[] buf = new byte[1024];
                while ((len = bis.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                img.put("data", new String(Base64.getEncoder().encode(baos.toByteArray())));
                //img.addProperty("data", new String(Base64.getEncoder().encode(baos.toByteArray())));
                imgs.add(img);
            }
        }

        File w = new File(path + "/" +name.replace(' ', '_') + ".json");
        w.getParentFile().mkdirs();
        JsonGenerator jgen = mapper.getFactory().createGenerator(w, JsonEncoding.UTF8);
        mapper.writeTree(jgen, node);
    }

    public void read(String name, int index) throws IOException {
        File f = findFile(name);
        if(f == null) throw new FileNotFoundException("Finding file failed. (name=" + name + ")");

        //FileReader fr = new FileReader(f);

        File _tmp = File.createTempFile("temp_", ".html");
        _tmp.deleteOnExit();

        ObjectMapper mapper = ObjectMapperConfiguration.getMapper();
        JsonNode node;

        //JsonObject object;
        try {
            node = mapper.readTree(f).get("webtoons")
                    .get(index);
//            object = new JsonParser().parse(fr).getAsJsonObject().get("webtoons")
//                    .getAsJsonArray().get(index).getAsJsonObject();
        }catch (/*JsonIOException | JsonSyntaxException |*/ ArrayIndexOutOfBoundsException e){
            throw new IOException("Getting json object failed. (name=" + name + ", file=" + f.getPath() + ", index=" + index + ")" , e);
        }

        HTMLTranslator ht = new HTMLJsonTranslator(node, _tmp);
        ht.write();

        Desktop.getDesktop().browse(_tmp.toURI());
    }

    public void read(String name) throws IOException{
        File f = findFile(name);
        if(f == null) throw new FileNotFoundException("Finding file failed. (name=" + name + ")");

        FileReader fr = new FileReader(f);

        File _tmp = File.createTempFile("temp_", ".html");
        _tmp.deleteOnExit();

        ObjectMapper mapper = ObjectMapperConfiguration.getMapper();
        JsonNode node;
        node = mapper.readTree(f).get("webtoons");

        HTMLTranslator ht = new HTMLJsonMultiTranslator(node, _tmp);

        ht.write();

        Desktop.getDesktop().browse(_tmp.toURI());
    }

    public void read(String name, int start, int end) throws IOException{
        File f = findFile(name);
        if(f == null) throw new FileNotFoundException("Finding file failed. (name=" + name + ")");

        FileReader fr = new FileReader(f);

        File _tmp = File.createTempFile("temp_", ".html");
        _tmp.deleteOnExit();

        ObjectMapper mapper = ObjectMapperConfiguration.getMapper();
        JsonNode node;
        node = mapper.readTree(f).get("webtoons");

        HTMLJsonMultiTranslator ht = new HTMLJsonMultiTranslator(node, _tmp);
        ht.setStart(start);
        ht.setEnd(end);
        ht.write();

        Desktop.getDesktop().browse(_tmp.toURI());
    }

    public boolean update(String name) throws IOException, ComicException {
        return update(name, Integer.MAX_VALUE);
    }

    public boolean update(String name, int size) throws IOException, ComicException{
        File f = findFile(name);
        if(f == null) throw new FileNotFoundException("Finding file failed. (name=" + name + ")");

        FileReader fr = new FileReader(f);

        ObjectMapper mapper = ObjectMapperConfiguration.getMapper();
        JsonNode object = mapper.readTree(f);

        ComicInfo info = findOne(object.get("title").asText());
        LOGGER.info(info.toString());
        int s = object.get("webtoons").size();

        ComicJsonSaver cjs = new ComicJsonSaver((ObjectNode) object);

        Downloader d = ComicTypeFactory.valueOf(info.getType()).downloader(info, f.getPath(), cjs);
        if(d instanceof CookieSettable){
            ((CookieSettable) d).setCookies(cookies);
        }
        int max = Math.min(d.size(), size);
        boolean flag = max > s;


        for (int i = s; i<max; ++i){
            try {
                d.download(i);
                LOGGER.info("Download done : " + i);
            }catch (ComicDownloadException e){
                e.printStackTrace();
            }
        }

        if(flag) {
            try {
                cjs.write();
            } catch (IOException e) {
                e.printStackTrace();
                throw new ComicDownloadException("Writing failed.", e);
            }
        }
        return flag;
    }

    public ComicInfo findOne(String name) throws ComicNotFoundException {
        for (ComicInfo info : webtoons){
            if(info.getTitle().equals(name)){
                return info;
            }
        }
        LOGGER.info(webtoons.toString());
        throw new ComicNotFoundException("Cannot find comic (name=" + name + ")");
    }

    public File findFile(String name){
        File[] child = this.file.listFiles();
        if(child == null) return null;
        for (File f : child){
            if(!f.isFile()) continue;
            if(f.getName().replace('_', ' ').equals(name + ".json")){
                return f;
            }
        }
        return null;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }
}
