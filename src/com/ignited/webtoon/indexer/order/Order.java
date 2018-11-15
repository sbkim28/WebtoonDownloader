package com.ignited.webtoon.indexer.order;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
        public void sort(File[] items) {
            Arrays.sort(items, Comparator.comparing(File::getName));
        }
    },
    /**
     * The Name descending.
     */
    NAME_DESCENDING{
        @Override
        public void sort(File[] items) {
            Arrays.sort(items, (o1, o2) -> o2.getName().compareTo(o1.getName()));
        }
    },
    /**
     * The Created ascending.
     */
    CREATED_ASCENDING{
        @Override
        public void sort(File[] items) {
            Arrays.sort(items, Comparator.comparingLong(File::lastModified));
        }
    },
    /**
     * The Created descending.
     */
    CREATED_DESCENDING{
        @Override
        public void sort(File[] items) {
            Arrays.sort(items, (o1, o2) -> Long.compare(o2.lastModified(), o1.lastModified()));
        }
    },
    /**
     * The Image Order;
     */
    IMAGE_ORDER {
        @Override
        public void sort(File[] items) {
            Arrays.sort(items, Comparator.comparingInt(o -> getNumber(o.getName())));
        }

        private int getNumber(String s){
            int number = 0;
            Pattern pattern = Pattern.compile("[0-9]+");
            Matcher m = pattern.matcher(s);
            if(m.find()){
                number = Integer.parseInt(m.group(m.groupCount()));
            }

            return number;
        }
    }
}
