package umc.kkijuk.server.career.controller.response;

import lombok.*;
import umc.kkijuk.server.career.domain.Project;
import umc.kkijuk.server.career.domain.ProjectType;

import java.time.LocalDate;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProjectResponse {
    private String name;
    private String alias;
    private Boolean unknown;
    private String summary;
    private LocalDate startdate;
    private LocalDate enddate;
    private int teamSize;
    private Boolean isTeam;
    private int contribution;
    private ProjectType location;
    public ProjectResponse(Project project) {
        this.name = project.getName();
        this.alias = project.getAlias();
        this.unknown = project.getUnknown();
        this.summary = project.getName();
        this.startdate = project.getStartdate();
        this.enddate = project.getEnddate();
        this.teamSize = project.getTeamSize();
        this.isTeam = project.getIsTeam();
        this.contribution = project.getContribution();
        this.location = project.getLocation();

    }
}
