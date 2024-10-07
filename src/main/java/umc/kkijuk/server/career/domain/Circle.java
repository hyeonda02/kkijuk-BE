package umc.kkijuk.server.career.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@DiscriminatorValue("circle")
@PrimaryKeyJoinColumn(name="circle_id")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Circle extends BaseCareer{
    private Boolean location;
    private String role;
    @Override
    public void setSummary(String summary) {
        super.setSummary(summary);
    }

    @Builder
    public Circle(Long memberId, String name, String alias,
                  Boolean unknown,LocalDate startdate,
                  LocalDate enddate, Boolean location, String role) {
        super(memberId, name, alias, unknown, startdate, enddate);
        this.location = location;
        this.role = role;
    }

    public void updateCircle(String name, String alias, Boolean unknown,
                             LocalDate startdate, LocalDate enddate,
                             Boolean location, String role) {
        this.updateBaseCareer(name, alias, unknown, startdate, enddate);
        this.location = location;
        this.role = role;
    }
}

