package umc.kkijuk.server.career.domain;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@DiscriminatorValue("competition")
@PrimaryKeyJoinColumn(name="competition_id")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Competition extends BaseCareer{
    private String organizer;
    private int teamSize;
    private int contribution;
    private Boolean isTeam;
    @Override
    public void setSummary(String summary) {
        super.setSummary(summary);
    }

    @Builder
    public Competition(Long memberId, String name, String alias,
                       Boolean unknown, LocalDate startdate,
                       LocalDate enddate, String organizer, int teamSize,
                       int contribution, Boolean isTeam) {
        super(memberId, name, alias, unknown, startdate, enddate);
        this.organizer = organizer;
        this.teamSize = teamSize;
        this.contribution = contribution;
        this.isTeam = isTeam;
    }

    public void updateComp(String name, String alias, Boolean unknown,
                           LocalDate startdate, LocalDate enddate,
                           String organizer, int teamSize, int contribution, Boolean isTeam) {
        this.updateBaseCareer(name, alias, unknown, startdate, enddate);
        this.organizer = organizer;
        this.teamSize = teamSize;
        this.contribution = contribution;
        this.isTeam = isTeam;
    }
}
