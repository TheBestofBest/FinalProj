package com.app.businessBridge.global.hoildayapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class HoliDayDto {

    private String locdate;

    private String totalCount;

}
