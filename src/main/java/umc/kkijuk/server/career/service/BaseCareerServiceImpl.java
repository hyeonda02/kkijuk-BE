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
    private final BaseCareerRepository baseCareerRepository;
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
                request.getWorkplace(),
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
        List<BaseCareer> baseCareers = baseCareerRepository.findByMemberId(memberId);

        List<ActivityResponse> activities = new ArrayList<>();
        List<CircleResponse> circles = new ArrayList<>();
        List<CompetitionResponse> competitions = new ArrayList<>();
        List<EduCareerResponse> eduCareers = new ArrayList<>();
        List<EmploymentResponse> employments = new ArrayList<>();
        List<ProjectResponse> projects = new ArrayList<>();

        for (BaseCareer baseCareer : baseCareers) {
            if(baseCareer instanceof Activity){
                activities.add(new ActivityResponse((Activity) baseCareer));
            } else if(baseCareer instanceof Circle){
                circles.add(new CircleResponse((Circle) baseCareer));
            } else if(baseCareer instanceof Competition){
                competitions.add(new CompetitionResponse((Competition) baseCareer));
            } else if(baseCareer instanceof EduCareer){
                eduCareers.add(new EduCareerResponse((EduCareer) baseCareer));
            }else if(baseCareer instanceof Employment){
                employments.add(new EmploymentResponse((Employment) baseCareer));
            } else {
                projects.add(new ProjectResponse((Project) baseCareer));
            }
        }

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
        List<BaseCareer> baseCareers = baseCareerRepository.findByMemberId(memberId);

        Map<String, List<Object>> groupedCareers = baseCareers.stream()
                .collect(Collectors.groupingBy(
                        career -> String.valueOf(career.getYear()),
                        Collectors.mapping(career -> {
                            if (career instanceof Activity) {
                                return new ActivityResponse((Activity) career);
                            } else if (career instanceof Circle) {
                                return new CircleResponse((Circle) career);
                            } else if (career instanceof Competition) {
                                return new CompetitionResponse((Competition) career);
                            } else if (career instanceof EduCareer) {
                                return new EduCareerResponse((EduCareer) career);
                            } else if (career instanceof Employment) {
                                return new EmploymentResponse((Employment) career);
                            } else if (career instanceof Project) {
                                return new ProjectResponse((Project) career);
                            }
                            return null;
                        }, Collectors.toList())
                ));
        Map<String, List<?>> result = new HashMap<>();
        for (Map.Entry<String, List<Object>> entry : groupedCareers.entrySet()) {
            List<?> filteredList = entry.getValue().stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            result.put(entry.getKey(), filteredList);
        }
        return result;
    }
    @Override
    public BaseCareerResponse findCareer(Member requestMember, Long careerId) {
        BaseCareer baseCareer = baseCareerRepository.findById(careerId)
                .orElseThrow(() -> new ResourceNotFoundException("BaseCareer", careerId));
        if(!baseCareer.getMemberId().equals(requestMember.getId())){
            throw new OwnerMismatchException();
        }

        if(baseCareer instanceof Activity){
            return getActivityResponse((Activity) baseCareer);
        }else if(baseCareer instanceof Circle){
            return getCircleResponse((Circle) baseCareer);
        }else if (baseCareer instanceof Project){
            return getProjectResponse((Project) baseCareer);
        }else if (baseCareer instanceof EduCareer){
            return getEduCareerResponse((EduCareer) baseCareer);
        }else if (baseCareer instanceof Employment){
            return getEmploymentResponse((Employment) baseCareer);
        } else if (baseCareer instanceof Competition){
            return getCompetitionResponse((Competition) baseCareer);
        } else {
            throw new IllegalArgumentException("지원하지 않는 활동 유형입니다.");
        }

    }
    @Override
    @Transactional
    public BaseCareerResponse createSummary(Member requestMember, Long careerId, CareerSummaryReqDto request) {
        BaseCareer baseCareer = baseCareerRepository.findById(careerId)
                .orElseThrow(() -> new ResourceNotFoundException("BaseCareer", careerId));
        if(!baseCareer.getMemberId().equals(requestMember.getId())){
            throw new OwnerMismatchException();
        }
        baseCareer.setSummary(request.getSummary());

        if(baseCareer instanceof Activity){
            return getActivityResponse((Activity) baseCareer);
        }else if(baseCareer instanceof Circle){
            return getCircleResponse((Circle) baseCareer);
        }else if (baseCareer instanceof Project){
            return getProjectResponse((Project) baseCareer);
        }else if (baseCareer instanceof EduCareer){
            return getEduCareerResponse((EduCareer) baseCareer);
        }else if (baseCareer instanceof Employment){
            return getEmploymentResponse((Employment) baseCareer);
        } else if (baseCareer instanceof Competition){
            return getCompetitionResponse((Competition) baseCareer);
        } else {
            throw new IllegalArgumentException("지원하지 않는 활동 유형입니다.");
        }
    }



    private BaseCareerResponse getCompetitionResponse(Competition competition) {
        List<BaseCareerDetail> details = detailRepository.findByBaseCareer(competition);
        return new CompetitionResponse(competition, details);
    }

    private BaseCareerResponse getEmploymentResponse(Employment emp) {
        List<BaseCareerDetail> details = detailRepository.findByBaseCareer(emp);
        return new EmploymentResponse(emp, details);
    }

    private BaseCareerResponse getEduCareerResponse(EduCareer edu) {
        List<BaseCareerDetail> details = detailRepository.findByBaseCareer(edu);
        return new EduCareerResponse(edu, details);
    }

    private BaseCareerResponse getProjectResponse(Project project) {
        List<BaseCareerDetail> details = detailRepository.findByBaseCareer(project);
        return new ProjectResponse(project, details);
    }

    private BaseCareerResponse getCircleResponse(Circle circle) {
        List<BaseCareerDetail> details = detailRepository.findByBaseCareer(circle);
        return new CircleResponse(circle, details);
    }

    private BaseCareerResponse getActivityResponse(Activity activity) {
        List<BaseCareerDetail> details = detailRepository.findByBaseCareer(activity);
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
