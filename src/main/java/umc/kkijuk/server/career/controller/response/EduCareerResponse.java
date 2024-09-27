package umc.kkijuk.server.career.controller.response;

import lombok.*;
import umc.kkijuk.server.career.domain.EduCareer;

import java.time.LocalDate;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
public class EduCareerResponse {
    private String name;
    private String alias;
    private Boolean unknown;
    private String summary;
    private LocalDate startdate;
    private LocalDate enddate;
    private String organizer;
    private int time;
    public EduCareerResponse(EduCareer eduCareer) {
        this.name = eduCareer.getName();
        this.alias = eduCareer.getAlias();
        this.unknown = eduCareer.getUnknown();
        this.summary = eduCareer.getName();
        this.startdate = eduCareer.getStartdate();
        this.enddate = eduCareer.getEnddate();
        this.organizer = eduCareer.getOrganizer();
        this.time = eduCareer.getTime();
    }
}
