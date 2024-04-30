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

    public static String YMDFormatter(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return dateTime.format(formatter);
    }
}
