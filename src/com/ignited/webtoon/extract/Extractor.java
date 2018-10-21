package com.ignited.webtoon.extract;

import java.util.List;

public abstract class Extractor{

    protected int index;
    protected String path;

    public Extractor() {
        this.index = 0;
    }

    public Extractor(int start) {
        this.index = start;
    }

    public void setPath(String path){
        this.path = path;
    }

    public final void extract(){
        save(getImageSrcs());
        ++index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public abstract boolean hasNext();

    public abstract int size();

    protected abstract void save(List<String> srcs);

    protected abstract List<String> getImageSrcs();
}
