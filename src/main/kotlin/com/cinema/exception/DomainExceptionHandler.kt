package com.cinema.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class DomainExceptionHandler {
    @ExceptionHandler(value = [DomainException::class])
    fun handleDomainException(exception: DomainException) =
        ResponseEntity(exception.message, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(value = [DomainObjectNotFoundException::class])
    fun handleDomainObjectNotFoundException(exception: DomainObjectNotFoundException) =
        ResponseEntity(exception.message, HttpStatus.NOT_FOUND)
}
