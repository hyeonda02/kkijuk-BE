package umc.kkijuk.server.review.controller.response;

import lombok.Builder;
import lombok.Getter;
import umc.kkijuk.server.review.domain.RecruitReviewDto;
import umc.kkijuk.server.recruit.domain.RecruitStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class ReviewByKeyword {
    private final Long recruitId;
    private final String recruitTitle;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final RecruitStatus status;
    private final List<ReviewDto> reviews;

    public static ReviewByKeyword from(RecruitReviewDto recruitReviewDto) {
        List<ReviewDto> reviews = new ArrayList<>();
        reviews.add(ReviewDto.from(recruitReviewDto));

        return ReviewByKeyword.builder()
                .recruitId(recruitReviewDto.getRecruitId())
                .recruitTitle(recruitReviewDto.getRecruitTitle())
                .startTime(recruitReviewDto.getStartTime())
                .endTime(recruitReviewDto.getEndTime())
                .status(recruitReviewDto.getStatus())
                .reviews(reviews)
                .build();
    }

    public void addReview(RecruitReviewDto recruitReviewDto) {
        this.reviews.add(ReviewDto.from(recruitReviewDto));
    }
}
