package umc.kkijuk.server.record.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.YearMonth;

@Getter
@Builder
@AllArgsConstructor
public class AwardReqDto {
    private String competitionName;
    private String administer;
    private String awardName;
    private YearMonth acquireDate;
}
