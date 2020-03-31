package com.karim.converter.utils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CsvParserTest extends BaseTest {

    private final CsvParser csvParser = new CsvParser();

    @Test
    void getRecordsFromCVSFile() {
        List<List<String>> recordsFromCVSFile =
            csvParser.getRecordsFromCSVFile(format("%s/%s", WORK_DIR, CSV_FILE_NAME));
        assertAll(
            () -> assertEquals("id", recordsFromCVSFile.get(0).get(0)),
            () -> assertEquals("Name", recordsFromCVSFile.get(0).get(1)),
            () -> assertEquals("Town", recordsFromCVSFile.get(0).get(2)),
            () -> assertEquals("1", recordsFromCVSFile.get(1).get(0)),
            () -> assertEquals("Vasya", recordsFromCVSFile.get(1).get(1)),
            () -> assertEquals("SPb", recordsFromCVSFile.get(1).get(2)),
            () -> assertEquals("2", recordsFromCVSFile.get(2).get(0)),
            () -> assertEquals("Kolya", recordsFromCVSFile.get(2).get(1)),
            () -> assertEquals("Moscow", recordsFromCVSFile.get(2).get(2))
        );
    }
}
