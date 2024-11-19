package umc.kkijuk.server.record.service;


import umc.kkijuk.server.record.controller.response.FileResponse;
import umc.kkijuk.server.record.dto.FileReqDto;
import umc.kkijuk.server.record.dto.UrlReqDto;

import java.util.Map;

public interface FileService {
    Map<String, String> getSignUrl (Long memberId, String fileName);
    FileResponse saveFile(Long memberId, Long recordId, FileReqDto request);
    Map<String, String> getDownloadUrl(Long memberId, String keyName);
    FileResponse deleteFile(Long memberId, String fileName);
    FileResponse saveUrl(Long memberId, Long recordId, UrlReqDto urlReqDto);
    FileResponse deleteUrl(Long memberId, UrlReqDto urlReqDto);
}
