package umc.kkijuk.server.career.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.kkijuk.server.career.controller.response.*;
import umc.kkijuk.server.career.domain.*;
import umc.kkijuk.server.career.dto.*;
import umc.kkijuk.server.career.dto.converter.BaseCareerConverter;
import umc.kkijuk.server.career.repository.*;
import umc.kkijuk.server.common.domian.exception.OwnerMismatchException;
import umc.kkijuk.server.common.domian.exception.ResourceNotFoundException;
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
@Transactional(readOnly = true)
public class BaseCareerServiceImpl implements BaseCareerService{
    private final ActivityRepository activityRepository;
    private final CircleRepository circleRepository;
    private final CompetitionRepository competitionRepository;
    private final EduCareerRepository eduCareerRepository;
    private final ProjectRepository projectRepository;
    private final EmploymentRepository employmentRepository;
    private final BaseCareerDetailRepository detailRepository;
    private final TagRepository tagRepository;

    @Override
    @Transactional
    public ActivityResponse createActivity(Member requestMember, ActivityReqDto activityReqDto) {
        Activity activity = BaseCareerConverter.toAvtivity(requestMember, activityReqDto);
        setCommonFields(activity);
        return new ActivityResponse(activityRepository.save(activity));

    }
    @Override
    @Transactional
    public CircleResponse createCircle(Member requestMember, CircleReqDto circleReqDto) {
        Circle circle = BaseCareerConverter.toCircle(requestMember, circleReqDto);
        setCommonFields(circle);
        return new CircleResponse(circleRepository.save(circle));
    }

    @Override
    @Transactional
    public CompetitionResponse createCompetition(Member requestMember, CompetitionReqDto competitionReqDto) {
        Competition competition = BaseCareerConverter.toCompetition(requestMember, competitionReqDto);
        setCommonFields(competition);
        return new CompetitionResponse(competitionRepository.save(competition));

    }

    @Override
    @Transactional
    public EduCareerResponse crateEduCareer(Member requestMember, EduCareerReqDto eduCareerReqDto) {
        EduCareer edu = BaseCareerConverter.toEduCareer(requestMember,eduCareerReqDto);
        setCommonFields(edu);
        return new EduCareerResponse(eduCareerRepository.save(edu));
    }

    @Override
    @Transactional
    public EmploymentResponse createEmployment(Member requestMember, EmploymentReqDto employmentReqDto) {
        Employment emp = BaseCareerConverter.toEmployment(requestMember, employmentReqDto);
        setCommonFields(emp);
        return new EmploymentResponse(employmentRepository.save(emp));
    }

    @Override
    @Transactional
    public ProjectResponse createProject(Member requestMember, ProjectReqDto projectReqDto) {
        Project project = BaseCareerConverter.toProject(requestMember, projectReqDto);
        setCommonFields(project);
        return new ProjectResponse(projectRepository.save(project));
    }

    @Override
    @Transactional
    public void deleteBaseCareer(Member requestMember, Long careerId, String type) {
        switch (type.toLowerCase()) {
            case "activity" -> deleteActivity(requestMember, careerId);
            case "circle" -> deleteCircle(requestMember, careerId);
            case "project" -> deleteProject(requestMember, careerId);
            case "edu" -> deleteEdu(requestMember, careerId);
            case "competition" -> deleteComp(requestMember, careerId);
            case "employment" -> deleteEmp(requestMember, careerId);
            default -> throw new IllegalArgumentException("지원하지 않는 활동 유형입니다 : " + type);
        }
    }

    @Override
    @Transactional
    public void deleteActivity(Member requestMember, Long activityId) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(
                () -> new ResourceNotFoundException("Activity",activityId)
        );
        if(!activity.getMemberId().equals(requestMember.getId())){
            throw new OwnerMismatchException();
        }
        activityRepository.delete(activity);
    }

    @Override
    @Transactional
    public void deleteCircle(Member requestMember, Long circleId) {
        Circle circle = circleRepository.findById(circleId).orElseThrow(
                () -> new ResourceNotFoundException("Circle",circleId)
        );
        if(!circle.getMemberId().equals(requestMember.getId())){
            throw new OwnerMismatchException();
        }
        circleRepository.delete(circle);
    }

    @Override
    @Transactional
    public void deleteComp(Member requestMember, Long competitionId) {
        Competition comp = competitionRepository.findById(competitionId).orElseThrow(
                () -> new ResourceNotFoundException("Competition",competitionId)
        );
        if(!comp.getMemberId().equals(requestMember.getId())){
            throw new OwnerMismatchException();
        }
        competitionRepository.delete(comp);
    }

    @Override
    @Transactional
    public void deleteEdu(Member requestMember, Long educareerId) {
        EduCareer eduCareer = eduCareerRepository.findById(educareerId).orElseThrow(
                () -> new ResourceNotFoundException("EduCareer",educareerId)
        );
        if(!eduCareer.getMemberId().equals(requestMember.getId())){
            throw new OwnerMismatchException();
        }
        eduCareerRepository.delete(eduCareer);
    }

    @Override
    @Transactional
    public void deleteEmp(Member requestMember, Long employmentId) {
        Employment employment = employmentRepository.findById(employmentId).orElseThrow(
                () -> new ResourceNotFoundException("Employment",employmentId)
        );
        if(!employment.getMemberId().equals(requestMember.getId())){
            throw new OwnerMismatchException();
        }
        employmentRepository.delete(employment);
    }

    @Override
    @Transactional
    public void deleteProject(Member requestMember, Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new ResourceNotFoundException("Project",projectId)
        );
        if(!project.getMemberId().equals(requestMember.getId())){
            throw new OwnerMismatchException();
        }
        projectRepository.delete(project);
    }
    @Override
    @Transactional
    public ActivityResponse updateActivity(Member requestMember, Long activityId, ActivityReqDto request){
        Activity updateActivity = activityRepository.findById(activityId).orElseThrow(
                () -> new ResourceNotFoundException("Activity",activityId)
        );
        if(!updateActivity.getMemberId().equals(requestMember.getId())){
            throw new OwnerMismatchException();
        }
        updateActivity.updateActivity(
                request.getName(),
                request.getAlias(),
                request.getUnknown(),
                request.getStartdate(),
                request.getEnddate(),
                request.getOrganizer(),
                request.getRole(),
                request.getTeamSize(),
                request.getContribution(),
                request.getIsTeam()
        );
        return new ActivityResponse(updateActivity);
    }

    @Override
    @Transactional
    public CircleResponse updateCircle(Member requestMember, Long circleId, CircleReqDto request) {
        Circle updateCircle = circleRepository.findById(circleId).orElseThrow(
                () -> new ResourceNotFoundException("Circle",circleId)
        );
        if(!updateCircle.getMemberId().equals(requestMember.getId())){
            throw new OwnerMismatchException();
        }
        updateCircle.updateCircle(
                request.getName(),
                request.getAlias(),
                request.getUnknown(),
                request.getStartdate(),
                request.getEnddate(),
                request.getLocation(),
                request.getRole()
        );
        return new CircleResponse(updateCircle);
    }

    @Override
    @Transactional
    public CompetitionResponse updateComp(Member requestMember, Long competitionId, CompetitionReqDto request) {
        Competition updateComp = competitionRepository.findById(competitionId).orElseThrow(
                () -> new ResourceNotFoundException("Competition",competitionId)
        );
        if(!updateComp.getMemberId().equals(requestMember.getId())){
            throw new OwnerMismatchException();
        }
        updateComp.updateComp(
                request.getName(),
                request.getAlias(),
                request.getUnknown(),
                request.getStartdate(),
                request.getEnddate(),
                request.getOrganizer(),
                request.getTeamSize(),
                request.getContribution(),
                request.getIsTeam()

        );
        return new CompetitionResponse(updateComp);
    }

    @Override
    @Transactional
    public EduCareerResponse updateEdu(Member requestMember, Long educareerId, EduCareerReqDto request) {
        EduCareer updateEduCareer = eduCareerRepository.findById(educareerId).orElseThrow(
                () -> new ResourceNotFoundException("EduCareer",educareerId)
        );
        if(!updateEduCareer.getMemberId().equals(requestMember.getId())){
            throw new OwnerMismatchException();
        }
        updateEduCareer.updateEduCareer(
                request.getName(),
                request.getAlias(),
                request.getUnknown(),
                request.getStartdate(),
                request.getEnddate(),
                request.getOrganizer(),
                request.getTime()
        );
        return new EduCareerResponse(updateEduCareer);
    }

    @Override
    @Transactional
    public EmploymentResponse updateEmp(Member requestMember, Long employmentId, EmploymentReqDto request) {
        Employment updateEmployment = employmentRepository.findById(employmentId).orElseThrow(
                () -> new ResourceNotFoundException("Employment",employmentId)
        );
        if(!updateEmployment.getMemberId().equals(requestMember.getId())){
            throw new OwnerMismatchException();
        }
        updateEmployment.updateEmployment(
                request.getName(),
                request.getAlias(),
                request.getUnknown(),
                request.getStartdate(),
                request.getEnddate(),
                request.getType(),
                request.getPosition(),
                request.getField()
        );
        return new EmploymentResponse(updateEmployment);

    }

    @Override
    @Transactional
    public ProjectResponse updateProject(Member requestMember, Long projectId, ProjectReqDto request) {
        Project updateProject = projectRepository.findById(projectId).orElseThrow(
                () -> new ResourceNotFoundException("Project",projectId)
        );
        if(!updateProject.getMemberId().equals(requestMember.getId())){
            throw new OwnerMismatchException();
        }
        updateProject.updateProject(
                request.getName(),
                request.getAlias(),
                request.getUnknown(),
                request.getStartdate(),
                request.getEnddate(),
                request.getTeamSize(),
                request.getIsTeam(),
                request.getContribution(),
                request.getLocation()

        );
        return new ProjectResponse(updateProject);
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


        Map<String, List<?>> careerList = new HashMap<>();
        careerList.put("대외활동",activities);
        careerList.put("동아리",circles);
        careerList.put("대회",competitions);
        careerList.put("교육",eduCareers);
        careerList.put("인턴",employments);
        careerList.put("프로젝트",projects);
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


        baseCareers.stream().sorted(Comparator.comparing(BaseCareerResponse::getEndDate).reversed());

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
            default -> throw new IllegalArgumentException("지원하지 않는 활동 유형입니다 : " + type);
        }
    }
    @Override
    @Transactional
    public BaseCareerResponse createSummary(Member requestMember, Long careerId, CareerSummaryReqDto request) {
        switch (request.getType()){
            case ACTIVITY -> {
                Activity activity = activityRepository.findById(careerId)
                        .orElseThrow(() -> new ResourceNotFoundException("Activity",careerId));
                if(!activity.getMemberId().equals(requestMember.getId())){
                    throw new OwnerMismatchException();
                }
                activity.setSummary(request.getSummary());
                return getResponse(activity, ActivityResponse::new);
            }
            case PROJECT -> {
                Project project = projectRepository.findById(careerId)
                        .orElseThrow(() -> new ResourceNotFoundException("Project", careerId));
                if(!project.getMemberId().equals(requestMember.getId())){
                    throw new OwnerMismatchException();
                }
                project.setSummary(request.getSummary());
                return getResponse(project, ProjectResponse::new);

            }
            case CIRCLE -> {
                Circle circle = circleRepository.findById(careerId)
                        .orElseThrow(() -> new ResourceNotFoundException("Circle", careerId));
                if(!circle.getMemberId().equals(requestMember.getId())){
                    throw new OwnerMismatchException();
                }
                circle.setSummary(request.getSummary());
                return getResponse(circle, CircleResponse::new);
            }
            case COM -> {
                Competition competition = competitionRepository.findById(careerId)
                        .orElseThrow(() -> new ResourceNotFoundException("Competition",careerId));
                if(!competition.getMemberId().equals(requestMember.getId())){
                    throw new OwnerMismatchException();
                }
                competition.setSummary(request.getSummary());
                return getResponse(competition, CompetitionResponse::new);
            }
            case EDU -> {
                EduCareer eduCareer = eduCareerRepository.findById(careerId)
                        .orElseThrow(() -> new ResourceNotFoundException("EduCareer",careerId));
                if(!eduCareer.getMemberId().equals(requestMember.getId())){
                    throw new OwnerMismatchException();
                }
                eduCareer.setSummary(request.getSummary());
                return getResponse(eduCareer, EduCareerResponse::new);
            }
            case EMP -> {
                Employment employment = employmentRepository.findById(careerId)
                        .orElseThrow(() -> new ResourceNotFoundException("Employment",careerId));
                if(!employment.getMemberId().equals(requestMember.getId())){
                    throw new OwnerMismatchException();
                }
                employment.setSummary(request.getSummary());
                return getResponse(employment,EmploymentResponse::new);
            }
            default -> throw new IllegalArgumentException("지원하지 않는 활동 유형입니다 : " + request.getType());

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

        if ("new".equalsIgnoreCase(sort)) {
            careers.sort(Comparator.comparing(BaseCareer::getEnddate).reversed());
        } else {
            careers.sort(Comparator.comparing(BaseCareer::getEnddate));
        }

        return careers.stream().limit(2)
                .map(career -> new FindCareerResponse(career, career.getClass().getSimpleName()))
                .collect(Collectors.toList());

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
                FindDetailInfo detailInfo = extractDetailInfo(details);

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
            default: return null;
        }
    }
    private FindDetailInfo extractDetailInfo(List<BaseCareerDetail> details) {
        if (details.isEmpty()) {
            return new FindDetailInfo(null, null, null, null);
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
        }

        startDate = firstDetail.getStartDate();
        endDate = firstDetail.getEndDate();

        return new FindDetailInfo(title, alias, startDate, endDate);
    }

    private <T extends BaseCareer, R extends BaseCareerResponse> R getResponse(T career,  BiFunction<T, List<BaseCareerDetail>, R> responseConstructor) {
        List<BaseCareerDetail> details;
        if (career instanceof Activity) {
            details = detailRepository.findByActivity((Activity) career);
        } else if (career instanceof Circle) {
            details = detailRepository.findByCircle((Circle) career);
        } else if (career instanceof Competition) {
            details = detailRepository.findByCompetition((Competition) career);
        } else if (career instanceof EduCareer) {
            details = detailRepository.findByEduCareer((EduCareer) career);
        } else if (career instanceof Employment) {
            details = detailRepository.findByEmployment((Employment) career);
        } else if (career instanceof Project) {
            details = detailRepository.findByProject((Project) career);
        } else {
            throw new IllegalArgumentException("지원하지 않는 타입입니다.");
        }
        return responseConstructor.apply(career,details);
    }

    private <T extends BaseCareer> void setCommonFields(T activity) {
        if (activity.getUnknown()) {
            activity.setEnddate(LocalDate.now());
            activity.setYear(LocalDate.now().getYear());
        } else {
            activity.setYear(activity.getEnddate().getYear());
        }
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
