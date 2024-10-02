package umc.kkijuk.server.career.dto.converter;

import umc.kkijuk.server.career.domain.*;
import umc.kkijuk.server.career.dto.*;
import umc.kkijuk.server.member.domain.Member;

public class BaseCareerConverter {
    public static EduCareer toEduCareer(Member requestMember, EduCareerReqDto eduCareerReqDto) {
        return EduCareer.builder()
                .memberId(requestMember.getId())
                .name(eduCareerReqDto.getName())
                .alias(eduCareerReqDto.getAlias())
                .unknown(eduCareerReqDto.getUnknown())
                .summary(eduCareerReqDto.getSummary())
                .startdate(eduCareerReqDto.getStartdate())
                .enddate(eduCareerReqDto.getEnddate())
                .organizer(eduCareerReqDto.getOrganizer())
                .time(eduCareerReqDto.getTime())
                .build();

    }
    public static Employment toEmployment(Member requestMember, EmploymentReqDto employmentReqDto) {
        return Employment.builder()
                .memberId(requestMember.getId())
                .name(employmentReqDto.getName())
                .alias(employmentReqDto.getAlias())
                .unknown(employmentReqDto.getUnknown())
                .summary(employmentReqDto.getSummary())
                .startdate(employmentReqDto.getStartdate())
                .enddate(employmentReqDto.getEnddate())
                .type(employmentReqDto.getType())
                .workplace(employmentReqDto.getWorkplace())
                .position(employmentReqDto.getPosition())
                .field(employmentReqDto.getField())
                .build();
    }
    public static Project toProject(Member requestMember, ProjectReqDto projectReqDto) {
        return Project.builder()
                .memberId(requestMember.getId())
                .name(projectReqDto.getName())
                .alias(projectReqDto.getAlias())
                .unknown(projectReqDto.getUnknown())
                .summary(projectReqDto.getSummary())
                .startdate(projectReqDto.getStartdate())
                .enddate(projectReqDto.getEnddate())
                .teamSize(projectReqDto.getTeamSize())
                .isTeam(projectReqDto.getIsTeam())
                .contribution(projectReqDto.getContribution())
                .location(projectReqDto.getLocation()).build();

    }

    public static Competition toCompetition(Member requestMember, CompetitionReqDto competitionReqDto) {
        return Competition.builder()
                .memberId(requestMember.getId())
                .name(competitionReqDto.getName())
                .alias(competitionReqDto.getAlias())
                .unknown(competitionReqDto.getUnknown())
                .summary(competitionReqDto.getSummary())
                .startdate(competitionReqDto.getStartdate())
                .enddate(competitionReqDto.getEnddate())
                .organizer(competitionReqDto.getOrganizer())
                .teamSize(competitionReqDto.getTeamSize())
                .contribution(competitionReqDto.getContribution())
                .isTeam(competitionReqDto.getIsTeam()).build();

    }

    public static Activity toAvtivity(Member requestMember, ActivityReqDto activityReqDto) {
        return Activity.builder()
                .memberId(requestMember.getId())
                .name(activityReqDto.getName())
                .alias(activityReqDto.getAlias())
                .unknown(activityReqDto.getUnknown())
                .summary(activityReqDto.getSummary())
                .startdate(activityReqDto.getStartdate())
                .enddate(activityReqDto.getEnddate())
                .organizer(activityReqDto.getOrganizer())
                .role(activityReqDto.getRole())
                .contribution(activityReqDto.getContribution())
                .teamSize(activityReqDto.getTeamSize())
                .isTeam(activityReqDto.getIsTeam())
                .build();
    }

    public static Circle toCircle(Member requestMember, CircleReqDto circleReqDto) {
        return Circle.builder()
                .memberId(requestMember.getId())
                .name(circleReqDto.getName())
                .alias(circleReqDto.getAlias())
                .unknown(circleReqDto.getUnknown())
                .summary(circleReqDto.getSummary())
                .startdate(circleReqDto.getStartdate())
                .enddate(circleReqDto.getEnddate())
                .location(circleReqDto.getLocation())
                .role(circleReqDto.getRole()).build();
    }

}
