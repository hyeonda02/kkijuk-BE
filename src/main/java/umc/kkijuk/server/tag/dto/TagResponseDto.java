package umc.kkijuk.server.tag.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class TagResponseDto {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResultTagDto{
        @Schema(description = "생성된 활동 태그 Id", example = "1", type = "Long")
        private Long id;
        @Schema(description = "생성된 활동 태그 이름", example = "핵심 역량", type = "String")
        private String tagName;
        private Long memberId;
    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResultTagDtoList{
        @Schema(description = "활동 태그들의 개수",example = "3")
        private int count;
        @Schema(description = "활동 태그들 리스트")
        private List<ResultTagDto> tagList;
    }
}
