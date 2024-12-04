package umc.kkijuk.server.recruit.controller.response;

import lombok.Builder;
import lombok.Getter;
import umc.kkijuk.server.recruit.domain.Recruit;
import umc.kkijuk.server.recruit.domain.RecruitStatus;
import umc.kkijuk.server.review.domain.Review;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class RecruitByKeyword {
    private final Long recruitId;
    private final String recruitTitle;
    private final List<String> tags;
    private final String reviewTag;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final RecruitStatus status;

    public static RecruitByKeyword from(Recruit recruit,String reviewTitle) {
        return RecruitByKeyword.builder()
                .recruitId(recruit.getId())
                .recruitTitle(recruit.getTitle())
                .tags(recruit.getTags())
                .reviewTag(reviewTitle)
                .startTime(recruit.getStartTime())
                .endTime(recruit.getEndTime())
                .status(recruit.getStatus())
                .build();
    }
}
