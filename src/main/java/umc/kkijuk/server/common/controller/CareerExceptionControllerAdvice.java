package umc.kkijuk.server.common.controller;


import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import umc.kkijuk.server.career.controller.response.CareerResponse;
import umc.kkijuk.server.career.controller.response.CareerResponseMessage;
import umc.kkijuk.server.common.domian.exception.CareerValidationException;


@RestControllerAdvice(assignableTypes = {umc.kkijuk.server.career.controller.CareerController.class})
public class CareerExceptionControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CareerResponse<?>> handleValidationExceptions(BindingResult bindingResult) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CareerResponse.createFail(bindingResult));
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<CareerResponse<?>> handleInvalidFormatExceptions(InvalidFormatException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CareerResponse.createError(CareerResponseMessage.CAREER_FOTMAT_INVALID));
    }

    @ExceptionHandler(CareerValidationException.class)
    public ResponseEntity<CareerResponse<?>> handleCareerValidationException(CareerValidationException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CareerResponse.createError(e.getMessage()));
    }
}
