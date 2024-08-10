package umc.kkijuk.server.recruit.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.kkijuk.server.common.domian.exception.RecruitOwnerMismatchException;
import umc.kkijuk.server.common.domian.exception.ResourceNotFoundException;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.recruit.controller.port.RecruitService;
import umc.kkijuk.server.recruit.domain.RecruitApplyDateUpdate;
import umc.kkijuk.server.recruit.domain.*;
import umc.kkijuk.server.recruit.service.port.RecruitRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Builder
@RequiredArgsConstructor
public class RecruitServiceImpl implements RecruitService {

    private final RecruitRepository recruitRepository;

    @Override
    public Recruit getById(long id) {
        return recruitRepository.findByIdAndIsActive(id, true)
                .orElseThrow(() -> new ResourceNotFoundException("Recruit", id));
    }

    @Override
    @Transactional
    public Recruit create(Member requestMember, RecruitCreate recruitCreate) {
        Recruit recruit = Recruit.from(requestMember.getId(), recruitCreate);
        return recruitRepository.save(recruit);
    }

    @Override
    @Transactional
    public Recruit update(Member requestMember, Long recruitId, RecruitUpdate recruitUpdate) {
        Recruit recruit = getById(recruitId);
        if (!recruit.getMemberId().equals(requestMember.getId())) {
            throw new RecruitOwnerMismatchException();
        }

        recruit = recruit.update(recruitUpdate);
        return recruitRepository.save(recruit);
    }

    @Override
    @Transactional
    public Recruit updateStatus(Member requestMember, long recruitId, RecruitStatusUpdate recruitStatusUpdate) {
        Recruit recruit = getById(recruitId);
        if (!recruit.getMemberId().equals(requestMember.getId())) {
            throw new RecruitOwnerMismatchException();
        }

        recruit = recruit.updateStatus(recruitStatusUpdate);
        return recruitRepository.save(recruit);
    }

    @Override
    @Transactional
    public Recruit disable(Member requestMember, long recruitId) {
        Recruit recruit = getById(recruitId);
        if (!recruit.getMemberId().equals(requestMember.getId())) {
            throw new RecruitOwnerMismatchException();
        }

        recruit = recruit.disable();
        return recruitRepository.save(recruit);
    }

    @Override
    @Transactional
    public List<Recruit> findAllByEndTime(Member requestMember, LocalDate date) {
        return recruitRepository.findAllActiveRecruitByMemberIdAndEndDate(requestMember.getId(), date);
    }

    @Override
    @Transactional
    public List<Recruit> findAllByEndTimeAfter(Member requestMember, LocalDateTime endTime) {
        return recruitRepository.findAllActiveRecruitByMemberIdAndEndTimeAfter(requestMember.getId(), endTime);
    }

    @Override
    public List<ValidRecruitDto> findAllValidRecruitByMemberId(Member requestMember, LocalDateTime endTime) {
        List<Recruit> recruits = recruitRepository.findAllActiveRecruitByMemberId(requestMember.getId());
        return recruits.stream()
                .filter(item -> !isUnappliedOrPlanned(item) || item.getEndTime().isAfter(endTime))
                .map(ValidRecruitDto::from)
                .toList();
    }

    @Override
    public List<RecruitListByMonthDto> findAllValidRecruitByYearAndMonth(Member requestMember, Integer year, Integer month) {
        List<Recruit> recruits = recruitRepository.findAllActiveRecruitByMemberIdAndMonth(requestMember.getId(), year, month);
        return recruits.stream()
                .map(RecruitListByMonthDto::from)
                .toList();
    }

    @Override
    public Recruit updateApplyDate(Member requestMember, long recruitId, RecruitApplyDateUpdate recruitApplyDateUpdate) {
        Recruit recruit = getById(recruitId);
        if (!recruit.getMemberId().equals(requestMember.getId())) {
            throw new RecruitOwnerMismatchException();
        }

        recruit = recruit.updateApplyDate(recruitApplyDateUpdate);
        return recruitRepository.save(recruit);
    }

    private boolean isUnappliedOrPlanned(Recruit recruit) {
        return recruit.getStatus().equals(RecruitStatus.UNAPPLIED) ||
                recruit.getStatus().equals(RecruitStatus.PLANNED);
    }
}
