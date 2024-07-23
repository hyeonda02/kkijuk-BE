package umc.kkijuk.server.review.domain;

import lombok.Builder;
import lombok.Getter;
import umc.kkijuk.server.recruit.domain.Recruit;

import java.time.LocalDate;

@Getter
@Builder
public class Review {
    private final Long id;
    private final Long recruitId;
    private final String title;
    private final String content;
    private final LocalDate date;

    public static Review from(Recruit recruit, ReviewCreate reviewCreate) {
        return Review.builder()
                .recruitId(recruit.getId())
                .title(reviewCreate.getTitle())
                .content(reviewCreate.getContent())
                .date(reviewCreate.getDate())
                .build();
    }

    public Review update(ReviewUpdate reviewUpdate) {
        return Review.builder()
                .id(this.id)
                .recruitId(this.recruitId)
                .title(reviewUpdate.getTitle())
                .content(reviewUpdate.getContent())
                .date(reviewUpdate.getDate())
                .build();
    }
}
