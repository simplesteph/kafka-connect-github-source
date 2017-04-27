package com.simplesteph.kafka.Validators;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigException;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

public class TimestampValidator implements ConfigDef.Validator {

    @Override
    public void ensureValid(String name, Object value) {
        String timestamp = (String) value;
        try {
            Instant.parse(timestamp);
        } catch (DateTimeParseException e) {
            throw new ConfigException(name, value, "Wasn't able to parse the timestamp, make sure it is formatted according to ISO-8601 standards");
        }


    }
}