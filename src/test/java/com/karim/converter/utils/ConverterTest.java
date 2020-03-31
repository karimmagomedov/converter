package com.karim.converter.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConverterTest extends BaseTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Converter converter = new Converter();

    @AfterEach
    void cleanUp() throws IOException {
        Files.deleteIfExists(avroFilePath);
    }

    @Test
    void convertFromCvsToAvroTest() throws JsonProcessingException {
        converter.convertFromCvsToAvro(WORK_DIR, CSV_FILE_NAME);

        String avroFileAsString = readAllBytes(avroFilePath);
        JsonNode jsonNode = objectMapper.readTree(avroFileAsString);

        assertTrue(Files.exists(avroFilePath));
        assertAll(
            () -> assertEquals("\"1\"", jsonNode.get(0).get("id").toString()),
            () -> assertEquals("\"Vasya\"", jsonNode.get(0).get("Name").toString()),
            () -> assertEquals("\"SPb\"", jsonNode.get(0).get("Town").toString()),
            () -> assertEquals("\"2\"", jsonNode.get(1).get("id").toString()),
            () -> assertEquals("\"Kolya\"", jsonNode.get(1).get("Name").toString()),
            () -> assertEquals("\"Moscow\"", jsonNode.get(1).get("Town").toString())
        );
    }

    private String readAllBytes(Path filePath) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
