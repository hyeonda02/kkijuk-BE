package umc.kkijuk.server.record.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import umc.kkijuk.server.introduce.domain.Introduce;
import umc.kkijuk.server.introduce.dto.QuestionDto;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.record.domain.Record;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class RecordResDto {
    private String address;
    private String profileImageUrl;
    private String updatedAt;
    private List<RecordListResDto> activitiesAndExperiences;
    private List<RecordListResDto> jobs;
    private String name;
    private LocalDate birthday;
    private String phone;
    private String email;

    // 이력서 없을 때
    @Builder
    public RecordResDto(Member member,
                        List<RecordListResDto> activitiesAndExperiences,
                        List<RecordListResDto> jobs) {
        this.activitiesAndExperiences=activitiesAndExperiences;
        this.jobs=jobs;
        this.name = member.getName();
        this.birthday=member.getBirthDate();
        this.phone=member.getPhoneNumber();
        this.email=member.getEmail();
    }

    // 이력서 있을 때
    @Builder
    public RecordResDto(Record record, Member member,
                        List<RecordListResDto> activitiesAndExperiences,
                        List<RecordListResDto> jobs) {
        this.address = record.getAddress();
        this.profileImageUrl=record.getProfileImageUrl();
        this.activitiesAndExperiences=activitiesAndExperiences;
        this.jobs=jobs;
        this.name = member.getName();
        this.birthday=member.getBirthDate();
        this.phone=member.getPhoneNumber();
        this.email=member.getEmail();
        this.updatedAt = formatUpdatedAt(record.getUpdatedAt());
    }

    private String formatUpdatedAt(LocalDateTime updatedAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return updatedAt != null ? updatedAt.format(formatter) : null;
    }
}
