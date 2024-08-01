package umc.kkijuk.server.careerdetail.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;


public class CareerDetailRequestDto {
    @Getter
    @Builder
    public static class CareerDetailCreate{

        @Size(max = 30)
        @NotBlank(message = "활동 기록 제목은 필수 입력 항목입니다. 최대 30자 까지 입력 가능")
        @Schema(description = "활동 기록 제목", example = "역량 키워드", type="string")
        String title;

        @Size(max = 800)
        @NotBlank(message = "활동 기록 내용은 필수 입력 항목입니다. 최대 800자 까지 입력 가능")
        @Schema(description = "활동 기록 내용", example = "역량 키워드", type="string")
        String content;

        @NotNull(message = "활동 시작 날짜는 필수 입력 항목입니다.")
        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        @Schema(description = "활동 기록 시작 날짜", example = "2024-04-14", type="string")
        LocalDate startDate;

        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        @Schema(description = "활동 기록 종료 날짜", example = "2024-04-14", type="string")
        LocalDate endDate;

        @Schema(description = "태그 리스트")
        List<Long> tagList;
    }
}
