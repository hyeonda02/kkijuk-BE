package umc.kkijuk.server.career.controller.response;

import lombok.*;
import umc.kkijuk.server.career.domain.EduCareer;
import umc.kkijuk.server.detail.controller.response.BaseCareerDetailResponse;
import umc.kkijuk.server.detail.domain.BaseCareerDetail;
import umc.kkijuk.server.detail.domain.CareerType;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
public class EduCareerResponse implements BaseCareerResponse {
    private String category;
    private String name;
    private String alias;
    private Boolean unknown;
    private String summary;
    private LocalDate startdate;
    private LocalDate enddate;
    private String organizer;
    private int time;
    private List<BaseCareerDetailResponse> detailList;
    public EduCareerResponse(EduCareer eduCareer) {
        this.category = CareerType.EDU.getDescription();
        this.name = eduCareer.getName();
        this.alias = eduCareer.getAlias();
        this.unknown = eduCareer.getUnknown();
        this.summary = eduCareer.getName();
        this.startdate = eduCareer.getStartdate();
        this.enddate = eduCareer.getEnddate();
        this.organizer = eduCareer.getOrganizer();
        this.time = eduCareer.getTime();
    }

    public EduCareerResponse(EduCareer edu, List<BaseCareerDetail> details) {
        this(edu);
        this.detailList = details.stream()
                .map(BaseCareerDetailResponse::new)
                .collect(Collectors.toList());
    }
}
