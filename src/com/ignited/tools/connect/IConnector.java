package com.ignited.tools.connect;

import java.io.IOException;
import java.io.InputStream;

public interface IConnector {
    boolean connect();
    String read();
    String read(String charset);
    InputStream getInputstream();
}
