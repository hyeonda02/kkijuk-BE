package umc.kkijuk.server.recruit.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitCreateDto {
    @NotBlank(message = "공고 제목은 필수 입력 항목입니다.")
    @Schema(description = "공고 제목", example = "[00] 공고제목", type = "string")
    private String title;

    @NotNull(message = "공고 모집 시작 날짜는 필수 입력 항목입니다.")
    @Schema(description = "공고 모집 시작 날짜", example = "2022-09-18 10:11", pattern = "yyyy-MM-dd HH:mm", type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime startTime;

    @NotNull(message = "공고 모집 마감 날짜는 필수 입력 항목입니다.")
    @Schema(description = "공고 모집 마감 날짜", example = "2022-09-18 10:11", pattern = "yyyy-MM-dd HH:mm", type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime endTime;

    @NotNull(message = "유효하지 않은 지원 상태가 입력 되었습니다.")
    @Schema(description = "공고 지원 상태", example = "PLANNED", type = "string")
    private RecruitStatus status;

    @Schema(description = "공고 지원 날짜", pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate applyDate;

//    @Schema(description = "태그", example = "[\"코딩 테스트\", \"인턴\", \"대외 활동\"]", type = "string")
    private List<String> tags;

    @Schema(description = "공고 링크", example = "https://www.naver.com", type = "string")
    private String link;
}
