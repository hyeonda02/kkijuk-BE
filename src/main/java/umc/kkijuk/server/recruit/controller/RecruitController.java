package umc.kkijuk.server.recruit.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.kkijuk.server.common.LoginUser;
import umc.kkijuk.server.recruit.controller.port.RecruitService;
import umc.kkijuk.server.recruit.domain.RecruitStatusUpdate;
import umc.kkijuk.server.recruit.domain.RecruitUpdate;
import umc.kkijuk.server.recruit.controller.response.RecruitResponse;
import umc.kkijuk.server.recruit.domain.Recruit;
import umc.kkijuk.server.recruit.domain.RecruitCreateDto;

@Tag(name = "recruit", description = "모집 공고 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/recruit")
public class RecruitController {
    private final RecruitService recruitService;

    @PostMapping
    public ResponseEntity<RecruitResponse> create(@RequestBody @Valid RecruitCreateDto recruitCreateDto) {
        LoginUser loginUser = LoginUser.get();
        Recruit recruit = recruitService.create(recruitCreateDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(RecruitResponse.from(recruit));
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
}
