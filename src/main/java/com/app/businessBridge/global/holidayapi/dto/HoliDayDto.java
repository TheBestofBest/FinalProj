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
public class HoliDayDto {
    private Response response;

    @Getter
    public static class Response {
        private Body body;
    }

    @Getter
    public static class Body {
        private Items items;

        private String totalCount;

    }

    @Getter
    public static class Items {
        private List<Item> item;

    }

    @Getter
    public static class Item {

        private String locdate;

    }
}