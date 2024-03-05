package com.align.infrastructure.exception;

import com.align.infrastructure.response.GlobalResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.util.CollectionUtils;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @SneakyThrows
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public GlobalResponse<Object> handleMethodArgumentException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        FieldError firstFieldError = CollectionUtils.firstElement(bindingResult.getFieldErrors());
        String errMessage = "[" + Objects.requireNonNull(firstFieldError).getField() + "] " + firstFieldError.getDefaultMessage();
        return GlobalResponse.response(500, null, errMessage);
    }

    @ExceptionHandler(value = BusinessException.class)
    public GlobalResponse<Object> globalExceptionHandler(BusinessException e) {
        log.error("error stack trace: ", e);
        return GlobalResponse.response(e.getCode(),null, e.getMessage());
    }

    @ExceptionHandler(value = Throwable.class)
    public GlobalResponse<Object> handleThrowable(HttpServletRequest request, Throwable throwable) {
        log.error("[{}] {} ", request.getMethod(), throwable);
        return GlobalResponse.response(500,null, throwable.getMessage());
    }
}