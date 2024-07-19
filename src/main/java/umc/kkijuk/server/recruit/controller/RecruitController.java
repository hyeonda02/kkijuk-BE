package umc.kkijuk.server.recruit.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.kkijuk.server.common.LoginUser;
import umc.kkijuk.server.recruit.controller.port.RecruitService;
import umc.kkijuk.server.recruit.controller.response.RecruitInfoResponse;
import umc.kkijuk.server.recruit.domain.*;
import umc.kkijuk.server.recruit.controller.response.RecruitIdResponse;

import java.util.ArrayList;

@Tag(name = "recruit", description = "모집 공고 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/recruit")
public class RecruitController {
    private final RecruitService recruitService;

    @PostMapping
    public ResponseEntity<RecruitIdResponse> create(@RequestBody @Valid RecruitCreateDto recruitCreateDto) {
        LoginUser loginUser = LoginUser.get();
        Recruit recruit = recruitService.create(recruitCreateDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(RecruitIdResponse.from(recruit));
    }

    @PutMapping("/{recruitId}")
    public ResponseEntity<Long> update(@RequestBody @Valid RecruitUpdate recruitUpdate,
                                       @PathVariable long recruitId) {
        LoginUser loginUser = LoginUser.get();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recruitService.update(recruitId, recruitUpdate).getId());
    }

    @PatchMapping("/{recruitId}")
    public ResponseEntity<Long> updateState(@RequestBody @Valid RecruitStatusUpdate recruitStatusUpdate,
                                            @PathVariable long recruitId) {
        LoginUser loginUser = LoginUser.get();
        return ResponseEntity
                .ok()
                .body(recruitService.updateStatus(recruitId, recruitStatusUpdate).getId());
    }

    @DeleteMapping("/{recruitId}")
    public ResponseEntity<Long> delete(@PathVariable long recruitId) {
        LoginUser loginUser = LoginUser.get();
        return ResponseEntity
                .ok()
                .body(recruitService.disable(recruitId).getId());
    }

    // review 만든 후 테스트 코드 작성
    @GetMapping("/{recruitId}")
    public ResponseEntity<RecruitInfoResponse> getRecruitInfo(@PathVariable long recruitId) {
        LoginUser loginUser = LoginUser.get();
        Recruit recruit = recruitService.getById(recruitId);
//        List<Review> reviews = reviewService.getByRecruitId(recruitId);

        return ResponseEntity
                .ok()
                .body(RecruitInfoResponse.from(recruit, new ArrayList<>()));
    }
}
