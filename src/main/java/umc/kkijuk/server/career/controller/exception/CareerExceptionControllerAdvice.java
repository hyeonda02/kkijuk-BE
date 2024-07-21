package umc.kkijuk.server.career.controller.exception;


import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import umc.kkijuk.server.career.controller.response.CareerResponse;
import umc.kkijuk.server.career.controller.response.CareerResponseMessage;


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

    @ExceptionHandler(CareerNotFoundException.class)
    public ResponseEntity<CareerResponse<?>> handleCareerNotFoundException(CareerNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CareerResponse.createError(exception.getMessage()));
    }
}
