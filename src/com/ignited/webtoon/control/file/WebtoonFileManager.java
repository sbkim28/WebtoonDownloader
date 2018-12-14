package com.ignited.webtoon.control.file;

import com.google.gson.*;
import com.ignited.webtoon.comp.ComicTypeFactory;
import com.ignited.webtoon.control.ComicFindStrategy;
import com.ignited.webtoon.extract.comic.*;
import com.ignited.webtoon.extract.comic.e.*;
import com.ignited.webtoon.indexer.FileLoader;
import com.ignited.webtoon.indexer.TextIndexedLoader;
import com.ignited.webtoon.translator.HTMLJsonTranslator;
import com.ignited.webtoon.translator.HTMLTranslator;
import com.ignited.webtoon.view.ImageCollector;

import java.awt.Desktop;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

public class WebtoonFileManager {

    private static final Logger LOGGER = Logger.getLogger(WebtoonFileManager.class.getName());

    private String path;

    private File file;

    private List<ComicInfo> webtoons;

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
        download(info,  name.replace(' ', '_') + ".json", Integer.MAX_VALUE);
    }

    public void create(String name, int size) throws ComicException {
        ComicInfo info = findOne(name);
        download(info,  name.replace(' ', '_') + ".json", size);
    }

    public void create(String name, int size, String path) throws ComicException {
        ComicInfo info = findOne(name);
        download(info, path, size);
    }

    public void create(String name, int size, String path, ComicFindStrategy cfs) throws ComicException {
        ComicInfo info = cfs.find(webtoons);
        download(info, path, size);
    }

    private void download(ComicInfo info, String path, int size) throws ComicListInitException, ComicDownloadException {

        ComicJsonSaver cjs = new ComicJsonSaver(info);
        Downloader d = ComicTypeFactory.valueOf(info.getType()).downloader(info, this.path + "/" + path, cjs);

        int s = Math.min(size, d.size());

        for (int i = 0; i<s; ++i){
            try {
                d.download(i);
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

        JsonObject object = new JsonObject();
        JsonArray webtoons = new JsonArray();

        object.add("webtoons", webtoons);
        object.addProperty("title", name);
        try {
            ComicInfo info = findOne(name);
            object.addProperty("type", info.getType());
            object.addProperty("id", info.getId());
        }catch (ComicNotFoundException e){
            e.printStackTrace();
            LOGGER.info("Cannot find webtoon");

            object.addProperty("type", file.getParentFile().getName().toUpperCase());
            object.addProperty("id", "");

        }
        ImageCollector ic = new ImageCollector(fl);

        for (int i = 0; i<fs.length;++i){
            JsonObject webtoon = new JsonObject();
            webtoons.add(webtoon);

            webtoon.addProperty("title", fs[i].getName());
            webtoon.addProperty("no", i + 1);

            JsonArray imgs = new JsonArray();
            webtoon.add("imgs", imgs);
            int seq = 0;
            for (File f : ic.collect(i)){
                JsonObject img = new JsonObject();
                img.addProperty("seq", ++seq);
                String n = f.getName();
                n = n.substring(n.lastIndexOf('.'));
                img.addProperty("type", "image/" + n);
                ByteArrayOutputStream baos;
                baos = new ByteArrayOutputStream();
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
                int len;
                byte[] buf = new byte[1024];
                while ((len = bis.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                img.addProperty("data", new String(Base64.getEncoder().encode(baos.toByteArray())));
                imgs.add(img);
            }
        }

        File w = new File(path + "/" +name.replace(' ', '_') + ".json");
        w.getParentFile().mkdirs();
        FileWriter fw = new FileWriter(w);
        new Gson().toJson(object, fw);
        fw.close();
    }

    public void read(String name, int index) throws IOException {
        File f = findFile(name);
        if(f == null) throw new FileNotFoundException("Finding file failed. (name=" + name + ")");

        FileReader fr = new FileReader(f);

        File _tmp = File.createTempFile("temp_", ".html");
        _tmp.deleteOnExit();
        JsonObject object;
        try {
            object = new JsonParser().parse(fr).getAsJsonObject().get("webtoons")
                    .getAsJsonArray().get(index).getAsJsonObject();
        }catch (JsonIOException | JsonSyntaxException | ArrayIndexOutOfBoundsException e){
            throw new IOException("Getting json object failed. (name=" + name + ", file=" + f.getPath() + ", index=" + index + ")" , e);
        }


        HTMLTranslator ht = new HTMLJsonTranslator(object, _tmp);
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
        JsonObject object;
        try {
            object = new JsonParser().parse(fr).getAsJsonObject();
        }catch (JsonSyntaxException | JsonIOException e){
            throw new IOException("Getting json object failed. (name=" + name + ", file=" + f.getPath() + ")", e);
        }

        ComicInfo info = findOne(object.get("title").getAsString());
        int s = object.get("webtoons").getAsJsonArray().size();
        ComicJsonSaver cjs = new ComicJsonSaver(object);

        Downloader d = ComicTypeFactory.valueOf(info.getType()).downloader(info, f.getPath(), cjs);

        int max = Math.min(d.size(), size);
        boolean flag = max > s;


        for (int i = s; i<max; ++i){
            try {
                d.download(i);
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

}
