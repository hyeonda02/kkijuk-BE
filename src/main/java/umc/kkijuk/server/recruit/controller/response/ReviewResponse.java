package umc.kkijuk.server.recruit.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import umc.kkijuk.server.recruit.domain.Review;

import java.time.LocalDate;

@Getter
@Builder
public class ReviewResponse {
    private final Long reviewId;
    private final String title;
    private final String content;

    @Schema(description = "날짜", example = "2024-07-19")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private final LocalDate date;

    public static ReviewResponse from(Review review) {
        return ReviewResponse.builder()
                .reviewId(review.getId())
                .title(review.getTitle())
                .content(review.getContent())
                .date(review.getDate())
                .build();
    }
}
