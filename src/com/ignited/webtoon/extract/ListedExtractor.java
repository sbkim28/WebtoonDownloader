package com.ignited.webtoon.extract;

import java.util.ArrayList;
import java.util.List;

public abstract class ListedExtractor extends Extractor {

    protected List<String> items;

    public ListedExtractor() {
        super();
    }

    public ListedExtractor(int start) {
        super(start);
    }

    public void setItems(List<String> items){
        this.items = new ArrayList<>(items);
    }

    @Override
    public boolean hasNext() {
        return items.size() > index;
    }

    @Override
    public int size() {
        return items.size();
    }
}
