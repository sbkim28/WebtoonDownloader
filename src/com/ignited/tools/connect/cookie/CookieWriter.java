package com.ignited.tools.connect.cookie;

import java.io.IOException;

public interface CookieWriter {

    void write(Cookie cookie) throws IOException;
    void write(CookieSet cookieSet) throws IOException;

}
