package com.example.demo.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 结果信息工具
 * @author Lhy
 * @since 2023/10/01 18:58
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 5187687995319002219L;
    private Integer code;
    private Boolean success;
    private String message;
    private T data;

    public static <T> Result<T> definition(int code, String message, Boolean success, T data) {
        Result<T> r = new Result<>();
        r.setCode(code);
        r.setSuccess(success);
        r.setMessage(message);
        r.setData(data);
        return r;
    }

    public static <T> Result<T> success(String message, T data) {
        Result<T> r = new Result<>();
        r.setCode(200);
        r.setMessage(message);
        r.setSuccess(true);
        r.setData(data);
        return r;
    }

    public static <T> Result<T> success(String message) {
        Result<T> r = new Result<>();
        r.setCode(200);
        r.setMessage(message);
        r.setSuccess(true);
        return r;
    }

    public static <T> Result<T> error(int code, String message) {
        Result<T> r = new Result<>();
        r.setCode(code);
        r.setSuccess(false);
        r.setMessage(message);
        return r;
    }
}

