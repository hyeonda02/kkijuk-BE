package umc.kkijuk.server.introduce.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.kkijuk.server.introduce.common.BaseResponse;
import umc.kkijuk.server.introduce.dto.*;
import umc.kkijuk.server.introduce.error.BaseErrorResponse;
import umc.kkijuk.server.introduce.error.BaseException;
import umc.kkijuk.server.introduce.service.IntroduceService;

import java.util.List;

@Tag(name = "introduce", description = "자기소개서 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/history/intro/")
@Slf4j
public class IntroduceController {
    private final IntroduceService introduceService;

    @PostMapping("/{recruitId}")
    @Operation(summary = "자기소개서 생성")
    public ResponseEntity<Object> save(@PathVariable("recruitId") Long recruitId, @RequestBody IntroduceReqDto introduceReqDto){
        try {
            IntroduceResDto introduceResDto = introduceService.saveIntro(recruitId, introduceReqDto);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "자기소개서 생성 완료", introduceResDto));
        } catch (BaseException e) {
            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseErrorResponse(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            log.info("Exception occurred: ", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server error"));
        }
    }

   @GetMapping("detail/{introId}")
   @Operation(summary = "자기소개서 개별 조회")
    public ResponseEntity<Object> get(@PathVariable("introId") Long introId){
        try {
            IntroduceResDto introduceResDto = introduceService.getIntro(introId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "자기소개서 조회 완료", introduceResDto));
        } catch (BaseException e) {
            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseErrorResponse(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            log.info("Exception occurred: ", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server error"));
        }
    }

    @GetMapping("list")
    @Operation(summary = "자기소개서 목록 조회")
    public ResponseEntity<Object> getList(){
        try {
            List<IntroduceListResDto> introduceListResDtos = introduceService.getIntroList();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "자기소개서 목록 조회 완료", introduceListResDtos));
        } catch (BaseException e) {
            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseErrorResponse(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            log.info("Exception occurred: ", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server error"));
        }
    }

    @PatchMapping("/{introId}")
    @Operation(summary = "자기소개서 수정")
    public ResponseEntity<Object> update(@PathVariable("introId") Long introId, @RequestBody IntroduceReqDto introduceReqDto){
        try {
            IntroduceResDto introduceResDto = introduceService.updateIntro(introId, introduceReqDto);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "자기소개서 수정 완료", introduceResDto));
        } catch (BaseException e) {
            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseErrorResponse(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            log.info("Exception occurred: ", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server error "+e.getMessage()+e.getCause()));
        }
    }

    @DeleteMapping("/{introId}")
    @Operation(summary = "자기소개서 삭제")
    public ResponseEntity<Object> delete(@PathVariable("introId") Long introId){
        try {
            Long intro_Id = introduceService.deleteIntro(introId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "자기소개서 삭제 완료", intro_Id));
        } catch (BaseException e) {
            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseErrorResponse(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            log.info("Exception occurred: ", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server error"));
        }
    }
}
