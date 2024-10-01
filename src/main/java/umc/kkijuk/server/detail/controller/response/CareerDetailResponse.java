package umc.kkijuk.server.detail.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@AllArgsConstructor
public class CareerDetailResponse<T> {
    private int staus;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    public static <T> CareerDetailResponse<T> success(HttpStatus status, String message, T data){
        return new CareerDetailResponse<>(status.value(),message,data);
    }

}