package umc.kkijuk.server.career.controller.response;

import lombok.*;
import umc.kkijuk.server.career.domain.Circle;
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
public class CircleResponse implements BaseCareerResponse{
    private String category;
    private String name;
    private String alias;
    private Boolean unknown;
    private String summary;
    private LocalDate startdate;
    private LocalDate enddate;
    private Boolean location;
    private String role;
    private List<BaseCareerDetailResponse> detailList;
    public CircleResponse(Circle circle) {
        this.category = CareerType.CIRCLE.getDescription();
        this.name = circle.getName();
        this.alias = circle.getAlias();
        this.unknown = circle.getUnknown();
        this.summary = circle.getName();
        this.startdate = circle.getStartdate();
        this.enddate = circle.getEnddate();
        this.location = circle.getLocation();
        this.role = circle.getRole();
    }
    public CircleResponse(Circle circle, List<BaseCareerDetail> details) {
        this(circle);
        this.detailList = details.stream()
                .map(BaseCareerDetailResponse::new)
                .collect(Collectors.toList());
    }
}
