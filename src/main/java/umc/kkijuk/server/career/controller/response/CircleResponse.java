package umc.kkijuk.server.career.controller.response;

import lombok.*;
import umc.kkijuk.server.career.domain.Circle;

import java.time.LocalDate;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
public class CircleResponse {
    private String name;
    private String alias;
    private Boolean unknown;
    private String summary;
    private LocalDate startdate;
    private LocalDate enddate;
    private Boolean location;
    private String role;
    public CircleResponse(Circle circle) {
        this.name = circle.getName();
        this.alias = circle.getAlias();
        this.unknown = circle.getUnknown();
        this.summary = circle.getName();
        this.startdate = circle.getStartdate();
        this.enddate = circle.getEnddate();
        this.location = circle.getLocation();
        this.role = circle.getRole();
    }
}
