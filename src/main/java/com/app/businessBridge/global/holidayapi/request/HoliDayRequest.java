package com.app.businessBridge.global.holidayapi.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class HoliDayRequest {
//    "body": {
//        "items": {
//            "item": [
//            {
//                "dateKind": "01",
//                    "dateName": "1월1일",
//                    "isHoliday": "Y",
//                    "locdate": 20230101,
//                    "seq": 1
//            }
//                ]
//        },
//        "numOfRows": 10,
//                "pageNo": 1,
//                "totalCount": 5

    private List<Item> items;

    private String totalCount;

    @Getter
    @Setter
    public static class Item {

        private String locdate;

    }

}
