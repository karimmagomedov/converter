package com.karim.converter.utils;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static java.lang.String.format;

public class Converter {

    private final SchemaGenerator schemaGenerator = new SchemaGenerator();
    private final CsvParser csvParser = new CsvParser();
    private static final Logger logger = LoggerFactory.getLogger(Converter.class);

    /**
     * Method for converting CSV file to Avro file.
     *
     * @param workDirectory directory where file is located
     * @param filename      the name of the file
     */
    public void convertToAvro(String workDirectory, String filename) throws IOException {
        logger.info("Start converting");
        Schema schema = schemaGenerator.generateSchema();
        GenericRecord genericRecord = new GenericData.Record(schema);
        List<String> recordsFromCvs = csvParser.getRecordsFromCSVFile(format("%s/%s", workDirectory, filename));

        genericRecord.put("fields", recordsFromCvs);

        DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);
        DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(datumWriter);

        String fileNameWithoutExtension = removeExtension(filename);
        dataFileWriter.create(schema, new File(format("%s/%s.avro", workDirectory, fileNameWithoutExtension)));
        dataFileWriter.append(genericRecord);

        dataFileWriter.close();
        logger.info("Converting successfully finished!");
    }

    private String removeExtension(String s) {
        return (s == null || s.length() == 0) ? null : (s.substring(0, s.length() - 4));
    }
}
