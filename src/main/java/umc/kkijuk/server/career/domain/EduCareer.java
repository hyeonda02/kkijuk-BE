package umc.kkijuk.server.career.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@DiscriminatorValue("edu")
@PrimaryKeyJoinColumn(name="edu_id")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EduCareer extends BaseCareer {
    private String organizer;
    private int time;

    @Builder
    public EduCareer(Long memberId, String name, String alias, Boolean unknown,
                     String summary, LocalDate startdate, LocalDate enddate,
                     String organizer, int time) {
        super(memberId, name, alias, unknown, summary, startdate, enddate);
        this.organizer = organizer;
        this.time = time;
    }

    public void updateEduCareer(String name, String alias, Boolean unknown,
                                String summary, LocalDate startdate, LocalDate enddate,
                                String organizer, int time) {
        this.updateBaseCareer(name, alias, unknown, summary, startdate, enddate);
        this.organizer = organizer;
        this.time = time;
    }
}
