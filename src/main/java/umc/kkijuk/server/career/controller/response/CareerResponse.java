package umc.kkijuk.server.career.controller.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;


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
    public static <T> CareerResponse<T> success(HttpStatus status, String message, T data){
        return new CareerResponse<>(status.value(),message,data);
    }
}
