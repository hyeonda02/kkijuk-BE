package umc.kkijuk.server.record.service;

import jakarta.transaction.Transactional;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.record.controller.response.EducationResponse;
import umc.kkijuk.server.record.controller.response.RecordResponse;
import umc.kkijuk.server.record.dto.EducationReqDto;
import umc.kkijuk.server.record.dto.RecordReqDto;

public interface RecordService {

    @Transactional
    RecordResponse saveRecord(Member requestMember, RecordReqDto recordReqDto);

    @Transactional
    EducationResponse saveEducation(Member requestMember, Long recordId, EducationReqDto educationReqDto);

    @Transactional
    Long deleteEducation(Member requestMember, Long educationId);

    @Transactional
    EducationResponse updateEducation(Member requestMember, Long educationId, EducationReqDto educationReqDto);

    @Transactional
    RecordResponse getRecord(Member requestMember);

    @Transactional
    RecordResponse updateRecord(Member requestMember, Long recordId, RecordReqDto recordReqDto);
}
