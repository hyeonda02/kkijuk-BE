package umc.kkijuk.server.record.service;


import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.record.controller.response.FileResponse;
import umc.kkijuk.server.record.controller.response.UrlResponse;
import umc.kkijuk.server.record.dto.FileReqDto;
import umc.kkijuk.server.record.dto.UrlReqDto;

import java.util.Map;

public interface FileService {
    Map<String, String> getSignUrl (Long memberId, String fileName);
    FileResponse createFile(Long memberId, FileReqDto request);
    Map<String, String> getDownloadUrl(Long memberId, String keyName);
    FileResponse deleteFile(Long memberId, String fileName);
    UrlResponse saveUrl(Long memberId, UrlReqDto urlReqDto);
    UrlResponse deleteUrl(Long memberId, UrlReqDto urlReqDto);
}
