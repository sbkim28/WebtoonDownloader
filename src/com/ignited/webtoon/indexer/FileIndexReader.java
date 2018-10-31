package com.ignited.webtoon.indexer;

import java.io.File;
import java.io.IOException;

/**
 * FileIndexReader
 *
 * Read Index File
 * @author Ignited
 */
public interface FileIndexReader{

    /**
     * Read files from an index file
     *
     * @return the array of files
     * @throws IOException when it failed to read
     */
    File[] read() throws IOException;
}
