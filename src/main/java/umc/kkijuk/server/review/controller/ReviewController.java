package umc.kkijuk.server.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.kkijuk.server.recruit.controller.port.RecruitService;
import umc.kkijuk.server.recruit.domain.Recruit;
import umc.kkijuk.server.review.controller.port.ReviewService;
import umc.kkijuk.server.review.controller.response.ReviewIdResponse;
import umc.kkijuk.server.review.domain.Review;
import umc.kkijuk.server.review.domain.ReviewCreate;

@Tag(name = "review", description = "모집 공고 후기 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/recruit/{recruitId}")
public class ReviewController {
    private final ReviewService reviewService;
    private final RecruitService recruitService;

    @Operation(
            summary = "지원 공고 후기 추가",
            description = "주어진 지원 공고에 후기를 생성합니다")
    @Parameter(name = "recruitId", description = "지원 공고 ID", example = "1")
    @PostMapping("/review")
    public ResponseEntity<ReviewIdResponse> create(
            @PathVariable Long recruitId,
            @RequestBody @Valid ReviewCreate reviewCreate
    ) {
        Recruit recruit = recruitService.getById(recruitId);
        Review review = reviewService.create(recruit, reviewCreate);

        return ResponseEntity
                .ok()
                .body(ReviewIdResponse.from(review));
    }
}
