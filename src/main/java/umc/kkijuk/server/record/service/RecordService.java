package umc.kkijuk.server.record.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.kkijuk.server.career.domain.Career;
import umc.kkijuk.server.career.repository.CareerRepository;
import umc.kkijuk.server.common.domian.exception.IntroFoundException;
import umc.kkijuk.server.common.domian.exception.IntroOwnerMismatchException;
import umc.kkijuk.server.common.domian.exception.ResourceNotFoundException;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.repository.MemberRepository;
import umc.kkijuk.server.record.domain.Education;
import umc.kkijuk.server.record.repository.EducationRepository;
import umc.kkijuk.server.record.domain.Record;
import umc.kkijuk.server.record.repository.RecordRepository;
import umc.kkijuk.server.record.dto.*;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecordService {
    private final CareerRepository careerRepository;
    private final RecordRepository recordRepository;
    private final MemberRepository memberRepository;
    private final EducationRepository educationRepository;

    @Transactional
    public RecordResDto saveRecord(Member requestMember, RecordReqDto recordReqDto){
        if (recordRepository.existsByMemberId(requestMember.getId())) {
            throw new IntroFoundException("이미 이력서가 존재합니다");
        }

        Record record=Record.builder()
                .memberId(requestMember.getId())
                .address(recordReqDto.getAddress())
                .profileImageUrl(recordReqDto.getProfileImageUrl())
                .build();

        recordRepository.save(record);

        return new RecordResDto(record, requestMember, null, null, null);
    }

    @Transactional
    public EducationResDto saveEducation(Member requestMember, Long recordId, EducationReqDto educationReqDto){
        Record record = recordRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Record", recordId));
        if (!record.getMemberId().equals(requestMember.getId())) {
            throw new IntroOwnerMismatchException();
        }
        Education education=Education.builder()
                .record(record)
                .category(educationReqDto.getCategory())
                .schoolName(educationReqDto.getSchoolName())
                .major(educationReqDto.getMajor())
                .state(educationReqDto.getState())
                .admissionDate(educationReqDto.getAdmissionDate())
                .graduationDate(educationReqDto.getGraduationDate())
                .build();

        educationRepository.save(education);

        return new EducationResDto(education);
    }

    @Transactional
    public Long deleteEducation(Member requestMember, Long educationId){
        Education education=educationRepository.findById(educationId)
                .orElseThrow(()-> new ResourceNotFoundException("education ", educationId));
        if (!education.getRecord().getMemberId().equals(requestMember.getId())) {
            throw new IntroOwnerMismatchException();
        }

        educationRepository.delete(education);

        return education.getId();
    }

    @Transactional
    public EducationResDto updateEducation(Member requestMember, Long educationId, EducationReqDto educationReqDto) {
        Education education = educationRepository.findById(educationId)
                .orElseThrow(() -> new ResourceNotFoundException("education ", educationId));
        if (!education.getRecord().getMemberId().equals(requestMember.getId())) {
            throw new IntroOwnerMismatchException();
        }
        education.update(
                educationReqDto.getCategory(),
                educationReqDto.getSchoolName(),
                educationReqDto.getMajor(),
                educationReqDto.getState(),
                educationReqDto.getAdmissionDate(),
                educationReqDto.getGraduationDate());

        return new EducationResDto(education);
    }

    @Transactional
    public RecordResDto getRecord(Member requestMember) {
        Member member= memberRepository.findById(requestMember.getId())
                .orElseThrow(() -> new ResourceNotFoundException("member ", requestMember.getId()));

        Record record = recordRepository.findByMemberId(requestMember.getId());
        List<Career> careers = careerRepository.findAllCareerByMemberId(requestMember.getId());

        // 활동 및 경험으로 필터링하고, endDate 기준으로 내림차순 정렬
        List<RecordListResDto> activitiesAndExperiences = careers.stream()
                .filter(career -> career.getCategory().getId().equals(1L) || career.getCategory().getId().equals(2L)||
                        career.getCategory().getId().equals(3L) || career.getCategory().getId().equals(4L)||
                        career.getCategory().getId().equals(6L) || career.getCategory().getId().equals(7L))
                .map(RecordListResDto::new)
                .sorted(Comparator.comparing(RecordListResDto::getEndDate).reversed())
                .collect(Collectors.toList());

        // 경력으로 필터링하고, endDate 기준으로 내림차순 정렬
        List<RecordListResDto> jobs = careers.stream()
                .filter(career -> career.getCategory().getId().equals(5L))
                .map(RecordListResDto::new)
                .sorted(Comparator.comparing(RecordListResDto::getEndDate).reversed())
                .collect(Collectors.toList());

        // 이력서 있을 때
        if (record!=null) {
            List<EducationResDto> educationList = record.getEducations()
                    .stream()
                    .map(education -> new EducationResDto(education))
                    .collect(Collectors.toList());

            return new RecordResDto(record, member, educationList, activitiesAndExperiences, jobs);
        }

        // 이력서 없을 때
        return new RecordResDto(member, activitiesAndExperiences, jobs);
    }

    @Transactional
    public RecordResDto updateRecord(Member requestMember, Long recordId, RecordReqDto recordReqDto){
        Record record = recordRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("record ", recordId));

        if (!record.getMemberId().equals(requestMember.getId())) {
            throw new IntroOwnerMismatchException();
        }

        Member member= memberRepository.findById(requestMember.getId())
                .orElseThrow(() -> new ResourceNotFoundException("member ", requestMember.getId()));

        List<Career> careers = careerRepository.findAllCareerByMemberId(requestMember.getId());

        // 활동 및 경험으로 필터링하고, endDate 기준으로 내림차순 정렬
        List<RecordListResDto> activitiesAndExperiences = careers.stream()
                .filter(career -> career.getCategory().getId().equals(1L) || career.getCategory().getId().equals(2L)||
                        career.getCategory().getId().equals(3L) || career.getCategory().getId().equals(4L)||
                        career.getCategory().getId().equals(6L) || career.getCategory().getId().equals(7L))
                .map(RecordListResDto::new)
                .sorted(Comparator.comparing(RecordListResDto::getEndDate).reversed())
                .collect(Collectors.toList());

        // 경력으로 필터링하고, endDate 기준으로 내림차순 정렬
        List<RecordListResDto> jobs = careers.stream()
                .filter(career -> career.getCategory().getId().equals(5L))
                .map(RecordListResDto::new)
                .sorted(Comparator.comparing(RecordListResDto::getEndDate).reversed())
                .collect(Collectors.toList());

        record.update(
                recordReqDto.getAddress(),
                recordReqDto.getProfileImageUrl());

        List<EducationResDto> educationList = record.getEducations()
                .stream()
                .map(education -> new EducationResDto(education))
                .collect(Collectors.toList());

        return new RecordResDto(record, member, educationList, activitiesAndExperiences, jobs);
    }


}
