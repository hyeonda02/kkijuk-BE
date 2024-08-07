package umc.kkijuk.server.common.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import umc.kkijuk.server.common.domian.exception.*;
import umc.kkijuk.server.common.domian.response.ErrorResponse;
import umc.kkijuk.server.common.domian.exception.ConfirmPasswordMismatchException;
import umc.kkijuk.server.common.domian.response.ErrorResultResponse;
import umc.kkijuk.server.login.exception.UnauthorizedException;

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
    @ExceptionHandler(MemberEmailNotFoundException.class)
    public ErrorResponse EmailNotFoundException(MemberEmailNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(CareerValidationException.class)
    public ErrorResponse CareerValidationException(CareerValidationException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResultResponse<?>> handleValidationExceptions(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResultResponse.createFail(bindingResult));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFormatException.class)
    public ErrorResponse handleInvalidFormatExceptions(InvalidFormatException exception) {
        return new ErrorResponse("올바른 형식이 아닙니다.");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(OwnerMismatchException.class)
    public ErrorResponse CareerOwnerMismatchException(OwnerMismatchException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ErrorResponse UnauthorizedException(UnauthorizedException exception) {
        return new ErrorResponse(exception.getMessage());
    }
}
