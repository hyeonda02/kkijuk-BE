package umc.kkijuk.server.career.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import umc.kkijuk.server.career.domain.JobType;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
public class EmploymentReqDto {
    @NotBlank(message = "활동명은 필수 입력 항목입니다. 최대 20자 까지 입력 가능")
    @Size(max = 20)
    @Schema(description = "활동명", example = "학원 채점 아르바이트", type="string")
    private String name;

    @NotNull(message = "활동 기간을 알고 있는지 여부를 나타냅니다.")
    @Schema(description = "활동 기간 인지 여부", example = "false", type = "boolean")
    private Boolean unknown;

    @NotNull(message = "활동 시작 날짜는 필수 입력 항목입니다.")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @Schema(description = "활동 시작 날짜", example = "2024-04-14", type="string")
    private LocalDate startdate;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @Schema(description = "활동 종료 날짜", example = "2024-07-20", type = "string")
    private LocalDate enddate;

    //경력 입력사항
    @NotNull(message = "직무 유형(분류)은 필수 입력 항목입니다.")
    @Schema(description = "직무 유형(분류)", example = "FULL_TIME", type = "string", allowableValues = {"FULL_TIME", "PART_TIME", "INTERNSHIP", "FREELANCE"})
    private JobType type;

    @NotBlank(message = "근무처는 필수 입력 항목입니다. 최대 15자 까지 입력 가능")
    @Size(max = 15)
    @Schema(description = "근무처", example = "근무처", type="string")
    private String alias;

    @Size(max = 15)
    @Schema(description = "직급/직위", example = "보조강사", type="string")
    private String position;

    @Size(max = 15)
    @Schema(description = "직무/분야", example = "마케팅", type="string")
    private String field;
}
