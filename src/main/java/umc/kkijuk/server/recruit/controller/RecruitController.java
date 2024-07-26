package umc.kkijuk.server.recruit.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.kkijuk.server.common.LoginUser;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.recruit.controller.port.RecruitService;
import umc.kkijuk.server.recruit.controller.response.RecruitInfoResponse;
import umc.kkijuk.server.recruit.controller.response.RecruitListByEndDateResponse;
import umc.kkijuk.server.recruit.controller.response.RecruitListByEndTimeAfterResponse;
import umc.kkijuk.server.recruit.domain.*;
import umc.kkijuk.server.recruit.controller.response.RecruitIdResponse;
import umc.kkijuk.server.review.controller.port.ReviewService;
import umc.kkijuk.server.review.domain.Review;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "recruit", description = "모집 공고 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/recruit")
public class RecruitController {
    private final RecruitService recruitService;
    private final ReviewService reviewService;

    private final Member requestMember = Member.builder()
            .id(LoginUser.get().getId())
            .build();

    @Operation(
            summary = "지원 공고 생성",
            description = "주어진 정보를 바탕으로 지원 공고 데이터를 생성합니다.")
    @PostMapping
    public ResponseEntity<RecruitIdResponse> create(@RequestBody @Valid RecruitCreate recruitCreate) {
//        Member requestMember = memberService.findOne(LoginUser.get().getId());
        Recruit recruit = recruitService.create(requestMember, recruitCreate);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(RecruitIdResponse.from(recruit));
    }

    @Operation(
            summary = "지원 공고 수정",
            description = "주어진 정보를 바탕으로 지원 공고 데이터를 수정합니다.")
    @Parameter(name = "recruitId", description = "지원 공고 ID", example = "1")
    @PutMapping("/{recruitId}")
    public ResponseEntity<Long> update(@RequestBody @Valid RecruitUpdate recruitUpdate,
                                       @PathVariable long recruitId) {
//        Member requestMember = memberService.findOne(LoginUser.get().getId());
        Recruit recruit = recruitService.update(requestMember, recruitId, recruitUpdate);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recruit.getId());
    }

    @Operation(
            summary = "지원 공고 상태 수정",
            description = "다음 중 주어진 상태로 지원 공고의 상태를 수정합니다." +  " [UNAPPLIED / PLANNED / APPLYING / REJECTED / ACCEPTED]")
    @Parameter(name = "recruitId", description = "지원 공고 ID", example = "1")
    @PatchMapping("/{recruitId}")
    public ResponseEntity<Long> updateState(@RequestBody @Valid RecruitStatusUpdate recruitStatusUpdate,
                                            @PathVariable long recruitId) {
//        Member requestMember = memberService.findOne(LoginUser.get().getId());
        Recruit recruit = recruitService.updateStatus(requestMember, recruitId, recruitStatusUpdate);
        return ResponseEntity
                .ok()
                .body(recruit.getId());
    }

    @Operation(
            summary = "지원 공고 삭제",
            description = "지원 공고 ID에 해당 하는 공고를 삭제합니다")
    @Parameter(name = "recruitId", description = "지원 공고 ID", example = "1")
    @DeleteMapping("/{recruitId}")
    public ResponseEntity<Long> delete(@PathVariable long recruitId) {
//        Member requestMember = memberService.findOne(LoginUser.get().getId());
        Recruit recruit = recruitService.disable(requestMember, recruitId);
        return ResponseEntity
                .ok()
                .body(recruit.getId());
    }

    @Operation(
            summary = "지원 공고 상세",
            description = "지원 공고 ID에 해당하는 공고의 상세 정보를 요청합니다.")
    @Parameter(name = "recruitId", description = "지원 공고 ID", example = "1")
    @GetMapping("/{recruitId}")
    public ResponseEntity<RecruitInfoResponse> getRecruitInfo(@PathVariable long recruitId) {
        LoginUser loginUser = LoginUser.get();
        Recruit recruit = recruitService.getById(recruitId);
        List<Review> reviews = reviewService.findAllByRecruitId(recruitId);

        return ResponseEntity
                .ok()
                .body(RecruitInfoResponse.from(recruit, reviews));
    }

    @Operation(
        summary = "지원 공고 목록 (특정 날짜 이후)",
        description = "주어진 날짜에 마감 종료되는 지원 공고들의 목록을 요청합니다.")
    @GetMapping("/list/end")
    public ResponseEntity<RecruitListByEndDateResponse> findAllRecruitListByEndTime(
            @Parameter(name = "date", description = "지원 공고 마감 날짜", example = "2024-07-20")
            @RequestParam LocalDate date) {
//        Member requestMember = memberService.findOne(LoginUser.get().getId());
        List<Recruit> recruits = recruitService.findAllByEndTime(requestMember, date);
        return ResponseEntity
                .ok()
                .body(RecruitListByEndDateResponse.from(recruits));
    }

    @Operation(
            summary = "지원 공고 목록 (특정 시간 이후)",
            description = "주어진 시간 이후 마감 종료되는 지원 공고들의 목록을 요청합니다.")
    @GetMapping("/list/after")
    public ResponseEntity<RecruitListByEndTimeAfterResponse> findAllRecruitListByEndTimeAfterNow(
            @Parameter(name = "time", description = "이 시간 이후에 마감되는 공고를 요청", example = "2024-07-20 10:30")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
            @RequestParam LocalDateTime time) {
//        Member requestMember = memberService.findOne(LoginUser.get().getId());
        List<Recruit> recruits = recruitService.findAllByEndTimeAfter(requestMember, time);
        return ResponseEntity
                .ok()
                .body(RecruitListByEndTimeAfterResponse.from(recruits));
    }

    // 멤버 추가시 추가
//    @GetMapping("/list/valid")
//    public ResponseEntity<ValidRecruitListResponse> findValidRecruit() {
//
//    }
}
