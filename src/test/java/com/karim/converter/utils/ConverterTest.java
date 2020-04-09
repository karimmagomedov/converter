package com.karim.converter.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ConverterTest extends BaseTest {

    private final Converter converter = new Converter();

    @AfterEach
    void cleanUp() throws IOException {
        Files.deleteIfExists(avroFilePath);
    }

    @Test
    void convertFromCvsToAvroTest() throws IOException {
        converter.convertToAvro(WORK_DIR, CSV_FILE_NAME);
        assertTrue(Files.exists(avroFilePath));
    }

}
