package umc.kkijuk.server.tag.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import umc.kkijuk.server.careerdetail.domain.mapping.CareerTag;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="tag_id")
    private Long id;
    @NotNull
    @Column(name="tag_name", length = 30)
    private String name;
    @Column(nullable = false)
    private Long memberId;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private List<CareerTag> careerTagList = new ArrayList<>();

}
