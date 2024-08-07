package umc.kkijuk.server.record.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.kkijuk.server.career.domain.base.BaseEntity;
import java.util.List;

@Entity
@Table(name="record")
@Getter
@NoArgsConstructor
public class Record extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    private String address;
    private String profileImageUrl;

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Education> educations;

    @Builder
    public Record(Long memberId, String address, String profileImageUrl, List<Education> educations) {
        this.memberId = memberId;
        this.address = address;
        this.profileImageUrl = profileImageUrl;
        this.educations = educations;
        if (educations != null) {
            setEducations(educations);
        }
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
        for (Education education : educations) {
            education.setRecord(this);
        }
    }

    public void update(String address, String profileImageUrl) {
        this.address = address;
        this.profileImageUrl = profileImageUrl;
    }
}
