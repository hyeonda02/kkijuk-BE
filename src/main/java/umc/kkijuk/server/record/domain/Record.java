package umc.kkijuk.server.record.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.kkijuk.server.career.domain.Career;
import umc.kkijuk.server.career.domain.base.BaseEntity;
import umc.kkijuk.server.introduce.domain.Question;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.recruit.infrastructure.RecruitEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="record")
@Getter
@NoArgsConstructor
public class Record extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    @NotNull
    private Member member;

    private String address;

    private String profileImageUrl;

    @Builder
    public Record(Member member, String address, String profileImageUrl) {
        this.member = member;
        this.address = address;
        this.profileImageUrl = profileImageUrl;
    }

    public void update(String address, String profileImageUrl) {
        this.address = address;
        this.profileImageUrl = profileImageUrl;
    }
}
