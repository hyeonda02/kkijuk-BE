package umc.kkijuk.server.career.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.kkijuk.server.career.controller.response.*;
import umc.kkijuk.server.career.domain.*;
import umc.kkijuk.server.career.repository.*;
import umc.kkijuk.server.common.domian.exception.OwnerMismatchException;
import umc.kkijuk.server.detail.controller.response.BaseCareerDetailResponse;
import umc.kkijuk.server.detail.domain.BaseCareerDetail;
import umc.kkijuk.server.detail.domain.CareerType;
import umc.kkijuk.server.detail.repository.BaseCareerDetailRepository;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.tag.domain.Tag;
import umc.kkijuk.server.tag.repository.TagRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CareerSearchServiceImpl implements CareerSearchService{
    private final ActivityRepository activityRepository;
    private final CircleRepository circleRepository;
    private final CompetitionRepository competitionRepository;
    private final EduCareerRepository eduCareerRepository;
    private final ProjectRepository projectRepository;
    private final EmploymentRepository employmentRepository;
    private final CareerEtcRepository etcRepository;
    private final BaseCareerDetailRepository detailRepository;
    private final TagRepository tagRepository;

    @Override
    public List<TimelineResponse> findCareerForTimeline(Member requestMember) {
        Long memberId = requestMember.getId();
        List<BaseCareer> careers = new ArrayList<>();


        careers.addAll(activityRepository.findByMemberId(memberId));
        careers.addAll(eduCareerRepository.findByMemberId(memberId));
        careers.addAll(employmentRepository.findByMemberId(memberId));
        careers.addAll(circleRepository.findByMemberId(memberId));
        careers.addAll(projectRepository.findByMemberId(memberId));
        careers.addAll(competitionRepository.findByMemberId(memberId));
        careers.addAll(etcRepository.findByMemberId(memberId));


        careers.sort(Comparator.comparing(BaseCareer::getEnddate).reversed());

        return careers.stream()
                .map(career -> new TimelineResponse(career, resolveCareerType(career)))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, List<?>> findAllCareerGroupedCategory(Long memberId) {
        List<EduCareerResponse> eduCareers = eduCareerRepository.findByMemberId(memberId)
                .stream().map(EduCareerResponse::new).collect(Collectors.toList());
        List<EmploymentResponse> employments = employmentRepository.findByMemberId(memberId)
                .stream().map(EmploymentResponse::new).collect(Collectors.toList());
        List<ProjectResponse> projects = projectRepository.findByMemberId(memberId)
                .stream().map(ProjectResponse::new).collect(Collectors.toList());
        List<ActivityResponse> activities = activityRepository.findByMemberId(memberId)
                .stream().map(ActivityResponse::new).collect(Collectors.toList());
        List<CircleResponse> circles = circleRepository.findByMemberId(memberId)
                .stream().map(CircleResponse::new).collect(Collectors.toList());
        List<CompetitionResponse> competitions = competitionRepository.findByMemberId(memberId)
                .stream().map(CompetitionResponse::new).collect(Collectors.toList());
        List<EtcResponse> etcs = etcRepository.findByMemberId(memberId)
                .stream().map(EtcResponse::new).collect(Collectors.toList());


        Map<String, List<?>> careerList = new HashMap<>();
        careerList.put("대외활동",activities);
        careerList.put("동아리",circles);
        careerList.put("대회",competitions);
        careerList.put("교육",eduCareers);
        careerList.put("인턴",employments);
        careerList.put("프로젝트",projects);
        careerList.put("기타", etcs);

        return careerList;
    }

    @Override
    public Map<String, List<?>> findAllCareerGroupedYear(Long memberId) {
        List<BaseCareerResponse> baseCareers = projectRepository.findByMemberId(memberId).stream()
                .map(ProjectResponse::new)
                .collect(Collectors.toList());
        baseCareers.addAll(competitionRepository.findByMemberId(memberId).stream()
                .map(CompetitionResponse::new).collect(Collectors.toList()));
        baseCareers.addAll(activityRepository.findByMemberId(memberId).stream()
                .map(ActivityResponse::new).collect(Collectors.toList()));
        baseCareers.addAll(circleRepository.findByMemberId(memberId).stream()
                .map(CircleResponse::new).collect(Collectors.toList()));
        baseCareers.addAll(eduCareerRepository.findByMemberId(memberId).stream()
                .map(EduCareerResponse::new).collect(Collectors.toList()));
        baseCareers.addAll(employmentRepository.findByMemberId(memberId).stream()
                .map(EmploymentResponse::new).collect(Collectors.toList()));
        baseCareers.addAll(etcRepository.findByMemberId(memberId).stream()
                .map(EtcResponse::new).collect(Collectors.toList()));


//        baseCareers.stream().sorted(Comparator.comparing(BaseCareerResponse::getEndDate).reversed());

        baseCareers = baseCareers.stream()
                .sorted(Comparator.comparing(BaseCareerResponse::getEndDate).reversed())
                .collect(Collectors.toList());


        Map<String, List<BaseCareerResponse>> groupedCareers = baseCareers.stream()
                .collect(Collectors.groupingBy(
                        career -> String.valueOf(career.getEndDate().getYear())
                ));


        Map<String, List<?>> result = new HashMap<>();
        for (Map.Entry<String, List<BaseCareerResponse>> entry : groupedCareers.entrySet()) {
            List<?> filteredList = entry.getValue().stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            result.put(entry.getKey(), filteredList);
        }
        return result;
    }

    @Override
    public List<BaseCareerResponse> findAllCareer(Long memberId) {
        List<BaseCareerResponse> baseCareers = projectRepository.findByMemberId(memberId).stream()
                .map(project-> new ProjectResponse(project,detailRepository.findByProject(project)))
                .collect(Collectors.toList());
        baseCareers.addAll(competitionRepository.findByMemberId(memberId).stream()
                .map(comp-> new CompetitionResponse(comp, detailRepository.findByCompetition(comp)))
                .collect(Collectors.toList()));
        baseCareers.addAll(activityRepository.findByMemberId(memberId).stream()
                .map(activity->new ActivityResponse(activity, detailRepository.findByActivity(activity)))
                .collect(Collectors.toList()));
        baseCareers.addAll(circleRepository.findByMemberId(memberId).stream()
                .map(circle-> new CircleResponse(circle, detailRepository.findByCircle(circle)))
                .collect(Collectors.toList()));
        baseCareers.addAll(eduCareerRepository.findByMemberId(memberId).stream()
                .map(eduCareer -> new EduCareerResponse(eduCareer, detailRepository.findByEduCareer(eduCareer)))
                .collect(Collectors.toList()));
        baseCareers.addAll(employmentRepository.findByMemberId(memberId).stream()
                .map(employment -> new EmploymentResponse(employment, detailRepository.findByEmployment(employment)))
                .collect(Collectors.toList()));

        baseCareers.addAll(etcRepository.findByMemberId(memberId).stream()
                .map(etc -> new EtcResponse(etc, detailRepository.findByEtc(etc)))
                .collect(Collectors.toList()));

        baseCareers = baseCareers.stream()
                .sorted(Comparator.comparing(BaseCareerResponse::getEndDate).reversed())
                .collect(Collectors.toList());

        return baseCareers;
    }
    @Override
    public BaseCareerResponse findCareer(Member requestMember, Long careerId, String type) {
        switch (type.toLowerCase()) {
            case "activity" -> {
                Activity activity = activityRepository.findById(careerId).get();
                if(!activity.getMemberId().equals(requestMember.getId())){
                    throw new OwnerMismatchException();
                }
                return getResponse(activity, ActivityResponse::new);
            }
            case "circle"  -> {
                Circle circle = circleRepository.findById(careerId).get();
                if(!circle.getMemberId().equals(requestMember.getId())){
                    throw new OwnerMismatchException();
                }
                return getResponse(circle, CircleResponse::new);
            }
            case "project"  -> {
                Project project = projectRepository.findById(careerId).get();
                if(!project.getMemberId().equals(requestMember.getId())){
                    throw new OwnerMismatchException();
                }
                return getResponse(project, ProjectResponse::new);
            }
            case "edu"  -> {
                EduCareer eduCareer = eduCareerRepository.findById(careerId).get();
                if(!eduCareer.getMemberId().equals(requestMember.getId())){
                    throw new OwnerMismatchException();
                }
                return getResponse(eduCareer, EduCareerResponse::new);
            }
            case "competition"  -> {
                Competition competition = competitionRepository.findById(careerId).get();
                if(!competition.getMemberId().equals(requestMember.getId())){
                    throw new OwnerMismatchException();
                }
                return getResponse(competition, CompetitionResponse::new);
            }
            case "employment"  -> {
                Employment employment = employmentRepository.findById(careerId).get();
                if(!employment.getMemberId().equals(requestMember.getId())){
                    throw new OwnerMismatchException();
                }
                return getResponse(employment, EmploymentResponse::new);
            }
            case "etc"  -> {
                CareerEtc etc = etcRepository.findById(careerId).get();
                if(!etc.getMemberId().equals(requestMember.getId())){
                    throw new OwnerMismatchException();
                }
                return getResponse(etc, EtcResponse::new);
            }
            default -> throw new IllegalArgumentException("지원하지 않는 활동 유형입니다 : " + type);
        }
    }

    @Override
    public List<FindTagResponse> findAllTag(Member requestMember, String keyword) {
        List<Tag> tags = tagRepository.findByKeywordAndMemberId(keyword, requestMember.getId());
        return tags.stream().map(FindTagResponse::new).collect(Collectors.toList());
    }
    @Override
    public List<FindDetailResponse> findAllDetail(Member requestMember, String keyword, String sort) {
        List<BaseCareerDetail> detailList = detailRepository.findByMemberIdAndKeyword(requestMember.getId(), keyword);
        return buildDetailResponse(detailList, sort);
    }

    @Override
    public List<FindDetailResponse> findAllDetailByTag(Member requestMember, Long tagId, String sort) {
        Tag tag = tagRepository.findById(tagId).get();
        if (!tag.getMemberId().equals(requestMember.getId())) {
            throw new OwnerMismatchException();
        }
        List<BaseCareerDetail> detailList = detailRepository.findByTag(tag.getId());
        return buildDetailResponse(detailList, sort);
    }

    @Override
    public List<FindCareerResponse> findCareerWithKeyword(Member requestMember, String keyword, String sort) {
        List<BaseCareer> careers = new ArrayList<>();
        careers.addAll(activityRepository.findByMemberIdAndNameContaining(requestMember.getId(),keyword));
        careers.addAll(eduCareerRepository.findByMemberIdAndNameContaining(requestMember.getId(),keyword));
        careers.addAll(employmentRepository.findByMemberIdAndNameContaining(requestMember.getId(),keyword));
        careers.addAll(circleRepository.findByMemberIdAndNameContaining(requestMember.getId(),keyword));
        careers.addAll(projectRepository.findByMemberIdAndNameContaining(requestMember.getId(),keyword));
        careers.addAll(competitionRepository.findByMemberIdAndNameContaining(requestMember.getId(),keyword));
        careers.addAll(etcRepository.findByMemberIdAndNameContaining(requestMember.getId(),keyword));

        if ("new".equalsIgnoreCase(sort)) {
            careers.sort(Comparator.comparing(BaseCareer::getEnddate).reversed());
        } else {
            careers.sort(Comparator.comparing(BaseCareer::getEnddate));
        }

        return careers.stream().limit(2)
                .map(career -> new FindCareerResponse(career, career.getClass().getSimpleName()))
                .collect(Collectors.toList());

    }




    //타임라인 쪽에서 데이터 구별 하기 위함
    private CareerType resolveCareerType(BaseCareer career) {
        if (career instanceof Activity) {
            return CareerType.ACTIVITY;
        } else if (career instanceof Project) {
            return CareerType.PROJECT;
        } else if (career instanceof Employment) {
            return CareerType.EMP;
        } else if (career instanceof EduCareer) {
            return CareerType.EDU;
        } else if (career instanceof Circle) {
            return CareerType.CIRCLE;
        } else if (career instanceof Competition) {
            return CareerType.COM;
        } else {
            return CareerType.ETC;
        }
    }
    //활동 기록 상세조회에서 사용
    private <T extends BaseCareer, R extends BaseCareerResponse> R getResponse(T career,  BiFunction<T, List<BaseCareerDetail>, R> responseConstructor) {
        List<BaseCareerDetail> details;
        if (career instanceof Activity) {
            details = Optional.ofNullable(detailRepository.findByActivity((Activity) career))
                    .orElseGet(Collections::emptyList);
        } else if (career instanceof Circle) {
            details = Optional.ofNullable(detailRepository.findByCircle((Circle) career))
                    .orElseGet(Collections::emptyList);
        } else if (career instanceof Competition) {
            details = Optional.ofNullable(detailRepository.findByCompetition((Competition) career))
                    .orElseGet(Collections::emptyList);
        } else if (career instanceof EduCareer) {
            details =Optional.ofNullable(detailRepository.findByEduCareer((EduCareer) career))
                    .orElseGet(Collections::emptyList);
        } else if (career instanceof Employment) {
            details = Optional.ofNullable( detailRepository.findByEmployment((Employment) career))
                    .orElseGet(Collections::emptyList);
        } else if (career instanceof Project) {
            details = Optional.ofNullable( detailRepository.findByProject((Project) career))
                    .orElseGet(Collections::emptyList);
        } else if (career instanceof CareerEtc) {
            details = Optional.ofNullable( detailRepository.findByEtc((CareerEtc) career))
                    .orElseGet(Collections::emptyList);
        }
        else {
            throw new IllegalArgumentException("지원하지 않는 타입입니다.");
        }
        return responseConstructor.apply(career,details);
    }
    private List<FindDetailResponse> buildDetailResponse(List<BaseCareerDetail> detailList, String sort) {
        Map<String, Map<Long, List<BaseCareerDetail>>> groupedDetails = new HashMap<>();
        for (BaseCareerDetail detail : detailList) {
            String careerType = getCareerType(detail);
            Long careerId = getCareerId(detail);
            groupedDetails
                    .computeIfAbsent(careerType, k -> new HashMap<>())
                    .computeIfAbsent(careerId, k -> new ArrayList<>())
                    .add(detail);
        }

        List<FindDetailResponse> result = new ArrayList<>();

        for(Map.Entry<String, Map<Long, List<BaseCareerDetail>>> entry : groupedDetails.entrySet()) {
            String type = entry.getKey();
            Map<Long, List<BaseCareerDetail>> careerMap = entry.getValue();

            for(Map.Entry<Long,List<BaseCareerDetail>> careerEntry : careerMap.entrySet()){
                Long careerId = careerEntry.getKey();
                List<BaseCareerDetail> details = careerEntry.getValue();
                List<BaseCareerDetailResponse> detailResponses = new ArrayList<>();
                CareerSearchServiceImpl.FindDetailInfo detailInfo = extractDetailInfo(details);

                for (BaseCareerDetail detail: details) {
                    detailResponses.add(new BaseCareerDetailResponse(detail));
                }

                result.add(new FindDetailResponse(careerId,type,detailInfo.title,detailInfo.alias,detailInfo.startDate,detailInfo.endDate,detailResponses));
            }
        }
        if (sort.equals("new")) {
            result.stream().sorted(Comparator.comparing(FindDetailResponse::getEndDate).reversed());
        } else {
            result.stream().sorted(Comparator.comparing(FindDetailResponse::getEndDate).reversed());
        }
        return result;

    }

    private String getCareerType(BaseCareerDetail detail) {
        switch (detail.getCareerType()) {
            case ACTIVITY: return CareerType.ACTIVITY.getDescription();
            case PROJECT: return CareerType.PROJECT.getDescription();
            case EMP: return CareerType.EMP.getDescription();
            case EDU: return CareerType.EDU.getDescription();
            case COM: return CareerType.COM.getDescription();
            case CIRCLE: return CareerType.CIRCLE.getDescription();
            case ETC:return CareerType.ETC.getDescription();
            default: return null;
        }
    }
    private Long getCareerId(BaseCareerDetail detail) {
        switch (detail.getCareerType()) {
            case ACTIVITY: return detail.getActivity().getId();
            case PROJECT: return detail.getProject().getId();
            case EMP: return detail.getEmployment().getId();
            case EDU: return detail.getEduCareer().getId();
            case COM: return detail.getCompetition().getId();
            case CIRCLE: return detail.getCircle().getId();
            case ETC: return detail.getEtc().getId();
            default: return null;
        }
    }
    private CareerSearchServiceImpl.FindDetailInfo extractDetailInfo(List<BaseCareerDetail> details) {
        if (details.isEmpty()) {
            return new CareerSearchServiceImpl.FindDetailInfo(null, null, null, null);
        }

        BaseCareerDetail firstDetail = details.get(0);
        String title = null;
        String alias = null;
        LocalDate startDate = null;
        LocalDate endDate = null;

        switch (firstDetail.getCareerType()) {
            case ACTIVITY -> {
                title = firstDetail.getActivity().getName();
                alias = firstDetail.getActivity().getAlias();
            }
            case PROJECT -> {
                title = firstDetail.getProject().getName();
                alias = firstDetail.getProject().getAlias();
            }
            case EMP -> {
                title = firstDetail.getEmployment().getName();
                alias = firstDetail.getEmployment().getAlias();
            }
            case EDU -> {
                title = firstDetail.getEduCareer().getName();
                alias = firstDetail.getEduCareer().getAlias();
            }
            case COM -> {
                title = firstDetail.getCompetition().getName();
                alias = firstDetail.getCompetition().getAlias();
            }
            case CIRCLE -> {
                title = firstDetail.getCircle().getName();
                alias = firstDetail.getCircle().getAlias();
            }
            case ETC -> {
                title = firstDetail.getEtc().getName();
                alias = firstDetail.getEtc().getAlias();
            }
        }

        startDate = firstDetail.getStartDate();
        endDate = firstDetail.getEndDate();

        return new CareerSearchServiceImpl.FindDetailInfo(title, alias, startDate, endDate);
    }
    private static class FindDetailInfo {
        String title;
        String alias;
        LocalDate startDate;
        LocalDate endDate;

        FindDetailInfo(String title, String alias, LocalDate startDate, LocalDate endDate) {
            this.title = title;
            this.alias = alias;
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }

}
