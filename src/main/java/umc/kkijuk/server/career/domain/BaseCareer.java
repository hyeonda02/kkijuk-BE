package umc.kkijuk.server.career.domain;

import jakarta.persistence.*;
import lombok.*;
import umc.kkijuk.server.common.domian.base.BaseEntity;

import java.time.LocalDate;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "career_type")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseCareer extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long memberId;
    @Column(length = 20)
    private String name;
    @Column(length = 20)
    private String alias;
    private Boolean unknown;
    @Column(length = 50)
    private String summary;
    private int year;
    private LocalDate startdate;
    private LocalDate enddate;

//    @OneToMany(mappedBy = "career", cascade = CascadeType.ALL)
//    private List<CareerDetail> careerDetailList = new ArrayList<>();

    public BaseCareer(Long memberId, String name, String alias, Boolean unknown,
                      String summary, LocalDate startdate, LocalDate enddate) {
        this.memberId = memberId;
        this.name = name;
        this.alias = alias;
        this.unknown = unknown;
        this.summary = summary;
        this.startdate = startdate;
        this.enddate = enddate;
    }

    public void updateBaseCareer(String name, String alias, Boolean unknown,
                                 String summary, LocalDate startdate, LocalDate enddate) {
        this.name = name;
        this.alias = alias;
        this.unknown = unknown;
        this.summary = summary;
        this.startdate = startdate;
        this.enddate = enddate;
    }
    public void setEnddate(LocalDate enddate) {
        this.enddate = enddate;
    }

    public void setYear(int year) {
        this.year = year;
    }
}