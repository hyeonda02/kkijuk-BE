package umc.kkijuk.server.review.controller.response;

import lombok.Builder;
import lombok.Getter;
import umc.kkijuk.server.review.domain.Review;

@Getter
@Builder
public class ReviewIdResponse {
    private Long id;

    public static ReviewIdResponse from(Review review) {
        return ReviewIdResponse.builder()
                .id(review.getId())
                .build();
    }
}
