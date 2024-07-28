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
import umc.kkijuk.server.member.controller.response.ResultResponse;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.dto.MemberFieldDto;
import umc.kkijuk.server.member.dto.MemberInfoChangeDto;
import umc.kkijuk.server.member.dto.MemberJoinDto;
import umc.kkijuk.server.member.dto.MemberPhoneNumberDto;
import umc.kkijuk.server.member.phoneauth.MessageService;
import umc.kkijuk.server.member.phoneauth.SmsCertificationDto;
import umc.kkijuk.server.member.phoneauth.SmsCertificationResponse;
import umc.kkijuk.server.member.service.MemberService;
import lombok.RequiredArgsConstructor;

import java.util.List;


@Tag(name = "member", description = "회원 관리 API")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MessageService messageService;

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
            Long loginUser = LoginUser.get().getId();
            memberService.join(memberJoinDto.toEntity());
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new CreateMemberResponse(loginUser , "Member created successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CreateMemberResponse("Member creation failed"));
        }
    }

    @Operation(
            summary = "내 정보 조회",
            description = "마이페이지에서 내 정보들을 가져옵니다.")
    @GetMapping("/myPage/info")
    public ResponseEntity<MemberInfoResponse> getInfo() {
        try {
            Long loginUser = LoginUser.get().getId();
            Member member = memberService.getMemberInfo(loginUser);
            MemberInfoResponse response = new MemberInfoResponse(
                    member.getEmail(),
                    member.getName(),
                    member.getPhoneNumber(),
                    member.getBirthDate()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
            summary = "내 정보 수정",
            description = "내 정보 수정 요청을 받아 성공/실패를 반환합니다.")
    @PutMapping("/myPage/info")
    public ResponseEntity<ResultResponse> changeMemberInfo(@RequestBody MemberInfoChangeDto memberInfoChangeDto) {
        Long loginUser = LoginUser.get().getId();
        try {
            memberService.updateMemberInfo(loginUser, memberInfoChangeDto);
            return ResponseEntity.ok()
                    .body(new ResultResponse("information update success"));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResultResponse("information update failed"));
        }

    }

    @Operation(
            summary = "관심분야 조회",
            description = "마이페이지에서 관심분야를 조회합니다.")
    @GetMapping("/myPage/field")
    public ResponseEntity<MemberFieldResponse> getField() {
        try {
            Long loginUser = LoginUser.get().getId();
            List<String> memberField = memberService.getMemberField(loginUser);
            return ResponseEntity.ok().body(new MemberFieldResponse(memberField));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
            summary = "관심분야 등록/수정",
            description = "초기/마이페이지에서 관심분야를 등록/수정합니다.")
    @PostMapping({"/field", "/myPage/field"})
    public ResponseEntity<MemberFieldResponse> postField(@RequestBody @Valid MemberFieldDto memberFieldDto) {
        try {
            Long loginUserId = LoginUser.get().getId();
            List<String> updatedMember = memberService.updateMemberField(loginUserId, memberFieldDto);
            MemberFieldResponse response = new MemberFieldResponse(updatedMember);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
            summary = "휴대폰 인증번호 요청",
            description = "휴대폰 인증정보를 요청합니다.")
    @PostMapping("/auth")
    public ResponseEntity<?> sendAuthNumber(@RequestBody @Valid MemberPhoneNumberDto memberPhoneNumberDto) {
        try {
            System.out.println("memberPhoneNumberDto = " + memberPhoneNumberDto);
            SmsCertificationResponse smsCertificationResponse = messageService.sendSMS(memberPhoneNumberDto.getPhoneNumber());
            return ResponseEntity.ok().body(smsCertificationResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send authentication number: " + e.getMessage());
        }
    }

    @Operation(
            summary = "휴대폰 인증번호 인증",
            description = "휴대폰 인증번호를 인증합니다.")
    @PostMapping("/auth/confirm")
    public ResponseEntity<?> confirmAuthNumber(@RequestBody @Valid SmsCertificationDto smsCertificationDto) {
        try {
            return ResponseEntity.ok(messageService.verifySms(smsCertificationDto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Verification failed: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during verification: " + e.getMessage());
        }
    }
}





