package umc.kkijuk.server.tag.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
        @Size(max = 30)
        @Schema(description = "태그 이름", example = "피그마 활용 능력", type="string")
        String tagName;
    }
}
