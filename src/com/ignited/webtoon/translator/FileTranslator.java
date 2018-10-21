package com.ignited.webtoon.translator;

import java.io.File;

public abstract class FileTranslator implements Translator<File>{

    protected File[] files;
    protected File writeOn;

    public FileTranslator(File[] files, File writeOn) {
        this.files = files;
        this.writeOn = writeOn;
    }

    public int length(){
        return files.length;
    }
}
