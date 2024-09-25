package umc.kkijuk.server.record.service;

import org.springframework.transaction.annotation.Transactional;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.record.controller.response.EducationResponse;
import umc.kkijuk.server.record.controller.response.LicenseResponse;
import umc.kkijuk.server.record.controller.response.RecordResponse;
import umc.kkijuk.server.record.dto.EducationReqDto;
import umc.kkijuk.server.record.dto.LicenseReqDto;
import umc.kkijuk.server.record.dto.RecordReqDto;

@Transactional
public interface RecordService {

    RecordResponse saveRecord(Member requestMember, RecordReqDto recordReqDto);

    EducationResponse saveEducation(Member requestMember, Long recordId, EducationReqDto educationReqDto);

    Long deleteEducation(Member requestMember, Long educationId);

    EducationResponse updateEducation(Member requestMember, Long educationId, EducationReqDto educationReqDto);

    RecordResponse getRecord(Member requestMember);

    RecordResponse updateRecord(Member requestMember, Long recordId, RecordReqDto recordReqDto);

    LicenseResponse saveLicense(Member requestMember, Long recordId, LicenseReqDto licenseReqDto);

    LicenseResponse updateLicense(Member requestMember, Long licenseId, LicenseReqDto licenseReqDto);

    Long deleteLicense(Member requestMember, Long licenseId);
}
