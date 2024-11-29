package umc.kkijuk.server.record.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.record.domain.Record;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Builder
@AllArgsConstructor
public class RecordDownResponse {
    private String address;
    private String profileImageUrl;

    private List<EducationResponse> educationList;

    private List<ResumeResponse> activitiesAndExperiences;
    private List<ResumeResponse> employments;
    private List<ResumeResponse> projects;
    private List<ResumeResponse> eduCareers;

    private List<AwardResponse> awards;
    private List<LicenseResponse> licenses;
    private List<SkillResponse> skills;
    private List<FileResponse> files;

    private String name;
    private LocalDate birthday;
    private String phone;
    private String email;

    public RecordDownResponse(Record record, Member member,
                              List<EducationResponse> educationList, List<ResumeResponse> employments, List<ResumeResponse> activitiesAndExperiences,
                              List<ResumeResponse> projectsAndComp, List<ResumeResponse> eduCareers, List<AwardResponse> awards,
                              List<LicenseResponse> licenses, List<SkillResponse> skills, List<FileResponse> files) {

        this.address = record.getAddress();
        this.profileImageUrl=record.getProfileImageUrl();

        this.educationList=educationList;
        this.employments = employments;
        this.activitiesAndExperiences = activitiesAndExperiences;
        this.projects = projectsAndComp;
        this.eduCareers = eduCareers;

        this.awards = awards;
        this.licenses = licenses;
        this.skills = skills;
        this.files = files;

        this.name = member.getName();
        this.birthday=member.getBirthDate();
        this.phone=member.getPhoneNumber();
        this.email=member.getEmail();

    }
}
