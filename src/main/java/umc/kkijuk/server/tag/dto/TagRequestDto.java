package umc.kkijuk.server.tag.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class TagRequestDto {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateTagDto{
        @NotBlank(message = "해시태그 이름은 필수 입력 사항입니다.")
        @Size(max = 30)
        @Schema(description = "해시태그 이름", example = "역량 키워드", type="string")
        String tagName;


    }
}
