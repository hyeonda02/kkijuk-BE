package umc.kkijuk.server.review.controller.response;

import lombok.Builder;
import lombok.Getter;
import umc.kkijuk.server.review.domain.RecruitReviewDto;

import java.time.LocalDate;

@Getter
@Builder
public class ReviewDto {
    private final Long reviewId;
    private final String reviewTitle;
    private final String reviewContent;
    private final LocalDate reviewDate;

    public static ReviewDto from(RecruitReviewDto recruitReviewDto) {
        return ReviewDto.builder()
                .reviewId(recruitReviewDto.getReviewId())
                .reviewTitle(recruitReviewDto.getReviewTitle())
                .reviewContent(recruitReviewDto.getReviewContent())
                .reviewDate(recruitReviewDto.getReviewDate())
                .build();
    }
}
