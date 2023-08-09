package com.example.ordersystem.utils;


import lombok.Data;

@Data
public class Result {
    private int code;
    private String message;
    private Object data;

    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result setDate(int code, String message) {
        return new Result(code, message, "");
    }

    public static Result setDate(int code, String message, Object data) {
        return new Result(code, message, data);
    }

    public static Result setDate(int code, Object data) {
        return new Result(code, "", data);
    }

}
