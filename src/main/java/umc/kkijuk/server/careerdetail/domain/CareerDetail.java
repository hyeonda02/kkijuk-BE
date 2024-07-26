package umc.kkijuk.server.careerdetail.domain;

import jakarta.persistence.*;
import lombok.*;
import umc.kkijuk.server.career.domain.base.BaseEntity;

import java.time.LocalDate;

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
    @Column(name="career_detail_name", length = 30)
    private String name;
    @Column(name="career_detail_content", length = 800)
    private String content;
    @Column(name="career_detail_date")
    private LocalDate date;
}
