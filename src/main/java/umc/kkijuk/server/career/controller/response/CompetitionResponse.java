package umc.kkijuk.server.career.controller.response;

import lombok.*;
import umc.kkijuk.server.career.domain.Competition;

import java.time.LocalDate;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
public class CompetitionResponse {
    private String name;
    private String alias;
    private Boolean unknown;
    private String summary;
    private LocalDate startdate;
    private LocalDate enddate;
    private String organizer;
    private int teamSize;
    private int contribution;
    private Boolean isTeam;
    public CompetitionResponse(Competition competition) {
        this.name = competition.getName();
        this.alias = competition.getAlias();
        this.unknown = competition.getUnknown();
        this.summary = competition.getName();
        this.startdate = competition.getStartdate();
        this.enddate = competition.getEnddate();
        this.organizer = competition.getOrganizer();
        this.teamSize = competition.getTeamSize();
        this.contribution = competition.getContribution();
        this.isTeam = competition.getIsTeam();

    }
}
