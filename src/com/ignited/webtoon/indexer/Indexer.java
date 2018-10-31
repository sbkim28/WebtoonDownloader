package com.ignited.webtoon.indexer;

import java.io.IOException;


/**
 * Indexer
 *
 * Index
 *
 * @author Ignited
 */
public interface Indexer {

    /**
     * Sets index.
     *
     * @throws IOException when it failed to index
     */
    void setIndex() throws IOException;

}
