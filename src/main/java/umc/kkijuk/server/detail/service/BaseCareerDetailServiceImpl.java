package umc.kkijuk.server.detail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.kkijuk.server.career.domain.BaseCareer;
import umc.kkijuk.server.career.repository.*;
import umc.kkijuk.server.common.domian.exception.*;
import umc.kkijuk.server.detail.controller.response.BaseCareerDetailResponse;
import umc.kkijuk.server.detail.domain.BaseCareerDetail;
import umc.kkijuk.server.detail.domain.CareerType;
import umc.kkijuk.server.detail.domain.mapping.CareerDetailTag;
import umc.kkijuk.server.detail.dto.BaseCareerDetailReqDto;
import umc.kkijuk.server.detail.dto.converter.BaseCareerDetailConverter;
import umc.kkijuk.server.detail.dto.converter.CareerDetailTagConverter;
import umc.kkijuk.server.detail.repository.BaseCareerDetailRepository;
import umc.kkijuk.server.detail.repository.CareerDetailTagRepository;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.tag.domain.Tag;
import umc.kkijuk.server.tag.repository.TagRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BaseCareerDetailServiceImpl implements BaseCareerDetailService{
    private final BaseCareerDetailRepository baseCareerDetailRepository;
    private final CareerDetailTagRepository careerDetailTagRepository;
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
        BaseCareer baseCareer = findBaseCareerByType(request.getCareerType(), careerId);

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

    @Override
    @Transactional
    public void deleteDetail(Member requestMember, Long careerId, Long detailId) {
        BaseCareerDetail baseCareerDetail = baseCareerDetailRepository.findById(detailId).orElseThrow(
                () -> new ResourceNotFoundException("BaseCareerDetail", detailId));

        BaseCareer baseCareer = findBaseCareerByType(baseCareerDetail.getCareerType(), careerId);
        if(!baseCareer.getMemberId().equals(requestMember.getId())){
            throw new OwnerMismatchException();
        }

        baseCareerDetailRepository.delete(baseCareerDetail);
    }

    @Override
    @Transactional
    public BaseCareerDetailResponse updateDetail(Member requestMember, BaseCareerDetailReqDto request, Long careerId, Long detailId) {
        BaseCareerDetail baseCareerDetail = baseCareerDetailRepository.findById(detailId).orElseThrow(
                () -> new ResourceNotFoundException("BaseCareerDetail", detailId));
        BaseCareer baseCareer = findBaseCareerByType(request.getCareerType(), careerId);

        if(!baseCareer.getId().equals(careerId)) {
            throw new CareerValidationException("주어진 활동 기록 Id는 해당 활동에 속하지 않습니다. 활동 Id와 활동 기록 Id를 확인해 주세요.");
        }
        if(!baseCareerDetail.getMemberId().equals(requestMember.getId())||!baseCareer.getMemberId().equals(requestMember.getId())){
            throw new OwnerMismatchException();
        }
        List<CareerDetailTag> existTags = new ArrayList<>(baseCareerDetail.getCareerTagList());
        baseCareerDetail.getCareerTagList().clear();
        existTags.forEach(careerDetailTag -> careerDetailTagRepository.delete(careerDetailTag));

        baseCareerDetail.updateBaseCareerDetail(
                request.getTitle(),
                request.getContent(),
                request.getStartDate(),
                request.getEndDate()
        );

        List<CareerDetailTag> careerDetailTags = returnCareerTagList(request.getTagList());
        careerDetailTags.forEach(careerDetailTag -> careerDetailTag.setBaseCareerDetail(baseCareerDetail));

        return new BaseCareerDetailResponse(baseCareerDetailRepository.save(baseCareerDetail));
    }



    private BaseCareer findBaseCareerByType(CareerType careerType, Long careerId) {
        return switch (careerType) {
            case ACTIVITY -> activityRepository.findById(careerId)
                    .orElseThrow(() -> new ResourceNotFoundException("대외활동 : ", careerId));
            case CIRCLE -> circleRepository.findById(careerId)
                    .orElseThrow(() -> new ResourceNotFoundException("동아리 : ", careerId));
            case PROJECT -> projectRepository.findById(careerId)
                    .orElseThrow(() -> new ResourceNotFoundException("프로젝트 : ", careerId));
            case EDU -> eduCareerRepository.findById(careerId)
                    .orElseThrow(() -> new ResourceNotFoundException("교육 : ", careerId));
            case COM -> competitionRepository.findById(careerId)
                    .orElseThrow(() -> new ResourceNotFoundException("대회 : ", careerId));
            case EMP -> employmentRepository.findById(careerId)
                    .orElseThrow(() -> new ResourceNotFoundException("경력 : ", careerId));
            default -> throw new IllegalArgumentException("지원하지 않는 활동 유형입니다");
        };
    }
    private List<CareerDetailTag> returnCareerTagList(List<Long> tagIdList) {
        List<Tag> tagList = tagIdList.stream().map(tagId -> {
            return tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag", tagId));
        }).collect(Collectors.toList());
        return CareerDetailTagConverter.toCareerDetailTagList(tagList);
    }
}
