package umc.kkijuk.server.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import umc.kkijuk.server.common.domian.exception.*;
import umc.kkijuk.server.common.domian.response.ErrorResponse;
import umc.kkijuk.server.common.domian.exception.ConfirmPasswordMismatchException;

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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConfirmPasswordMismatchException.class)
    public ErrorResponse ConfirmPasswordMismatchException(ConfirmPasswordMismatchException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FieldUpdateException.class)
    public ErrorResponse FieldUpdateException(FieldUpdateException e) {
        return new ErrorResponse(e.getMessage());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidMemberDataException.class)
    public ErrorResponse InvalidMemberDataException(InvalidMemberDataException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CurrentPasswordMismatchException.class)
    public ErrorResponse CurrentPasswordMismatchException(CurrentPasswordMismatchException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailNotFoundException.class)
    public ErrorResponse EmailNotFoundException(EmailNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

}
