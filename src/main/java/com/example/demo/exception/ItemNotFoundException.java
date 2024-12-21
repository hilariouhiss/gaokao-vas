package com.example.demo.exception;

/**
 * 用户Id未找到错误类
 *
 * @author Lhy
 * @since 2023/10/26 23:51
 */
public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(String message) {
        super(message);
    }
}
