package umc.kkijuk.server.career.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import umc.kkijuk.server.career.validation.ValidCategory;
import umc.kkijuk.server.career.validation.ValidPeriod;

import java.time.LocalDate;

public class CareerRequestDto {
    @Getter
    @Builder
    @ValidPeriod
    public static class CareerDto{

        @NotBlank(message = "활동명은 필수 입력 항목입니다. 최대 20자 까지 입력 가능")
        @Size(max = 20)
        @Schema(description = "활동명", example = "IT 서비스 개발 동아리", type="string")
        String careerName;

        @NotBlank(message = "활동 별칭은 필수 입력 항목입니다. 최대 20자 까지 입력 가능")
        @Size(max = 20)
        @Schema(description = "활동 별칭", example = "동아리", type="string")
        String alias;

        @NotNull(message = "활동 여부는 필수 선택 항목입니다.")
        @Schema(description = "활동 여부", example = "false", type = "boolean")
        Boolean isCurrent;

        @NotNull(message = "활동 시작 날짜는 필수 입력 항목입니다.")
        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        @Schema(description = "활동 시작 날짜", example = "2024-04-14", type="string")
        LocalDate startDate;


        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        @Schema(description = "활동 종료 날짜", example = "2024-07-20", type = "string")
        LocalDate endDate;

        @ValidCategory
        @NotNull(message = "활동 카테고리는 필수 선택 항목입니다.")
        @Schema(description = "활동 카테고리", example = "1", type = "int")
        int category;
    }

}
