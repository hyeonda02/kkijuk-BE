package umc.kkijuk.server.career.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    public static class UpdateCareerResultDto{
        String careerName;
        String alias;
        String summary;
        Boolean isCurrent;
        LocalDate startDate;
        LocalDate endDate;
        int category;
    }


}
