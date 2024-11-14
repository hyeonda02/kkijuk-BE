package umc.kkijuk.server.career.controller.response;

import lombok.*;
import umc.kkijuk.server.career.domain.BaseCareer;

import java.time.LocalDate;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
public class TimelineResponse {
    private Long careerId;
    private String category;
    private String title;
    private LocalDate startdate;
    private LocalDate enddate;
    public TimelineResponse(BaseCareer career, String type){
        this.careerId = career.getId();
        this.title = career.getName();
        this.startdate = career.getStartdate();
        this.enddate = career.getEnddate();
        this.category = type;
    }

}
