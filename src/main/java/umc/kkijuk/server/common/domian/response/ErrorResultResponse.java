package umc.kkijuk.server.common.domian.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
@Builder
public class ErrorResultResponse<T> {
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public ErrorResultResponse(final String message, T data) {
        this.message = message;
        this.data = data;
    }

    public static ErrorResultResponse<?> createFail(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError o : allErrors) {
            if (o instanceof FieldError) {
                errors.put(((FieldError) o).getField(), o.getDefaultMessage());
            } else {
                errors.put(o.getObjectName(),o.getDefaultMessage());
            }
        }
        return new ErrorResultResponse<>("요청에 실패했습니다. ", errors);
    }

}
