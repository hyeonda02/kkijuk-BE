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
import umc.kkijuk.server.detail.domain.BaseCareerDetail;
import umc.kkijuk.server.detail.repository.BaseCareerDetailRepository;
import umc.kkijuk.server.member.domain.Member;

import java.time.LocalDate;
import java.util.*;
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
                return getActivityResponse(activity);
            }
            case "circle"  -> {
                Circle circle = circleRepository.findById(careerId).get();
                if(!circle.getMemberId().equals(requestMember.getId())){
                    throw new OwnerMismatchException();
                }
                return getCircleResponse(circle);
            }
            case "project"  -> {
                Project project = projectRepository.findById(careerId).get();
                if(!project.getMemberId().equals(requestMember.getId())){
                    throw new OwnerMismatchException();
                }
                return getProjectResponse(project);
            }
            case "edu"  -> {
                EduCareer eduCareer = eduCareerRepository.findById(careerId).get();
                if(!eduCareer.getMemberId().equals(requestMember.getId())){
                    throw new OwnerMismatchException();
                }
                return getEduCareerResponse(eduCareer);
            }
            case "competition"  -> {
                Competition competition = competitionRepository.findById(careerId).get();
                if(!competition.getMemberId().equals(requestMember.getId())){
                    throw new OwnerMismatchException();
                }
                return getCompetitionResponse(competition);
            }
            case "employment"  -> {
                Employment employment = employmentRepository.findById(careerId).get();
                if(!employment.getMemberId().equals(requestMember.getId())){
                    throw new OwnerMismatchException();
                }
                return getEmploymentResponse(employment);
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
                return getActivityResponse(activity);
            }
            case PROJECT -> {
                Project project = projectRepository.findById(careerId)
                        .orElseThrow(() -> new ResourceNotFoundException("Project", careerId));
                if(!project.getMemberId().equals(requestMember.getId())){
                    throw new OwnerMismatchException();
                }
                project.setSummary(request.getSummary());
                return getProjectResponse(project);
            }
            case CIRCLE -> {
                Circle circle = circleRepository.findById(careerId)
                        .orElseThrow(() -> new ResourceNotFoundException("Circle", careerId));
                if(!circle.getMemberId().equals(requestMember.getId())){
                    throw new OwnerMismatchException();
                }
                circle.setSummary(request.getSummary());
                return getCircleResponse(circle);
            }
            case COM -> {
                Competition competition = competitionRepository.findById(careerId)
                        .orElseThrow(() -> new ResourceNotFoundException("Competition",careerId));
                if(!competition.getMemberId().equals(requestMember.getId())){
                    throw new OwnerMismatchException();
                }
                competition.setSummary(request.getSummary());
                return getCompetitionResponse(competition);
            }
            case EDU -> {
                EduCareer eduCareer = eduCareerRepository.findById(careerId)
                        .orElseThrow(() -> new ResourceNotFoundException("EduCareer",careerId));
                if(!eduCareer.getMemberId().equals(requestMember.getId())){
                    throw new OwnerMismatchException();
                }
                eduCareer.setSummary(request.getSummary());
                return getEduCareerResponse(eduCareer);
            }
            case EMP -> {
                Employment employment = employmentRepository.findById(careerId)
                        .orElseThrow(() -> new ResourceNotFoundException("Employment",careerId));
                if(!employment.getMemberId().equals(requestMember.getId())){
                    throw new OwnerMismatchException();
                }
                employment.setSummary(request.getSummary());
                return getEmploymentResponse(employment);
            }
            default -> throw new IllegalArgumentException("지원하지 않는 활동 유형입니다 : " + request.getType());

        }
    }



    private BaseCareerResponse getCompetitionResponse(Competition competition) {
        List<BaseCareerDetail> details = detailRepository.findByCompetition(competition);
        return new CompetitionResponse(competition, details);
    }

    private BaseCareerResponse getEmploymentResponse(Employment emp) {
        List<BaseCareerDetail> details = detailRepository.findByEmployment(emp);
        return new EmploymentResponse(emp, details);
    }

    private BaseCareerResponse getEduCareerResponse(EduCareer edu) {
        List<BaseCareerDetail> details = detailRepository.findByEduCareer(edu);
        return new EduCareerResponse(edu, details);
    }

    private BaseCareerResponse getProjectResponse(Project project) {
        List<BaseCareerDetail> details = detailRepository.findByProject(project);
        return new ProjectResponse(project, details);
    }

    private BaseCareerResponse getCircleResponse(Circle circle) {
        List<BaseCareerDetail> details = detailRepository.findByCircle(circle);
        return new CircleResponse(circle, details);
    }

    private BaseCareerResponse getActivityResponse(Activity activity) {
        List<BaseCareerDetail> details = detailRepository.findByActivity(activity);
        return new ActivityResponse(activity, details);
    }

    private <T extends BaseCareer> void setCommonFields(T activity) {
        if (activity.getUnknown()) {
            activity.setEnddate(LocalDate.now());
            activity.setYear(LocalDate.now().getYear());
        } else {
            activity.setYear(activity.getEnddate().getYear());
        }
    }

}
