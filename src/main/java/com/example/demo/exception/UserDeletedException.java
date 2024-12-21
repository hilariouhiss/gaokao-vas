package com.example.demo.exception;

/**
 * 用户删除异常
 *
 * @author Lhy
 * @since 2023/10/31 21:19
 */
public class UserDeletedException extends RuntimeException{
    public UserDeletedException(String message) {
        super(message);
    }
}
