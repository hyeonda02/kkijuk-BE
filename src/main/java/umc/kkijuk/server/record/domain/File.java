package umc.kkijuk.server.record.domain;

import jakarta.persistence.*;
import lombok.*;
import umc.kkijuk.server.common.domian.base.BaseEntity;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long memberId;
    @Column(length = 20)
    private String fileTitle;
    private String keyName;
    private String urlTitle;
    private String url;
}