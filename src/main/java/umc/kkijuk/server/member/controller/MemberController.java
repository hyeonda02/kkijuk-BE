package umc.kkijuk.server.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.kkijuk.server.common.LoginUser;
import umc.kkijuk.server.member.controller.response.CreateMemberResponse;
import umc.kkijuk.server.member.controller.response.MemberFieldResponse;
import umc.kkijuk.server.member.controller.response.MemberInfoResponse;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.dto.MemberFieldDto;
import umc.kkijuk.server.member.dto.MemberInfoChangeDto;
import umc.kkijuk.server.member.dto.MemberJoinDto;
import umc.kkijuk.server.member.dto.MemberPasswordChangeDto;
import umc.kkijuk.server.member.service.MemberService;
import lombok.RequiredArgsConstructor;

import java.util.List;


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
        Member joinMember = memberService.join(memberJoinDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CreateMemberResponse(joinMember.getId(), "Member created successfully"));
    }

    @Operation(
            summary = "내 정보 조회",
            description = "마이페이지에서 내 정보들을 가져옵니다.")
    @GetMapping("/myPage/info")
    public ResponseEntity<MemberInfoResponse> getInfo() {
        Long loginUser = LoginUser.get().getId();
        MemberInfoResponse memberInfoResponse = memberService.getMemberInfo(loginUser);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberInfoResponse);
    }

    @Operation(
            summary = "내 정보 수정",
            description = "내 정보 수정 요청을 받아 성공/실패를 반환합니다.")
    @PutMapping("/myPage/info")
    public ResponseEntity<Boolean> changeMemberInfo(@RequestBody @Valid  MemberInfoChangeDto memberInfoChangeDto) {
        Long loginUser = LoginUser.get().getId();
        memberService.updateMemberInfo(loginUser, memberInfoChangeDto);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @Operation(
            summary = "관심분야 조회",
            description = "마이페이지에서 관심분야를 조회합니다.")
    @GetMapping("/myPage/field")
    public ResponseEntity<MemberFieldResponse> getField() {
        Long loginUser = LoginUser.get().getId();
        List<String> memberField = memberService.getMemberField(loginUser);
        return ResponseEntity.ok().body(new MemberFieldResponse(memberField));
    }

    @Operation(
            summary = "관심분야 등록/수정",
            description = "초기/마이페이지에서 관심분야를 등록/수정합니다.")
    @PostMapping({"/field", "/myPage/field"})
    public ResponseEntity<Boolean> postField(@RequestBody MemberFieldDto memberFieldDto) {

        Long loginUser = LoginUser.get().getId();
        memberService.updateMemberField(loginUser, memberFieldDto);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @Operation(
            summary = "비밀번호 변경",
            description = "비밀번호를 변경합니다.")
    @PostMapping("myPage/password")
    public ResponseEntity<Boolean> changePassword(@RequestBody @Valid MemberPasswordChangeDto memberPasswordChangeDto){
        Long loginUser = LoginUser.get().getId();
        memberService.changeMemberPassword(loginUser, memberPasswordChangeDto);
        return ResponseEntity.ok(Boolean.TRUE);
    }



}






