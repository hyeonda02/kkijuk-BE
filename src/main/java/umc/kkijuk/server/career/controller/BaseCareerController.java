package umc.kkijuk.server.career.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.kkijuk.server.career.controller.response.*;
import umc.kkijuk.server.career.dto.*;
import umc.kkijuk.server.career.service.BaseCareerService;
import umc.kkijuk.server.login.argumentresolver.Login;
import umc.kkijuk.server.login.controller.dto.LoginInfo;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.service.MemberService;


@Tag(name="basecareer",description = "활동 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/basecareer")
public class BaseCareerController {
    private final BaseCareerService baseCareerService;
    private final MemberService memberService;

    @PostMapping("/activity")
    @Operation(summary = "커리어(대외활동) 생성", description = "주어진 정보를 바탕으로 활동을 추가합니다.")
    public CareerResponse<ActivityResponse> createActivity(
            @Login LoginInfo loginInfo,
            @RequestBody @Valid ActivityReqDto activityReqDto
            ) {
        Member requestMember = memberService.getById(loginInfo.getMemberId());

        return CareerResponse.success(
                CareerResponseMessage.CAREER_CREATE_SUCCESS,
                baseCareerService.createActivity(requestMember, activityReqDto)
        );

    }

    @PatchMapping("/activity/{activityId}")
    @Operation(summary = "커리어(대외활동) 수정", description = "활동 ID에 해당하는 활동을 수정합니다..")
    @Parameter(name="activityId", description = "커리어(대외활동) Id, path variable 입니다.",example = "1")
    public CareerResponse<ActivityResponse> updateActivity(
            @Login LoginInfo loginInfo,
            @PathVariable Long activityId,
            @Valid @RequestBody ActivityReqDto activityReqDto
    ) {
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        return CareerResponse.success(
                CareerResponseMessage.CAREER_UPDATE_SUCCESS,
                baseCareerService.updateActivity(requestMember,activityId,activityReqDto)
        );
    }
    @PostMapping("/circle")
    @Operation(summary = "커리어(동아리) 생성", description = "주어진 정보를 바탕으로 활동을 추가합니다.")
    public CareerResponse<CircleResponse> createCircle(
            @Login LoginInfo loginInfo,
            @Valid @RequestBody CircleReqDto circleReqDto
    ) {
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        return CareerResponse.success(
                CareerResponseMessage.CAREER_CREATE_SUCCESS,
                baseCareerService.createCircle(requestMember, circleReqDto)
        );
    }

    @PatchMapping("/circle/{circleId}")
    @Operation(summary = "커리어(동아리) 수정", description = "활동 ID에 해당하는 활동을 수정합니다..")
    @Parameter(name="circleId", description = "커리어(동아리) Id, path variable 입니다.",example = "1")
    public CareerResponse<CircleResponse> updateCircle(
            @Login LoginInfo loginInfo,
            @PathVariable Long circleId,
            @Valid @RequestBody CircleReqDto circleReqDto
    ) {
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        return CareerResponse.success(
                CareerResponseMessage.CAREER_UPDATE_SUCCESS,
                baseCareerService.updateCircle(requestMember,circleId,circleReqDto)
        );
    }
    @PostMapping("/competition")
    @Operation(summary = "커리어(대회) 생성", description = "주어진 정보를 바탕으로 활동을 추가합니다.")
    public CareerResponse<CompetitionResponse> createComp(
            @Login LoginInfo loginInfo,
            @Valid @RequestBody CompetitionReqDto competitionReqDto
    ) {
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        return CareerResponse.success(
                CareerResponseMessage.CAREER_CREATE_SUCCESS,
                baseCareerService.createCompetition(requestMember,competitionReqDto)
        );
    }

    @PatchMapping("/competition/{competitionId}")
    @Operation(summary = "커리어(대회) 수정", description = "활동 ID에 해당하는 활동을 수정합니다..")
    @Parameter(name="competitionId", description = "커리어(대회) Id, path variable 입니다.",example = "1")
    public CareerResponse<CompetitionResponse> updateComp(
            @Login LoginInfo loginInfo,
            @PathVariable Long competitionId,
            @Valid @RequestBody CompetitionReqDto competitionReqDto
    ) {
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        return CareerResponse.success(
                CareerResponseMessage.CAREER_UPDATE_SUCCESS,
                baseCareerService.updateComp(requestMember,competitionId,competitionReqDto)
        );
    }
    @PostMapping("/educareer")
    @Operation(summary = "커리어(교육) 생성", description = "주어진 정보를 바탕으로 활동을 추가합니다.")
    public CareerResponse<EduCareerResponse> createEdu(
            @Login LoginInfo loginInfo,
            @Valid @RequestBody EduCareerReqDto eduCareerReqDto
    ) {
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        return CareerResponse.success(
                CareerResponseMessage.CAREER_CREATE_SUCCESS,
                baseCareerService.crateEduCareer(requestMember,eduCareerReqDto)
        );
    }

    @PatchMapping("/educareer/{educareerId}")
    @Operation(summary = "커리어(교육) 수정", description = "활동 ID에 해당하는 활동을 수정합니다..")
    @Parameter(name="educareerId", description = "커리어(교육) Id, path variable 입니다.",example = "1")
    public CareerResponse<EduCareerResponse> updateEdu(
            @Login LoginInfo loginInfo,
            @PathVariable Long educareerId,
            @Valid @RequestBody EduCareerReqDto eduCareerReqDto
    ) {
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        return CareerResponse.success(
                CareerResponseMessage.CAREER_UPDATE_SUCCESS,
                baseCareerService.updateEdu(requestMember,educareerId,eduCareerReqDto)
        );
    }

    @PostMapping("/employment")
    @Operation(summary = "커리어(아르바이트/인턴) 생성", description = "주어진 정보를 바탕으로 활동을 추가합니다.")
    public CareerResponse<EmploymentResponse> createEmp(
            @Login LoginInfo loginInfo,
            @Valid @RequestBody EmploymentReqDto employmentReqDto
    ) {
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        return CareerResponse.success(
                CareerResponseMessage.CAREER_CREATE_SUCCESS,
                baseCareerService.createEmployment(requestMember,employmentReqDto)
        );
    }
    @PatchMapping("/employment/{employmentId}")
    @Operation(summary = "커리어(아르바이트/인턴) 수정", description = "활동 ID에 해당하는 활동을 수정합니다..")
    @Parameter(name="employmentId", description = "커리어(아르바이트/인턴) Id, path variable 입니다.",example = "1")
    public CareerResponse<EmploymentResponse> updateEmp(
            @Login LoginInfo loginInfo,
            @PathVariable Long employmentId,
            @Valid @RequestBody EmploymentReqDto employmentReqDto
    ) {
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        return CareerResponse.success(
                CareerResponseMessage.CAREER_UPDATE_SUCCESS,
                baseCareerService.updateEmp(requestMember, employmentId, employmentReqDto)
        );
    }

    @PostMapping("/project")
    @Operation(summary = "커리어(프로젝트) 생성", description = "주어진 정보를 바탕으로 활동을 추가합니다.")
    public CareerResponse<ProjectResponse> createProject(
            @Login LoginInfo loginInfo,
            @Valid @RequestBody ProjectReqDto projectReqDto
    ) {
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        return CareerResponse.success(
                CareerResponseMessage.CAREER_CREATE_SUCCESS,
                baseCareerService.createProject(requestMember, projectReqDto)
        );
    }

    @PatchMapping("/project/{projectId}")
    @Operation(summary = "커리어(프로젝트) 수정", description = "활동 ID에 해당하는 활동을 수정합니다..")
    @Parameter(name="projectId", description = "커리어(프로젝트) Id, path variable 입니다.",example = "1")
    public CareerResponse<ProjectResponse> updateProject(
            @Login LoginInfo loginInfo,
            @PathVariable Long projectId,
            @Valid @RequestBody ProjectReqDto projectReqDto
    ) {
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        return CareerResponse.success(
                CareerResponseMessage.CAREER_UPDATE_SUCCESS,
                baseCareerService.updateProject(requestMember, projectId, projectReqDto)
        );
    }
    @GetMapping("")
    @Operation(
            summary = "활동 목록",
            description = "활동을 카테고리, 연도 별로 조회합니다. query 값으로  category(카테고리 기준)나, year(연도 기준) 값을 주세요. " )
    public CareerResponse<?> findAllCareersGroupedYear(
            @Login LoginInfo loginInfo,
            @RequestParam(name="status") String value
    ) {
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        if(value.equals("category")){
            return CareerResponse.success(
                    CareerResponseMessage.CAREER_FINDALL_SUCCESS,
                    baseCareerService.findAllCareerGroupedCategory(requestMember.getId())
            );
        }
        return CareerResponse.success(
                CareerResponseMessage.CAREER_FINDALL_SUCCESS,
                baseCareerService.findAllCareerGroupedYear(requestMember.getId())
        );
    }

    @DeleteMapping("/{type}/{careerId}")
    @Operation(summary = "커리어 삭제", description = "활동의 type과 ID에 해당하는 활동을 삭제합니다.")
    public CareerResponse<Object> deleteBaseCareer(
            @Login LoginInfo loginInfo,
            @PathVariable String type,
            @PathVariable Long careerId

    ){
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        baseCareerService.deleteBaseCareer(requestMember,careerId, type);
        return CareerResponse.success(
                CareerResponseMessage.CAREER_DELETE_SUCCESS,
                null
        );

    }
    @GetMapping("/{careerId}")
    @Operation(summary = "활동 상세", description = "활동 ID에 해당하는 활동의 세부 내용과, 활동 기록을 조회합니다.")
    @Parameter(name = "careerId", description = "활동 Id, path variable 입니다.", example = "1")
    public CareerResponse<Object> findCareer(
            @Login LoginInfo loginInfo,
            @PathVariable Long careerId
    ){
        Member requestMember = memberService.getById(loginInfo.getMemberId());
        return CareerResponse.success(
          CareerResponseMessage.CAREER_FINDALL_SUCCESS, baseCareerService.findCareer(requestMember, careerId)
        );
    }



}
