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
import umc.kkijuk.server.recruit.controller.port.RecruitService;
import umc.kkijuk.server.recruit.controller.response.RecruitInfoResponse;
import umc.kkijuk.server.recruit.controller.response.RecruitListByEndDateResponse;
import umc.kkijuk.server.recruit.controller.response.RecruitListByEndTimeAfterResponse;
import umc.kkijuk.server.recruit.domain.*;
import umc.kkijuk.server.recruit.controller.response.RecruitIdResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "recruit", description = "모집 공고 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/recruit")
public class RecruitController {
    private final RecruitService recruitService;

    @Operation(
            summary = "지원 공고 생성",
            description = "주어진 정보를 바탕으로 지원 공고 데이터를 생성합니다.")
    @PostMapping
    public ResponseEntity<RecruitIdResponse> create(@RequestBody @Valid RecruitCreate recruitCreate) {
        LoginUser loginUser = LoginUser.get();
        Recruit recruit = recruitService.create(recruitCreate);
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
        LoginUser loginUser = LoginUser.get();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recruitService.update(recruitId, recruitUpdate).getId());
    }

    @Operation(
            summary = "지원 공고 상태 수정",
            description = "다음 중 주어진 상태로 지원 공고의 상태를 수정합니다." +  " [UNAPPLIED / PLANNED / APPLYING / REJECTED / ACCEPTED]")
    @Parameter(name = "recruitId", description = "지원 공고 ID", example = "1")
    @PatchMapping("/{recruitId}")
    public ResponseEntity<Long> updateState(@RequestBody @Valid RecruitStatusUpdate recruitStatusUpdate,
                                            @PathVariable long recruitId) {
        LoginUser loginUser = LoginUser.get();
        return ResponseEntity
                .ok()
                .body(recruitService.updateStatus(recruitId, recruitStatusUpdate).getId());
    }

    @Operation(
            summary = "지원 공고 삭제",
            description = "지원 공고 ID에 해당 하는 공고를 삭제합니다")
    @Parameter(name = "recruitId", description = "지원 공고 ID", example = "1")
    @DeleteMapping("/{recruitId}")
    public ResponseEntity<Long> delete(@PathVariable long recruitId) {
        LoginUser loginUser = LoginUser.get();
        return ResponseEntity
                .ok()
                .body(recruitService.disable(recruitId).getId());
    }

    // review 만든 후 테스트 코드 작성
    @Operation(
            summary = "지원 공고 상세",
            description = "지원 공고 ID에 해당하는 공고의 상세 정보를 요청합니다.")
    @Parameter(name = "recruitId", description = "지원 공고 ID", example = "1")
    @GetMapping("/{recruitId}")
    public ResponseEntity<RecruitInfoResponse> getRecruitInfo(@PathVariable long recruitId) {
        LoginUser loginUser = LoginUser.get();
        Recruit recruit = recruitService.getById(recruitId);
//        List<Review> reviews = reviewService.getByRecruitId(recruitId);

        return ResponseEntity
                .ok()
                .body(RecruitInfoResponse.from(recruit, new ArrayList<>()));
    }
    @Operation(
        summary = "지원 공고 목록 (특정 날짜 이후)",
        description = "주어진 날짜에 마감 종료되는 지원 공고들의 목록을 요청합니다.")
    @GetMapping("/list/end")
    public ResponseEntity<RecruitListByEndDateResponse> findAllRecruitListByEndTime(
            @Parameter(name = "date", description = "지원 공고 마감 날짜", example = "2024-07-20")
            @RequestParam LocalDate date) {
        List<Recruit> recruits = recruitService.findAllByEndTime(date);
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
        List<Recruit> recruits = recruitService.findAllByEndTimeAfter(time);
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
