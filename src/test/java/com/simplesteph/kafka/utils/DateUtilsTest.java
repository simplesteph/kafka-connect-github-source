package com.simplesteph.kafka.utils;

import org.junit.Test;
import java.time.Instant;
import java.time.ZonedDateTime;
import static org.junit.Assert.*;

public class DateUtilsTest {

    @Test
    public void maxInstant() {
        Instant i1 = ZonedDateTime.now().toInstant();
        Instant i2 = i1.plusSeconds(1);
        assertEquals(DateUtils.MaxInstant(i1, i2), i2);
        assertEquals(DateUtils.MaxInstant(i2, i1), i2);
    }
}
