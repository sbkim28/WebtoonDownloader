package com.ignited.webtoon.indexer.order;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;


/**
 * The enum Order.
 *
 * The default File Orders.
 *
 * @author Ignited
 * @see com.ignited.webtoon.indexer.order.Sortable
 */
public enum Order implements Sortable<File>{
    /**
     * The Name ascending.
     */
    NAME_ASCENDING{
        @Override
        public File[] sort(File[] items) {
            return Arrays.stream(items).sorted(Comparator.comparing(File::getName)).toArray(File[]::new);
        }
    },
    /**
     * The Name descending.
     */
    NAME_DESCENDING{
        @Override
        public File[] sort(File[] items) {
            return Arrays.stream(items).sorted((o1, o2) -> o2.getName().compareTo(o1.getName())).toArray(File[]::new);
        }
    },
    /**
     * The Created ascending.
     */
    CREATED_ASCENDING{
        @Override
        public File[] sort(File[] items) {
            return Arrays.stream(items).sorted(Comparator.comparingLong(File::lastModified)).toArray(File[]::new);
        }
    },
    /**
     * The Created descending.
     */
    CREATED_DESCENDING{
        @Override
        public File[] sort(File[] items) {
            return Arrays.stream(items).sorted((o1, o2) -> Long.compare(o2.lastModified(), o1.lastModified())).toArray(File[]::new);
        }
    }
}
