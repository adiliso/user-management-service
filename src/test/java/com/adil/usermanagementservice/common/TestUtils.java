package com.adil.usermanagementservice.common;

import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import java.io.IOException;

import static java.nio.charset.Charset.defaultCharset;

public class TestUtils {

    public static String json(String fileName) throws IOException {
        return FileUtils.readFileToString(
                ResourceUtils.getFile("classpath:__Files/" + fileName), defaultCharset());
    }
}
