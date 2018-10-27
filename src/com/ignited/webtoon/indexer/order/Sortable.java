package com.ignited.webtoon.indexer.order;

public interface Sortable<T> {
    T[] sort(T[] items);
}
