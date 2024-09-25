package umc.kkijuk.server.record.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import umc.kkijuk.server.record.domain.Award;

import java.time.YearMonth;

@Data
@AllArgsConstructor
@Getter
@Builder
public class AwardResponse {
    private Long awardId;
    private String competitionName;
    private String administer;
    private String awardName;
    private YearMonth acquireDate;

    public AwardResponse(Award award) {
        this.awardId = award.getId();
        this.competitionName = award.getCompetitionName();
        this.administer = award.getAdminister();
        this.awardName = award.getAwardName();
        this.acquireDate = award.getAcquireDate();
    }
}
