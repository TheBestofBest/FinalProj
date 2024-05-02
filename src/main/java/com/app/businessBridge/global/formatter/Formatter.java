package com.app.businessBridge.global.formatter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequestScope
@RequiredArgsConstructor
public class Formatter {

    // 2024-05-02 형태로 포맷
    public static String YMDFormatter(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return dateTime.format(formatter);
    }

    // 2024-05 형태로 포맷
    public static String YMFormatter(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        return dateTime.format(formatter);
    }
}
