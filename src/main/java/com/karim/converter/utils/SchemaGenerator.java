package com.karim.converter.utils;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;

public class SchemaGenerator {

    /**
     * Method for generate avro schema.
     *
     * @return the {@code Schema}
     */
    public Schema generateSchema() {
        return SchemaBuilder.record("converted_csv_file")
            .namespace("com.karim.converter")
            .fields()
            .name("fields")
            .type()
            .stringType()
            .noDefault()
            .endRecord();
    }
}

