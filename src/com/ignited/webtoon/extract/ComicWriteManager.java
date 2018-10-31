package com.ignited.webtoon.extract;

import com.ignited.webtoon.extract.comic.*;
import com.ignited.webtoon.indexer.FileIndexer;
import com.ignited.webtoon.indexer.order.Order;
import com.ignited.webtoon.indexer.TextIndexer;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * ComicWriteManager
 * Class dealing with downloading webtoons
 *
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
     * @throws IOException when it failed to download
     */
    public static void executeOne(ComicFactory factory, String name, String path, int index) throws IOException {
        Finder finder = factory.finder();
        Downloader downloader = factory.downloader(findInfo(finder, name));
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
    public static void execute(ComicFactory factory, String name, String path) throws IOException {
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
    public static void execute(ComicFactory factory, String name, String path, int index) throws IOException {
        Finder finder = factory.finder();
        Downloader downloader = factory.downloader(findInfo(finder,name));
        downloader.setPath(path);
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
    public static void execute(ComicFactory factory , String name, String path, int index, Map<String, String> cookieSet) throws IOException {
        Finder finder = factory.finder();
        Downloader downloader = factory.downloader(findInfo(finder, name));
        downloader.setPath(path);
        if(downloader instanceof CookieSettable){
            ((CookieSettable) downloader).setCookies(cookieSet);
        }
        for(int i = index;i<downloader.size();++i){
            downloader.download(i);
        }
        setIndex(path);
    }

    private static ComicInfo findInfo(Finder finder, String name){
        ComicInfo info = finder.findMatch(name);
        if(info == null) {
            List<ComicInfo> infos = finder.findAllContains(name);
            if (infos.size() == 0) {
                throw new IllegalArgumentException("Element not found");
            } else if (infos.size() == 1) {
                info = infos.get(0);
            } else {
                for (ComicInfo i : infos) {
                    if (i.getTitle().indexOf(name) == 0) {
                        info = i;
                        break;
                    }
                }
                if (info == null) info = infos.get(0);
            }
        }
        return info;
    }
}
