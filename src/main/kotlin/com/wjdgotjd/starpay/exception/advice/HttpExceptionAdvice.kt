package com.wjdgotjd.starpay.exception.advice

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class HttpExceptionAdvice {
    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleBadRequest(e: Exception): String? {
        return e.message
    }
}