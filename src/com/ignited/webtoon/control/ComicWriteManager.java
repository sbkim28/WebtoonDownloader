package com.ignited.webtoon.control;

import com.ignited.webtoon.extract.comic.*;
import com.ignited.webtoon.extract.comic.e.ComicException;
import com.ignited.webtoon.extract.comic.e.ComicNotFoundException;
import com.ignited.webtoon.indexer.FileIndexer;
import com.ignited.webtoon.indexer.order.Order;
import com.ignited.webtoon.indexer.TextIndexer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * ComicWriteManager
 * Class dealing with downloading webtoons
 *
 * @author Ignited
 */
public class ComicWriteManager {

    /**
     * Make an index file at the given location.
     *
     * @param path the location where an index file will be created.
     * @throws IOException the io exception
     */
    public static void setIndex(String path) throws IOException {
        FileIndexer indexer = new TextIndexer(path, Order.CREATED_ASCENDING);
        indexer.setIndex();
    }

    /**
     * Download one chapter of the comic
     *
     * @param factory the ComicFactory
     * @param name    the name of the webtoon
     * @param path    the location where the webtoon will be saved
     * @param index   the number of the chapter of the webtoon
     * @throws ComicException when it failed to download
     */
    public static void executeOne(ComicFactory factory, String name, String path, int index) throws ComicException {
        Downloader downloader = factory.downloader(findInfo(factory, name), path);
        downloader.setPath(path);

        downloader.download(index);
    }

    /**
     * Donwload entire chapter of the comic, creating an index file
     *
     * @param factory the ComicFactory
     * @param name    the name of the webtoon
     * @param path    the location where the webtoon will be saved
     * @throws IOException when it failed to download
     */
    public static void execute(ComicFactory factory, String name, String path) throws IOException, ComicException {
        execute(factory, name, path, 0);
    }

    /**
     * Donwload some chapters of the comic, creating an index file
     *
     * @param factory the ComicFactory
     * @param name    the name of the webtoon
     * @param path    the location where the webtoon will be saved
     * @param index   the start number of the chapter of the webtoon
     * @throws IOException when it failed to download
     */
    public static void execute(ComicFactory factory, String name, String path, int index) throws IOException, ComicException {
        Downloader downloader = factory.downloader(findInfo(factory,name), path);
        for(int i = index;i<downloader.size();++i){
            downloader.download(i);
        }
        setIndex(path);
    }

    /**
     * Donwload some chapters of the comic with cookies, creating an index file
     *
     * @param factory the ComicFactory
     * @param name    the name of the webtoon
     * @param path    the location where the webtoon will be saved
     * @param index   the start number of the chapter of the webtoon
     * @param cookieSet the cookieset
     * @throws IOException when it failed to download
     */
    public static void execute(ComicFactory factory , String name, String path, int index, Map<String, String> cookieSet) throws IOException, ComicException {
        Downloader downloader = factory.downloader(findInfo(factory, name), path);
        downloader.setPath(path);
        if(downloader instanceof CookieSettable){
            ((CookieSettable) downloader).setCookies(cookieSet);
        }
        for(int i = index;i<downloader.size();++i){
            downloader.download(i);
        }
        setIndex(path);
    }

    private static ComicInfo findInfo(ComicFactory factory, String name) throws ComicException {
        List<ComicInfo> infos = factory.cataloger().catalog().getList();
        List<ComicInfo> containsKeywords = new ArrayList<>();
        for (ComicInfo info : infos){
            if(info.getTitle().equals(name)){
                return info;
            }else if(info.getTitle().contains(name)){
                containsKeywords.add(info);
            }
        }
        if(containsKeywords.isEmpty()){
            throw new ComicNotFoundException(factory.toString(), name);
        }
        return containsKeywords.get(0);
    }


}
