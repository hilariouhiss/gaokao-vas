package com.example.demo.handler;

import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.exception.UserDeletedException;
import com.example.demo.util.Result;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

/**
 * 全局异常处理器
 *
 * @author Lhy
 * @since 2023/10/26 21:39
 */
@ControllerAdvice
public class CustomGlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public Result<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        return Result.error(403, e.getMessage());
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Result<Void> handleDataAccessException(DataAccessException e) {
        return Result.error(500, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Result<Void> handleException(Exception e) {
        return Result.error(500, e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public Result<Void> handleAccessDeniedException(AccessDeniedException e) {
        return Result.error(403, e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result<Void> handleConstraintViolationException(ConstraintViolationException e) {
        return Result.error(400, e.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Result<Void> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return Result.error(404, e.getMessage());
    }

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Result<Void> handleUserNotFoundException(ItemNotFoundException e) {
        return Result.error(404, e.getMessage());
    }

    @ExceptionHandler(UserDeletedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Result<Void> handleUserDeletedException(UserDeletedException e) {
        return Result.error(500, e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Result<Void> handleIOException(IOException e) {
        return Result.error(500, e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Result<Void> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return Result.error(500, "违反数据完整性约束");
    }

}
