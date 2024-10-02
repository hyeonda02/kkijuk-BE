package umc.kkijuk.server.career.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@DiscriminatorValue("activity")
@PrimaryKeyJoinColumn(name="activity_id")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Activity extends BaseCareer {
    private String organizer;
    private String role;
    private int teamSize;
    private int contribution;
    private Boolean isTeam;
    @Builder
    public Activity(Long memberId, String name, String alias, Boolean unknown,
                    String summary, LocalDate startdate,
                    LocalDate enddate, String organizer,
                    String role, int teamSize, int contribution,
                    Boolean isTeam) {
        super(memberId, name, alias, unknown, summary, startdate, enddate);
        this.organizer = organizer;
        this.role = role;
        this.teamSize = teamSize;
        this.contribution = contribution;
        this.isTeam = isTeam;
    }
    public void updateActivity(String name, String alias, Boolean unknown, String summary, LocalDate startdate,
                               LocalDate enddate, String organizer, String role, int teamSize,
                               int contribution, Boolean isTeam) {

        this.updateBaseCareer(name, alias, unknown, summary, startdate, enddate);
        this.organizer = organizer;
        this.role = role;
        this.teamSize = teamSize;
        this.contribution = contribution;
        this.isTeam = isTeam;
    }

}


