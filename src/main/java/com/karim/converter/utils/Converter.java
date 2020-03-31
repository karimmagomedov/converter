package com.karim.converter.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static java.lang.String.format;

public class Converter {

    private final CsvParser csvParser = new CsvParser();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(Converter.class);

    /**
     * Method for converting CSV file to Avro file.
     *
     * @param workDirectory directory where file is located
     * @param filename      the name of the file
     */
    public void convertFromCvsToAvro(String workDirectory, String filename) {
        String filePath = format("%s\\%s", workDirectory, filename);
        List<List<String>> recordsFromFile = csvParser.getRecordsFromCSVFile(filePath);
        List<String> metadata = recordsFromFile.get(0);
        List<LinkedHashMap<String, String>> resultMap = new ArrayList<>();
        logger.info("Converting CSV file");
        for (int i = 1; i < recordsFromFile.size(); i++) {
            List<String> data = recordsFromFile.get(i);
            resultMap.add(createField(metadata, data));
        }
        createFile(resultMap, workDirectory, filename);
        logger.info("Converting successfully finished!");
    }

    private LinkedHashMap<String, String> createField(List<String> meta, List<String> data) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        int index = 0;
        for (String s : meta) {
            map.put(s, data.get(index));
            index++;
        }
        return map;
    }

    private void createFile(List<LinkedHashMap<String, String>> fields, String workDirectory, String fileName) {
        logger.info("Crete Avro file");
        String fileNameWithoutExtension = removeExtension(fileName);
        Path path = Paths.get(workDirectory, fileNameWithoutExtension);
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), fields);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write file");
        }
    }

    private String removeExtension(String s) {
        return (s == null || s.length() == 0) ? null : (s.substring(0, s.length() - 4));
    }
}
