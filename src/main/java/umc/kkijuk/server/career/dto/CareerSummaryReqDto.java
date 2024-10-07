package umc.kkijuk.server.career.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CareerSummaryReqDto {
    @Size(max = 200)
    @Schema(description = "활동 내역", example = "주요 활동 내용을 요약하여 작성해주세요. 최대 50자 까지 입력 가능 선택사항입니다.", type = "string")
    private String summary;
}
