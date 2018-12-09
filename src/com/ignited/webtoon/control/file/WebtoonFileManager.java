package com.ignited.webtoon.control.file;

import com.ignited.webtoon.comp.ComicTypeFactory;
import com.ignited.webtoon.control.ComicFindStrategy;
import com.ignited.webtoon.extract.comic.Cataloger;
import com.ignited.webtoon.extract.comic.ComicFactory;
import com.ignited.webtoon.extract.comic.ComicInfo;
import com.ignited.webtoon.extract.comic.Downloader;
import com.ignited.webtoon.extract.comic.e.ComicCatalogException;
import com.ignited.webtoon.extract.comic.e.ComicDownloadException;
import com.ignited.webtoon.extract.comic.e.ComicException;
import com.ignited.webtoon.extract.comic.e.ComicNotFoundException;
import com.ignited.webtoon.indexer.FileIndexer;
import com.ignited.webtoon.indexer.TextIndexer;

import java.io.File;
import java.util.ArrayList;
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
        String type = info.getType();
        LOGGER.info("Type : "+ type);
        ComicFactory cf = ComicTypeFactory.valueOf(info.getType());

        Downloader d = cf.downloader(info);

        int size = d.size();
        LOGGER.info("Size : "+ size);

        String p = path + "/" + type.toLowerCase() + "/" + name;
        d.setPath(p);
        LOGGER.info(p);

        download(d);
    }

    public void create(String name, int size) throws ComicException {
        ComicInfo info = findOne(name);
    }

    public void create(String name, int size, String path) throws ComicException {

    }

    public void create(String name, int size, String path, ComicFindStrategy cfs){

    }

    private void download(Downloader d){
        int size = d.size();
        for (int i = 0; i<size; ++i){
            try {
                d.download(i);
            }catch (ComicDownloadException e){
                e.printStackTrace();
            }
        }
    }

    public ComicInfo findOne(String name) throws ComicNotFoundException {
        for (ComicInfo info : webtoons){
            if(info.getTitle().equals(name)){
                return info;
            }
        }
        throw new ComicNotFoundException("Cannot find comic : " + name);
    }

    public ComicInfo find(ComicFindStrategy fs) throws ComicNotFoundException{
        return fs.find(webtoons);
    }

}
