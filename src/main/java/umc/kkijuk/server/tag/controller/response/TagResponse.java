package umc.kkijuk.server.tag.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import umc.kkijuk.server.career.controller.response.CareerResponse;

@Builder
@Getter
public class TagResponse<T> {
    private int status;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public TagResponse(final int status,final String message,T data){
        this.status = status;
        this.message = message;
        this.data = data;
    }
    public static <T> TagResponse<T> success(HttpStatus status, String message, T data){
        return new TagResponse<>(status.value(),message,data);
    }
}
