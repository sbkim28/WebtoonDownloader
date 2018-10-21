package com.ignited.webtoon.util;

import java.io.IOException;

public class ErrorHandler {
    public static void writeFail(IOException e){
        System.err.println("Failed to write file : " + e.getMessage());
    }

}
