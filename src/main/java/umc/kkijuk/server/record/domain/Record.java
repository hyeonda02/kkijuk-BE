package umc.kkijuk.server.record.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.kkijuk.server.career.domain.base.BaseEntity;

@Entity
@Table(name="record")
@Getter
@NoArgsConstructor
public class Record extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
