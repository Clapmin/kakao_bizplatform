package com.kakao.bizplatform.navi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class CommonTest {
    @Test
    @DisplayName("시간 변환 확인")
    void localDateTime() throws ParseException {
        Date date = new SimpleDateFormat("yyyyMMddHHmmssSSSS").parse("202011131101020003");
        LocalDateTime localDateTimeFromDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyyMMddHHmmss")
                                                                    .appendValue(ChronoField.MILLI_OF_SECOND, 4)
                                                                    .toFormatter();

        LocalDateTime localDateTimeFromDateTimeFormatter = LocalDateTime.parse("202011131101020003", formatter);
        System.out.println("From Date: " + localDateTimeFromDate);
        System.out.println("From DateTimeFormatter: " + localDateTimeFromDateTimeFormatter);

        assertThat(localDateTimeFromDate).isEqualTo(localDateTimeFromDateTimeFormatter);
    }
}
