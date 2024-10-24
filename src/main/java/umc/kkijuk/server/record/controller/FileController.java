package umc.kkijuk.server.record.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.kkijuk.server.introduce.common.BaseResponse;
import umc.kkijuk.server.login.argumentresolver.Login;
import umc.kkijuk.server.login.controller.dto.LoginInfo;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.service.MemberService;
import umc.kkijuk.server.record.controller.response.FileResponse;
import umc.kkijuk.server.record.dto.FileReqDto;
import umc.kkijuk.server.record.service.FileService;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@Tag(name = "file", description = "File 관련 API")
public class FileController {

    private final FileService fileService;
    private final MemberService memberService;

    @GetMapping(value="/file")
    @Operation(summary = "파일 업로드를 위한 url 생성 API", description = "S3에 접근하기 위한 Presigned URL을 반환합니다.")
    public ResponseEntity<BaseResponse<Map<String,String>>> createFileUrl(@Login LoginInfo loginInfo, @RequestParam String fileName) {
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        Map<String, String> response = fileService.getSignUrl(requestMember,fileName);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new BaseResponse<>(HttpStatus.CREATED.value(), "Presigned URL 반환 완료", response));
    }

    @PostMapping(value="/file")
    @Operation(summary = "파일 keyName 저장 API", description = "주어진 keyName을 바탕으로 해당 파일에 대한 정보를 저장합니다.")
    public ResponseEntity<BaseResponse<FileResponse>> createFile(@Login LoginInfo loginInfo, @Valid @RequestBody FileReqDto request) {
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        FileResponse fileResponse = fileService.createFile(requestMember,request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new BaseResponse<>(HttpStatus.CREATED.value(), "파일 정보 저장 완료",fileResponse));
    }

    @GetMapping("/file/download")
    @Operation(summary = "파일 다운로드용 URL 생성", description = "fileName에 해당하는 presigned URL을 반환합니다.")
    public ResponseEntity<BaseResponse<Map<String,String>>> getDownloadUrl(@Login LoginInfo loginInfo, @RequestParam String fileName) {
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        Map<String, String> response = fileService.getDownloadUrl(requestMember, fileName);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "파일 다운로드 URL 반환 완료", response));
    }

    @DeleteMapping("/file")
    @Operation(summary = "버킷 내 파일 삭제 API", description = "fileName으로 S3 버킷에서 파일을 삭제합니다.")
    public ResponseEntity<BaseResponse<FileResponse>> deleteFile(@Login LoginInfo loginInfo, @RequestParam String fileName){
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        FileResponse fileResponse = fileService.deleteFile(requestMember, fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "파일 삭제 완료", fileResponse));
    }

}
