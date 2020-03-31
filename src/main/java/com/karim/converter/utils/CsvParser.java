package com.karim.converter.utils;

import com.opencsv.CSVReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvParser {

    private static final Logger logger = LoggerFactory.getLogger(CsvParser.class);

    /**
     * Method for getting records from CSV file.
     *
     * @param patToFile path to CSV file
     */
    public List<List<String>> getRecordsFromCSVFile(String patToFile) {
        logger.info("Processing CSV file");
        List<List<String>> records = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(patToFile))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new IllegalArgumentException("Please, type correct file path");
        }
        return records;
    }

}
