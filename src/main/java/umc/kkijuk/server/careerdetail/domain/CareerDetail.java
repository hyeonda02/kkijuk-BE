package umc.kkijuk.server.careerdetail.domain;

import jakarta.persistence.*;
import lombok.*;
import umc.kkijuk.server.career.domain.Career;
import umc.kkijuk.server.career.domain.base.BaseEntity;
import umc.kkijuk.server.careerdetail.domain.mapping.CareerTag;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CareerDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="career_detail_id")
    private Long id;
    @Column(name="career_detail_title", length = 30)
    private String title;
    @Column(name="career_detail_content", length = 800)
    private String content;
    @Column(name="career_detail_startdate")
    private LocalDate startDate;
    @Column(name="career_detail_enddate")
    private LocalDate endDate;

    @Column(nullable = false)
    private Long memberId;


    @OneToMany(mappedBy = "careerDetail", cascade = CascadeType.ALL)
    private List<CareerTag> careerTagList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="career_id")
    private Career career;

    public void setCareer(Career career) {
        this.career = career;
    }

}
