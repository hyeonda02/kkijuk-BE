package umc.kkijuk.server.introduce.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import umc.kkijuk.server.career.domain.base.BaseEntity;

@Entity
@Table(name="question")
@Getter
@Setter
@NoArgsConstructor
public class Question extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "introduce_id", nullable = false)
    private Introduce introduce;

    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    private int number;

    @Builder
    public Question(String title, String content, int number) {
        this.title = title;
        this.content = content;
        this.number = number;
    }

    public void setIntroduce(Introduce introduce) {
        this.introduce = introduce;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }


}
