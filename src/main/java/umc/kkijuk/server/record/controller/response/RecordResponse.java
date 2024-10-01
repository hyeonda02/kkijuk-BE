package umc.kkijuk.server.record.controller.response;

import lombok.*;
import umc.kkijuk.server.career.controller.response.BaseCareerResponse;
import umc.kkijuk.server.career.controller.response.EduCareerResponse;
import umc.kkijuk.server.career.controller.response.EmploymentResponse;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.record.domain.Record;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
public class RecordResponse {
    private Long record_id;
    private String address;
    private String profileImageUrl;
    private String updatedAt;
    private List<EducationResponse> educationList;
    private List<BaseCareerResponse> activitiesAndExperiences;
    private List<EmploymentResponse> employments;
    private List<BaseCareerResponse> projects;
    private List<EduCareerResponse> eduCareers;
    private String name;
    private LocalDate birthday;
    private String phone;
    private String email;

    // 이력서 없을 때
//    @Builder
//    public RecordResponse(Member member,
//                        List<RecordListResponse> activitiesAndExperiences,
//                        List<RecordListResponse> jobs) {
//        this.activitiesAndExperiences=activitiesAndExperiences;
//        this.jobs=jobs;
//        this.name = member.getName();
//        this.birthday=member.getBirthDate();
//        this.phone=member.getPhoneNumber();
//        this.email=member.getEmail();
//    }

    // 이력서 있을 때
//    @Builder
//    public RecordResponse(Record record, Member member, List<EducationResponse> educationList,
//                        List<RecordListResponse> activitiesAndExperiences,
//                        List<RecordListResponse> jobs) {
//        this.record_id=record.getId();
//        this.address = record.getAddress();
//        this.profileImageUrl=record.getProfileImageUrl();
//        this.educationList=educationList;
//        this.activitiesAndExperiences=activitiesAndExperiences;
//        this.jobs=jobs;
//        this.name = member.getName();
//        this.birthday=member.getBirthDate();
//        this.phone=member.getPhoneNumber();
//        this.email=member.getEmail();
//        this.updatedAt = formatUpdatedAt(record.getUpdatedAt());
//    }

    // 이력서 있을 때
    @Builder
    public RecordResponse(Record record, Member member, List<EducationResponse> educationList,
                          List<EmploymentResponse> employments, List<BaseCareerResponse> activitiesAndExperiences,
                          List<BaseCareerResponse> projects, List<EduCareerResponse> eduCareers) {
        this.record_id=record.getId();
        this.address = record.getAddress();
        this.profileImageUrl=record.getProfileImageUrl();
        this.educationList=educationList;
        this.employments = employments;
        this.activitiesAndExperiences = activitiesAndExperiences;
        this.projects = projects;
        this.eduCareers = eduCareers;
        this.name = member.getName();
        this.birthday=member.getBirthDate();
        this.phone=member.getPhoneNumber();
        this.email=member.getEmail();
        this.updatedAt = formatUpdatedAt(record.getUpdatedAt());
    }

    @Builder
    public RecordResponse(Member member,
                          List<EmploymentResponse> employments, List<BaseCareerResponse> activitiesAndExperiences,
                          List<BaseCareerResponse> projects, List<EduCareerResponse> eduCareers) {
        this.name = member.getName();
        this.birthday=member.getBirthDate();
        this.phone=member.getPhoneNumber();
        this.email=member.getEmail();
        this.employments = employments;
        this.activitiesAndExperiences = activitiesAndExperiences;
        this.projects = projects;
        this.eduCareers = eduCareers;
    }

    private String formatUpdatedAt(LocalDateTime updatedAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return updatedAt != null ? updatedAt.format(formatter) : null;
    }
}
