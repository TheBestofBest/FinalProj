package com.app.businessBridge.global.holidayapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AllDayDto {
    private Response response;

    @Getter
    public static class Response {
        private Body body;
    }

    @Getter
    public static class Body {

        private Items items;

        private Integer totalCount;

    }

    @Getter
    public static class Items {
        private List<Item> item;
    }

    @Getter
    public static class Item {
        // '2024'
        private String solYear;
        // '05'
        private String solMonth;
        // '09'
        private String solDay;

        private String solWeek;

    }
}
