package com.ignited.webtoon.indexer.order;


/**
 * The interface Sortable.
 *
 * @author Ignited
 * @param <T> the type parameter
 */
public interface Sortable<T> {
    /**
     * Sort an array of items
     * Sort  following the order,
     * @param items the items to sort
     */
    void sort(T[] items);
}
