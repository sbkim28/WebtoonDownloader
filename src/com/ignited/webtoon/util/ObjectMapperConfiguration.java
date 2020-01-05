package com.ignited.webtoon.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

public class ObjectMapperConfiguration {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES,true);
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }

    public static ObjectMapper getMapper() {
        return MAPPER;
    }

    public static ObjectReader getReader(){
        return getMapper().reader();
    }

    public static ObjectWriter getWriter(){
        return getMapper().writer();
    }
}
