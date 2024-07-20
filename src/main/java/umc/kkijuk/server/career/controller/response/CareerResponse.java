package umc.kkijuk.server.career.controller.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.AnyKeyJavaClass;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@Getter
public class CareerResponse<T> {
    private int status;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public CareerResponse(final int status,final String message,T data){
        this.status = status;
        this.message = message;
        this.data = data;
    }
    public static <T> CareerResponse<T> success(int status, String message, T data){
        return new CareerResponse<>(status,message,data);
    }
    public static <T> CareerResponse<T> Success(int status, String message){
        return new CareerResponse<>(status, message, null);
    }
    public static <T> CareerResponse<T> success(HttpStatus status, String message, T data){
        return new CareerResponse<>(status.value(),message,data);
    }
    public static CareerResponse<?> createFail (BindingResult bindingResult){
        Map<String, String> errors = new HashMap<>();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for ( ObjectError error: allErrors ) {
            if( error instanceof FieldError) {
                errors.put(((FieldError)error).getField(),error.getDefaultMessage());
            }else{
                errors.put(error.getObjectName(),error.getDefaultMessage());
            }
        }
        return new CareerResponse<>(CareerStatusCode.FAIL,CareerResponseMessage.CAREER_CREATE_FAIL,errors);
    }
    public static CareerResponse<?> createError(String message){
        return new CareerResponse<>(CareerStatusCode.FAIL, message, null);
    }


}
