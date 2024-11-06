package umc.kkijuk.server.introduce.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.kkijuk.server.common.LoginUser;
import umc.kkijuk.server.introduce.common.BaseResponse;
import umc.kkijuk.server.introduce.controller.response.IntroduceListResponse;
import umc.kkijuk.server.introduce.controller.response.IntroduceResponse;
import umc.kkijuk.server.introduce.dto.*;
import umc.kkijuk.server.introduce.service.IntroduceService;
import umc.kkijuk.server.login.argumentresolver.Login;
import umc.kkijuk.server.login.controller.dto.LoginInfo;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.service.MemberService;

import java.util.List;

@Tag(name = "introduce", description = "자기소개서 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/history/intro/")
public class IntroduceController {
    private final IntroduceService introduceService;
    private final MemberService memberService;

    private final Member requestMember = Member.builder()
            .id(LoginUser.get().getId())
            .build();

    @PostMapping("/{recruitId}")
    @Operation(summary = "자기소개서 생성")
    public ResponseEntity<Object> save(
            @Login LoginInfo loginInfo,
            @PathVariable("recruitId") Long recruitId, @RequestBody IntroduceReqDto introduceReqDto){
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        IntroduceResponse introduceResponse = introduceService.saveIntro(requestMember, recruitId, introduceReqDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "자기소개서 생성 완료", introduceResponse));
    }

   @GetMapping("detail/{introId}")
   @Operation(summary = "자기소개서 개별 조회")
    public ResponseEntity<Object> get(
           @Login LoginInfo loginInfo,
           @PathVariable("introId") Long introId){
       Member requestMember = memberService.getById(loginInfo.getMemberId());
       IntroduceResponse introduceResponse = introduceService.getIntro(requestMember, introId);
       return ResponseEntity
               .status(HttpStatus.OK)
               .body(new BaseResponse<>(HttpStatus.OK.value(), "자기소개서 조회 완료", introduceResponse));
    }

    @GetMapping("list")
    @Operation(summary = "자기소개서 목록 조회")
    public ResponseEntity<Object> getList(@Login LoginInfo loginInfo){
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        List<IntroduceListResponse> introduceListResponses = introduceService.getIntroList(requestMember);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "자기소개서 목록 조회 완료", introduceListResponses));
    }

    @PatchMapping("/{introId}")
    @Operation(summary = "자기소개서 수정")
    public ResponseEntity<Object> update(
            @Login LoginInfo loginInfo,
            @PathVariable("introId") Long introId, @RequestBody IntroduceReqDto introduceReqDto) throws Exception {
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        IntroduceResponse introduceResponse = introduceService.updateIntro(requestMember, introId, introduceReqDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "자기소개서 수정 완료", introduceResponse));
    }

    @DeleteMapping("/{introId}")
    @Operation(summary = "자기소개서 삭제")
    public ResponseEntity<Object> delete(
            @Login LoginInfo loginInfo,
            @PathVariable("introId") Long introId){
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        Long intro_Id = introduceService.deleteIntro(requestMember, introId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "자기소개서 삭제 완료", intro_Id));
    }

}
