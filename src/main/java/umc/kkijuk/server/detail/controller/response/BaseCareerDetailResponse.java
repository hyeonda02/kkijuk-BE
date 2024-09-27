package umc.kkijuk.server.detail.controller.response;

import lombok.*;
import umc.kkijuk.server.detail.domain.BaseCareerDetail;
import umc.kkijuk.server.detail.domain.CareerType;
import umc.kkijuk.server.detail.domain.mapping.CareerDetailTag;
import umc.kkijuk.server.tag.domain.Tag;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
public class BaseCareerDetailResponse {
    private String careerName;
    private String title;
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;
    private CareerType careerType;
    private List<TagResponse> detailTag;

    public BaseCareerDetailResponse(BaseCareerDetail detail) {
        this.careerType = detail.getCareerType();
        this.title = detail.getTitle();
        this.content = detail.getContent();
        this.startDate = detail.getStartDate();
        this.endDate = detail.getEndDate();
        this.careerName  = detail.getBaseCareer().getName();
        this.detailTag = Optional.ofNullable(detail.getCareerTagList())
                .orElse(Collections.emptyList())
                .stream()
                .map(careerDetailTag -> new TagResponse(careerDetailTag.getTag()))
                .collect(Collectors.toList());
    }

}
