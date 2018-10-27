package com.ignited.webtoon.extract;

import com.ignited.webtoon.indexer.FileIndexer;
import com.ignited.webtoon.indexer.order.Order;
import com.ignited.webtoon.indexer.TextIndexer;

import java.io.IOException;
import java.util.List;

public class ComicWriteManager {

    public static void setIndex(String path) throws IOException {
        FileIndexer indexer = new TextIndexer(path, Order.CREATED_ASCENDING);
        indexer.setIndex();
    }

    public static void executeOne(ComicFactory factory, String name, String path, int index) throws IOException {
        Finder finder = factory.finder();
        ComicInfo info = finder.findMatch(name);
        if(info == null){
            List<ComicInfo> infos = finder.findAllContains(name);
            if(infos.size() == 0){
                throw new IllegalArgumentException("Element not found");
            }else if(infos.size() == 1){
                info = infos.get(0);
            }else {
                for (ComicInfo i :infos) {
                    if(i.getTitle().indexOf(name) == 0){
                        info = i;
                        break;
                    }
                }
                if(info == null) info = infos.get(0);
            }
        }
        Downloader downloader = factory.downloader(info);
        downloader.setPath(path);
        downloader.download(index);
    }

    public static void execute(ComicFactory factory, String name, String path) throws IOException {
        execute(factory, name, path, 0);
    }

    public static void execute(ComicFactory factory, String name, String path, int index) throws IOException {
        Finder finder = factory.finder();
        ComicInfo info = finder.findMatch(name);
        if(info == null){
            List<ComicInfo> infos = finder.findAllContains(name);
            if(infos.size() == 0){
                throw new IllegalArgumentException("Element not found");
            }else if(infos.size() == 1){
                info = infos.get(0);
            }else {
                for (ComicInfo i :infos) {
                    if(i.getTitle().indexOf(name) == 0){
                        info = i;
                        break;
                    }
                }
                if(info == null) info = infos.get(0);
            }
        }
        Downloader downloader = factory.downloader(info);
        downloader.setPath(path);
        for(int i = 0;i<downloader.size();++i){
            downloader.download(i);
        }
        setIndex(path);
    }
        // todo
    /*
    public static void execute(ComicFactory factory , String name, String path, int index, CookieSet cookieSet) throws IOException {
        Finder finder = factory.finder();
        ComicInfo info = finder.findMatch(name);
        if(info == null){
            List<ComicInfo> infos = finder.findAllContains(name);
            if(infos.size() == 0){
                throw new IllegalArgumentException("Element not found");
            }else if(infos.size() == 1){
                info = infos.get(0);
            }else {
                for (ComicInfo i :infos) {
                    if(i.getTitle().indexOf(name) == 0){
                        info = i;
                        break;
                    }
                }
                if(info == null) info = infos.get(0);
            }
        }
        Downloader downloader = factory.downloader(info);
        Linker.getInstance(null, extractor).setCookieSet(cookieSet);
        extractor.setPath(path);
        setIndex(path);
    }
    */
}
