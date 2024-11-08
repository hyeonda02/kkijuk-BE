package umc.kkijuk.server.recruit.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import umc.kkijuk.server.recruit.domain.Recruit;
import umc.kkijuk.server.review.domain.RecruitReviewDto;
import umc.kkijuk.server.review.controller.response.ReviewByKeyword;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class RecruitReviewListByKeywordResponse {
    @Schema(description = "검색 키워드", example = "2024-07-19")
    private final String keyword;

    @Schema(description = "검색 키워드가 제목 또는 태그에 포함된 공고 목록")
    private final List<RecruitByKeyword> recruitResult;

    @Schema(description = "검색 키워드가 review에 포함된 공고 목록")
    private final List<ReviewByKeyword> reviewResult;

    public static RecruitReviewListByKeywordResponse from(String keyword, List<Recruit> recruits, List<RecruitReviewDto> reviews) {
        List<ReviewByKeyword> reviewResult = new ArrayList<>();

        for (RecruitReviewDto recruitReviewDto : reviews) {
            ReviewByKeyword existingReview = reviewResult.stream()
                    .filter(r -> r.getRecruitId().equals(recruitReviewDto.getRecruitId()))
                    .findFirst()
                    .orElse(null);

            if (existingReview == null) reviewResult.add(ReviewByKeyword.from(recruitReviewDto));
            else existingReview.addReview(recruitReviewDto);
        }


        return RecruitReviewListByKeywordResponse.builder()
                .keyword(keyword)
                .recruitResult(recruits.stream().map(RecruitByKeyword::from).toList())
                .reviewResult(reviewResult)
                .build();
    }
}
