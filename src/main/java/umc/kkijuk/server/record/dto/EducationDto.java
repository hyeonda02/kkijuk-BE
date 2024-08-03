package umc.kkijuk.server.record.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import umc.kkijuk.server.introduce.domain.Question;
import umc.kkijuk.server.record.domain.Education;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class EducationDto {
    private String category;
    private String schoolName;
    private String major;
    private String state;
    private LocalDateTime admissionDate;
    private LocalDateTime graduationDate;

    public EducationDto(Education education) {
        this.category = education.getCategory();
        this.schoolName = education.getSchoolName();
        this.major = education.getMajor();
        this.state = education.getState();
        this.admissionDate = education.getAdmissionDate();
        this.graduationDate = education.getGraduationDate();
    }
}
