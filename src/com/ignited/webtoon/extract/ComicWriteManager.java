package com.ignited.webtoon.extract;

import com.ignited.tools.connect.Linker;
import com.ignited.tools.connect.cookie.CookieSet;
import com.ignited.webtoon.indexer.FileIndexer;
import com.ignited.webtoon.util.Order;
import com.ignited.webtoon.indexer.TextIndexer;

import java.io.IOException;
import java.util.List;

public class ComicWriteManager {

    public static void setIndex(String path){
        try {
            FileIndexer indexer = new TextIndexer(path, Order.CREATED_ASCENDING);
            indexer.setIndex();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void executeOne(ComicFactory factory, String name, String path, int index){
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
        Extractor extractor = factory.extractor(info.getId());
        extractor.setPath(path);
        extractor.setIndex(index);
        extractor.extract();
    }

    public static void execute(ComicFactory factory, String name, String path){
        execute(factory, name, path, 0);
    }

    public static void execute(ComicFactory factory, String name, String path, int index){
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
        Extractor extractor = factory.extractor(info.getId());
        extractor.setPath(path);
        extractor.setIndex(index);
        while (extractor.hasNext()){
            extractor.extract();
        }
        try {
            FileIndexer indexer = new TextIndexer(path, Order.CREATED_ASCENDING);
            indexer.setIndex();
        } catch (IOException e) {
            // happens path is not dir; but never happens;
        }
    }

    public static void execute(ComicFactory factory , String name, String path, int index, CookieSet cookieSet){
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
        Extractor extractor = factory.extractor(info.getId());
        Linker.getInstance(null, extractor).setCookieSet(cookieSet);
        extractor.setPath(path);
        extractor.setIndex(index);
        while (extractor.hasNext()){
            extractor.extract();
        }
        try {
            FileIndexer indexer = new TextIndexer(path, Order.CREATED_ASCENDING);
            indexer.setIndex();
        } catch (IOException e) {
            // happens path is not dir; but never happens;
        }
    }

    public static void textExtract(ComicFactory factory, String path, String source, int index){
        Extractor extractor = factory.extractor(source);
        extractor.setPath(path);
        extractor.setIndex(index);
        while (extractor.hasNext()){
            extractor.extract();
        }
        try {
            FileIndexer indexer = new TextIndexer(path, Order.CREATED_ASCENDING);
            indexer.setIndex();
        } catch (IOException e) {
            // happens path is not dir; but never happens;
        }
    }
}
