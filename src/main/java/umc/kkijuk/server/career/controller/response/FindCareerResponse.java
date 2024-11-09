package umc.kkijuk.server.career.controller.response;

import lombok.*;
import umc.kkijuk.server.career.domain.BaseCareer;

import java.time.LocalDate;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
public class FindCareerResponse {
    private Long careerId;
    private String careerType;
    private String careerTitle;
    private String careerAlias;
    private LocalDate startdate;
    private LocalDate enddate;

    public FindCareerResponse(BaseCareer career, String careerType){
        this.careerId = career.getId();
        this.careerType = careerType;
        this.careerTitle = career.getName();
        this.careerAlias = career.getAlias();
        this.startdate = career.getStartdate();
        this.enddate = career.getEnddate();
    }

}
