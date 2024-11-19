package umc.kkijuk.server.record.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.kkijuk.server.common.domian.base.BaseEntity;
import umc.kkijuk.server.common.domian.exception.ResourceNotFoundException;

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

    @Column(nullable = false)
    private Long memberId;

    private String address;
    private String profileImageUrl;

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Education> educations = new ArrayList<>();

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<License> licenses = new ArrayList<>();

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Award> awards = new ArrayList<>();

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skill> skills = new ArrayList<>();

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> files = new ArrayList<>();

    @Builder
    public Record(Long memberId, String address, String profileImageUrl, List<Education> educations) {
        this.memberId = memberId;
        this.address = address;
        this.profileImageUrl = profileImageUrl;
        this.educations = educations;
    }

    public void update(String address, String profileImageUrl) {
        this.address = address;
        this.profileImageUrl = profileImageUrl;
    }
}
