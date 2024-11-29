package umc.kkijuk.server.record.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.kkijuk.server.common.LoginUser;
import umc.kkijuk.server.introduce.common.BaseResponse;
import umc.kkijuk.server.login.argumentresolver.Login;
import umc.kkijuk.server.login.controller.dto.LoginInfo;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.service.MemberService;
import umc.kkijuk.server.record.controller.response.*;
import umc.kkijuk.server.record.dto.*;
import umc.kkijuk.server.record.service.RecordService;

@Tag(name = "record", description = "이력서 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/history/resume")
public class RecordController {
    private final RecordService recordService;
    private final MemberService memberService;

    private final Member requestMember = Member.builder()
            .id(LoginUser.get().getId())
            .build();

    @PostMapping
    @Operation(summary = "이력서 생성")
    public ResponseEntity<Object> save(
            @Login LoginInfo loginInfo,
            @RequestBody RecordReqDto recordReqDto){
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        RecordResponse recordResponse = recordService.saveRecord(requestMember, recordReqDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "이력서 생성 완료", recordResponse));
    }

    @GetMapping
    @Operation(summary = "이력서 전체 조회")
    public ResponseEntity<Object> get(@Login LoginInfo loginInfo){
        RecordResponse recordResponse = recordService.getRecord(loginInfo.getMemberId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "이력서 전체 조회 완료", recordResponse));
    }

    @PatchMapping
    @Operation(summary = "이력서 정보 수정")
    public ResponseEntity<Object> update(
            @Login LoginInfo loginInfo,
             @RequestBody RecordReqDto recordReqDto){
        RecordResponse recordResponse = recordService.updateRecord(loginInfo.getMemberId(),
                recordService.findByMemberId(loginInfo.getMemberId()).getId(), recordReqDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "이력서 수정 완료", recordResponse));
    }

    @GetMapping("/download")
    @Operation(
            summary = "이력서 내보내기",
            description = "이력서 내보내기에 필요한 정보들을 조회합니다."
    )
    public ResponseEntity<Object> downloadResume(
            @Login LoginInfo loginInfo ){
        Long memberId = loginInfo.getMemberId();
        RecordDownResponse response = recordService.downloadResume(recordService.findByMemberId(memberId).getId(),memberId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(),"이력서 내보내기 정보 조회 완료",response));

    }


    @PostMapping("/education")
    @Operation(summary = "학력 생성")
    public ResponseEntity<Object> saveEducation(
            @Login LoginInfo loginInfo,
             @RequestBody EducationReqDto educationReqDto){
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        EducationResponse educationResponse = recordService.saveEducation(requestMember,
                recordService.findByMemberId(loginInfo.getMemberId()).getId(), educationReqDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "학력 생성 완료", educationResponse));
    }

    @PatchMapping("/education")
    @Operation(summary = "학력 수정")
    public ResponseEntity<Object> patchEducation(
            @Login LoginInfo loginInfo,
            Long educationId, @RequestBody EducationReqDto educationReqDto){
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        EducationResponse educationResponse = recordService.updateEducation(requestMember, educationId, educationReqDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "학력 수정 완료", educationResponse));
    }

    @DeleteMapping("/education")
    @Operation(summary = "학력 삭제")
    public ResponseEntity<Object> deleteEducation(@Login LoginInfo loginInfo, Long educationId){
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        Long id = recordService.deleteEducation(requestMember, educationId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "학력 삭제 완료", "id: "+id));
    }


    @PostMapping("/license")
    @Operation(summary = "자격증 생성")
    public ResponseEntity<Object> saveLicense(
            @Login LoginInfo loginInfo,
            @RequestBody LicenseReqDto licenseReqDto){
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        LicenseResponse licenseResponse = recordService.saveLicense(requestMember,
                recordService.findByMemberId(loginInfo.getMemberId()).getId(), licenseReqDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "자격증 생성 완료", licenseResponse));
    }

    @PatchMapping("/license")
    @Operation(summary = "자격증 수정")
    public ResponseEntity<Object> patchLicense(
            @Login LoginInfo loginInfo,
            Long licenseId, @RequestBody LicenseReqDto licenseReqDto){
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        LicenseResponse licenseResponse = recordService.updateLicense(requestMember, licenseId, licenseReqDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "자격증 수정 완료", licenseResponse));
    }

    @DeleteMapping("/license")
    @Operation(summary = "자격증 삭제")
    public ResponseEntity<Object> deleteLicense(@Login LoginInfo loginInfo, Long licenseId){
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        Long id = recordService.deleteLicense(requestMember, licenseId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "자격증 삭제 완료", "id: " + id));
    }

    @PostMapping("/award")
    @Operation(summary = "수상 생성")
    public ResponseEntity<Object> saveAward(
            @Login LoginInfo loginInfo,
            @RequestBody AwardReqDto awardReqDto){
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        AwardResponse awardResponse = recordService.saveAward(requestMember,
                recordService.findByMemberId(loginInfo.getMemberId()).getId(), awardReqDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "수상 생성 완료", awardResponse));
    }

    @PatchMapping("/award")
    @Operation(summary = "수상 수정")
    public ResponseEntity<Object> patchAward(
            @Login LoginInfo loginInfo,
            Long awardId, @RequestBody AwardReqDto awardReqDto){
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        AwardResponse awardResponse = recordService.updateAward(requestMember, awardId, awardReqDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "수상 수정 완료", awardResponse));
    }

    @DeleteMapping("/award")
    @Operation(summary = "수상 삭제")
    public ResponseEntity<Object> deleteAward(@Login LoginInfo loginInfo, Long awardId){
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        Long id = recordService.deleteAward(requestMember, awardId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "수상 삭제 완료", "id: " + id));
    }

    @PostMapping("/skill")
    @Operation(summary = "스킬 생성")
    public ResponseEntity<Object> saveSkill(
            @Login LoginInfo loginInfo,
            @RequestBody SkillReqDto skillReqDto){
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        SkillResponse skillResponse = recordService.saveSkill(requestMember,
                recordService.findByMemberId(loginInfo.getMemberId()).getId(), skillReqDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "스킬 생성 완료", skillResponse));
    }

    @PatchMapping("/skill")
    @Operation(summary = "스킬 수정")
    public ResponseEntity<Object> patchSkill(
            @Login LoginInfo loginInfo,
            Long skillId, @RequestBody SkillReqDto skillReqDto){
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        SkillResponse skillResponse = recordService.updateSkill(requestMember, skillId, skillReqDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "스킬 수정 완료", skillResponse));
    }

    @DeleteMapping("/skill")
    @Operation(summary = "스킬 삭제")
    public ResponseEntity<Object> deleteSkill(@Login LoginInfo loginInfo, Long skillId){
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        Long id = recordService.deleteSkill(requestMember, skillId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "스킬 삭제 완료", "id: " + id));
    }

}
