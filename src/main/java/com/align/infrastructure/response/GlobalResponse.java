package com.align.infrastructure.response;

import lombok.Data;

@Data
public class GlobalResponse<T> {
    private int code;
    private T data;
    private String message;

    public GlobalResponse(int responseCode, T responseData, String responseMessage) {
        code = responseCode;
        data = responseData;
        message = responseMessage;
    }
    public static <T> GlobalResponse<T> response(int code, T data, String message) {
        return new GlobalResponse<>(code, data, message);
    }
}
