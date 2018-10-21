package com.ignited.webtoon.util;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public enum Order implements Sortable<File>{
    NAME_ASCENDING{
        @Override
        public File[] sort(File[] items) {
            return Arrays.stream(items).sorted(Comparator.comparing(File::getName)).toArray(File[]::new);
        }
    }, NAME_DESCENDING{
        @Override
        public File[] sort(File[] items) {
            return Arrays.stream(items).sorted((o1, o2) -> o2.getName().compareTo(o1.getName())).toArray(File[]::new);
        }
    }, CREATED_ASCENDING{
        @Override
        public File[] sort(File[] items) {
            return Arrays.stream(items).sorted(Comparator.comparingLong(File::lastModified)).toArray(File[]::new);
        }
    }, CREATED_DESCENDING{
        @Override
        public File[] sort(File[] items) {
            return Arrays.stream(items).sorted((o1, o2) -> Long.compare(o2.lastModified(), o1.lastModified())).toArray(File[]::new);
        }
    }
}
