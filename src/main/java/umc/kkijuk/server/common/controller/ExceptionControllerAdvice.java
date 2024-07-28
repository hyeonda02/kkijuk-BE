package umc.kkijuk.server.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import umc.kkijuk.server.common.domian.exception.RecruitOwnerMismatchException;
import umc.kkijuk.server.common.domian.exception.InvalidTagNameException;
import umc.kkijuk.server.common.domian.exception.ResourceNotFoundException;
import umc.kkijuk.server.common.domian.exception.ReviewRecruitMismatchException;
import umc.kkijuk.server.common.domian.response.ErrorResponse;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResponse resourceNotFoundException(ResourceNotFoundException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ReviewRecruitMismatchException.class)
    public ErrorResponse ReviewRecruitMatchException(ReviewRecruitMismatchException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(RecruitOwnerMismatchException.class)
    public ErrorResponse RecruitOwnerMismatchException(RecruitOwnerMismatchException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidTagNameException.class)
    public ErrorResponse InvalidTagNameException(InvalidTagNameException exception) {
        return new ErrorResponse(exception.getMessage());
    }
}
