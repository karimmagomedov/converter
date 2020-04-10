package com.karim.converter.utils;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumWriter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static java.lang.String.format;

public class Converter {

    private static final Logger logger = LoggerFactory.getLogger(Converter.class);

    private static final String FIELDS = "fields";
    private final SchemaGenerator schemaGenerator = new SchemaGenerator();

    /**
     * Method for converting CSV file to Avro file.
     *
     * @param workDirectory directory where file is located
     * @param filename      the name of the file
     */
    public void convertToAvro(String workDirectory, String filename) {
        logger.info("Start converting");
        Schema schema = schemaGenerator.generateSchema();
        GenericRecord genericRecord = new GenericData.Record(schema);
        DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);
        DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(datumWriter);
        logger.info("Create schema");
        String fileNameWithoutExtension = removeExtension(filename);
        try {
            dataFileWriter.create(schema, new File(format("%s/%s.avro", workDirectory, fileNameWithoutExtension)));
            logger.info("Write data");
            readCvsAndWriteToAvro(genericRecord, dataFileWriter, format("%s/%s", workDirectory, filename));
            dataFileWriter.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("Can't create avro file");
        }
        logger.info("Converting successfully finished!");
    }

    private void readCvsAndWriteToAvro(GenericRecord genericRecord, DataFileWriter<GenericRecord> dataFileWriter,
        String path) throws IOException {
        LineIterator it = FileUtils.lineIterator(new File(path), "UTF-8");
        try {
            while (it.hasNext()) {
                String line = it.nextLine();
                String[] splittedLine = line.split("\\s*,\\s*");
                writeDataToAvroFile(genericRecord, splittedLine, dataFileWriter);
            }
        } finally {
            LineIterator.closeQuietly(it);
        }
    }

    private void writeDataToAvroFile(GenericRecord genericRecord, String[] records,
        DataFileWriter<GenericRecord> dataFileWriter) throws IOException {
        for (String record : records) {
            genericRecord.put(FIELDS, record);
            dataFileWriter.append(genericRecord);
        }
    }

    private String removeExtension(String s) {
        return (s == null || s.length() == 0) ? null : (s.substring(0, s.length() - 4));
    }
}
