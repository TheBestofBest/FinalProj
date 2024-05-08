package com.app.businessBridge.global.holidayapi.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class AllDayRequest {

//    "body": {
//        "items": {
//            "item": [
//            {
//                "lunDay": 19,
//                    "lunIljin": "계사(癸巳)",
//                    "lunLeapmonth": "평",
//                    "lunMonth": 10,
//                    "lunNday": 30,
//                    "lunSecha": "계묘(癸卯)",
//                    "lunWolgeon": "계해(癸亥)",
//                    "lunYear": 2023,
//                    "solDay": "01",
//                    "solJd": 2460280,
//                    "solLeapyear": "평",
//                    "solMonth": 12,
//                    "solWeek": "금",
//                    "solYear": 2023
//            }
//                    ]
//        },
//        "numOfRows": 31,
//                "pageNo": 1,
//                "totalCount": 31

    private List<Item> items;

    private String totalCount;

    @Getter
    @Setter
    public static class Item {

        private String solYear;

        private String solMonth;

        private String solDay;

        private String solWeek;
    }

}
