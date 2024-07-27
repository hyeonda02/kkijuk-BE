package umc.kkijuk.server.introduce.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="question")
@Getter
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "introduce_id", nullable = false)
    private Introduce introduce;

    private String title;
    private String content;

    @Builder
    public Question(String title, String content) {
        this.introduce = introduce;
        this.title = title;
        this.content = content;
    }

    public void setIntroduce(Introduce introduce) {
        this.introduce = introduce;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }


}
