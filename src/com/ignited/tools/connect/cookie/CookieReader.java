package com.ignited.tools.connect.cookie;

import java.io.IOException;

public interface CookieReader {

    CookieSet read() throws IOException;

}
