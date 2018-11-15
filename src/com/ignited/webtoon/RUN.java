package com.ignited.webtoon;

import com.ignited.webtoon.comp.ComicTypeFactory;
import com.ignited.webtoon.extract.ComicWriteManager;
import com.ignited.webtoon.view.HTMLViewer;

import java.io.IOException;

public class RUN {


    public static void main(String[] args) {

        if (args == null || args.length == 0) {
            printInfo();
        }
        try {
            if (args[0].equals("-d") && (args.length == 4 || args.length == 5)) {
                if (args.length == 4) {
                    downlaod(args[1], args[2], args[3]);
                } else {
                    downlaod(args[1], args[2], args[3], args[4]);
                }
            } else if (args[0].equals("-r") && args.length == 3) {
                read(args[1], args[2]);
            } else if(args[0].equals("-rf") && args.length == 3){
                readFile(args[1], args[2]);
            } else if (args[0].equals("-do") && args.length == 5) {
                downlaodOnly(args[1], args[2], args[3], args[4]);
            } else if (args[0].equals("-i") && args.length == 2) {
                index(args[1]);
            } else {
                printInfo();
            }
        }catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Invalid Parameter");
            printInfo();
        }
    }

    public static void printInfo(){
        System.out.println("-d : download (-d <comictype[daum, naver, lezhin]> <name> <path> (<index>))");
        System.out.println("-do : download one chapter (-do <comictype[daum, naver, lezhin]> <name> <path> <index>");
        System.out.println("-i : create index file (-i <path>");
        System.out.println("-r : read (-r <path> <page>)");
        System.out.println("-rf : read - searching files by filename (-r <path> <filename>)");
        System.exit(1);
    }


    public static void downlaod(String type, String name, String path) throws IOException {
        if(type.equalsIgnoreCase("naver")) {
            ComicWriteManager.execute(ComicTypeFactory.NAVER, name, path);
        }else if (type.equalsIgnoreCase("daum")) {
            ComicWriteManager.execute(ComicTypeFactory.DAUM, name, path);
        }else if(type.equalsIgnoreCase("lezhin")) {
            ComicWriteManager.execute(ComicTypeFactory.LEZHIN, name, path);
        }else{
            System.out.println("Not supported Comic type");

        }
    }

    public static void downlaod(String type, String name, String path, String index) throws IOException {
        if(type.equalsIgnoreCase("naver")) {
            ComicWriteManager.execute(ComicTypeFactory.NAVER, name, path, Integer.parseInt(index));
        }else if (type.equalsIgnoreCase("daum")) {
            ComicWriteManager.execute(ComicTypeFactory.DAUM, name, path, Integer.parseInt(index));
        }else if(type.equalsIgnoreCase("lezhin")) {
            ComicWriteManager.execute(ComicTypeFactory.LEZHIN, name, path, Integer.parseInt(index));
        }else {
            System.out.println("Not supported Comic type");
        }
    }

    public static void downlaodOnly(String type, String name, String path, String index) throws IOException {
        if(type.equalsIgnoreCase("naver")) {
            ComicWriteManager.executeOne(ComicTypeFactory.NAVER, name, path, Integer.parseInt(index));
        }else if (type.equalsIgnoreCase("daum")) {
            ComicWriteManager.executeOne(ComicTypeFactory.DAUM, name, path, Integer.parseInt(index));
        }else if(type.equalsIgnoreCase("lezhin")) {
            ComicWriteManager.executeOne(ComicTypeFactory.LEZHIN, name, path, Integer.parseInt(index));
        }else {
            System.out.println("Not supported Comic type");
        }
    }

    public static void index(String path) throws IOException {
        ComicWriteManager.setIndex(path);
    }

    public static void read(String path, String page){
        try {
            HTMLViewer viewer = new HTMLViewer(path);
            viewer.view(Integer.parseInt(page));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void readFile(String path, String file){
        try {
            HTMLViewer viewer = new HTMLViewer(path);
            viewer.view(file);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}