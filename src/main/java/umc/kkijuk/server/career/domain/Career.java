package umc.kkijuk.server.career.domain;

import jakarta.persistence.*;
import lombok.*;
import umc.kkijuk.server.career.domain.base.BaseEntity;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Career extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="career_id")
    private Long id;

    @Column(name="career_name", length = 20)
    private String name;

    @Column(name="career_alias", length = 20)
    private String alias;

    @Column(name="career_current")
    private Boolean current;

    @Column(name="career_summary",length = 50)
    private String summary;

    @Column(name="career_year")
    private int year;

    @Column(name="career_startdate")
    private LocalDate startdate;

    @Column(name="career_enddate")
    private LocalDate enddate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public void setCategory(Category category) {
        this.category = category;
    }
    public void setYear(int dayOfYear) {
        this.year = dayOfYear;
    }
}
