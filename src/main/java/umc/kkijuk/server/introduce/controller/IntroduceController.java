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
import umc.kkijuk.server.introduce.dto.*;
import umc.kkijuk.server.introduce.service.IntroduceService;
import umc.kkijuk.server.member.domain.Member;

import java.util.List;

@Tag(name = "introduce", description = "자기소개서 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/history/intro/")
public class IntroduceController {
    private final IntroduceService introduceService;

    private final Member requestMember = Member.builder()
            .id(LoginUser.get().getId())
            .build();

    @PostMapping("/{recruitId}")
    @Operation(summary = "자기소개서 생성")
    public ResponseEntity<Object> save(@PathVariable("recruitId") Long recruitId, @RequestBody IntroduceReqDto introduceReqDto){
        IntroduceResDto introduceResDto = introduceService.saveIntro(requestMember, recruitId, introduceReqDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "자기소개서 생성 완료", introduceResDto));
    }

   @GetMapping("detail/{introId}")
   @Operation(summary = "자기소개서 개별 조회")
    public ResponseEntity<Object> get(@PathVariable("introId") Long introId){
       IntroduceResDto introduceResDto = introduceService.getIntro(requestMember, introId);
       return ResponseEntity
               .status(HttpStatus.OK)
               .body(new BaseResponse<>(HttpStatus.OK.value(), "자기소개서 조회 완료", introduceResDto));
    }

    @GetMapping("list")
    @Operation(summary = "자기소개서 목록 조회")
    public ResponseEntity<Object> getList(){
        List<IntroduceListResDto> introduceListResDtos = introduceService.getIntroList(requestMember);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "자기소개서 목록 조회 완료", introduceListResDtos));
    }

    @PatchMapping("/{introId}")
    @Operation(summary = "자기소개서 수정")
    public ResponseEntity<Object> update(@PathVariable("introId") Long introId, @RequestBody IntroduceReqDto introduceReqDto) throws Exception {
        IntroduceResDto introduceResDto = introduceService.updateIntro(requestMember, introId, introduceReqDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "자기소개서 수정 완료", introduceResDto));
    }

    @DeleteMapping("/{introId}")
    @Operation(summary = "자기소개서 삭제")
    public ResponseEntity<Object> delete(@PathVariable("introId") Long introId){
        Long intro_Id = introduceService.deleteIntro(requestMember, introId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "자기소개서 삭제 완료", intro_Id));
    }
}
