package umc.kkijuk.server.record.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.kkijuk.server.career.domain.base.BaseEntity;
import umc.kkijuk.server.introduce.domain.Introduce;

import java.time.LocalDateTime;

@Entity
@Table(name="education")
@Getter
@NoArgsConstructor
public class Education extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "record_id", nullable = false)
    private Record record;

    private String category;
    private String schoolName;
    private String major;
    private String state;
    private LocalDateTime admissionDate;
    private LocalDateTime graduationDate;

    @Builder
    public Education(String category, String schoolName, String major
    , String state, LocalDateTime admissionDate, LocalDateTime graduationDate) {
        this.category = category;
        this.schoolName = schoolName;
        this.major = major;
        this.state = state;
        this.admissionDate = admissionDate;
        this.graduationDate = graduationDate;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public void update(String category, String schoolName, String major
            , String state, LocalDateTime admissionDate, LocalDateTime graduationDate) {
        this.category = category;
        this.schoolName = schoolName;
        this.major = major;
        this.state = state;
        this.admissionDate = admissionDate;
        this.graduationDate = graduationDate;
    }
}
