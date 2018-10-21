package com.ignited.webtoon.indexer;

import java.io.File;
import java.io.IOException;

public interface FileIndexReader{

    File[] read() throws IOException;
}
