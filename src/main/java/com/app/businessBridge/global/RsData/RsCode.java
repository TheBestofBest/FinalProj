package com.app.businessBridge.global.RsData;

import lombok.Getter;

@Getter
public enum RsCode {

    S_01("S-01","Success"),
    S_02("S-02", "Created"),
    S_03("S-03", "Updated"),
    S_04("S-04", "Deleted"),
    S_05("S-05", "Found"),
    S_06("S-06", "Authorized"),
    S_07("S-07", "Completed"),
    S_08("S-08", "Validated"),
    S_09("S-09", "Processed"),
    S_10("S-10", "Accepted"),


    F_01("F-01", "Bad Request"),
    F_02("F-02", "Unauthorized"),
    F_03("F-03", "Forbidden"),
    F_04("F-04", "Not Found"),
    F_05("F-05", "Conflict"),
    F_06("F-06", "Validation Error"),
    F_07("F-07", "Internal Server Error"),
    F_08("F-08", "Service Unavailable"),
    F_09("F-09", "Timeout"),
    F_10("F-10", "Unexpected Error"),
    ;


    private String code;
    private String keyWord;

    RsCode(String code,String keyWord) {
        this.code = code;
        this.keyWord = keyWord;
    }

}
