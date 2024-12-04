package umc.kkijuk.server.recruit.domain;

import lombok.Builder;
import lombok.Getter;
import umc.kkijuk.server.review.domain.Review;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Getter
@Builder
public class ValidRecruitDto {
    private Long id;
    private RecruitStatus status;
    private String title;
    private String reviewTag;
    private List<String> tags;

    public static ValidRecruitDto from(List<Review> reviews, Recruit recruit) {
        return ValidRecruitDto.builder()
                .id(recruit.getId())
                .status(recruit.getStatus())
                .title(recruit.getTitle())
                .reviewTag(reviews.stream().max(Comparator.comparing(Review::getDate))
                                .map(Review::getTitle).orElse(""))
                .tags(recruit.getTags())
                .build();
    }
}
