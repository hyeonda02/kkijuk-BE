package umc.kkijuk.server.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.kkijuk.server.member.dto.MemberJoinDto;
import umc.kkijuk.server.member.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Tag(name = "member", description = "회원 관리 API")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(
            summary = "회원가입 요청",
            description = "회원가입 요청을 받아 성공/실패 여부를 반환합니다.")
    @PostMapping
    public ResponseEntity<CreateMemberResponse> saveMember(@RequestBody @Valid MemberJoinDto memberJoinDto) {
        String passwordConfirm = memberJoinDto.getPasswordConfirm();
        if (!passwordConfirm.equals(memberJoinDto.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new CreateMemberResponse("Passwords do not match"));
        }

        try {
            Long memberId = memberService.join(memberJoinDto.toEntity());
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new CreateMemberResponse(memberId, "Member created successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CreateMemberResponse("Member creation failed"));
        }
    }

    @Data
    static class CreateMemberResponse {
        private Long id;
        private String message;

        public CreateMemberResponse(String message) {
            this.message = message;
        }

        public CreateMemberResponse(Long id, String message) {
            this.id = id;
            this.message = message;
        }
    }
}
