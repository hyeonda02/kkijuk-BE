package umc.kkijuk.server.introduce.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="master_question")
@Getter
@Setter
@NoArgsConstructor
public class MasterQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "masterIntroduce_id", nullable = false)
    private MasterIntroduce masterIntroduce;

    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    private int number;

    @Builder
    public MasterQuestion(String title, String content, int number) {
        this.title = title;
        this.content = content;
        this.number = number;
    }
    public void setMasterIntroduce(MasterIntroduce masterIntroduce) {
        this.masterIntroduce = masterIntroduce;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
