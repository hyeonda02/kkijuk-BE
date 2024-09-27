package umc.kkijuk.server.career.controller.response;

import lombok.*;
import umc.kkijuk.server.career.domain.Employment;
import umc.kkijuk.server.career.domain.JobType;

import java.time.LocalDate;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
public class EmploymentResponse {
    private String name;
    private String alias;
    private Boolean unknown;
    private String summary;
    private LocalDate startdate;
    private LocalDate enddate;
    private JobType type;
    private String workplace;
    private String position;
    private String field;
    public EmploymentResponse(Employment employment) {
        this.name = employment.getName();
        this.alias = employment.getAlias();
        this.unknown = employment.getUnknown();
        this.summary = employment.getName();
        this.startdate = employment.getStartdate();
        this.enddate = employment.getEnddate();
        this.type = employment.getType();
        this.workplace = employment.getWorkplace();
        this.position = employment.getPosition();
        this.field = employment.getField();

    }
}
