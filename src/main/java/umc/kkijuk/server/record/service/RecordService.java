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
import umc.kkijuk.server.member.repository.MemberJpaRepository;
import umc.kkijuk.server.record.domain.Record;
import umc.kkijuk.server.record.domain.RecordRepository;
import umc.kkijuk.server.record.dto.RecordListResDto;
import umc.kkijuk.server.record.dto.RecordReqDto;
import umc.kkijuk.server.record.dto.RecordResDto;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecordService {
    private final CareerRepository careerRepository;
    private final RecordRepository recordRepository;
    private final MemberJpaRepository memberJpaRepository;

    @Transactional
    public Long saveRecord(Member requestMember, RecordReqDto recordReqDto){
        if(recordRepository.findAll().stream().count()>0 ){
            throw new IntroFoundException("이미 이력서가 존재합니다");
        }

        Record record=Record.builder()
                .member(requestMember)
                .address(recordReqDto.getAddress())
                .profileImageUrl(recordReqDto.getProfileImageUrl())
                .build();

        recordRepository.save(record);

        return record.getId();
    }

    @Transactional
    public RecordResDto getRecord(Member requestMember) {
        Member member=memberJpaRepository.findById(requestMember.getId())
                .orElseThrow(() -> new ResourceNotFoundException("member ", requestMember.getId()));

        List<Record> records = recordRepository.findAll();
        List<Career> careers = careerRepository.findAll(); // null 처리 필요

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
        if (!records.isEmpty()) {
            Record record = records.get(0);
            return new RecordResDto(record, activitiesAndExperiences, jobs);
        }

        // 이력서 없을 때
        return new RecordResDto(member, activitiesAndExperiences, jobs);
    }

    @Transactional
    public RecordResDto updateRecord(Member requestMember, Long recordId, RecordReqDto recordReqDto){
        Record record = recordRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("record ", recordId));

        if (!record.getMember().getId().equals(requestMember.getId())) {
            throw new IntroOwnerMismatchException();
        }

        List<Career> careers = careerRepository.findAll(); // null 처리 필요

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

        return new RecordResDto(record, activitiesAndExperiences, jobs);
    }

}
