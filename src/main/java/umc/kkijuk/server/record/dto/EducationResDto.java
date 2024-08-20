package umc.kkijuk.server.record.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import umc.kkijuk.server.record.domain.Education;

import java.time.LocalDateTime;
import java.time.YearMonth;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class EducationResDto {
    private Long educationId;
    private String category;
    private String schoolName;
    private String major;
    private String state;
    private YearMonth admissionDate;
    private YearMonth graduationDate;
    private Boolean isCurrent;

    @Builder
    public EducationResDto(Education education) {
        this.educationId = education.getId();
        this.category = education.getCategory();
        this.schoolName = education.getSchoolName();
        this.major = education.getMajor();
        this.state = education.getState();
        this.admissionDate = education.getAdmissionDate();
        this.graduationDate = education.getGraduationDate();
        this.isCurrent=determineIsCurrent(graduationDate);
    }

    private static Boolean determineIsCurrent(YearMonth graduationDate) {
        return graduationDate.isAfter(YearMonth.now());
    }
}
