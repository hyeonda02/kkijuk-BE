package umc.kkijuk.server.record.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.kkijuk.server.career.controller.response.*;
import umc.kkijuk.server.career.domain.*;
import umc.kkijuk.server.career.repository.BaseCareerRepository;
import umc.kkijuk.server.common.domian.exception.IntroFoundException;
import umc.kkijuk.server.common.domian.exception.IntroOwnerMismatchException;
import umc.kkijuk.server.common.domian.exception.ResourceNotFoundException;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.repository.MemberRepository;
import umc.kkijuk.server.record.controller.response.*;
import umc.kkijuk.server.record.domain.*;
import umc.kkijuk.server.record.domain.Record;
import umc.kkijuk.server.record.repository.*;
import umc.kkijuk.server.record.dto.*;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;
    private final MemberRepository memberRepository;
    private final EducationRepository educationRepository;
    private final LicenseRepository licenseRepository;
    private final AwardRepository awardRepository;
    private final SkillRepository skillRepository;

    private final BaseCareerRepository baseCareerRepository;

    @Override
    @Transactional
    public RecordResponse saveRecord(Member requestMember, RecordReqDto recordReqDto) {
        if (recordRepository.existsByMemberId(requestMember.getId())) {
            throw new IntroFoundException("이미 이력서가 존재합니다");
        }

        Record record = Record.builder()
                .memberId(requestMember.getId())
                .address(recordReqDto.getAddress())
                .profileImageUrl(recordReqDto.getProfileImageUrl())
                .build();

        recordRepository.save(record);

        return new RecordResponse(record, requestMember, null, null, null,null,null);
    }

    @Override
    @Transactional
    public RecordResponse getRecord(Member requestMember) {
        Member member = memberRepository.findById(requestMember.getId())
                .orElseThrow(() -> new ResourceNotFoundException("member ", requestMember.getId()));

        Record record = recordRepository.findByMemberId(requestMember.getId());

        List<BaseCareer> baseCareers = baseCareerRepository.findByMemberId(requestMember.getId());

        //경력 - 경력
        List<EmploymentResponse> employments = baseCareers.stream()
                .filter(baseCareer -> baseCareer instanceof Employment)
                .map(baseCareer -> new EmploymentResponse((Employment) baseCareer))
                .toList();

        //활동 및 경험 ( 동아리, 대외활동)
        List<BaseCareerResponse> activitiesAndExperiences = baseCareers.stream()
                .filter(baseCareer -> baseCareer instanceof Activity || baseCareer instanceof Circle)
                .map(baseCareer -> {
                    if(baseCareer instanceof Activity){
                        return new ActivityResponse((Activity) baseCareer);
                    }else{
                        return new CircleResponse((Circle) baseCareer);
                    }
                }).collect(Collectors.toList());

        //프로젝트 ( 프로젝트, 공모전/대회)
        List<BaseCareerResponse> projects = baseCareers.stream()
                .filter(baseCareer -> baseCareer instanceof Project || baseCareer instanceof Competition)
                .map(baseCareer -> {
                    if(baseCareer instanceof Project){
                        return new ProjectResponse((Project) baseCareer);
                    }else{
                        return new CompetitionResponse((Competition) baseCareer);
                    }
                }).collect(Collectors.toList());

        //교육 ( 교육)
        List<EduCareerResponse> eduCareers = baseCareers.stream()
                .filter(baseCareer -> baseCareer instanceof EduCareer)
                .map(baseCareer -> new EduCareerResponse((EduCareer) baseCareer))
                .toList();


//        List<RecordListResponse> activitiesAndExperiences = careers.stream()
//                .filter(career -> Arrays.asList(1L, 2L, 3L, 4L, 6L, 7L).contains(career.getCategory().getId()))
//                .map(RecordListResponse::new)
//                .sorted(Comparator.comparing(RecordListResponse::getEndDate).reversed())
//                .collect(Collectors.toList());
//
//        List<RecordListResponse> jobs = careers.stream()
//                .filter(career -> career.getCategory().getId().equals(5L))
//                .map(RecordListResponse::new)
//                .sorted(Comparator.comparing(RecordListResponse::getEndDate).reversed())
//                .collect(Collectors.toList());


        //만약 이력서가 존재한다면
        if (record != null) {
            // 학력
            List<EducationResponse> educationList = record.getEducations()
                    .stream()
                    .map(EducationResponse::new)
                    .collect(Collectors.toList());

            return new RecordResponse(record, member, educationList, employments,activitiesAndExperiences, projects,eduCareers);
        }
        return new RecordResponse(member, employments,activitiesAndExperiences, projects,eduCareers);
    }

    @Override
    @Transactional
    public RecordResponse updateRecord(Member requestMember, Long recordId, RecordReqDto recordReqDto) {
        Record record = recordRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("record ", recordId));
        if (!record.getMemberId().equals(requestMember.getId())) {
            throw new IntroOwnerMismatchException();
        }

        Member member = memberRepository.findById(requestMember.getId())
                .orElseThrow(() -> new ResourceNotFoundException("member ", requestMember.getId()));

        List<BaseCareer> baseCareers = baseCareerRepository.findByMemberId(requestMember.getId());

        //경력 - 경력
        List<EmploymentResponse> employments = baseCareers.stream()
                .filter(baseCareer -> baseCareer instanceof Employment)
                .map(baseCareer -> new EmploymentResponse((Employment) baseCareer))
                .toList();

        //활동 및 경험 ( 동아리, 대외활동)
        List<BaseCareerResponse> activitiesAndExperiences = baseCareers.stream()
                .filter(baseCareer -> baseCareer instanceof Activity || baseCareer instanceof Circle)
                .map(baseCareer -> {
                    if(baseCareer instanceof Activity){
                        return new ActivityResponse((Activity) baseCareer);
                    }else{
                        return new CircleResponse((Circle) baseCareer);
                    }
                }).collect(Collectors.toList());

        //프로젝트 ( 프로젝트, 공모전/대회)
        List<BaseCareerResponse> projects = baseCareers.stream()
                .filter(baseCareer -> baseCareer instanceof Project || baseCareer instanceof Competition)
                .map(baseCareer -> {
                    if(baseCareer instanceof Project){
                        return new ProjectResponse((Project) baseCareer);
                    }else{
                        return new CompetitionResponse((Competition) baseCareer);
                    }
                }).collect(Collectors.toList());

        //교육 ( 교육)
        List<EduCareerResponse> eduCareers = baseCareers.stream()
                .filter(baseCareer -> baseCareer instanceof EduCareer)
                .map(baseCareer -> new EduCareerResponse((EduCareer) baseCareer))
                .toList();

        record.update(
                recordReqDto.getAddress(),
                recordReqDto.getProfileImageUrl());

        List<EducationResponse> educationList = record.getEducations()
                .stream()
                .map(EducationResponse::new)
                .collect(Collectors.toList());

        return new RecordResponse(record, member, educationList, employments,activitiesAndExperiences, projects,eduCareers);
    }




    @Override
    @Transactional
    public EducationResponse saveEducation(Member requestMember, Long recordId, EducationReqDto educationReqDto) {
        Record record = recordRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Record", recordId));
        if (!record.getMemberId().equals(requestMember.getId())) {
            throw new IntroOwnerMismatchException();
        }

        Education education = Education.builder()
                .record(record)
                .category(educationReqDto.getCategory())
                .schoolName(educationReqDto.getSchoolName())
                .major(educationReqDto.getMajor())
                .state(educationReqDto.getState())
                .admissionDate(educationReqDto.getAdmissionDate())
                .graduationDate(educationReqDto.getGraduationDate())
                .build();

        educationRepository.save(education);

        return new EducationResponse(education);
    }

    @Override
    @Transactional
    public EducationResponse updateEducation(Member requestMember, Long educationId, EducationReqDto educationReqDto) {
        Education education = educationRepository.findById(educationId)
                .orElseThrow(() -> new ResourceNotFoundException("education ", educationId));
        if (!education.getRecord().getMemberId().equals(requestMember.getId())) {
            throw new IntroOwnerMismatchException();
        }
        education.changeEducationInfo(
                educationReqDto.getCategory(),
                educationReqDto.getSchoolName(),
                educationReqDto.getMajor(),
                educationReqDto.getState(),
                educationReqDto.getAdmissionDate(),
                educationReqDto.getGraduationDate());

        return new EducationResponse(education);
    }
    @Override
    @Transactional
    public Long deleteEducation(Member requestMember, Long educationId) {
        Education education = educationRepository.findById(educationId)
                .orElseThrow(() -> new ResourceNotFoundException("education ", educationId));
        if (!education.getRecord().getMemberId().equals(requestMember.getId())) {
            throw new IntroOwnerMismatchException();
        }

        educationRepository.delete(education);

        return education.getId();
    }

    @Override
    @Transactional
    public LicenseResponse saveLicense(Member requestMember, Long recordId, LicenseReqDto licenseReqDto) {
        Record record = recordRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Record", recordId));

        if (!record.getMemberId().equals(requestMember.getId())) {
            throw new IntroOwnerMismatchException();
        }

        License license = License.builder()
                .record(record)
                .licenseTag((licenseReqDto.getLicenseTag()))
                .licenseName(licenseReqDto.getLicenseName())
                .administer(licenseReqDto.getAdminister())
                .licenseNumber(licenseReqDto.getLicenseNumber())
                .licenseGrade(licenseReqDto.getLicenseGrade())
                .acquireDate(licenseReqDto.getAcquireDate())
                .build();

        licenseRepository.save(license);

        return new LicenseResponse(license);
    }

    @Override
    @Transactional
    public LicenseResponse updateLicense(Member requestMember, Long licenseId, LicenseReqDto licenseReqDto) {

        License license = licenseRepository.findById(licenseId)
                .orElseThrow(() -> new ResourceNotFoundException("License", licenseId));

        if (!license.getRecord().getMemberId().equals(requestMember.getId())) {
            throw new IntroOwnerMismatchException();
        }

        license.changeLicenseInfo(
                licenseReqDto.getLicenseTag(),
                licenseReqDto.getLicenseName(),
                licenseReqDto.getAdminister(),
                licenseReqDto.getLicenseNumber(),
                licenseReqDto.getLicenseGrade(),
                licenseReqDto.getAcquireDate());

        return new LicenseResponse(license);
    }

    @Override
    @Transactional
    public Long deleteLicense(Member requestMember, Long licenseId) {

        License license = licenseRepository.findById(licenseId)
                .orElseThrow(() -> new ResourceNotFoundException("License", licenseId));

        if (!license.getRecord().getMemberId().equals(requestMember.getId())) {
            throw new IntroOwnerMismatchException();
        }

        licenseRepository.delete(license);

        return license.getId();
    }



    @Override
    @Transactional
    public AwardResponse saveAward(Member requestMember, Long recordId, AwardReqDto awardReqDto) {

        Record record = recordRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Record", recordId));

        if (!record.getMemberId().equals(requestMember.getId())) {
            throw new IntroOwnerMismatchException();
        }

        Award award = Award.builder()
                .record(record)
                .competitionName(awardReqDto.getCompetitionName())
                .administer(awardReqDto.getAdminister())
                .awardName(awardReqDto.getAwardName())
                .acquireDate(awardReqDto.getAcquireDate())
                .build();

        awardRepository.save(award);

        return new AwardResponse(award);
    }

    @Override
    @Transactional
    public AwardResponse updateAward(Member requestMember, Long awardId, AwardReqDto awardReqDto) {

        Award award = awardRepository.findById(awardId)
                .orElseThrow(() -> new ResourceNotFoundException("Award", awardId));

        if (!award.getRecord().getMemberId().equals(requestMember.getId())) {
            throw new IntroOwnerMismatchException();
        }

        award.changeAwardInfo(
                awardReqDto.getCompetitionName(),
                awardReqDto.getAdminister(),
                awardReqDto.getAwardName(),
                awardReqDto.getAcquireDate()
        );

        return new AwardResponse(award);
    }

    @Override
    @Transactional
    public Long deleteAward(Member requestMember, Long awardId) {

        Award award = awardRepository.findById(awardId)
                .orElseThrow(() -> new ResourceNotFoundException("Award", awardId));

        if (!award.getRecord().getMemberId().equals(requestMember.getId())) {
            throw new IntroOwnerMismatchException();
        }

        awardRepository.delete(award);

        return award.getId();
    }

    @Override
    @Transactional
    public SkillResponse saveSkill(Member requestMember, Long recordId, SkillReqDto skillReqDto) {

        Record record = recordRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Record", recordId));

        if (!record.getMemberId().equals(requestMember.getId())) {
            throw new IntroOwnerMismatchException();
        }

        Skill skill = Skill.builder()
                .record(record)
                .skillTag(skillReqDto.getSkillTag())
                .skillName(skillReqDto.getSkillName())
                .workmanship(skillReqDto.getWorkmanship())
                .build();

        skillRepository.save(skill);

        return new SkillResponse(skill);
    }

    @Override
    @Transactional
    public SkillResponse updateSkill(Member requestMember, Long skillId, SkillReqDto skillReqDto) {

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill", skillId));

        if (!skill.getRecord().getMemberId().equals(requestMember.getId())) {
            throw new IntroOwnerMismatchException();
        }

        skill.changeSkillInfo(
                skillReqDto.getSkillTag(),
                skillReqDto.getSkillName(),
                skillReqDto.getWorkmanship()
        );

        return new SkillResponse(skill);
    }

    @Override
    @Transactional
    public Long deleteSkill(Member requestMember, Long skillId) {

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill", skillId));

        if (!skill.getRecord().getMemberId().equals(requestMember.getId())) {
            throw new IntroOwnerMismatchException();
        }

        skillRepository.delete(skill);

        return skill.getId();
    }


}
