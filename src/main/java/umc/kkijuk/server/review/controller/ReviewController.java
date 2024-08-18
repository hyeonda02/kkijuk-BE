package umc.kkijuk.server.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.kkijuk.server.login.argumentresolver.Login;
import umc.kkijuk.server.login.controller.dto.LoginInfo;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.service.MemberService;
import umc.kkijuk.server.recruit.controller.port.RecruitService;
import umc.kkijuk.server.recruit.domain.Recruit;
import umc.kkijuk.server.review.controller.port.ReviewService;
import umc.kkijuk.server.review.controller.response.ReviewIdResponse;
import umc.kkijuk.server.review.domain.Review;
import umc.kkijuk.server.review.domain.ReviewCreate;
import umc.kkijuk.server.review.domain.ReviewUpdate;

@Tag(name = "review", description = "모집 공고 후기 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/recruit/{recruitId}")
public class ReviewController {
    private final ReviewService reviewService;
    private final RecruitService recruitService;
    private final MemberService memberService;

//    private final Member requestMember = Member.builder()
//            .id(LoginUser.get().getId())
//            .build();

    @Operation(
            summary = "지원 공고 후기 추가",
            description = "주어진 지원 공고에 후기를 생성합니다")
    @Parameter(name = "recruitId", description = "지원 공고 ID", example = "1")
    @PostMapping("/review")
    public ResponseEntity<ReviewIdResponse> create(
            @Login LoginInfo loginInfo,
            @PathVariable Long recruitId,
            @RequestBody @Valid ReviewCreate reviewCreate
    ) {
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        Recruit recruit = recruitService.getById(recruitId);
        Review review = reviewService.create(requestMember, recruit, reviewCreate);

        return ResponseEntity
                .ok()
                .body(ReviewIdResponse.from(review));
    }

    @Operation(
            summary = "지원 공고 후기 수정",
            description = "주어진 지원 공고 후기를 수정합니다")
    @Parameter(name = "recruitId", description = "지원 공고 ID", example = "1")
    @Parameter(name = "reviewId", description = "지원 공고 후기 ID", example = "1")
    @PutMapping("/review/{reviewId}")
    public ResponseEntity<ReviewIdResponse> update(
            @Login LoginInfo loginInfo,
            @PathVariable Long recruitId,
            @PathVariable Long reviewId,
            @RequestBody @Valid ReviewUpdate reviewUpdate
    ) {
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        Recruit recruit = recruitService.getById(recruitId);
        Review review = reviewService.update(requestMember, recruit, reviewId, reviewUpdate);

        return ResponseEntity
                .ok()
                .body(ReviewIdResponse.from(review));
    }

    @Operation(
            summary = "지원 공고 후기 삭제",
            description = "주어진 지원 공고 후기를 삭제합니다")
    @Parameter(name = "recruitId", description = "지원 공고 ID", example = "1")
    @Parameter(name = "reviewId", description = "지원 공고 후기 ID", example = "1")
    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity<Void> delete(
            @Login LoginInfo loginInfo,
            @PathVariable Long recruitId,
            @PathVariable Long reviewId
    ) {
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        Recruit recruit = recruitService.getById(recruitId);
        reviewService.delete(requestMember, recruit, reviewId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
