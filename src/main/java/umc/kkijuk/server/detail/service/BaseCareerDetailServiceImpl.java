package umc.kkijuk.server.detail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.kkijuk.server.career.domain.*;
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
import java.util.Optional;
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
        BaseCareer baseCareer;
        switch (request.getCareerType()) {
            case COM:
                baseCareer = competitionRepository.findById(careerId)
                        .orElseThrow(() -> new ResourceNotFoundException("Competition",careerId));
                break;
            case ACTIVITY:
                baseCareer = activityRepository.findById(careerId)
                        .orElseThrow(() -> new ResourceNotFoundException("Activity",careerId));
                break;
            case CIRCLE:
                baseCareer = circleRepository.findById(careerId)
                        .orElseThrow(() -> new ResourceNotFoundException("Circle",careerId));
                break;
            case EDU:
                baseCareer = eduCareerRepository.findById(careerId)
                        .orElseThrow(() -> new ResourceNotFoundException("EduCareer",careerId));
                break;
            case EMP:
                baseCareer = employmentRepository.findById(careerId)
                        .orElseThrow(() -> new ResourceNotFoundException("Employment",careerId));
                break;
            default:
                baseCareer = projectRepository.findById(careerId)
                        .orElseThrow(() -> new ResourceNotFoundException("Project",careerId));
                break;
        }
        if (!baseCareer.getMemberId().equals(requestMember.getId())) {
            throw new OwnerMismatchException();
        }

        List<CareerDetailTag> detailTagList =returnCareerTagList(request.getTagList());

        if (baseCareer instanceof Activity) {
            Activity activity = (Activity) baseCareer;
            BaseCareerDetail newBaseCareerDetail = BaseCareerDetailConverter.toBaseCareerDetail(requestMember, request, baseCareer);
            activity.getDetailList().add(newBaseCareerDetail);
            detailTagList.forEach(careerDetailTag -> careerDetailTag.setBaseCareerDetail(newBaseCareerDetail));

            return new BaseCareerDetailResponse(baseCareerDetailRepository.save(newBaseCareerDetail));
        } else if (baseCareer instanceof Project) {
            Project project = (Project) baseCareer;
            BaseCareerDetail newBaseCareerDetail = BaseCareerDetailConverter.toBaseCareerDetail(requestMember, request, baseCareer);
            project.getDetailList().add(newBaseCareerDetail);
            detailTagList.forEach(careerDetailTag -> careerDetailTag.setBaseCareerDetail(newBaseCareerDetail));

            return new BaseCareerDetailResponse(baseCareerDetailRepository.save(newBaseCareerDetail));
        } else if ( baseCareer instanceof Competition){
            Competition comp = (Competition) baseCareer;
            BaseCareerDetail newBaseCareerDetail = BaseCareerDetailConverter.toBaseCareerDetail(requestMember, request, baseCareer);
            comp.getDetailList().add(newBaseCareerDetail);
            detailTagList.forEach(careerDetailTag -> careerDetailTag.setBaseCareerDetail(newBaseCareerDetail));

            return new BaseCareerDetailResponse(baseCareerDetailRepository.save(newBaseCareerDetail));
        } else if ( baseCareer instanceof Circle){
            Circle circle = (Circle) baseCareer;
            BaseCareerDetail newBaseCareerDetail = BaseCareerDetailConverter.toBaseCareerDetail(requestMember, request, baseCareer);
            circle.getDetailList().add(newBaseCareerDetail);
            detailTagList.forEach(careerDetailTag -> careerDetailTag.setBaseCareerDetail(newBaseCareerDetail));

            return new BaseCareerDetailResponse(baseCareerDetailRepository.save(newBaseCareerDetail));
        } else if ( baseCareer instanceof EduCareer){
            EduCareer eduCareer = (EduCareer) baseCareer;
            BaseCareerDetail newBaseCareerDetail = BaseCareerDetailConverter.toBaseCareerDetail(requestMember, request, baseCareer);
            eduCareer.getDetailList().add(newBaseCareerDetail);
            detailTagList.forEach(careerDetailTag -> careerDetailTag.setBaseCareerDetail(newBaseCareerDetail));

            return new BaseCareerDetailResponse(baseCareerDetailRepository.save(newBaseCareerDetail));
        } else {
            Employment employment = (Employment) baseCareer;
            BaseCareerDetail newBaseCareerDetail = BaseCareerDetailConverter.toBaseCareerDetail(requestMember, request, baseCareer);
            employment.getDetailList().add(newBaseCareerDetail);
            detailTagList.forEach(careerDetailTag -> careerDetailTag.setBaseCareerDetail(newBaseCareerDetail));
            return new BaseCareerDetailResponse(baseCareerDetailRepository.save(newBaseCareerDetail));
        }

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


        BaseCareer baseCareer;
        switch (request.getCareerType()) {
            case COM:
                baseCareer = competitionRepository.findById(careerId)
                        .orElseThrow(() -> new ResourceNotFoundException("Competition",careerId));
                break;
            case ACTIVITY:
                baseCareer = activityRepository.findById(careerId)
                        .orElseThrow(() -> new ResourceNotFoundException("Activity",careerId));
                break;
            case CIRCLE:
                baseCareer = circleRepository.findById(careerId)
                        .orElseThrow(() -> new ResourceNotFoundException("Circle",careerId));
                break;
            case EDU:
                baseCareer = eduCareerRepository.findById(careerId)
                        .orElseThrow(() -> new ResourceNotFoundException("EduCareer",careerId));
                break;
            case EMP:
                baseCareer = employmentRepository.findById(careerId)
                        .orElseThrow(() -> new ResourceNotFoundException("Employment",careerId));
                break;
            default:
                baseCareer = projectRepository.findById(careerId)
                        .orElseThrow(() -> new ResourceNotFoundException("Project",careerId));
                break;
        }


        if(!baseCareerDetail.getMemberId().equals(requestMember.getId())||!baseCareer.getMemberId().equals(requestMember.getId())){
            throw new OwnerMismatchException();
        }


        if (baseCareer instanceof Activity) {
            Activity activity = (Activity) baseCareer;
            if(!activity.getId().equals(careerId)) {
                throw new CareerValidationException("주어진 활동 기록 Id는 해당 활동에 속하지 않습니다. 활동 Id와 활동 기록 Id를 확인해 주세요.");
            }

        } else if (baseCareer instanceof Project) {
            Project project = (Project) baseCareer;
            if(!project.getId().equals(careerId)) {
                throw new CareerValidationException("주어진 활동 기록 Id는 해당 활동에 속하지 않습니다. 활동 Id와 활동 기록 Id를 확인해 주세요.");
            }
        } else if ( baseCareer instanceof Competition){
            Competition comp = (Competition) baseCareer;
            if(!comp.getId().equals(careerId)) {
                throw new CareerValidationException("주어진 활동 기록 Id는 해당 활동에 속하지 않습니다. 활동 Id와 활동 기록 Id를 확인해 주세요.");
            }
        } else if ( baseCareer instanceof Circle){
            Circle circle = (Circle) baseCareer;
            if(!circle.getId().equals(careerId)) {
                throw new CareerValidationException("주어진 활동 기록 Id는 해당 활동에 속하지 않습니다. 활동 Id와 활동 기록 Id를 확인해 주세요.");
            }
        } else if ( baseCareer instanceof EduCareer){
            EduCareer eduCareer = (EduCareer) baseCareer;
            if(!eduCareer.getId().equals(careerId)) {
                throw new CareerValidationException("주어진 활동 기록 Id는 해당 활동에 속하지 않습니다. 활동 Id와 활동 기록 Id를 확인해 주세요.");
            }
        } else {
            Employment employment = (Employment) baseCareer;
            if(!employment.getId().equals(careerId)) {
                throw new CareerValidationException("주어진 활동 기록 Id는 해당 활동에 속하지 않습니다. 활동 Id와 활동 기록 Id를 확인해 주세요.");
            }
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