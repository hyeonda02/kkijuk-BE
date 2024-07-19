package umc.kkijuk.server.recruit.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitStatusUpdate {
    @NotNull
    @Schema(description = "변경될 공고 상태", example = "REJECTED", type = "string")
    private RecruitStatus status;
}
