package com.app.businessBridge.global.RsData;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RsData<T> {
    private RsCode rsCode;
    private String msg;
    private T data;
    private Boolean isSuccess;

    public static <T> RsData<T> of(RsCode rsCode, String msg, T data) {

        return new RsData<>(rsCode, msg, data, rsCode.getCode().startsWith("S-"));
    }

    public static <T> RsData<T> of(RsCode rsCode, String msg) {

        return new RsData<>(rsCode, msg, null, rsCode.getCode().startsWith("S-"));
    }


}