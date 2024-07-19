package umc.kkijuk.server.recruit.infrastructure;

import jakarta.persistence.*;
import umc.kkijuk.server.common.converter.StringListToStringConverter;
import umc.kkijuk.server.recruit.domain.Recruit;
import umc.kkijuk.server.recruit.domain.RecruitStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "recruit")
public class RecruitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RecruitStatus status;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    private LocalDate applyDate;

    @Convert(converter = StringListToStringConverter.class)
    private List<String> tags;

    private String link;
    private Boolean isActive;
    private LocalDateTime disabledTime;

    public static RecruitEntity from(Recruit recruit) {
        RecruitEntity recruitEntity = new RecruitEntity();
        recruitEntity.id = recruit.getId();
        recruitEntity.title = recruit.getTitle();
        recruitEntity.status = recruit.getStatus();
        recruitEntity.startTime = recruit.getStartTime();
        recruitEntity.endTime = recruit.getEndTime();
        recruitEntity.applyDate = recruit.getApplyDate();
        recruitEntity.tags = recruit.getTags();
        recruitEntity.link= recruit.getLink();
        recruitEntity.isActive = recruit.getIsActive();
        recruitEntity.disabledTime = recruit.getDisabledTime();
        return recruitEntity;
    }

    public Recruit toModel() {
        return Recruit.builder()
                .id(id)
                .title(title)
                .status(status)
                .startTime(startTime)
                .endTime(endTime)
                .applyDate(applyDate)
                .tags(tags)
                .link(link)
                .isActive(isActive)
                .disabledTime(disabledTime)
                .build();
    }
}
