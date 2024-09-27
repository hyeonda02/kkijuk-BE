package umc.kkijuk.server.career.domain;

import jakarta.persistence.*;
import lombok.*;
import umc.kkijuk.server.common.domian.base.BaseEntity;
import umc.kkijuk.server.careerdetail.domain.CareerDetail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name="career_unknown")
    private Boolean unknown;

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


    @OneToMany(mappedBy = "career", cascade = CascadeType.ALL)
    private List<CareerDetail> careerDetailList = new ArrayList<>();


    @Column(nullable = false)
    private Long memberId;

    public void setName(String name) {
        this.name = name;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }
    public void setUnknown(Boolean unknown) {
        this.unknown = unknown;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public void setStartdate(LocalDate startdate) {
        this.startdate = startdate;
    }
    public void setEnddate(LocalDate enddate) {
        this.enddate = enddate;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public void setYear(int dayOfYear) {
        this.year = dayOfYear;
    }
}
