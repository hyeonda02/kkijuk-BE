package umc.kkijuk.server.career.controller.response;

import lombok.*;
import umc.kkijuk.server.detail.controller.response.BaseCareerDetailResponse;


import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
public class FindDetailResponse implements BaseCareerResponse {
    private Long careerId;
    private String careerType;
    private String careerTitle;
    private String careerAlias;
    private LocalDate startdate;
    private LocalDate enddate;
    private List<BaseCareerDetailResponse> detailList;


    @Override
    public LocalDate getEndDate() {
        return this.enddate;
    }
}
