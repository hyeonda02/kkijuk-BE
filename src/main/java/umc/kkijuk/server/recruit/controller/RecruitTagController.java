package umc.kkijuk.server.recruit.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.kkijuk.server.common.LoginUser;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.service.MemberService;
import umc.kkijuk.server.recruit.controller.response.RecruitTagResponse;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "recruitTag", description = "모집 공고 태그 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/recruit/tag")
public class RecruitTagController {
    private final MemberService memberService;

    private final Member requestMember = Member.builder()
            .id(LoginUser.get().getId())
            .recruitTags(new ArrayList<>(List.of("인턴", "정규직", "대외활동", "동아리")))
            .build();

    @Operation(
            summary = "지원 공고 태그",
            description = "사용자의 지원 공고 태그 정보들을 요청합니다")
    @GetMapping
    public ResponseEntity<RecruitTagResponse> getRecruitTag() {
//        Member requestMember = memberService.getById(1L);
        return ResponseEntity
                .ok()
                .body(RecruitTagResponse.from(requestMember.getRecruitTags()));
    }

    @Operation(
            summary = "지원 공고 태그 추가",
            description = "사용자의 지원 공고 태그를 추가합니다")
    @PostMapping
    public ResponseEntity<RecruitTagResponse> getRecruitTag(@RequestParam String tag) {
//        Member requestMember = memberService.getById(1L);
        List<String> tags = memberService.addRecruitTag(requestMember, tag);
        return ResponseEntity
                .ok()
                .body(RecruitTagResponse.from(tags));
    }

    @Operation(
            summary = "지원 공고 태그 제거",
            description = "사용자의 지원 공고 태그를 제거합니다")
    @DeleteMapping
    public ResponseEntity<RecruitTagResponse> deleteRecruitTag(@RequestParam String tag) {
//        Member requestMember = memberService.getById(1L);
        List<String> tags = memberService.deleteRecruitTag(requestMember, tag);
        return ResponseEntity
                .ok()
                .body(RecruitTagResponse.from(tags));
    }
}
