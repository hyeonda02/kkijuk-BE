package umc.kkijuk.server.introduce.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.kkijuk.server.introduce.common.BaseResponse;
import umc.kkijuk.server.introduce.domain.MasterIntroduce;
import umc.kkijuk.server.introduce.dto.MasterIntroduceReqDto;
import umc.kkijuk.server.introduce.dto.MasterIntroduceResDto;
import umc.kkijuk.server.introduce.error.BaseErrorResponse;
import umc.kkijuk.server.introduce.error.BaseException;
import umc.kkijuk.server.introduce.service.MasterIntroduceService;

import java.util.List;

@Tag(name = "master", description = "마스터 자기소개서 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/history/intro/master")
@Slf4j
public class MasterIntroduceController {
    private final MasterIntroduceService masterIntroduceService;

    @PostMapping
    @Operation(summary = "마스터 자기소개서 생성")
    public ResponseEntity<Object> save(@RequestBody MasterIntroduceReqDto masterIntroduceReqDto) throws Exception {
        MasterIntroduceResDto masterIntroduceResDto = masterIntroduceService.saveMasterIntro(masterIntroduceReqDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "마스터 자기소개서 생성 완료", masterIntroduceResDto));
    }

    @GetMapping
    @Operation(summary = "마스터 자기소개서 조회")
    public ResponseEntity<Object> get(){
        List<MasterIntroduceResDto> masterIntroduce = masterIntroduceService.getMasterIntro();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "마스터 자기소개서 조회 완료", masterIntroduce));
    }

    @PatchMapping
    @Operation(summary = "마스터 자기소개서 수정")
    public ResponseEntity<Object> update(Long id, @RequestBody MasterIntroduceReqDto masterIntroduceReqDto) throws Exception {
        MasterIntroduceResDto masterIntroduceResDto = masterIntroduceService.updateMasterIntro(id, masterIntroduceReqDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "마스터 자기소개서 수정 완료", masterIntroduceResDto));
    }

}
