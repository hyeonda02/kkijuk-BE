package umc.kkijuk.server.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import umc.kkijuk.server.common.domian.exception.*;
import umc.kkijuk.server.common.domian.response.ErrorResponse;

@RestControllerAdvice
@RequiredArgsConstructor
public class IntroExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(IntroFoundException.class)
    public ErrorResponse IntroFoundException(IntroFoundException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(IntroOwnerMismatchException.class)
    public ErrorResponse IntroOwnerMismatchException(IntroOwnerMismatchException exception) {
        return new ErrorResponse(exception.getMessage());
    }
}
