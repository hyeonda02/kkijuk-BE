package umc.kkijuk.server.careerdetail.domain.mapping;


import jakarta.persistence.*;
import lombok.*;
import umc.kkijuk.server.careerdetail.domain.CareerDetail;
import umc.kkijuk.server.tag.domain.Tag;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CareerTag {
    @Id @Column(name="career_detail_tag")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="career_detail_id")
    private CareerDetail careerDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="tag_id")
    private Tag tag;

    public void setCareerDetail(CareerDetail careerDetail) {
        if (this.careerDetail != null) {
            careerDetail.getCareerTagList().remove(this);
        }
        this.careerDetail = careerDetail;
        careerDetail.getCareerTagList().add(this);
    }




}
