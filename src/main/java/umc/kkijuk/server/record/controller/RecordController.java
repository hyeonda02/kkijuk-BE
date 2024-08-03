package umc.kkijuk.server.record.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.kkijuk.server.common.LoginUser;
import umc.kkijuk.server.introduce.common.BaseResponse;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.record.dto.EducationReqDto;
import umc.kkijuk.server.record.dto.EducationResDto;
import umc.kkijuk.server.record.dto.RecordReqDto;
import umc.kkijuk.server.record.dto.RecordResDto;
import umc.kkijuk.server.record.service.RecordService;

@Tag(name = "record", description = "이력서 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/history/resume/")
public class RecordController {
    private final RecordService recordService;

    private final Member requestMember = Member.builder()
            .id(LoginUser.get().getId())
            .build();

    @PostMapping
    @Operation(summary = "이력서 생성")
    public ResponseEntity<Object> save(@RequestBody RecordReqDto recordReqDto){
        Long id = recordService.saveRecord(requestMember, recordReqDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "이력서 생성 완료","id: "+id));
    }

    @GetMapping
    @Operation(summary = "이력서 전체 조회")
    public ResponseEntity<Object> get(){
        RecordResDto recordResDto = recordService.getRecord(requestMember);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "이력서 전체 조회 완료", recordResDto));
    }

    @PatchMapping
    @Operation(summary = "이력서 정보 수정")
    public ResponseEntity<Object> update(Long recordId, @RequestBody RecordReqDto recordReqDto){
        RecordResDto recordResDto = recordService.updateRecord(requestMember, recordId, recordReqDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "이력서 수정 완료", recordResDto));
    }

    @PostMapping("/education")
    @Operation(summary = "학력 생성")
    public ResponseEntity<Object> saveEducation(Long recordId, @RequestBody EducationReqDto educationReqDto){
        Long id = recordService.saveEducation(recordId, educationReqDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "학력 생성 완료", "id: "+id));
    }
}
