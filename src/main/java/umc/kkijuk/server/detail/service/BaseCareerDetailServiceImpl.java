package umc.kkijuk.server.detail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.kkijuk.server.career.domain.BaseCareer;
import umc.kkijuk.server.career.repository.*;
import umc.kkijuk.server.careerdetail.domain.mapping.CareerTag;
import umc.kkijuk.server.careerdetail.dto.converter.CareerTagConverter;
import umc.kkijuk.server.common.domian.exception.OwnerMismatchException;
import umc.kkijuk.server.common.domian.exception.ResourceNotFoundException;
import umc.kkijuk.server.detail.controller.response.BaseCareerDetailResponse;
import umc.kkijuk.server.detail.domain.BaseCareerDetail;
import umc.kkijuk.server.detail.domain.mapping.CareerDetailTag;
import umc.kkijuk.server.detail.dto.BaseCareerDetailReqDto;
import umc.kkijuk.server.detail.dto.converter.BaseCareerDetailConverter;
import umc.kkijuk.server.detail.dto.converter.CareerDetailTagConverter;
import umc.kkijuk.server.detail.repository.BaseCareerDetailRepository;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.tag.domain.Tag;
import umc.kkijuk.server.tag.repository.TagRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BaseCareerDetailServiceImpl implements BaseCareerDetailService{
    private final BaseCareerDetailRepository baseCareerDetailRepository;
    private final BaseCareerRepository baseCareerRepository;
    private final ActivityRepository activityRepository;
    private final EduCareerRepository eduCareerRepository;
    private final ProjectRepository projectRepository;
    private final CircleRepository circleRepository;
    private final EmploymentRepository employmentRepository;
    private final CompetitionRepository competitionRepository;
    private final TagRepository tagRepository;


    @Override
    @Transactional
    public BaseCareerDetailResponse createDetail(Member requestMember, BaseCareerDetailReqDto request, Long careerId) {
        BaseCareer baseCareer;
        switch (request.getCareerType()) {
            case ACTIVITY -> baseCareer = activityRepository.findById(careerId)
                    .orElseThrow(() -> new ResourceNotFoundException("대외활동 : ", careerId));
            case CIRCLE -> baseCareer = circleRepository.findById(careerId)
                    .orElseThrow(() -> new ResourceNotFoundException("동아리 : ",careerId));
            case PROJECT -> baseCareer = projectRepository.findById(careerId)
                    .orElseThrow(() -> new ResourceNotFoundException("프로젝트 : ",careerId));
            case EDU -> baseCareer = eduCareerRepository.findById(careerId)
                    .orElseThrow(() -> new ResourceNotFoundException("교육 : ",careerId));
            case COM -> baseCareer = competitionRepository.findById(careerId)
                    .orElseThrow(() -> new ResourceNotFoundException("대회 : ",careerId));
            case EMP -> baseCareer = employmentRepository.findById(careerId)
                    .orElseThrow(() -> new ResourceNotFoundException("경력 : ",careerId));
            default -> throw new IllegalArgumentException("지원하지 않는 활동 유형입니다");
        }

        if (!baseCareer.getMemberId().equals(requestMember.getId())) {
            throw new OwnerMismatchException();
        }

        BaseCareerDetail newBaseCareerDetail =
                BaseCareerDetailConverter.toBaseCareerDetail(requestMember,request, baseCareer);
        baseCareer.getBaseCareerDetailList().add(newBaseCareerDetail);

        List<CareerDetailTag> detailTagList =returnCareerTagList(request.getTagList());
        detailTagList.forEach(careerDetailTag -> careerDetailTag.setBaseCareerDetail(newBaseCareerDetail));

        return new BaseCareerDetailResponse(baseCareerDetailRepository.save(newBaseCareerDetail));
    }

    private List<CareerDetailTag> returnCareerTagList(List<Long> tagIdList) {
        List<Tag> tagList = tagIdList.stream().map(tagId -> {
            return tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag", tagId));
        }).collect(Collectors.toList());
        return CareerDetailTagConverter.toCareerDetailTagList(tagList);
    }
}
