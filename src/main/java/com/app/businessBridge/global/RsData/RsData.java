package com.app.businessBridge.global.RsData;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RsData<T> {
    private String resultCode;
    private String keyWord;
    private String msg;
    private T data;
    private Boolean isSuccess;

    public static <T> RsData<T> of(RsCode resultCode, String msg, T data) {

        return new RsData<>(resultCode.getCode(),resultCode.getKeyWord(), msg, data, resultCode.getCode().startsWith("S-"));
    }

    public static <T> RsData<T> of(RsCode resultCode, String msg) {

        return new RsData<>(resultCode.getCode(),resultCode.getKeyWord(), msg, null, resultCode.getCode().startsWith("S-"));
    }


}