package umc.kkijuk.server.career.controller.response;

import lombok.*;
import umc.kkijuk.server.career.domain.Employment;
import umc.kkijuk.server.career.domain.JobType;
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
public class EmploymentResponse implements BaseCareerResponse{
    private String category;
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
    private List<BaseCareerDetailResponse> detailList;
    public EmploymentResponse(Employment employment) {
        this.category = CareerType.EMP.getDescription();
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

    public EmploymentResponse(Employment emp, List<BaseCareerDetail> details) {
        this(emp);
        this.detailList = details.stream()
                .map(BaseCareerDetailResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public LocalDate getEndDate() {
        return enddate;
    }
}
