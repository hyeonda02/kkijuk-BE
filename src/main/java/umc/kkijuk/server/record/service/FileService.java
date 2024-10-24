package umc.kkijuk.server.record.service;


import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.record.controller.response.FileResponse;
import umc.kkijuk.server.record.dto.FileReqDto;

import java.util.Map;

public interface FileService {
    Map<String, String> getSignUrl (Member requestMember, String fileName);
    FileResponse createFile(Member requestMember, FileReqDto request);
    String findKeyNameByFileName(String fileName);
    Map<String, String> getDownloadUrl(Member requestMember, String keyName);
}
