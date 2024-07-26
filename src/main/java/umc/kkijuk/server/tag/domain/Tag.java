package umc.kkijuk.server.tag.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="hashtag_id")
    private Long id;
    @Column(name="hashtag_name", length = 30) //일단 length 30 ( 최대 길이 여쭤보고 변경해야 함. )
    private String name;

}
