package umc.kkijuk.server.career.controller.response;

import lombok.*;
import umc.kkijuk.server.career.domain.Activity;

import java.time.LocalDate;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ActivityResponse {
    private String name;
    private String alias;
    private Boolean unknown;
    private String summary;
    private LocalDate startdate;
    private LocalDate enddate;
    private String organizer;
    private String role;
    private int teamSize;
    private int contribution;
    private Boolean isTeam;

    public ActivityResponse(Activity activity) {
        this.name = activity.getName();
        this.alias = activity.getAlias();
        this.unknown = activity.getUnknown();
        this.summary = activity.getName();
        this.startdate = activity.getStartdate();
        this.enddate = activity.getEnddate();
        this.organizer = activity.getOrganizer();
        this.role  = activity.getRole();
        this.teamSize = activity.getTeamSize();
        this.contribution = activity.getContribution();
        this.isTeam = activity.getIsTeam();
    }
}
