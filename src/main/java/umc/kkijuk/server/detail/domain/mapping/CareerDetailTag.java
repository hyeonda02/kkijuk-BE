package umc.kkijuk.server.detail.domain.mapping;

import jakarta.persistence.*;
import lombok.*;
import umc.kkijuk.server.detail.domain.BaseCareerDetail;
import umc.kkijuk.server.tag.domain.Tag;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CareerDetailTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="basecareer_detail_id")
    private BaseCareerDetail baseCareerDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id" )
    private Tag tag;

    public void setBaseCareerDetail(BaseCareerDetail baseCareerDetail) {
        if (this.baseCareerDetail != null) {
            this.baseCareerDetail.getCareerTagList().remove(this);
        }
        this.baseCareerDetail = baseCareerDetail;
        if (baseCareerDetail != null) {
            baseCareerDetail.getCareerTagList().add(this);
        }
    }

}
