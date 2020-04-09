package com.karim.converter.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.String.format;

public class BaseTest {

    protected static final String WORK_DIR = "src/test/resources";
    protected static final String CSV_FILE_NAME = "test_file.csv";
    protected static final String AVRO_FILE_NAME = "test_file.avro";

    protected final Path avroFilePath = Paths.get(format("%s\\%s", WORK_DIR, AVRO_FILE_NAME));

}
