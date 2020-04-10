package com.karim.converter.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConverterTest {

    private static final String WORK_DIR = "src/test/resources";
    private static final String CSV_FILE_NAME = "test_file.csv";
    private static final String AVRO_FILE_NAME = "test_file.avro";

    private final Path avroFilePath = Paths.get(format("%s\\%s", WORK_DIR, AVRO_FILE_NAME));
    private final Converter converter = new Converter();

    @AfterEach
    void cleanUp() throws IOException {
        Files.deleteIfExists(avroFilePath);
    }

    @Test
    void convertFromCvsToAvroTest() {
        converter.convertToAvro(WORK_DIR, CSV_FILE_NAME);
        assertTrue(Files.exists(avroFilePath));
    }

}
