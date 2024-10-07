package umc.kkijuk.server.career.controller.response;

import lombok.*;
import umc.kkijuk.server.career.domain.Activity;
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
public class ActivityResponse implements BaseCareerResponse{
    private String category;
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
    private List<BaseCareerDetailResponse> detailList;

    public ActivityResponse(Activity activity) {
        this.category = CareerType.ACTIVITY.getDescription();
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
    public ActivityResponse(Activity activity, List<BaseCareerDetail> details) {
        this(activity);
        if (details != null && !details.isEmpty()) {
            this.detailList = details.stream()
                    .map(BaseCareerDetailResponse::new)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public LocalDate getEndDate() {
        return enddate;
    }
}
