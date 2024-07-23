package umc.kkijuk.server.career.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.kkijuk.server.career.controller.response.CareerGroupedByResponse;

import java.time.LocalDate;
import java.util.List;

public class CareerResponseDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CareerResultDto{
        @Schema(description = "생성된 활동 Id", example = "1", type = "Long")
        private Long careerId;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CareerDto{
        Long id;
        String careerName;
        String alias;
        String summary;
        Boolean isCurrent;
        LocalDate startDate;
        LocalDate endDate;
        int year;
        String categoryName;
        Long categoryId;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CareerGroupedByCategoryDto extends CareerGroupedByResponse {
        @Schema(description = "카테고리명",example = "동아리",type = "String")
        private String category;
        @Schema(description = "카테고리 내 활동 개수",example = "2",type="int")
        private int count;
        @Schema(description = "해당 카테고리 내 활동 목록")
        private List<CareerDto> careers;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CareerGroupedByYearDto extends CareerGroupedByResponse{
        @Schema(description = "연도", example = "2024", type = "int")
        private int year;
        @Schema(description = "연도 내 활동 개수", example = "2", type = "int")
        private int count;
        @Schema(description = "해당 연도 내 활동 목록")
        private List<CareerDto> careers;
    }


}
